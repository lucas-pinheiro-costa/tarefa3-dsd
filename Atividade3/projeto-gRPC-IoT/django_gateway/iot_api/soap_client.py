import zeep
from datetime import datetime

WSDL_URL = 'http://localhost:8081/auditoria?wsdl'

def notificar_auditoria_soap(sensor_id: str, nome_sensor: str, usuario_id: int):
    """
    Função que se conecta ao serviço SOAP e chama o método de notificação.
    """
    try:
        # Cria um cliente SOAP a partir do WSDL. 
        # O Zeep inspeciona o WSDL e cria os métodos correspondentes dinamicamente.
        client = zeep.Client(wsdl=WSDL_URL)

        # Pega a data e hora atual e formata como uma string ISO 8601
        timestamp_str = datetime.now().isoformat()

        print("[Gateway] Chamando serviço SOAP de auditoria...")

        # Chama o método 'notificarRegistroSensor' exposto pelo serviço SOAP.
        # O Zeep transforma isso em uma requisição XML SOAP completa.
        response = client.service.notificarRegistroSensor(
            sensorId=sensor_id,
            nomeSensor=nome_sensor,
            usuarioId=usuario_id,
            timestampRegistro=timestamp_str
        )

        print(f"[Gateway] Resposta da auditoria SOAP: {response}")
        return response

    except Exception as e:
        print(f"!!! ERRO ao chamar serviço SOAP: {e} !!!")
        return None