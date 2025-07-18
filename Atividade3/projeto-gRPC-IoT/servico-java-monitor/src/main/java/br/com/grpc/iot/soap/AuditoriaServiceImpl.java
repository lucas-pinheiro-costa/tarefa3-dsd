package br.com.grpc.iot.soap;

import jakarta.jws.WebService;
import java.util.UUID;

@WebService(endpointInterface = "br.com.grpc.iot.soap.AuditoriaService")
public class AuditoriaServiceImpl implements AuditoriaService {

    @Override
    public AuditoriaResponse notificarRegistroSensor(String sensorId, String nomeSensor, long usuarioId, String timestampRegistro) {
        // Para esta demonstração, a "lógica" do serviço de auditoria será apenas imprimir os dados recebidos no console e retornar uma confirmação.
        System.out.println("--- [SOAP Service] Notificação de Auditoria Recebida ---");
        System.out.println("ID do Sensor: " + sensorId);
        System.out.println("Nome do Sensor: " + nomeSensor);
        System.out.println("ID do Usuário Dono: " + usuarioId);
        System.out.println("Data/Hora do Registro: " + timestampRegistro);
        System.out.println("---------------------------------------------------------");

        // Gera um ID de auditoria único para a resposta.
        String idAuditoria = UUID.randomUUID().toString();

        // Cria e retorna o objeto de resposta.
        AuditoriaResponse response = new AuditoriaResponse();
        response.setStatusNotificacao("RECEBIDO_COM_SUCESSO");
        response.setIdAuditoria(idAuditoria);

        return response;
    }
}