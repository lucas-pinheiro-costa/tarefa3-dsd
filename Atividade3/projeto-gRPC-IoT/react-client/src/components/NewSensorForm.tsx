import React, { useState } from 'react';
import { registerNewSensor } from '../api/api';
import type { SensorRegistrationRequest, SensorRegistrationResponse } from '../types/api';

interface NewSensorFormProps {
  userId: number; 
  onSuccess: () => void;
  onClose: () => void;
}

const NewSensorForm: React.FC<NewSensorFormProps> = ({ userId, onSuccess, onClose }) => { 
  const [nome, setNome] = useState('');
  const [descricao, setDescricao] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    setError('');
    setSuccessMessage('');

    try {
      const sensorData: SensorRegistrationRequest = {
        nome,
        descricao,
        usuario_id: userId, 
      };

      const response: SensorRegistrationResponse = await registerNewSensor(sensorData);

      if (response.sucesso) {
        setSuccessMessage('Sensor registrado com sucesso!');
        onSuccess();
      } else {
        setError(response.mensagem || 'Erro ao registrar sensor. Tente novamente.');
      }
    } catch (err) {
      setError('Erro de comunicação com o servidor. Tente novamente.');
      console.error('Erro ao registrar sensor:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {error && <p className="text-red-500 text-center">{error}</p>}
      {successMessage && <p className="text-green-500 text-center">{successMessage}</p>}

      <div>
        <label htmlFor="nome" className="block text-sm font-medium text-gray-700">
          Nome do Sensor:
        </label>
        <input
          type="text"
          id="nome"
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
      </div>

      <div>
        <label htmlFor="descricao" className="block text-sm font-medium text-gray-700">
          Descrição:
        </label>
        <textarea
          id="descricao"
          rows={3}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          required
        ></textarea>
      </div>

      <div className="flex justify-end space-x-3 mt-6">
        <button
          type="button"
          onClick={onClose}
          className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
          disabled={loading}
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:opacity-50"
          disabled={loading}
        >
          {loading ? 'Registrando...' : 'Registrar'}
        </button>
      </div>
    </form>
  );
};

export default NewSensorForm;