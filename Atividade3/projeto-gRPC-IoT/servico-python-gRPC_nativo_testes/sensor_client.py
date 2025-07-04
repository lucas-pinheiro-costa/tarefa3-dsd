import grpc
import time
import random
from datetime import datetime
from google.protobuf.timestamp_pb2 import Timestamp

# Importa as classes geradas pelo gRPC
import contrato_pb2
import contrato_pb2_grpc

# Endereço e porta do servidor Java gRPC
SERVER_ADDRESS = 'localhost:50051'

def display_menu():
    """Exibe o menu de opções para o usuário."""
    print("\n--- Menu do Cliente gRPC IoT ---")
    print("1. Registrar Novo Sensor")
    print("2. Enviar Dados de Leitura para um Sensor")
    print("3. Exibir Dados de um Sensor")
    print("4. Listar Sensores")
    print("5. Sair")
    print("-------------------------------")

def register_user(stub,email):
    """
    Função para registrar um novo usuário via RPC RegistrarUsuario.
    """
    print("\n--- Registrar Novo Usuário ---")
    nome = input("Digite o nome do usuário: ")

    request = contrato_pb2.RegistrarUsuarioRequest(email=email, nome=nome)
    
    try:
        response = stub.RegistrarUsuario(request)
        print("\n--- RESPOSTA DO SERVIDOR (Registrar Usuário) ---")
        print(f"Mensagem: {response.mensagem}")
        print(f"Sucesso: {response.sucesso}")
        if response.sucesso:
            print(f"ID do Usuário: {response.usuario_id}")
            #print(f"Por favor, guarde este ID para registrar sensores para este usuário.")
        print("---------------------------------------------")
    except grpc.RpcError as e:
        print(f"Erro ao registrar usuário: {e.details}")
    return response.usuario_id

def register_sensor(stub,usuario_id):
    """
    Função para registrar um novo sensor via RPC RegistrarSensor.
    """
    print("\n--- Registrar Novo Sensor ---")

    nome = input("Digite um nome amigável para o sensor (ex: 'Sensor de Cozinha'): ")
    descricao = input("Digite uma breve descrição para o sensor (opcional): ")

    request = contrato_pb2.RegistrarSensorRequest(
        usuario_id=usuario_id,
        nome=nome,
        descricao=descricao
    )

    try:
        response = stub.RegistrarSensor(request)
        print("\n--- RESPOSTA DO SERVIDOR (Registrar Sensor) ---")
        print(f"Mensagem: {response.mensagem}")
        print(f"Sucesso: {response.sucesso}")
        if response.sucesso:
            print(f"ID do Sensor: {response.sensor_id}")
            #print(f"Por favor, guarde este ID para enviar dados de leitura para este sensor.")
        print("------------------------------------------")
    except grpc.RpcError as e:
        print(f"Erro ao registrar sensor: {e.details}")

def generate_single_sensor_data(sensor_id_param):
    """
    Gera uma única mensagem SensorData com dados aleatórios.
    """
    temperatura = random.uniform(20.0, 35.0)
    umidade = random.uniform(40.0, 60.0)
    
    now = datetime.utcnow()
    ts = Timestamp()
    ts.FromDatetime(now)

    print(f"  -> Gerando dados para Sensor ID '{sensor_id_param}': Temp={temperatura:.2f}°C, Umid={umidade:.2f}%")
    
    return contrato_pb2.SensorData(
        sensor_id=sensor_id_param,
        temperatura=temperatura,
        umidade=umidade,
        timestamp=ts
    )

def send_sensor_data(stub,sensor_id):
    """
    Função para enviar dados de leitura para um sensor via RPC EnviarDadosSensor (unário).
    """
    print("\n--- Enviar Dados de Leitura para um Sensor ---")
    sensor_data_message = generate_single_sensor_data(sensor_id)

    try:
        response = stub.EnviarDadosSensor(sensor_data_message) # Chamada unária
        print("\n--- RESPOSTA DO SERVIDOR (Enviar Dados) ---")
        print("------------------------------------------")
    except grpc.RpcError as e:
        print(f"Erro ao enviar dados do sensor: {e.details}")

def get_userId(stub,email):
    request = contrato_pb2.UserData(email=email)
    
    try:
        response = stub.GetUser(request)
    except grpc.RpcError as e:
        print(f"Erro ao buscar usuário: {e.details}")
    if response.sucesso:
        return response.usuario_id
    else:
        return -1

def get_dados(stub,sensor_id):
    request = contrato_pb2.DadosRequest(sensor_id=sensor_id)
    
    try:
        response = stub.GetDados(request)
        print("\n--- RESPOSTA DO SERVIDOR ---")
        print(f"Mensagem: {response.mensagem}")
        print(f"Sucesso: {response.sucesso}")
        if response.sucesso:
            timestamp_dt = response.timestamp_encontrado.ToDatetime()
            print(f"\n--- Dados da Última Leitura ---")
            print(f"  Temperatura: {response.temperatura_encontrada:.2f}°C")
            print(f"  Umidade: {response.umidade_encontrada:.2f}%")
            print(f"  Data/Hora: {timestamp_dt.strftime('%d/%m/%Y %H:%M:%S')}")
            print("------------------------------------------")
        else:
            print("Não foi possível obter a última leitura.")
    except grpc.RpcError as e:
        print(f"Erro ao buscar dados do sensor: {e.details}")
  
def print_sensores(stub,user_id,opp):
    sensores = get_sensores(stub,user_id)
    while True:
        for i,sensor in enumerate(sensores, start=1):
            print(f"{i}. {sensor.nome}")
        if opp == 4:
            break
        choice = int(input("Informe o número do sensor: "))
        if choice < 1 or choice > len(sensores):
            print("Número inválido")
        else:
            sensor = sensores[choice-1]
            sensor_id = sensor.sensor_id
            if opp == 1:
                send_sensor_data(stub,sensor_id)
            elif opp == 2:
                get_dados(stub,sensor_id)
            break

def get_sensores(stub,user_id):
    request = contrato_pb2.ListarSensoresRequest(usuario_id=user_id)

    try:
        response = stub.ListarSensores(request)
    except grpc.RpcError as e:
        print(f"Erro ao buscar sensores do usuário: {e.details}")
    return response.sensores

def run():
    """
    Função principal que configura e executa o cliente gRPC com menu interativo.
    """
    with grpc.insecure_channel(SERVER_ADDRESS) as channel:
        stub = contrato_pb2_grpc.MonitorServiceStub(channel)
        print(f"Conectado ao servidor gRPC em {SERVER_ADDRESS}.")

        email = input("Informe o email: ")
        user_id = get_userId(stub,email)
        if user_id == -1:
            user_id = register_user(stub,email)
        else:
            print("Usuário encontrado com sucesso!")
        while True:
            display_menu()
            choice = input("Escolha uma opção: ")

            if choice == '1':
                register_sensor(stub,user_id)
            elif choice == '2':
                print_sensores(stub,user_id,1)
            elif choice == '3':
                print_sensores(stub,user_id,2)
            elif choice == '4':
                print_sensores(stub,user_id,4)
            elif choice == '5':
                print("Saindo...")
                break
            else:
                print("Opção inválida. Tente novamente.")
            
            time.sleep(1)

if __name__ == '__main__':
    run()
