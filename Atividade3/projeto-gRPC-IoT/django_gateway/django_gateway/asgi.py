import os
import django
from channels.routing import ProtocolTypeRouter, URLRouter
from django.core.asgi import get_asgi_application

# Configura o Django
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'django_gateway.settings')
django.setup()

# Importa o roteamento WebSocket após django.setup()
from iot_api import routing # Onde definiremos as rotas WebSocket

application = ProtocolTypeRouter({
    "http": get_asgi_application(), # Para requisições HTTP normais (REST)
    "websocket": URLRouter(routing.websocket_urlpatterns), # Para requisições WebSocket
})
