# Projeto: Monitoramento de sensores IoT com gRPC

Este é um sistema de demonstração desenvolvido para a disciplina de Desenvolvimento de Sistemas Distribuídos (DSD), lecionado pelo professor Gracon Huttennberg E. L. de Lima, que utiliza **gRPC** para estabelecer uma comunicação de alta performance entre um servidor central (monitor) e múltiplos clientes (sensores).

O objetivo é demonstrar como uma interface de usuário interativa, construída com **React**, pode se comunicar com um backend de alta performance escrito em **Java e gRPC** através de um **API Gateway** que atua como uma ponte, desenvolvido em **Python** com **Django**.

## Arquitetura empregada

A arquitetura do projeto foi desenhada em um modelo de quatro camadas para garantir desacoplamento, escalabilidade e a especialização de cada tecnologia em sua melhor função, demonstrando três protocolos de comunicação diferentes.

1. **Frontend (React):** Uma aplicação de página única (SPA) interativa que roda no navegador. É responsável por toda a interface gráfica e experiência do usuário.

2. **API Gateway (Python/Django):** Um microsserviço que atua como um "tradutor". Ele expõe uma API RESTful (HTTP/JSON) para o frontend e converte essas chamadas em requisições gRPC para o servidor principal.

3. **Backend (Java/gRPC):** O cérebro do sistema, responsável pela lógica de negócio (gerenciamento de usuários, sensores) e pela persistência dos dados em um banco H2.

4. **Sistema de auditoria (sistemas legados):** Um sistema externo que recebe notificações de conformidade e auditoria através de SOAP/XML para demonstrar integração com sistemas corporativos ou legados.

### Responsabilidades dos Protocolos

**REST (HTTP/JSON) - A API pública e moderna:**
- **Papel:** REST é a forma principal e padronizada pela qual o mundo externo interage com o sistema.
- **Onde:** Na comunicação entre o Cliente Web (React) e o API Gateway (Django).
- **Por quê:** REST é o padrão de fato para APIs web consumidas por frontends. É flexível, sem estado (stateless) e usa padrões HTTP e JSON bem compreendidos por todos os navegadores e frameworks.

**gRPC - O núcleo de performance (a comunicação interna):**
- **Papel:** gRPC é a espinha dorsal da comunicação interna de alta velocidade.
- **Onde:** Na comunicação entre o API Gateway (Django) e o Servidor de Lógica (Java).
- **Por quê:** Para a comunicação serviço-a-serviço, onde o desempenho e a segurança de tipos são cruciais, o gRPC é a melhor ferramenta.

**SOAP (XML) - A integração corporativa ou legada:**
- **Papel:** SOAP é usado para demonstrar um cenário de integração com um sistema externo que exige um contrato mais rígido e formal.
- **Onde:** Na comunicação entre o Servidor de Lógica (Java) e o Sistema de Auditoria externo.
- **Por quê:** SOAP, com seu contrato estrito definido por um WSDL e seu formato XML, é perfeito para simular cenários B2B ou de integração com sistemas mais antigos. Toda vez que um novo sensor é registrado, o sistema notifica o Sistema de Auditoria por razões de conformidade.

```
+------------------+    (Requisições      +-------------------+    (Chamadas       +---------------------+    (SOAP/XML)     +------------------------+
|                  |    HTTP 1.1/JSON)    |                   |      gRPC)         |                     |                   |                        |
|   Cliente Web    | <------------------> |   API Gateway     | <----------------> |   Servidor gRPC     | <---------------> |  Sistema de Auditoria  |
|     (React)      |                      |     (Django)      |                    |      (Java)         |                   |    (Ex: Legado)        |
|  (no Navegador)  |                      |                   |                    |   (com Banco H2)    |                   |                        |
|                  |                      |  - REST API       |                    |  - Lógica Negócio   |                   |  - Logs de Auditoria   |
|  - Interface UI  |                      |  - Conversão      |                    |  - Gestão Sensores  |                   |  - Conformidade        |
|  - Formulários   | -------------------> |    HTTP -> gRPC   | -----------------> |  - Persistência     | ----------------> |  - Relatórios          |
|  - Dashboards    |                      |  - Validação      |                    |  - Validação Dados  |                   |  - Integração Legado   |
|                  |                      |                   |                    |                     |                   |                        |
+------------------+                      +-------------------+                    +---------------------+                   +------------------------+

### Fluxo de comunicação e protocolos

**Exemplo de fluxo completo:**

1. **REST (Cliente → API Gateway):** O usuário no React clica em "Cadastrar novo sensor"
   - Uma requisição `POST /api/sensors` é enviada via HTTP/JSON para o Django

2. **gRPC (API Gateway → Servidor Java):** O Django processa a requisição REST
   - Converte os dados JSON em uma mensagem gRPC `CreateSensorRequest`
   - Envia via gRPC para o servidor Java na porta 50051

3. **Java (processamento):** O servidor Java processa a lógica de negócio
   - Valida os dados do sensor
   - Persiste no banco H2
   - Prepara resposta para o Django

4. **SOAP (Servidor Java → Sistema de auditoria):** Por compliance, o Java notifica o sistema de auditoria
   - Cria uma mensagem SOAP/XML com os dados do sensor registrado
   - Envia para o sistema de auditoria externo (simulado via logs)

5. **Retorno:** As respostas seguem o caminho inverso até chegar ao usuário React

**Demonstração dos três "protocolos":**
- **REST:** Visível no navegador Web e nos logs do Django
- **gRPC:** Visível nos logs do Django (cliente) e Java (servidor)
- **SOAP:** Visível nos logs do Java quando sensores são registrados
```

