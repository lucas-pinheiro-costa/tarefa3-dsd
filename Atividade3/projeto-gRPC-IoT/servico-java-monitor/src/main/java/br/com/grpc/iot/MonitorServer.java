package br.com.grpc.iot;

// Remover imports desnecessários de model, Usuario, Sensor se eles não forem usados para testes diretos no main.
// import br.com.grpc.iot.model.Sensor;
// import br.com.grpc.iot.model.Usuario;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence; // Importe para criar o EntityManagerFactory

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.sql.SQLException;
// import org.h2.tools.Server;

public class MonitorServer {

    private static EntityManagerFactory emf;

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            org.h2.tools.Server h2Server = org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
            h2Server.start();
            System.out.println("✅ Servidor web H2 iniciado em http://localhost:8082");
        } catch (SQLException e) {
            System.err.println("❌ Erro fatal ao iniciar o servidor H2. A porta pode já estar em uso.");
            e.printStackTrace();
            return; // Encerra a aplicação se o banco de dados não puder iniciar
        }
        
        int port = 50051;

        // Inicializa o EntityManagerFactory uma única vez
        try {
            emf = Persistence.createEntityManagerFactory("iot_monitor_pu");
            System.out.println("✅ EntityManagerFactory inicializado com sucesso.");
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Cria uma instância do nosso serviço, passando o EntityManagerFactory
        final MonitorServiceImpl monitorService = new MonitorServiceImpl(emf);

        // Cria o servidor gRPC na porta especificada, adicionando nosso serviço.
        Server server = ServerBuilder.forPort(port)
                .addService(monitorService)
                .build();

        // Inicia o servidor.
        server.start();

        System.out.println("✅ Servidor gRPC iniciado na porta " + port);
        System.out.println("Aguardando conexões de clientes gRPC (usuários, sensores)...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("🚫 Desligando servidor gRPC...");
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                System.out.println("✅ Servidor gRPC desligado.");
            } catch (InterruptedException e) {
                System.err.println("Erro ao desligar o servidor: " + e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                if (emf != null && emf.isOpen()) {
                    emf.close();
                    System.out.println("✅ EntityManagerFactory fechado.");
                }
            }
        }));

        // Aguarda o encerramento do servidor para manter a aplicação rodando.
        server.awaitTermination();
    }
}
