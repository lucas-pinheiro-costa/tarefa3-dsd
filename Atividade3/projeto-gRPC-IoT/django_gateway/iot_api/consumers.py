import asyncio
import json
import grpc
from channels.generic.websocket import AsyncWebsocketConsumer
from google.protobuf.json_format import MessageToJson # Para converter Protobuf para JSON

# Importa as classes gRPC geradas
from .grpc_client import contrato_pb2
from .grpc_client import contrato_pb2_grpc

# Endereço do seu microserviço Gerador de Dados gRPC (Python)
GRPC_GENERATOR_ADDRESS = '127.0.0.1:50052' # Porta do novo microserviço Python

class RealtimeSensorConsumer(AsyncWebsocketConsumer):
    """
    Consumer WebSocket para transmitir dados de sensor em tempo real.
    Atua como um cliente gRPC de streaming para o Gerador de Dados Python.
    """
    grpc_channel = None
    grpc_stub = None
    grpc_stream_task = None # Tarefa para consumir o stream gRPC em segundo plano

    async def connect(self):
        await self.accept()
        print(f"🔌 WebSocket Conectado: {self.channel_name}")

        # Inicia a conexão gRPC com o Gerador de Dados Python
        self.grpc_channel = grpc.aio.insecure_channel(GRPC_GENERATOR_ADDRESS)
        self.grpc_stub = contrato_pb2_grpc.MonitorServiceStub(self.grpc_channel)

        # Inicia a tarefa de consumir o stream gRPC em segundo plano
        self.grpc_stream_task = asyncio.create_task(self.consume_grpc_stream())

    async def disconnect(self, close_code):
        print(f"❌ WebSocket Desconectado: {self.channel_name} (Código: {close_code})")
        
        # Cancela a tarefa de streaming gRPC
        if self.grpc_stream_task:
            self.grpc_stream_task.cancel()
            try:
                await self.grpc_stream_task # Espera a tarefa ser cancelada
            except asyncio.CancelledError:
                pass # Esperado

        # Fecha o canal gRPC
        if self.grpc_channel:
            await self.grpc_channel.close()
            print("Canal gRPC com Gerador Python fechado.")

    async def receive(self, text_data):
        # Neste cenário, o cliente React não envia dados, apenas recebe.
        pass

    async def consume_grpc_stream(self):
        """
        Consome o stream de dados em tempo real do servidor gRPC Python
        e retransmite via WebSocket para o cliente React.
        """
        try:
            grpc_request = contrato_pb2.StreamRequest() # Requisição vazia para iniciar o stream
            
            # Itera sobre o stream de respostas do gRPC
            async for realtime_data_pb in self.grpc_stub.StreamRealtimeSensorData(grpc_request):
                # Converte a mensagem Protobuf para um dicionário Python
                # MessageToJson converte o timestamp para string ISO 8601
                json_data = json.loads(MessageToJson(realtime_data_pb, preserving_proto_field_name=True,
                                                     use_integers_for_enums=False,
                                                     always_print_fields_with_no_presence=True))
                
                # Envia os dados JSON para o cliente WebSocket
                await self.send(text_data=json.dumps(json_data))
                # print(f"Dados retransmitidos para WebSocket: {json_data['sensor_id']}")

        except grpc.RpcError as e:
            print(f"Erro no stream gRPC do Gerador Python: {e.details}")
            await self.send(text_data=json.dumps({"error": f"Erro no fluxo de dados do Gerador: {e.details}"}))
            await self.close() # Fecha a conexão WebSocket em caso de erro no stream gRPC
        except asyncio.CancelledError:
            print("Tarefa de stream gRPC cancelada pelo Django.")
        except Exception as e:
            print(f"Erro inesperado ao consumir stream gRPC do Gerador Python: {e}")
            await self.send(text_data=json.dumps({"error": f"Erro interno no fluxo de dados do Gerador: {e}"}))
            await self.close()
