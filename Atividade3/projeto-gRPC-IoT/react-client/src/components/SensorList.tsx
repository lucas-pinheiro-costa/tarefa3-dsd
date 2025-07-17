import React, { useEffect, useState, useCallback } from 'react'; // Adicione useCallback
import { useParams } from 'react-router-dom';
import { getUserSensors, getLatestSensorData } from '../api/api';
import type { Sensor, SensorDataResponse } from '../types/api';
import NewSensorButton from './NewSensorButton';
import GenerateSensorDataButton from './GenerateSensorDataButton'; // <--- Importe o novo botão

interface SensorWithData extends Sensor {
  latestData?: SensorDataResponse;
}

const SensorList: React.FC = () => {
  const { userId } = useParams<{ userId: string }>();
  const [sensors, setSensors] = useState<SensorWithData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // Função para buscar TODOS os sensores e seus dados (usada no carregamento inicial e ao registrar novo sensor)
  const fetchSensorsAndData = useCallback(async () => { // Use useCallback para otimização
    try {
      setLoading(true);
      const sensorResponse = await getUserSensors(Number(userId));
      if (sensorResponse.sucesso) {
        const sensorsWithData: SensorWithData[] = sensorResponse.sensores;

        const dataPromises = sensorResponse.sensores.map((sensor) =>
          getLatestSensorData(sensor.sensor_id)
        );
        const dataResponses = await Promise.all(dataPromises);

        const updatedSensors = sensorsWithData.map((sensor, index) => ({
          ...sensor,
          latestData: dataResponses[index],
        }));

        setSensors(updatedSensors);
        setError('');
      } else {
        setError('Nenhum sensor encontrado');
        setSensors([]);
      }
    } catch (err) {
      setError('Erro ao carregar sensores');
      console.error('Erro ao buscar sensores:', err);
    } finally {
      setLoading(false);
    }
  }, [userId]); // Dependência do useCallback

  // NOVA FUNÇÃO: Para atualizar os dados de UM sensor específico
  const handleGenerateDataForSensor = useCallback(async (sensorId: number) => {
    // Definir o estado de carregamento para o sensor específico (opcional, para feedback visual)
    setSensors((prevSensors) =>
      prevSensors.map((s) => (s.sensor_id === sensorId ? { ...s, isLoadingData: true } : s))
    );

    try {
      const latestDataResponse = await getLatestSensorData(sensorId);
      setSensors((prevSensors) =>
        prevSensors.map((s) =>
          s.sensor_id === sensorId
            ? {
                ...s,
                latestData: latestDataResponse,
                isLoadingData: false, // Remove o estado de carregamento
              }
            : s
        )
      );
    } catch (err) {
      console.error(`Erro ao recarregar dados para o sensor ${sensorId}:`, err);
      setSensors((prevSensors) =>
        prevSensors.map((s) =>
          s.sensor_id === sensorId
            ? { ...s, isLoadingData: false, latestData: { ...s.latestData, sucesso: false, mensagem: "Erro ao carregar dados" } as SensorDataResponse } // Adiciona mensagem de erro ao sensor
            : s
        )
      );
    }
  }, []);

  useEffect(() => {
    fetchSensorsAndData();
  }, [userId, fetchSensorsAndData]); // Inclua fetchSensorsAndData como dependência

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