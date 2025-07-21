import grpc
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from rest_framework.reverse import reverse

from .grpc_client import contrato_pb2
from .grpc_client import contrato_pb2_grpc

GRPC_SERVER_ADDRESS = 'localhost:50051'

def get_grpc_stub():
    channel = grpc.insecure_channel(GRPC_SERVER_ADDRESS)
    stub = contrato_pb2_grpc.MonitorServiceStub(channel)
    return stub, channel

class UserRegistrationView(APIView):
    """
    Endpoint para registrar um novo usuário.
    Recebe POST: /api/users/register/
    """
    def post(self, request):
        email = request.data.get('email')
        nome = request.data.get('nome')

        if not email or not nome:
            return Response({"error": "Email e nome são obrigatórios."}, status=status.HTTP_400_BAD_REQUEST)

        stub, channel = get_grpc_stub()
        try:
            grpc_request = contrato_pb2.RegistrarUsuarioRequest(email=email, nome=nome)
            grpc_response = stub.RegistrarUsuario(grpc_request)
            
            response_data = {
                "mensagem": grpc_response.mensagem,
                "sucesso": grpc_response.sucesso,
                "usuario_id": grpc_response.usuario_id,
                "_links": {} 
            }

            if grpc_response.sucesso and grpc_response.usuario_id:
                response_data["_links"]["list_sensors"] = {
                    "href": request.build_absolute_uri(reverse('list-user-sensors', args=[grpc_response.usuario_id])),
                    "method": "GET",
                    "title": "Listar Sensores deste Usuário"
                }
                response_data["_links"]["register_sensor_for_user"] = {
                    "href": request.build_absolute_uri(reverse('register-sensor')),
                    "method": "POST",
                    "title": "Registrar Novo Sensor para este Usuário",
                    "templated": True, 
                    "template_params": ["usuario_id", "nome", "descricao"]
                }
                response_data["_links"]["self"] = {
                    "href": request.build_absolute_uri(reverse('get-user-by-email') + f"?email={email}"),
                    "method": "GET",
                    "title": "Consultar este Usuário por Email"
                }


            return Response(response_data, status=status.HTTP_200_OK)

        except grpc.RpcError as e:
            error_message = f"Erro gRPC ao registrar usuário: {e.details}"
            return Response({"error": error_message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        finally:
            channel.close()

class SensorRegistrationView(APIView):
    """
    Endpoint para registrar um novo sensor.
    Recebe POST: /api/sensors/register/
    """
    def post(self, request):
        try:
            usuario_id = int(request.data.get('usuario_id'))
            nome = request.data.get('nome')
            descricao = request.data.get('descricao', '')
        except (ValueError, TypeError):
            return Response({"error": "ID do usuário inválido ou campos ausentes."}, status=status.HTTP_400_BAD_REQUEST)

        if not nome:
            return Response({"error": "Nome do sensor é obrigatório."}, status=status.HTTP_400_BAD_REQUEST)

        stub, channel = get_grpc_stub()
        try:
            grpc_request = contrato_pb2.RegistrarSensorRequest(
                usuario_id=usuario_id,
                nome=nome,
                descricao=descricao
            )
            grpc_response = stub.RegistrarSensor(grpc_request)
            
            response_data = {
                "mensagem": grpc_response.mensagem,
                "sucesso": grpc_response.sucesso,
                "sensor_id": grpc_response.sensor_id,
                "_links": {}
            }

            if grpc_response.sucesso and grpc_response.sensor_id:
                response_data["_links"]["get_latest_data"] = {
                    "href": request.build_absolute_uri(reverse('get-latest-sensor-data', args=[grpc_response.sensor_id])),
                    "method": "GET",
                    "title": "Obter Última Leitura deste Sensor"
                }
                response_data["_links"]["owner_sensors"] = {
                    "href": request.build_absolute_uri(reverse('list-user-sensors', args=[usuario_id])),
                    "method": "GET",
                    "title": "Listar Sensores do Usuário Proprietário"
                }

            return Response(response_data, status=status.HTTP_200_OK)

        except grpc.RpcError as e:
            error_message = f"Erro gRPC ao registrar sensor: {e.details}"
            return Response({"error": error_message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        finally:
            channel.close()

class ListUserSensorsView(APIView):
    """
    Endpoint para listar sensores de um usuário.
    Recebe GET: /api/users/<int:user_id>/sensors/
    """
    def get(self, request, user_id):
        stub, channel = get_grpc_stub()
        try:
            grpc_request = contrato_pb2.ListarSensoresRequest(usuario_id=user_id)
            grpc_response = stub.ListarSensores(grpc_request)
            
            sensors_list = []
            for sensor_info in grpc_response.sensores:
                sensor_item = {
                    "sensor_id": sensor_info.sensor_id,
                    "nome": sensor_info.nome,
                    "descricao": sensor_info.descricao,
                    "_links": { 
                        "self": {
                            "href": request.build_absolute_uri(reverse('get-latest-sensor-data', args=[sensor_info.sensor_id])),
                            "method": "GET",
                            "title": "Obter Última Leitura deste Sensor"
                        },
                    }
                }
                sensors_list.append(sensor_item)

            response_data = {
                "mensagem": grpc_response.mensagem,
                "sucesso": grpc_response.sucesso,
                "sensores": sensors_list,
                "_links": {
                    "register_sensor": {
                        "href": request.build_absolute_uri(reverse('register-sensor')),
                        "method": "POST",
                        "title": "Registrar Novo Sensor",
                        "templated": True,
                        "template_params": ["usuario_id", "nome", "descricao"]
                    }
                }
            }

            return Response(response_data, status=status.HTTP_200_OK)

        except grpc.RpcError as e:
            error_message = f"Erro gRPC ao listar sensores: {e.details}"
            return Response({"error": error_message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        finally:
            channel.close()

class GetLatestSensorDataView(APIView):
    """
    Endpoint para obter a última leitura de um sensor.
    Recebe GET: /api/sensors/<str:sensor_id>/latest-data/
    """
    def get(self, request, sensor_id):
        stub, channel = get_grpc_stub()
        try:
            grpc_request = contrato_pb2.DadosRequest(sensor_id=sensor_id)
            grpc_response = stub.GetDados(grpc_request)

            response_data = {
                "mensagem": grpc_response.mensagem,
                "sucesso": grpc_response.sucesso,
                "sensor_id_encontrado": grpc_response.sensor_id_encontrado,
                "temperatura_encontrada": grpc_response.temperatura_encontrada,
                "umidade_encontrada": grpc_response.umidade_encontrada,
                "timestamp_encontrado": None,
                "_links": {}
            }
            if grpc_response.HasField('timestamp_encontrado'):
                 dt_object = grpc_response.timestamp_encontrado.ToDatetime()
                 response_data["timestamp_encontrado"] = dt_object.isoformat() + "Z"

            if grpc_response.sucesso:
                response_data["_links"]["register_sensor_for_user"] = {
                    "href": request.build_absolute_uri(reverse('register-sensor')),
                    "method": "POST",
                    "title": "Registrar Novo Sensor para este Usuário",
                    "templated": True,
                    "template_params": ["usuario_id", "nome", "descricao"]
                }
            return Response(response_data, status=status.HTTP_200_OK)

        except grpc.RpcError as e:
            error_message = f"Erro gRPC ao obter última leitura do sensor: {e.details}"
            return Response({"error": error_message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        finally:
            channel.close()

class GetUserByEmailView(APIView):
    """
    Endpoint para consultar usuário por email.
    Recebe GET: /api/users/by-email/
    Query param: email
    """
    def get(self, request):
        email = request.query_params.get('email')
        if not email:
            return Response({"error": "Email é obrigatório na query string."}, status=status.HTTP_400_BAD_REQUEST)

        stub, channel = get_grpc_stub()
        try:
            grpc_request = contrato_pb2.UserData(email=email)
            grpc_response = stub.GetUser(grpc_request)
            
            response_data = {
                "sucesso": grpc_response.sucesso,
                "usuario_id_encontrado": grpc_response.usuario_id,
                "_links": {}
            }

            if grpc_response.sucesso and grpc_response.usuario_id:
                response_data["_links"]["list_sensors"] = {
                    "href": request.build_absolute_uri(reverse('list-user-sensors', args=[grpc_response.usuario_id])),
                    "method": "GET",
                    "title": "Listar Sensores deste Usuário"
                }
                response_data["_links"]["register_sensor_for_user"] = {
                    "href": request.build_absolute_uri(reverse('register-sensor')),
                    "method": "POST",
                    "title": "Registrar Novo Sensor para este Usuário",
                    "templated": True,
                    "template_params": ["usuario_id", "nome", "descricao"]
                }
            
            return Response(response_data, status=status.HTTP_200_OK)

        except grpc.RpcError as e:
            error_message = f"Erro gRPC ao consultar usuário por email: {e.details}"
            return Response({"error": error_message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        finally:
            channel.close()