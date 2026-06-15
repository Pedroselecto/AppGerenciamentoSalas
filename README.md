# Gerenciador de Salas IBMEC

## Descrição da Proposta
Aplicativo Android desenvolvido para otimizar o gerenciamento de salas, permitindo a visualização, criação e exclusão de aulas agendadas através de uma interface interativa que consome uma API RESTful.

## Tecnologias Utilizadas
* **Front-end Mobile:** Kotlin, XML.
* **Comunicação de Rede:** Retrofit2 e Gson.
* **Back-end:** Java, Spring Boot.
* **Banco de Dados:** MySQL.

## Instruções de Execução
1. Clone o repositório da API Spring Boot (https://github.com/Pedroselecto/AP2_DevMobile_2026-1) e inicie o servidor localmente (porta padrão 8080).
2. Certifique-se de que o banco MySQL está em execução na porta 3306 e atualize as credenciais no arquivo `application.properties` do back-end.
3. Clone este repositório do aplicativo Android e abra-o no Android Studio.
4. Execute o projeto em um emulador.

## Prints do Aplicativo

<img width="267" height="570" alt="telaDeLoginApp" src="https://github.com/user-attachments/assets/50e90a55-49cd-49d8-a354-1833526930da" />

Tela de Login do app.

<img width="277" height="565" alt="TelaAulasDeHoje" src="https://github.com/user-attachments/assets/32e82290-ac53-4fec-89f9-eb52618b907e" />

Tela inicial que mostra as aulas do dia. Usa a classe nativa "Calendar" para identificar o dia no dispositivo do usuário.

<img width="262" height="560" alt="TelaSalasApp" src="https://github.com/user-attachments/assets/85421d1e-f3a8-4ba1-a3ba-976156788116" />

Tela que mostra todas as salas divididas por andar. O botão de "+" na direita de cada card serve para adicionar uma aula à sala. A função de adicionar aula só pode ser acessada por um admin.

<img width="262" height="560" alt="TelaAddNovaAulaApp" src="https://github.com/user-attachments/assets/75d31308-b780-426e-84d2-052f500f4e99" />

Dialog que serve para registrar as informações da aula adicionada na sala determinada.

<img width="270" height="562" alt="TelaAulasApp" src="https://github.com/user-attachments/assets/e250c040-8ac3-4ddf-ab42-b34a9b4d008c" />

Tela que mostra todas as aulas cadastradas no banco e permite a exclusão delas. (Pertence somente ao admin)

<img width="267" height="562" alt="TelaPerfilApp" src="https://github.com/user-attachments/assets/58fe05cb-b10d-485c-8001-134e036b7dae" />

Tela de perfil contendo um botão que leva de volta à tela de login e uma intent implícita no botão de suporte que manda para o site do ibmec.

## Descrição da API
A API RESTful construída em Spring Boot atua como o motor do aplicativo, sendo responsável por processar as requisições de leitura, criação e exclusão (CRUD) das disciplinas, professores e salas. A comunicação com a aplicação mobile é realizada exclusivamente através de requisições HTTP, trafegando os dados estruturados no formato JSON.

## Link do Swagger
Quando o servidor local estiver em execução, a documentação OpenAPI detalhada da API pode ser acessada através do link:
`http://localhost:8080/swagger-ui.html`
