import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserByEmail, registerUser } from '../api/api';
import type { UserRegistrationRequest, UserRegistrationResponse, UserResponse } from '../types/api';


const EmailForm: React.FC = () => {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [showRegistrationForm, setShowRegistrationForm] = useState(false); 
  const [loading, setLoading] = useState(false); 
  const navigate = useNavigate();

  const [newUserName, setNewUserName] = useState('');

  const handleEmailSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    setShowRegistrationForm(false); 

    try {
      const response: UserResponse = await getUserByEmail(email);
      if (response.sucesso && response.usuario_id) {
        navigate(`/sensors/${response.usuario_id}`);
      } else {
        setError('Usuário não encontrado. Deseja registrar-se?');
        setShowRegistrationForm(true); 
      }
    } catch (err) {
      setError('Erro ao buscar usuário. Tente novamente.');
      console.error('Erro ao buscar usuário:', err);
      setShowRegistrationForm(true);
    } finally {
      setLoading(false); 
    }
  };

  const handleRegistrationSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const registrationData: UserRegistrationRequest = {
        email: email,
        nome: newUserName,
      };
      const response: UserRegistrationResponse = await registerUser(registrationData);

      if (response.sucesso && response.usuario_id) {
        navigate(`/sensors/${response.usuario_id}`);
      } else {
        setError(response.mensagem || 'Erro desconhecido ao registrar usuário.');
      }
    } catch (err: unknown) {
      console.error('Erro ao registrar usuário:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h1 className="text-2xl font-bold mb-6 text-center">
          {showRegistrationForm ? 'Registrar Novo Usuário' : 'Consultar Sensores'}
        </h1>
        {!showRegistrationForm && (
          <form onSubmit={handleEmailSubmit} className="space-y-4">
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                Email
              </label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="mt-1 p-2 w-full border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Digite seu email"
                required
                disabled={loading} 
              />
            </div>
            {error && <p className="text-red-500 text-sm">{error}</p>}
            <button
              type="submit"
              className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 transition disabled:opacity-50"
              disabled={loading} 
            >
              {loading ? 'Buscando...' : 'Buscar Sensores'}
            </button>
          </form>
        )}

        {showRegistrationForm && (
          <form onSubmit={handleRegistrationSubmit} className="space-y-4 mt-6">
            <p className="text-gray-700 text-center">
              O email **{email}** não foi encontrado. Por favor, registre-se:
            </p>
            <div>
              <label htmlFor="registerName" className="block text-sm font-medium text-gray-700">
                Nome
              </label>
              <input
                type="text"
                id="registerName"
                value={newUserName}
                onChange={(e) => setNewUserName(e.target.value)}
                className="mt-1 p-2 w-full border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Seu nome completo"
                required
                disabled={loading}
              />
            </div>
            <button
              type="submit"
              className="w-full bg-green-500 text-white p-2 rounded-md hover:bg-green-600 transition disabled:opacity-50"
              disabled={loading}
            >
              {loading ? 'Registrando...' : 'Registrar'}
            </button>
            <button
              type="button"
              onClick={() => setShowRegistrationForm(false)}
              className="w-full bg-gray-300 text-gray-800 p-2 rounded-md hover:bg-gray-400 transition mt-2"
              disabled={loading}
            >
              Voltar
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default EmailForm;