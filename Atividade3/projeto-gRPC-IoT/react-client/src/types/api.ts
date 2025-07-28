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

export interface RealtimeSensorData {
  sensor_id: string;
  temperatura: number;
  umidade: number;
  timestamp: string;
}

export interface NormalizedSensorData {
  temperatura: number;
  umidade: number;
  timestamp: string | null;
  mensagem?: string;
  sucesso: boolean;
}

export function normalizeSensorData(data: RealtimeSensorData | SensorDataResponse): NormalizedSensorData {
  if ('temperatura' in data) {
    return {
      temperatura: data.temperatura,
      umidade: data.umidade,
      timestamp: data.timestamp,
      sucesso: true,
    };
  }
  return {
    temperatura: data.temperatura_encontrada,
    umidade: data.umidade_encontrada,
    timestamp: data.timestamp_encontrado,
    mensagem: data.mensagem,
    sucesso: data.sucesso,
  };
}
