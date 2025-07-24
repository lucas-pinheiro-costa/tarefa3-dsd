# Projeto: Monitoramento de sensores IoT com gRPC

Este é um sistema de demonstração desenvolvido para a disciplina de Desenvolvimento de Sistemas Distribuídos (DSD), lecionado pelo professor Gracon Huttennberg E. L. de Lima, que utiliza **gRPC** para estabelecer uma comunicação de alta performance entre um servidor central (monitor) e múltiplos clientes (sensores).

O objetivo é demonstrar como uma interface de usuário interativa, construída com **React**, pode se comunicar com um backend de alta performance escrito em **Java e gRPC** através de um **API Gateway** que atua como uma ponte, desenvolvido em **Python** com **Django**.

## Arquitetura empregada

A arquitetura do projeto foi desenhada em um modelo de três camadas para garantir desacoplamento, escalabilidade e a especialização de cada tecnologia em sua melhor função.

1. **Frontend (React):** Uma aplicação de página única (SPA) interativa que roda no navegador. É responsável por toda a interface gráfica e experiência do usuário.

1. **API Gateway (Python/Django):** Um microsserviço que atua como um "tradutor". Ele expõe uma API RESTful (HTTP/JSON) para o frontend e converte essas chamadas em requisições gRPC para o servidor principal.

1. **Backend (Java/gRPC):** O cérebro do sistema, responsável pela lógica de negócio (gerenciamento de usuários, sensores) e pela persistência dos dados em um banco H2.

```
+----------------+      (Requisições       +----------------+      (Chamadas      +-----------------+
|                |      HTTP 1.1/JSON)     |                |        gRPC)        |                 |
|  Cliente Web   | <---------------------> |   API Gateway  | <-----------------> | Servidor gRPC   |
|    (React)     |                         |    (Django)    |                     |     (Java)      |
| (no Navegador) |                         |                |                     | (com Banco H2)  |
|                | ----------------------> |                | ------------------> |                 |
+----------------+                         +----------------+                     +-----------------+
```

<br>

## Como Executar o Projeto (Ambiente Completo)

Para executar e testar a aplicação, é necessário rodar os três componentes (Servidor Java, API Gateway Django e Cliente React) simultaneamente.

> [!IMPORTANT]
> Você precisará de 3 terminais abertos para executar cada serviço de forma independente.

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