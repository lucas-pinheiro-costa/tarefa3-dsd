import React, { useState } from 'react';
import NewSensorForm from './NewSensorForm';
import { useParams } from 'react-router-dom'; 

interface NewSensorButtonProps {
  onSensorRegistered: () => void;
}

const NewSensorButton: React.FC<NewSensorButtonProps> = ({ onSensorRegistered }) => {
  const { userId } = useParams<{ userId: string }>(); 
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleSensorFormSuccess = () => {
    handleCloseModal();
    onSensorRegistered();
  };

  return (
    <div>
      <button
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mb-4"
        onClick={handleOpenModal}
      >
        Registrar Novo Sensor
      </button>

      {isModalOpen && (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-lg shadow-xl w-full max-w-md mx-auto">
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-xl font-bold">Registrar Novo Sensor</h2>
              <button
                className="text-gray-500 hover:text-gray-800 text-2xl"
                onClick={handleCloseModal}
              >
                &times;
              </button>
            </div>
            <NewSensorForm
              userId={Number(userId)} 
              onSuccess={handleSensorFormSuccess}
              onClose={handleCloseModal}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default NewSensorButton;