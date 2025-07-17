export interface UserResponse {
  sucesso: boolean;
  usuario_id: number;
  error?: string;
}

export interface Sensor {
  sensor_id: string;
  nome: string;
  descricao: string;
}

export interface SensorListResponse {
  mensagem: string;
  sucesso: boolean;
  sensores: Sensor[];
  error?: string;
}

export interface GenerateDataResponse {
  mensagem: string;
  sucesso: boolean;
  sensor_id: string;
  temperatura: number;
  umidade: number;
  timestamp: string;
}

export interface SensorDataResponse {
  mensagem: string;
  sucesso: boolean;
  sensor_id_encontrado: string;
  temperatura_encontrada: number;
  umidade_encontrada: number;
  timestamp_encontrado: string | null;
  error?: string;
}

export interface SensorRegistrationRequest {
  nome: string;
  descricao: string;
  usuario_id: number;
}

export interface SensorRegistrationResponse {
  sucesso: boolean;
  mensagem?: string;
  sensor_id?: number;
}

export interface UserRegistrationRequest {
  email: string;
  nome: string;
}

export interface UserRegistrationResponse {
  sucesso: boolean;
  mensagem?: string;
  usuario_id?: number;
}
