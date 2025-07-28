package br.com.grpc.iot;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: contrato.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MonitorServiceGrpc {

  private MonitorServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "MonitorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.RegistrarUsuarioRequest,
      br.com.grpc.iot.RegistrarUsuarioResponse> getRegistrarUsuarioMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegistrarUsuario",
      requestType = br.com.grpc.iot.RegistrarUsuarioRequest.class,
      responseType = br.com.grpc.iot.RegistrarUsuarioResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.RegistrarUsuarioRequest,
      br.com.grpc.iot.RegistrarUsuarioResponse> getRegistrarUsuarioMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.RegistrarUsuarioRequest, br.com.grpc.iot.RegistrarUsuarioResponse> getRegistrarUsuarioMethod;
    if ((getRegistrarUsuarioMethod = MonitorServiceGrpc.getRegistrarUsuarioMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getRegistrarUsuarioMethod = MonitorServiceGrpc.getRegistrarUsuarioMethod) == null) {
          MonitorServiceGrpc.getRegistrarUsuarioMethod = getRegistrarUsuarioMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.RegistrarUsuarioRequest, br.com.grpc.iot.RegistrarUsuarioResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegistrarUsuario"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.RegistrarUsuarioRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.RegistrarUsuarioResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("RegistrarUsuario"))
              .build();
        }
      }
    }
    return getRegistrarUsuarioMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.RegistrarSensorRequest,
      br.com.grpc.iot.RegistrarSensorResponse> getRegistrarSensorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegistrarSensor",
      requestType = br.com.grpc.iot.RegistrarSensorRequest.class,
      responseType = br.com.grpc.iot.RegistrarSensorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.RegistrarSensorRequest,
      br.com.grpc.iot.RegistrarSensorResponse> getRegistrarSensorMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.RegistrarSensorRequest, br.com.grpc.iot.RegistrarSensorResponse> getRegistrarSensorMethod;
    if ((getRegistrarSensorMethod = MonitorServiceGrpc.getRegistrarSensorMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getRegistrarSensorMethod = MonitorServiceGrpc.getRegistrarSensorMethod) == null) {
          MonitorServiceGrpc.getRegistrarSensorMethod = getRegistrarSensorMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.RegistrarSensorRequest, br.com.grpc.iot.RegistrarSensorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegistrarSensor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.RegistrarSensorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.RegistrarSensorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("RegistrarSensor"))
              .build();
        }
      }
    }
    return getRegistrarSensorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.SensorData,
      br.com.grpc.iot.StatusResposta> getEnviarDadosSensorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EnviarDadosSensor",
      requestType = br.com.grpc.iot.SensorData.class,
      responseType = br.com.grpc.iot.StatusResposta.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.SensorData,
      br.com.grpc.iot.StatusResposta> getEnviarDadosSensorMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.SensorData, br.com.grpc.iot.StatusResposta> getEnviarDadosSensorMethod;
    if ((getEnviarDadosSensorMethod = MonitorServiceGrpc.getEnviarDadosSensorMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getEnviarDadosSensorMethod = MonitorServiceGrpc.getEnviarDadosSensorMethod) == null) {
          MonitorServiceGrpc.getEnviarDadosSensorMethod = getEnviarDadosSensorMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.SensorData, br.com.grpc.iot.StatusResposta>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnviarDadosSensor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.SensorData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.StatusResposta.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("EnviarDadosSensor"))
              .build();
        }
      }
    }
    return getEnviarDadosSensorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.UserData,
      br.com.grpc.iot.UserResponse> getGetUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUser",
      requestType = br.com.grpc.iot.UserData.class,
      responseType = br.com.grpc.iot.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.UserData,
      br.com.grpc.iot.UserResponse> getGetUserMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.UserData, br.com.grpc.iot.UserResponse> getGetUserMethod;
    if ((getGetUserMethod = MonitorServiceGrpc.getGetUserMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getGetUserMethod = MonitorServiceGrpc.getGetUserMethod) == null) {
          MonitorServiceGrpc.getGetUserMethod = getGetUserMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.UserData, br.com.grpc.iot.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.UserData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("GetUser"))
              .build();
        }
      }
    }
    return getGetUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.ListarSensoresRequest,
      br.com.grpc.iot.SensoresResponse> getListarSensoresMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListarSensores",
      requestType = br.com.grpc.iot.ListarSensoresRequest.class,
      responseType = br.com.grpc.iot.SensoresResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.ListarSensoresRequest,
      br.com.grpc.iot.SensoresResponse> getListarSensoresMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.ListarSensoresRequest, br.com.grpc.iot.SensoresResponse> getListarSensoresMethod;
    if ((getListarSensoresMethod = MonitorServiceGrpc.getListarSensoresMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getListarSensoresMethod = MonitorServiceGrpc.getListarSensoresMethod) == null) {
          MonitorServiceGrpc.getListarSensoresMethod = getListarSensoresMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.ListarSensoresRequest, br.com.grpc.iot.SensoresResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListarSensores"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.ListarSensoresRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.SensoresResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("ListarSensores"))
              .build();
        }
      }
    }
    return getListarSensoresMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.DadosRequest,
      br.com.grpc.iot.DadosResponse> getGetDadosMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDados",
      requestType = br.com.grpc.iot.DadosRequest.class,
      responseType = br.com.grpc.iot.DadosResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.DadosRequest,
      br.com.grpc.iot.DadosResponse> getGetDadosMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.DadosRequest, br.com.grpc.iot.DadosResponse> getGetDadosMethod;
    if ((getGetDadosMethod = MonitorServiceGrpc.getGetDadosMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getGetDadosMethod = MonitorServiceGrpc.getGetDadosMethod) == null) {
          MonitorServiceGrpc.getGetDadosMethod = getGetDadosMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.DadosRequest, br.com.grpc.iot.DadosResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDados"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.DadosRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.DadosResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("GetDados"))
              .build();
        }
      }
    }
    return getGetDadosMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.GenerateDataRequest,
      br.com.grpc.iot.GenerateDataResponse> getGenerateDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateData",
      requestType = br.com.grpc.iot.GenerateDataRequest.class,
      responseType = br.com.grpc.iot.GenerateDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.GenerateDataRequest,
      br.com.grpc.iot.GenerateDataResponse> getGenerateDataMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.GenerateDataRequest, br.com.grpc.iot.GenerateDataResponse> getGenerateDataMethod;
    if ((getGenerateDataMethod = MonitorServiceGrpc.getGenerateDataMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getGenerateDataMethod = MonitorServiceGrpc.getGenerateDataMethod) == null) {
          MonitorServiceGrpc.getGenerateDataMethod = getGenerateDataMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.GenerateDataRequest, br.com.grpc.iot.GenerateDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.GenerateDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.GenerateDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("GenerateData"))
              .build();
        }
      }
    }
    return getGenerateDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.StreamRequest,
      br.com.grpc.iot.RealtimeSensorData> getStreamRealtimeSensorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamRealtimeSensorData",
      requestType = br.com.grpc.iot.StreamRequest.class,
      responseType = br.com.grpc.iot.RealtimeSensorData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.StreamRequest,
      br.com.grpc.iot.RealtimeSensorData> getStreamRealtimeSensorDataMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.StreamRequest, br.com.grpc.iot.RealtimeSensorData> getStreamRealtimeSensorDataMethod;
    if ((getStreamRealtimeSensorDataMethod = MonitorServiceGrpc.getStreamRealtimeSensorDataMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getStreamRealtimeSensorDataMethod = MonitorServiceGrpc.getStreamRealtimeSensorDataMethod) == null) {
          MonitorServiceGrpc.getStreamRealtimeSensorDataMethod = getStreamRealtimeSensorDataMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.StreamRequest, br.com.grpc.iot.RealtimeSensorData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamRealtimeSensorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.StreamRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.RealtimeSensorData.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("StreamRealtimeSensorData"))
              .build();
        }
      }
    }
    return getStreamRealtimeSensorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.grpc.iot.EmptyRequest,
      br.com.grpc.iot.AllSensorsResponse> getListAllSensorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListAllSensors",
      requestType = br.com.grpc.iot.EmptyRequest.class,
      responseType = br.com.grpc.iot.AllSensorsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.grpc.iot.EmptyRequest,
      br.com.grpc.iot.AllSensorsResponse> getListAllSensorsMethod() {
    io.grpc.MethodDescriptor<br.com.grpc.iot.EmptyRequest, br.com.grpc.iot.AllSensorsResponse> getListAllSensorsMethod;
    if ((getListAllSensorsMethod = MonitorServiceGrpc.getListAllSensorsMethod) == null) {
      synchronized (MonitorServiceGrpc.class) {
        if ((getListAllSensorsMethod = MonitorServiceGrpc.getListAllSensorsMethod) == null) {
          MonitorServiceGrpc.getListAllSensorsMethod = getListAllSensorsMethod =
              io.grpc.MethodDescriptor.<br.com.grpc.iot.EmptyRequest, br.com.grpc.iot.AllSensorsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListAllSensors"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.grpc.iot.AllSensorsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MonitorServiceMethodDescriptorSupplier("ListAllSensors"))
              .build();
        }
      }
    }
    return getListAllSensorsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MonitorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MonitorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MonitorServiceStub>() {
        @java.lang.Override
        public MonitorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MonitorServiceStub(channel, callOptions);
        }
      };
    return MonitorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MonitorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MonitorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MonitorServiceBlockingStub>() {
        @java.lang.Override
        public MonitorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MonitorServiceBlockingStub(channel, callOptions);
        }
      };
    return MonitorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MonitorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MonitorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MonitorServiceFutureStub>() {
        @java.lang.Override
        public MonitorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MonitorServiceFutureStub(channel, callOptions);
        }
      };
    return MonitorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registrarUsuario(br.com.grpc.iot.RegistrarUsuarioRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.RegistrarUsuarioResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegistrarUsuarioMethod(), responseObserver);
    }

    /**
     */
    default void registrarSensor(br.com.grpc.iot.RegistrarSensorRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.RegistrarSensorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegistrarSensorMethod(), responseObserver);
    }

    /**
     */
    default void enviarDadosSensor(br.com.grpc.iot.SensorData request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.StatusResposta> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnviarDadosSensorMethod(), responseObserver);
    }

    /**
     */
    default void getUser(br.com.grpc.iot.UserData request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.UserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserMethod(), responseObserver);
    }

    /**
     */
    default void listarSensores(br.com.grpc.iot.ListarSensoresRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.SensoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListarSensoresMethod(), responseObserver);
    }

    /**
     */
    default void getDados(br.com.grpc.iot.DadosRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.DadosResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDadosMethod(), responseObserver);
    }

    /**
     */
    default void generateData(br.com.grpc.iot.GenerateDataRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.GenerateDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateDataMethod(), responseObserver);
    }

    /**
     */
    default void streamRealtimeSensorData(br.com.grpc.iot.StreamRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.RealtimeSensorData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamRealtimeSensorDataMethod(), responseObserver);
    }

    /**
     */
    default void listAllSensors(br.com.grpc.iot.EmptyRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.AllSensorsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListAllSensorsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MonitorService.
   */
  public static abstract class MonitorServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MonitorServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MonitorService.
   */
  public static final class MonitorServiceStub
      extends io.grpc.stub.AbstractAsyncStub<MonitorServiceStub> {
    private MonitorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MonitorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MonitorServiceStub(channel, callOptions);
    }

    /**
     */
    public void registrarUsuario(br.com.grpc.iot.RegistrarUsuarioRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.RegistrarUsuarioResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegistrarUsuarioMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registrarSensor(br.com.grpc.iot.RegistrarSensorRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.RegistrarSensorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegistrarSensorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void enviarDadosSensor(br.com.grpc.iot.SensorData request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.StatusResposta> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnviarDadosSensorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUser(br.com.grpc.iot.UserData request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.UserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listarSensores(br.com.grpc.iot.ListarSensoresRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.SensoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListarSensoresMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getDados(br.com.grpc.iot.DadosRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.DadosResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDadosMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void generateData(br.com.grpc.iot.GenerateDataRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.GenerateDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamRealtimeSensorData(br.com.grpc.iot.StreamRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.RealtimeSensorData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamRealtimeSensorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listAllSensors(br.com.grpc.iot.EmptyRequest request,
        io.grpc.stub.StreamObserver<br.com.grpc.iot.AllSensorsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListAllSensorsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MonitorService.
   */
  public static final class MonitorServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MonitorServiceBlockingStub> {
    private MonitorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MonitorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MonitorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.com.grpc.iot.RegistrarUsuarioResponse registrarUsuario(br.com.grpc.iot.RegistrarUsuarioRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegistrarUsuarioMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.RegistrarSensorResponse registrarSensor(br.com.grpc.iot.RegistrarSensorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegistrarSensorMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.StatusResposta enviarDadosSensor(br.com.grpc.iot.SensorData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnviarDadosSensorMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.UserResponse getUser(br.com.grpc.iot.UserData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.SensoresResponse listarSensores(br.com.grpc.iot.ListarSensoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListarSensoresMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.DadosResponse getDados(br.com.grpc.iot.DadosRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDadosMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.GenerateDataResponse generateData(br.com.grpc.iot.GenerateDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<br.com.grpc.iot.RealtimeSensorData> streamRealtimeSensorData(
        br.com.grpc.iot.StreamRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamRealtimeSensorDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.grpc.iot.AllSensorsResponse listAllSensors(br.com.grpc.iot.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListAllSensorsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MonitorService.
   */
  public static final class MonitorServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<MonitorServiceFutureStub> {
    private MonitorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MonitorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MonitorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.RegistrarUsuarioResponse> registrarUsuario(
        br.com.grpc.iot.RegistrarUsuarioRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegistrarUsuarioMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.RegistrarSensorResponse> registrarSensor(
        br.com.grpc.iot.RegistrarSensorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegistrarSensorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.StatusResposta> enviarDadosSensor(
        br.com.grpc.iot.SensorData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnviarDadosSensorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.UserResponse> getUser(
        br.com.grpc.iot.UserData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.SensoresResponse> listarSensores(
        br.com.grpc.iot.ListarSensoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListarSensoresMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.DadosResponse> getDados(
        br.com.grpc.iot.DadosRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDadosMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.GenerateDataResponse> generateData(
        br.com.grpc.iot.GenerateDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.grpc.iot.AllSensorsResponse> listAllSensors(
        br.com.grpc.iot.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListAllSensorsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTRAR_USUARIO = 0;
  private static final int METHODID_REGISTRAR_SENSOR = 1;
  private static final int METHODID_ENVIAR_DADOS_SENSOR = 2;
  private static final int METHODID_GET_USER = 3;
  private static final int METHODID_LISTAR_SENSORES = 4;
  private static final int METHODID_GET_DADOS = 5;
  private static final int METHODID_GENERATE_DATA = 6;
  private static final int METHODID_STREAM_REALTIME_SENSOR_DATA = 7;
  private static final int METHODID_LIST_ALL_SENSORS = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTRAR_USUARIO:
          serviceImpl.registrarUsuario((br.com.grpc.iot.RegistrarUsuarioRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.RegistrarUsuarioResponse>) responseObserver);
          break;
        case METHODID_REGISTRAR_SENSOR:
          serviceImpl.registrarSensor((br.com.grpc.iot.RegistrarSensorRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.RegistrarSensorResponse>) responseObserver);
          break;
        case METHODID_ENVIAR_DADOS_SENSOR:
          serviceImpl.enviarDadosSensor((br.com.grpc.iot.SensorData) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.StatusResposta>) responseObserver);
          break;
        case METHODID_GET_USER:
          serviceImpl.getUser((br.com.grpc.iot.UserData) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.UserResponse>) responseObserver);
          break;
        case METHODID_LISTAR_SENSORES:
          serviceImpl.listarSensores((br.com.grpc.iot.ListarSensoresRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.SensoresResponse>) responseObserver);
          break;
        case METHODID_GET_DADOS:
          serviceImpl.getDados((br.com.grpc.iot.DadosRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.DadosResponse>) responseObserver);
          break;
        case METHODID_GENERATE_DATA:
          serviceImpl.generateData((br.com.grpc.iot.GenerateDataRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.GenerateDataResponse>) responseObserver);
          break;
        case METHODID_STREAM_REALTIME_SENSOR_DATA:
          serviceImpl.streamRealtimeSensorData((br.com.grpc.iot.StreamRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.RealtimeSensorData>) responseObserver);
          break;
        case METHODID_LIST_ALL_SENSORS:
          serviceImpl.listAllSensors((br.com.grpc.iot.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<br.com.grpc.iot.AllSensorsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegistrarUsuarioMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.RegistrarUsuarioRequest,
              br.com.grpc.iot.RegistrarUsuarioResponse>(
                service, METHODID_REGISTRAR_USUARIO)))
        .addMethod(
          getRegistrarSensorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.RegistrarSensorRequest,
              br.com.grpc.iot.RegistrarSensorResponse>(
                service, METHODID_REGISTRAR_SENSOR)))
        .addMethod(
          getEnviarDadosSensorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.SensorData,
              br.com.grpc.iot.StatusResposta>(
                service, METHODID_ENVIAR_DADOS_SENSOR)))
        .addMethod(
          getGetUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.UserData,
              br.com.grpc.iot.UserResponse>(
                service, METHODID_GET_USER)))
        .addMethod(
          getListarSensoresMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.ListarSensoresRequest,
              br.com.grpc.iot.SensoresResponse>(
                service, METHODID_LISTAR_SENSORES)))
        .addMethod(
          getGetDadosMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.DadosRequest,
              br.com.grpc.iot.DadosResponse>(
                service, METHODID_GET_DADOS)))
        .addMethod(
          getGenerateDataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.GenerateDataRequest,
              br.com.grpc.iot.GenerateDataResponse>(
                service, METHODID_GENERATE_DATA)))
        .addMethod(
          getStreamRealtimeSensorDataMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              br.com.grpc.iot.StreamRequest,
              br.com.grpc.iot.RealtimeSensorData>(
                service, METHODID_STREAM_REALTIME_SENSOR_DATA)))
        .addMethod(
          getListAllSensorsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.grpc.iot.EmptyRequest,
              br.com.grpc.iot.AllSensorsResponse>(
                service, METHODID_LIST_ALL_SENSORS)))
        .build();
  }

  private static abstract class MonitorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MonitorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.com.grpc.iot.Contrato.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MonitorService");
    }
  }

  private static final class MonitorServiceFileDescriptorSupplier
      extends MonitorServiceBaseDescriptorSupplier {
    MonitorServiceFileDescriptorSupplier() {}
  }

  private static final class MonitorServiceMethodDescriptorSupplier
      extends MonitorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MonitorServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MonitorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MonitorServiceFileDescriptorSupplier())
              .addMethod(getRegistrarUsuarioMethod())
              .addMethod(getRegistrarSensorMethod())
              .addMethod(getEnviarDadosSensorMethod())
              .addMethod(getGetUserMethod())
              .addMethod(getListarSensoresMethod())
              .addMethod(getGetDadosMethod())
              .addMethod(getGenerateDataMethod())
              .addMethod(getStreamRealtimeSensorDataMethod())
              .addMethod(getListAllSensorsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
