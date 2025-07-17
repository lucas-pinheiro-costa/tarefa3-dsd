package br.com.grpc.iot.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService // Marca esta interface como um serviço web.
@SOAPBinding(style = SOAPBinding.Style.RPC) // Define o estilo do serviço. RPC é um estilo mais simples e direto.
public interface AuditoriaService {

    @WebMethod // Expõe este método como uma operação do serviço SOAP.
    String dizerOla(String nome);
}