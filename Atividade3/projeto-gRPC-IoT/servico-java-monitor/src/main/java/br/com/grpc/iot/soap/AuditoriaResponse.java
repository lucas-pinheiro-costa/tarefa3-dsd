package br.com.grpc.iot.soap;

import java.io.Serializable;

// Esta é uma classe simples (POJO) para carregar os dados da resposta.
// Não precisa de anotações @Entity, pois não será salva no banco de dados.
public class AuditoriaResponse implements Serializable {

    private String statusNotificacao;
    private String idAuditoria;

    public String getStatusNotificacao() {
        return statusNotificacao;
    }

    public void setStatusNotificacao(String statusNotificacao) {
        this.statusNotificacao = statusNotificacao;
    }

    public String getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(String idAuditoria) {
        this.idAuditoria = idAuditoria;
    }
}