from django.urls import re_path
from . import consumers

websocket_urlpatterns = [
    # Esta rota capturará conexões WebSocket para ws://localhost:8000/ws/sensor-data/
    re_path(r'ws/sensor-data/$', consumers.RealtimeSensorConsumer.as_asgi()),
]