<br>

## Como Executar o Projeto (Ambiente Completo)

Para executar e testar a aplicação, é necessário rodar os três componentes principais (Servidor Java, API Gateway Django e Cliente React) simultaneamente. O Sistema de auditoria é simulado através de logs demonstrativos que mostram como seria a integração SOAP/XML.

> [!IMPORTANT]
> Você precisará de 3 terminais abertos para executar cada serviço de forma independente. O sistema de auditoria SOAP é demonstrado através de logs no servidor Java.

### Pré-requisitos

Antes de começar, garanta que você tenha os seguintes programas instalados:

1.  **Git:** Para clonar o repositório.
2.  **Java (JDK 11+) e Maven**
3.  **Python (3.8+) e Pip**
6.  **Node.js (16+) e NPM**

* **Clone o Repositório:** Abra um terminal ou prompt de comando e execute o comando abaixo para baixar o projeto.
    ```bash
    git clone https://github.com/lucas-pinheiro-costa/tarefa3-dsd.git
    ```

<br>

## Instruções para execução do Servidor

Para iniciar o servidor na sua máquina e permitir que os clientes se conectem, siga estes passos.

### Passo a Passo

1.  **Encontre seu Endereço IP Local:** os clientes precisarão deste endereço.
    -   No **Windows**, abra o `cmd` e digite `ipconfig`. Procure pelo "Endereço IPv4".
    -   No **Linux** ou **macOS**, abra o terminal e digite `ifconfig` ou `ip a`. Procure pelo seu endereço de rede local (geralmente começa com `192.168`, `10.0` ou `172.16`).

2.  **Navegue para a pasta do Servidor:** Em um terminal, na raiz do projeto, entre na pasta do serviço Java.
    ```bash
    cd Atividade3/projeto-gRPC-IoT/servico-java-monitor
    ```

3.  **Execute o Servidor Java:**
    ```bash
    mvn clean install
    mvn exec:java -Dexec.mainClass="br.com.grpc.iot.MonitorServer"
    ```

4.  **Deixe este terminal rodando:** Ele deve exibir "Servidor gRPC iniciado na porta 50051".

<br>

## Instruções para execução da API Gateway

### Passo a Passo

1.  **Crie e ative um Ambiente Virtual:**
    ```bash
    # Navegue para a pasta do gateway
    cd Atividade3/projeto-gRPC-IoT/django_gateway
    
    # Crie e ative o ambiente virtual
    python -m venv .venv
    
    # No Windows:
    .venv\Scripts\activate
    # No Linux ou macOS:
    source .venv/bin/activate
    ```
    > [!NOTE]
    > Após ativar, você deverá ver um `(.venv)` no início da linha de seu terminal.

2.  **Instale as dependências Python:** Instale as bibliotecas necessárias, incluindo o gRPC.
    ```bash
    # Instale os pacotes
    pip install -r requirements.txt
    ```

3.  **Inicie o servidor Django:**
    ```bash
    python manage.py runserver
    ```

4.  **Deixe este terminal rodando:** Ele deve exibir "Starting development server at http://127.0.0.1:8000/".

<br>

## Instruções para execução do Cliente Web (React)

### Passo a Passo

1. **Navegue para a pasta do cliente React:**
```bash
cd Atividade3/projeto-gRPC-IoT/react-client
```

2. **Instale as dependências do Node.js:**
```bash
npm install
```

3. **Inicie o servidor de desenvolvimento:**
```bash
npm run dev
```

4. **Deixe este terminal rodando:** Ele fornecerá uma URL local para acessar a aplicação, geralmente http://localhost:5173.

5. **Acesse a Aplicação Web:**
```bash
# Abra seu navegador e vá para a URL fornecida pelo React (a do Terminal 3, ex: http://localhost:5173).
# Interaja com a Interface: Você verá a página web do projeto. Utilize os formulários e botões para:
#   - Cadastrar um novo usuário.
#   - Buscar os sensores de um usuário existente.
#   - Realizar as demais operações disponíveis na interface.

# Observe os Logs: A melhor forma de verificar se tudo está funcionando é observar os terminais. Ao interagir com o site, você verá logs aparecendo:
#   - No Terminal 3 (React/Vite), você verá logs do frontend.
#   - No Terminal 2 (Django), você verá as requisições HTTP chegando do navegador (GET /api/..., POST /api/...).Buscar os sensores de um usuário existente.
#   - No Terminal 1 (Java), você verá as chamadas gRPC que o Django fez em nome do navegador, e os logs de interação com o banco de dados.
```

<br>

## Autores

1. **Lucas de Moraes dos Santos e** 
2. **Lucas Pinheiro Costa**