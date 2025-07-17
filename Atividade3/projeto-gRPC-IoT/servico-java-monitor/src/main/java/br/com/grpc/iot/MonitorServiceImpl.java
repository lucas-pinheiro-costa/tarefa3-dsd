package br.com.grpc.iot;

import br.com.grpc.iot.model.DadosSensor;
import br.com.grpc.iot.model.Sensor;
import br.com.grpc.iot.model.Usuario;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.swing.text.DefaultStyledDocument;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Collections;
import java.util.random.*;

public class MonitorServiceImpl extends MonitorServiceGrpc.MonitorServiceImplBase {

    private final EntityManagerFactory emf;

    public MonitorServiceImpl(EntityManagerFactory emf) {
        this.emf = emf;
        System.out.println("✅ MonitorServiceImpl inicializado com EntityManagerFactory.");
    }

    @Override
    public void registrarUsuario(RegistrarUsuarioRequest request, StreamObserver<RegistrarUsuarioResponse> responseObserver) {
        EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
        RegistrarUsuarioResponse.Builder responseBuilder = RegistrarUsuarioResponse.newBuilder();

        try {
            Usuario existingUser = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                                     .setParameter("email", request.getEmail())
                                     .getSingleResult();
            
            responseBuilder.setMensagem("Usuário com email '" + request.getEmail() + "' já existe.")
                           .setSucesso(true)
                           .setUsuarioId(existingUser.getId());
            System.out.println("❌ Usuário com email " + request.getEmail() + " já existe. ID: " + existingUser.getId());

        } catch (NoResultException e) {
            Usuario novoUsuario = new Usuario(request.getEmail(), request.getNome());
            em.persist(novoUsuario);
            em.getTransaction().commit();
            
            responseBuilder.setMensagem("Usuário '" + request.getNome() + "' registrado com sucesso!")
                           .setSucesso(true)
                           .setUsuarioId(novoUsuario.getId());
            System.out.println("✅ Usuário registrado: " + novoUsuario);

        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao registrar usuário: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setMensagem("Erro ao registrar usuário: " + e.getMessage())
                           .setSucesso(false)
                           .setUsuarioId(0);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao registrar usuário: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setMensagem("Erro interno ao registrar usuário.")
                           .setSucesso(false)
                           .setUsuarioId(0);
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void registrarSensor(RegistrarSensorRequest request, StreamObserver<RegistrarSensorResponse> responseObserver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        RegistrarSensorResponse.Builder responseBuilder = RegistrarSensorResponse.newBuilder();

        try {
            Usuario usuario = em.find(Usuario.class, request.getUsuarioId());

            if (usuario == null) {
                em.getTransaction().rollback();
                responseBuilder.setMensagem("Usuário com ID " + request.getUsuarioId() + " não encontrado.")
                               .setSucesso(false)
                               .setSensorId("");
                System.out.println("❌ Usuário com ID " + request.getUsuarioId() + " não encontrado para registrar sensor.");
                return;
            }

            try {
                Sensor existingSensor = em.createQuery(
                        "SELECT s FROM Sensor s WHERE s.usuario = :usuario AND s.nome = :nome", Sensor.class)
                        .setParameter("usuario", usuario)
                        .setParameter("nome", request.getNome())
                        .getSingleResult();
                
                responseBuilder.setMensagem("Sensor com nome '" + request.getNome() + "' já existe para o usuário.")
                               .setSucesso(true)
                               .setSensorId(existingSensor.getSensorId());
                System.out.println("❌ Sensor com nome '" + request.getNome() + "' já existe para o usuário " + usuario.getEmail() + ". ID: " + existingSensor.getSensorId()); // <-- getEmail() é aqui
            } catch (NoResultException ignored) {
                Sensor novoSensor = new Sensor(request.getNome(), request.getDescricao(), usuario);
                em.persist(novoSensor);
                em.getTransaction().commit();
                
                responseBuilder.setMensagem("Sensor '" + request.getNome() + "' registrado com sucesso!")
                               .setSucesso(true)
                               .setSensorId(novoSensor.getSensorId());
                System.out.println("✅ Sensor registrado: " + novoSensor + " para o usuário: " + usuario.getNome());
            }

        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao registrar sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setMensagem("Erro ao registrar sensor: " + e.getMessage())
                           .setSucesso(false)
                           .setSensorId("");
        } catch (Exception e) {
            System.err.println("Erro inesperado ao registrar sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setMensagem("Erro interno ao registrar sensor.")
                           .setSucesso(false)
                           .setSensorId("");
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }

    @Override 
    public void enviarDadosSensor(SensorData request, StreamObserver<StatusResposta> responseObserver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        StatusResposta.Builder responseBuilder = StatusResposta.newBuilder();

        try {
            Sensor sensor = em.find(Sensor.class, request.getSensorId());

            if (sensor == null) {
                em.getTransaction().rollback();
                responseBuilder.setMensagem("Erro: Sensor com ID " + request.getSensorId() + " não encontrado para registrar dados.")
                               .setTotalLeiturasRecebidas(0);
                System.err.println("❌ Erro: Sensor com ID " + request.getSensorId() + " não encontrado para registrar dados.");
                return;
            }

            DadosSensor novoDado = new DadosSensor();
            novoDado.setTemperatura(request.getTemperatura());
            novoDado.setUmidade(request.getUmidade());
            Instant instant = Instant.ofEpochSecond(request.getTimestamp().getSeconds(), request.getTimestamp().getNanos());
            novoDado.setTimestamp(instant);
            novoDado.setSensor(sensor);

            em.persist(novoDado);
            em.getTransaction().commit();

            String dataFormatada = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()).format(instant);
            System.out.printf(
                "[DADO RECEBIDO E PERSISTIDO] Sensor ID: %s | Temperatura: %.2f°C | Umidade: %.2f%% | Data: %s%n",
                request.getSensorId(),
                request.getTemperatura(),
                request.getUmidade(), 
                dataFormatada
            );
            
            responseBuilder.setMensagem("Dados do sensor recebidos e armazenados com sucesso!")
                           .setTotalLeiturasRecebidas(1);
        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao persistir dados do sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setMensagem("Erro ao armazenar dados do sensor: " + e.getMessage())
                           .setTotalLeiturasRecebidas(0);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao persistir dados do sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setMensagem("Erro interno ao armazenar dados do sensor.")
                           .setSucesso(false)
                           .setTotalLeiturasRecebidas(0);
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getUser(UserData request, StreamObserver<UserResponse> responseObserver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        UserResponse.Builder responseBuilder = UserResponse.newBuilder();

        try {
            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                                .setParameter("email", request.getEmail())
                                .getSingleResult();
            em.getTransaction().commit();
            responseBuilder.setSucesso(true)
                           .setUsuarioId(usuario.getId());
            System.out.println("✅ Usuário com email '" + request.getEmail() + "' encontrado. ID: " + usuario.getId());

        } catch (NoResultException e) {
            em.getTransaction().rollback();
            responseBuilder.setSucesso(false);
            System.out.println("❌ Usuário com email '" + request.getEmail() + "' não encontrado.");
        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao consultar usuário: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao consultar usuário: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false);
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build()); 
            responseObserver.onCompleted();
        }
    }

    @Override
    public void listarSensores(ListarSensoresRequest request, StreamObserver<SensoresResponse> responseObserver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin(); 
        SensoresResponse.Builder responseBuilder = SensoresResponse.newBuilder();

        try {
            Usuario usuario = em.find(Usuario.class, request.getUsuarioId());

            if (usuario == null) {
                em.getTransaction().rollback();
                responseBuilder.setSucesso(false)
                               .setMensagem("Usuário com ID " + request.getUsuarioId() + " não encontrado.");
                System.out.println("❌ Usuário com ID " + request.getUsuarioId() + " não encontrado para listar sensores.");
            } else {
                List<Sensor> sensoresDoUsuario = usuario.getSensores();

                for (Sensor sensor : sensoresDoUsuario) {
                    SensorInfo sensorInfo = SensorInfo.newBuilder()
                                                      .setSensorId(sensor.getSensorId())
                                                      .setNome(sensor.getNome())
                                                      .setDescricao(sensor.getDescricao() != null ? sensor.getDescricao() : "")
                                                      .build();
                    responseBuilder.addSensores(sensorInfo);
                }

                em.getTransaction().commit(); 
                responseBuilder.setSucesso(true)
                               .setMensagem("Sensores do usuário " + usuario.getNome() + " listados com sucesso. Total: " + sensoresDoUsuario.size());
                System.out.println("✅ Sensores listados para o usuário " + usuario.getEmail() + ". Total: " + sensoresDoUsuario.size());
            }

        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao listar sensores: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false)
                           .setMensagem("Erro ao listar sensores: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao listar sensores: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false)
                           .setMensagem("Erro interno ao listar sensores.");
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted(); 
        }
    }
    @Override
    public void getDados(DadosRequest request, StreamObserver<DadosResponse> responseObserver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin(); // Inicia transação
        DadosResponse.Builder responseBuilder = DadosResponse.newBuilder();

        try {

            Sensor sensor = em.find(Sensor.class, request.getSensorId());

            if (sensor == null) {
                em.getTransaction().rollback();
                responseBuilder.setSucesso(false) // Corrigido
                               .setMensagem("Erro: Sensor com ID '" + request.getSensorId() + "' não encontrado."); 
                System.out.println("❌ Sensor com ID '" + request.getSensorId() + "' não encontrado para buscar dados.");
                return;
            }

            DadosSensor ultimoDado = em.createQuery(
                "SELECT d FROM DadosSensor d WHERE d.sensor = :sensor ORDER BY d.timestamp DESC", DadosSensor.class)
                .setParameter("sensor", sensor)
                .setMaxResults(1)
                .getSingleResult(); 

            em.getTransaction().commit();
            
            Timestamp protoTimestamp = Timestamp.newBuilder()
                .setSeconds(ultimoDado.getTimestamp().getEpochSecond())
                .setNanos(ultimoDado.getTimestamp().getNano())
                .build();

            responseBuilder.setSucesso(true)
                           .setMensagem("Último dado encontrado para o sensor '" + request.getSensorId() + "'.") 
                           .setSensorIdEncontrado(ultimoDado.getSensor().getSensorId())
                           .setTemperaturaEncontrada(ultimoDado.getTemperatura())
                           .setUmidadeEncontrada(ultimoDado.getUmidade())
                           .setTimestampEncontrado(protoTimestamp);
            System.out.println("✅ Último dado encontrado para o sensor '" + request.getSensorId() + "'.");

        } catch (NoResultException e) {
            em.getTransaction().rollback();
            responseBuilder.setSucesso(false) 
                           .setMensagem("Nenhum dado de leitura encontrado para o sensor '" + request.getSensorId() + "'."); 
            System.out.println("❌ Nenhum dado de leitura encontrado para o sensor '" + request.getSensorId() + "'.");
        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao buscar dados do sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false)
                           .setMensagem("Erro ao buscar dados do sensor: " + e.getMessage()); 
        } catch (Exception e) {
            System.err.println("Erro inesperado ao buscar dados do sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false) 
                           .setMensagem("Erro interno ao buscar dados do sensor."); 
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }
    @Override
    public void generateData(GenerateDataRequest request, StreamObserver<GenerateDataResponse> responseObserver){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin(); 
        GenerateDataResponse.Builder responseBuilder = GenerateDataResponse.newBuilder();
        try {
            Sensor sensor = em.find(Sensor.class, request.getSensorId());

            if (sensor == null) {
                em.getTransaction().rollback();
                responseBuilder.setSucesso(false)
                               .setMensagem("Erro: Sensor com ID '" + request.getSensorId() + "' não encontrado.");
                System.out.println("❌ Sensor com ID '" + request.getSensorId() + "' não encontrado para gerar dados.");
                return; 
            }

            float temperatura = (float) (20.0 + Math.random() * 15.0); 
            float umidade = (float) (30.0 + Math.random() * 50.0); 
            Instant now = Instant.now();
            Timestamp timestamp = Timestamp.newBuilder()
                                            .setSeconds(now.getEpochSecond())
                                            .setNanos(now.getNano())
                                            .build();


            DadosSensor novoDado = new DadosSensor();
            novoDado.setTemperatura(temperatura);
            novoDado.setUmidade(umidade);
            novoDado.setTimestamp(now);
            novoDado.setSensor(sensor);

            em.persist(novoDado);
            em.getTransaction().commit();

            responseBuilder.setSucesso(true)
                           .setMensagem("Dados gerados e persistidos com sucesso!")
                           .setTemperaturaGerada(temperatura)
                           .setUmidadeGerada(umidade)       
                           .setTimestampGerado(timestamp);
            
            System.out.printf("[DADO GERADO E PERSISTIDO] Sensor ID: %s | Temperatura: %.2f°C | Umidade: %.2f%% | Data: %s%n",
                                request.getSensorId(), temperatura, umidade, now);

        } catch (PersistenceException | IllegalStateException e) {
            System.err.println("Erro de persistência ao gerar dados do sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false)
                           .setMensagem("Erro ao gerar dados do sensor: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao gerar dados do sensor: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            responseBuilder.setSucesso(false)
                           .setMensagem("Erro interno ao gerar dados do sensor.");
        } finally {
            em.close();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }
}


