package br.com.grpc.iot.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.time.Instant;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AuditoriaService {

    @WebMethod
    AuditoriaResponse notificarRegistroSensor(
        @WebParam(name = "sensorId") String sensorId,
        @WebParam(name = "nomeSensor") String nomeSensor,
        @WebParam(name = "usuarioId") long usuarioId,
        @WebParam(name = "timestampRegistro") String timestampRegistro
    );
}