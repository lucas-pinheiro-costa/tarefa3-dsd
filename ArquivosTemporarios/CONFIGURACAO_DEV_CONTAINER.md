# Configuração do Dev Container para React + Django

Este documento contém as instruções para configurar corretamente o ambiente de desenvolvimento local para o projeto React + Django quando executado em dev containers.

## Problema Identificado

Quando executando o projeto em um dev container local (não GitHub Codespaces), o cliente React não é acessível através de browsers externos (como Microsoft Edge) devido a configurações de rede restritivas.

### Diferenças entre ambientes:

- **GitHub Codespaces**: Automaticamente expõe portas e cria URLs públicas
- **Dev Container Local**: Por padrão, apenas aceita conexões localhost

## Soluções Necessárias

### 1. Configurar Vite para Aceitar Conexões Externas

Edite o arquivo `Atividade3/projeto-gRPC-IoT/react-client/vite.config.ts`:

```typescript
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    host: '0.0.0.0', // Permite conexões de qualquer IP
    port: 5173,
    strictPort: true, // Falha se a porta não estiver disponível
    cors: true // Habilita CORS
  }
})
```

### 2. Configurar URLs para Ambiente Local

#### API URLs (React Client)

Edite `Atividade3/projeto-gRPC-IoT/react-client/src/api/api.ts`:

```typescript
// Para ambiente local (dev container)
const API_BASE_URL = 'http://localhost:8000/api';

// Para GitHub Codespaces (substituir conforme necessário)
// const API_BASE_URL = 'https://shiny-journey-69rp9jpgvrwvf5vpx-8000.app.github.dev/api';
```

#### WebSocket URLs (React Client)

Edite `Atividade3/projeto-gRPC-IoT/react-client/src/components/SensorList.tsx`:

```typescript
// Para ambiente local (dev container)
const websocket_url = 'ws://localhost:8000/ws/sensor-data/';

// Para GitHub Codespaces (substituir conforme necessário)
// const websocket_url = 'wss://shiny-journey-69rp9jpgvrwvf5vpx-8000.app.github.dev/ws/sensor-data/';
```

### 3. Configurar CORS no Django Gateway

Verifique se o arquivo `Atividade3/projeto-gRPC-IoT/django_gateway/django_gateway/settings.py` possui:

```python
CORS_ALLOW_ALL_ORIGINS = False

CORS_ALLOWED_ORIGINS = [
    "http://localhost:5173",  # Para dev container local
    "https://shiny-journey-69rp9jpgvrwvf5vpx-5173.app.github.dev",  # Codespaces React
    "https://shiny-journey-69rp9jpgvrwvf5vpx-8000.app.github.dev",  # Codespaces Django
]

CORS_ALLOW_CREDENTIALS = True
CORS_ALLOW_METHODS = ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS']
```

## Comandos para Execução

### 1. Iniciar Django Gateway

```bash
cd "Atividade3/projeto-gRPC-IoT/django_gateway"
python manage.py runserver
```

### 2. Iniciar React Client

```bash
cd "Atividade3/projeto-gRPC-IoT/react-client"
npm run dev
```

### 3. Verificar Serviços

```bash
# Verificar se Django está rodando na porta 8000
netstat -tlnp | grep :8000

# Verificar se React está rodando na porta 5173
netstat -tlnp | grep :5173
```

## Troubleshooting

### Problema: Página preta/branca no browser

1. **Verificar logs do terminal**: Procure por erros nos terminais do React e Django
2. **Reiniciar serviços**: Pare e reinicie tanto o Django quanto o React
3. **Limpar cache do browser**: Force refresh (Ctrl+F5)
4. **Verificar console do browser**: Abra Developer Tools → Console para ver erros JavaScript

### Problema: Erro de CORS

1. **Verificar URLs**: Certifique-se de que as URLs estão configuradas para localhost
2. **Verificar configuração Django**: Confirme que localhost:5173 está em CORS_ALLOWED_ORIGINS
3. **Reiniciar Django**: Mudanças em settings.py requerem restart

### Problema: Erro de conexão com API

1. **Verificar se Django está rodando**: `curl http://localhost:8000/api/users/by-email/?email=test@example.com`
2. **Verificar URLs da API**: Confirme que estão usando localhost no código React
3. **Verificar logs Django**: Procure por erros nos logs do servidor Django

## URLs de Acesso

- **React Client**: `http://localhost:5173/`
- **Django API**: `http://localhost:8000/api/`
- **Django Admin**: `http://localhost:8000/admin/`
- **API Documentation**: `http://localhost:8000/swagger/`

## Notas Importantes

- Sempre usar `localhost` para desenvolvimento local
- Usar URLs completas do Codespaces quando executando no GitHub Codespaces
- Certificar-se de que ambos os serviços estão rodando antes de testar
- O Vite pode mudar automaticamente a porta se 5173 estiver ocupada
- WebSocket usa `ws://` para local e `wss://` para HTTPS (Codespaces)

## Checklist de Verificação

- [ ] Vite configurado com `host: '0.0.0.0'`
- [ ] URLs da API configuradas para localhost
- [ ] URLs do WebSocket configuradas para localhost
- [ ] CORS configurado no Django para localhost:5173
- [ ] Django rodando na porta 8000
- [ ] React rodando na porta 5173
- [ ] Browser consegue acessar ambas as aplicações
