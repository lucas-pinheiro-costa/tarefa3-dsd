import grpc
import time
import random
from datetime import datetime
from concurrent import futures
import asyncio

import contrato_pb2
import contrato_pb2_grpc

endereco_servidorJava = 'localhost:50051'
porta_gerador = 50052          
intervalo_geracao = 10           
intervalo_sensores = 10  

grpc_main_channel = grpc.insecure_channel(endereco_servidorJava)
grpc_main_stub = contrato_pb2_grpc.MonitorServiceStub(grpc_main_channel)

class RealtimeDataServicer(contrato_pb2_grpc.MonitorServiceServicer):
    """
    Implementa o serviço gRPC MonitorService para expor o stream de dados em tempo real.
    """
    def __init__(self):
        self.clients = []
        self.active_sensor_ids = set() 
        self.generation_task = None 
        self.sensor_refresh_task = None
        print(f"✅ Cliente gRPC interno para MonitorService configurado para {endereco_servidorJava}")

    async def StreamRealtimeSensorData(self, request, context):
        """
        Método RPC de Server Streaming.
        Quando um cliente se conecta, este método começa a gerar e enviar dados.
        """
        print(f"🔌 Cliente conectado para stream de dados em tempo real de {context.peer()}")
        
        self.clients.append(context)

        if self.sensor_refresh_task is None or self.sensor_refresh_task.done():
            self.sensor_refresh_task = asyncio.create_task(self._fetch_all_sensors_periodically())
            print("Iniciando tarefa de atualização periódica de sensores.")

        if self.generation_task is None or self.generation_task.done():
            self.generation_task = asyncio.create_task(self._generate_and_broadcast_data())
            print("Iniciando tarefa de geração de dados em segundo plano.")

        try:
            while True:
                await asyncio.sleep(1) 
        except asyncio.CancelledError:
            print(f"Stream para {context.peer()} cancelado.")
        except Exception as e:
            print(f"Erro inesperado no stream para {context.peer()}: {e}")
        finally:
            if context in self.clients:
                self.clients.remove(context)
            print(f"Cliente {context.peer()} removido da lista de streams.")
            
            if not self.clients:
                if self.generation_task and not self.generation_task.done():
                    self.generation_task.cancel()
                    print("Todos os clientes de stream desconectados, tarefa de geração de dados cancelada.")
                if self.sensor_refresh_task and not self.sensor_refresh_task.done():
                    self.sensor_refresh_task.cancel()
                    print("Todos os clientes de stream desconectados, tarefa de atualização de sensores cancelada.")

    async def _fetch_all_sensors_periodically(self):
        """
        Consulta o servidor Java principal periodicamente para obter a lista de todos os sensores.
        """
        while True:
            try:
                print("Consultando todos os sensores do servidor principal...")
                request = contrato_pb2.EmptyRequest()
                loop = asyncio.get_running_loop()
                response = await loop.run_in_executor(None, lambda: grpc_main_stub.ListAllSensors(request))

                if response.sucesso:
                    new_sensor_ids = {sensor.sensor_id for sensor in response.sensores}
                    if new_sensor_ids != self.active_sensor_ids:
                        self.active_sensor_ids = new_sensor_ids
                        print(f"Lista de sensores atualizada. Total: {len(self.active_sensor_ids)}")
                else:
                    print(f"{response.mensagem}")

            except grpc.RpcError as e:
                print(f"Erro gRPC ao consultar todos os sensores: {e.details}")
            except asyncio.CancelledError:
                print("Tarefa de atualização de sensores cancelada.")
                break
            except Exception as e:
                print(f"Erro inesperado na atualização de sensores: {e}")
            
            await asyncio.sleep(intervalo_sensores)

    async def _generate_and_broadcast_data(self):
        """
        Gera dados aleatórios para cada sensor ativo, persiste no servidor principal
        e envia para todos os clientes de streaming.
        """
        while True:
            try:
                if not self.active_sensor_ids:
                    await asyncio.sleep(intervalo_geracao)
                    continue

                for sensor_id in list(self.active_sensor_ids):
                    temperatura = random.uniform(20.0, 35.0)
                    umidade = random.uniform(40.0, 60.0)
                    now = datetime.utcnow()
                    
                    ts = contrato_pb2.google_dot_protobuf_dot_timestamp__pb2.Timestamp()
                    ts.FromDatetime(now)

                    sensor_data_to_persist = contrato_pb2.SensorData(
                        sensor_id=sensor_id,
                        temperatura=temperatura,
                        umidade=umidade,
                        timestamp=ts
                    )
                    try:
                        loop = asyncio.get_running_loop()
                        persist_response = await loop.run_in_executor(None, lambda: grpc_main_stub.EnviarDadosSensor(sensor_data_to_persist))
                        if not persist_response.sucesso:
                            print(f"{sensor_id}: {persist_response.mensagem}")
                    except grpc.RpcError as e:
                        print(f"Erro gRPC ao persistir dados: {e.details}")
                    except Exception as e:
                        print(f"Erro inesperado na persistência para {sensor_id}: {e}")

                    realtime_data = contrato_pb2.RealtimeSensorData(
                        sensor_id=sensor_id,
                        temperatura=temperatura,
                        umidade=umidade,
                        timestamp=ts
                    )
    
                    for client_context in list(self.clients):
                        try:
                            await client_context.write(realtime_data)
                        except grpc.RpcError as e:
                            print(f"Erro ao enviar dados para cliente {client_context.peer()}: {e.details}")
                            if e.code() == grpc.StatusCode.UNAVAILABLE or e.code() == grpc.StatusCode.CANCELLED:
                                if client_context in self.clients:
                                    self.clients.remove(client_context)
                                    print(f"Cliente {client_context.peer()} removido devido a erro de stream.")
                        except Exception as e:
                            print(f"Erro inesperado ao enviar dados para cliente {client_context.peer()}: {e}")
                            if client_context in self.clients:
                                self.clients.remove(client_context)
                
                await asyncio.sleep(intervalo_geracao)

            except asyncio.CancelledError:
                print("Tarefa de geração de dados cancelada.")
                break
            except Exception as e:
                print(f"Erro na tarefa de geração de dados: {e}")
                await asyncio.sleep(intervalo_geracao)

async def serve():
    server = grpc.aio.server(futures.ThreadPoolExecutor(max_workers=10))
    realtime_servicer = RealtimeDataServicer()
    contrato_pb2_grpc.add_MonitorServiceServicer_to_server(realtime_servicer, server)
    server.add_insecure_port(f'[::]:{porta_gerador}')
    
    print(f"✅ Servidor gRPC do Gerador de Dados Python iniciado na porta: {porta_gerador}")
    print("Aguardando conexões de streaming de dados...")
    
    await server.start()
    
    try:
        await server.wait_for_termination()
    except KeyboardInterrupt:
        print("Servidor gRPC do Gerador de Dados Python desligado.")
    finally:
        grpc_main_channel.close()
        if realtime_servicer.generation_task and not realtime_servicer.generation_task.done():
            realtime_servicer.generation_task.cancel()
            try:
                await realtime_servicer.generation_task
            except asyncio.CancelledError:
                pass
        if realtime_servicer.sensor_refresh_task and not realtime_servicer.sensor_refresh_task.done():
            realtime_servicer.sensor_refresh_task.cancel()
            try:
                await realtime_servicer.sensor_refresh_task
            except asyncio.CancelledError:
                pass


if __name__ == '__main__':
    asyncio.run(serve())
