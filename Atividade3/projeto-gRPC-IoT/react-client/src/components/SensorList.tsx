import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { getUserSensors, getLatestSensorData } from '../api/api';
import type { Sensor, SensorDataResponse, RealtimeSensorData } from '../types/api';
import { normalizeSensorData } from '../types/api';
import NewSensorButton from './NewSensorButton';

const websocket_url = 'wss://shiny-journey-69rp9jpgvrwvf5vpx-8000.app.github.dev/ws/sensor-data/';

interface SensorWithData extends Sensor {
  latestData?: SensorDataResponse; 
  realtimeData?: RealtimeSensorData; 
}

const SensorList: React.FC = () => {
  const { userId } = useParams<{ userId: string }>();
  const [sensors, setSensors] = useState<SensorWithData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const ws = React.useRef<WebSocket | null>(null);

  useEffect(() => {
    if (userId) {
      ws.current = new WebSocket(websocket_url);
      ws.current.onmessage = (event) => {
        try {
          const data: RealtimeSensorData = JSON.parse(event.data);
          setSensors(prevSensors =>
            prevSensors.map(s =>
              s.sensor_id === data.sensor_id
                ? { ...s, realtimeData: data }
                : s
            )
          );
        } catch (error) {
          console.error('Erro ao parsear mensagem WebSocket:', error);
        }
      };

      ws.current.onerror = (error) => {
        console.error('Erro no WebSocket:', error);
      };

      ws.current.onclose = () => {
        console.log('WebSocket Desconectado do Django Gateway.');
      };

      return () => {
        if (ws.current && ws.current.readyState === WebSocket.OPEN) {
          ws.current.close();
        }
      };
    }
  }, [userId]);

  const fetchSensorsAndData = useCallback(async () => {
  try {
    setLoading(true);
    const sensorResponse = await getUserSensors(Number(userId));
    if (sensorResponse.sucesso) {
      setSensors(prevSensors => {
        const newSensors = sensorResponse.sensores.map(newSensor => {
          const existingSensor = prevSensors.find(s => s.sensor_id === newSensor.sensor_id); 
          if (existingSensor) {
            return {
              ...newSensor,
              realtimeData: existingSensor.realtimeData,
              latestData: existingSensor.latestData
            };
          }
          return { ...newSensor };
        });
        
        return newSensors;
      });
      
      const newSensorIds = sensorResponse.sensores
        .filter(newSensor => !sensors.some(s => s.sensor_id === newSensor.sensor_id))
        .map(s => s.sensor_id);
        
      if (newSensorIds.length > 0) {
        const dataPromises = newSensorIds.map(sensorId => 
          getLatestSensorData(sensorId)
        );
        
        const dataResponses = await Promise.allSettled(dataPromises);
        
        setSensors(prevSensors => 
          prevSensors.map(sensor => {
            const index = newSensorIds.indexOf(sensor.sensor_id);
            if (index !== -1) {
              const dataResult = dataResponses[index];
              if (dataResult.status === 'fulfilled' && dataResult.value.sucesso) {
                return { ...sensor, latestData: dataResult.value };
              }
            }
            return sensor;
          })
        );
      }
      
      setError('');
    } else {
      setError('Nenhum sensor encontrado');
      setSensors([]);
    }
  } catch (err: any) {
    setError('Erro ao carregar sensores: ' + err.message);
    console.error('Erro ao buscar sensores:', err);
  } finally {
    setLoading(false);
  }
}, [userId]);

  useEffect(() => {
    fetchSensorsAndData();
  }, [userId, fetchSensorsAndData]);

  if (loading && sensors.length === 0) {
    return <div className="min-h-screen flex items-center justify-center">Carregando...</div>;
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-2xl font-bold mb-6 text-center">Sensores do Usuário</h1>
      <div className="flex justify-center mb-6">
        <NewSensorButton onSensorRegistered={fetchSensorsAndData} />
      </div>
      {error && <p className="text-red-500 text-center mb-4">{error}</p>}
      {sensors.length === 0 && !error && (
        <p className="text-center text-gray-600">Nenhum sensor registrado.</p>
      )}
      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {sensors.map((sensor) => (
          <div
            key={sensor.sensor_id}
            className="bg-white p-6 rounded-lg shadow-md"
          >
            <h2 className="text-xl font-semibold mb-2">{sensor.nome}</h2>
            <p className="text-gray-600 mb-4">{sensor.descricao}</p>
            {/* Exibe os dados do sensor */}
            {sensor.latestData?.sucesso ? (
              <div>
                <p>
                  <strong>Temperatura:</strong>{' '}
                  {sensor.latestData.temperatura_encontrada?.toFixed(2)} °C
                </p>
                <p>
                  <strong>Umidade:</strong>{' '}
                  {sensor.latestData.umidade_encontrada?.toFixed(2)}%
                </p>
                <p>
                  <strong>Data:</strong>{' '}
                  {sensor.latestData.timestamp_encontrado
                    ? new Date(sensor.latestData.timestamp_encontrado).toLocaleString()
                    : 'N/A'}
                </p>
              </div>
            ) : (
              <p className="text-red-500">Sem dados recentes</p>
            )}
            <GenerateSensorDataButton
              sensorId={sensor.sensor_id}
              onDataGenerated={handleGenerateDataForSensor} 
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default SensorList;