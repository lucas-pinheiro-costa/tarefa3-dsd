# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: contrato.proto
# Protobuf Python Version: 6.31.0
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC,
    6,
    31,
    0,
    '',
    'contrato.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import timestamp_pb2 as google_dot_protobuf_dot_timestamp__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0e\x63ontrato.proto\x1a\x1fgoogle/protobuf/timestamp.proto\"t\n\nSensorData\x12\x11\n\tsensor_id\x18\x01 \x01(\t\x12\x13\n\x0btemperatura\x18\x02 \x01(\x02\x12\x0f\n\x07umidade\x18\x03 \x01(\x02\x12-\n\ttimestamp\x18\x04 \x01(\x0b\x32\x1a.google.protobuf.Timestamp\"D\n\x0eStatusResposta\x12\x10\n\x08mensagem\x18\x01 \x01(\t\x12 \n\x18total_leituras_recebidas\x18\x02 \x01(\x05\x32\x45\n\x0eMonitorService\x12\x33\n\x11\x45nviarDadosSensor\x12\x0b.SensorData\x1a\x0f.StatusResposta(\x01\x42\x13\n\x0f\x62r.com.grpc.iotP\x01\x62\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'contrato_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'\n\017br.com.grpc.iotP\001'
  _globals['_SENSORDATA']._serialized_start=51
  _globals['_SENSORDATA']._serialized_end=167
  _globals['_STATUSRESPOSTA']._serialized_start=169
  _globals['_STATUSRESPOSTA']._serialized_end=237
  _globals['_MONITORSERVICE']._serialized_start=239
  _globals['_MONITORSERVICE']._serialized_end=308
# @@protoc_insertion_point(module_scope)
