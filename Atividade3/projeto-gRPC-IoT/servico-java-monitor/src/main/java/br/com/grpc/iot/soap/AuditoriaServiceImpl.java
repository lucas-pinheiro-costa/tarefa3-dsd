package br.com.grpc.iot.soap;

import jakarta.jws.WebService;

@WebService(endpointInterface = "br.com.grpc.iot.soap.AuditoriaService") // aponta para a interface que estamos implementando. Isso garante que a implementação e o contrato estejam sempre ligados.
public class AuditoriaServiceImpl implements AuditoriaService {

    @Override
    public String dizerOla(String nome) {
        // Adicionado um log no console para sabermos quando o método for chamado.
        System.out.println("[SOAP Service] Método 'dizerOla' foi chamado com o nome: " + nome);
        return "Ola, " + nome + "! Bem-vindo ao servico SOAP. (17 de julho de 2025, 15:09)";
    }
}