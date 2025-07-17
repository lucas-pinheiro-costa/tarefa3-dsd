import React, { useState } from 'react';
import { generateSensorData } from '../api/api'; 

interface GenerateSensorDataButtonProps {
  sensorId: number;
  onDataGenerated: (sensorId: number) => void;
}

const GenerateSensorDataButton: React.FC<GenerateSensorDataButtonProps> = ({
  sensorId,
  onDataGenerated,
}) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleClick = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await generateSensorData(sensorId); 

      if (response.sucesso) {
        onDataGenerated(sensorId);
      } else {
        setError(response.mensagem || 'Falha ao gerar dados para o sensor.');
      }
    } catch (err) {
      setError('Erro ao comunicar com a API de geração de dados.');
      console.error('Erro ao gerar dados:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <button
        onClick={handleClick}
        className="mt-4 bg-purple-600 hover:bg-purple-700 text-white font-bold py-1 px-3 rounded text-sm disabled:opacity-50"
        disabled={loading}
      >
        {loading ? 'Gerando...' : 'Gerar Dados'}
      </button>
      {error && <p className="text-red-500 text-xs mt-1">{error}</p>}
    </div>
  );
};

export default GenerateSensorDataButton;