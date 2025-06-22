# Spring Boot Essentials - API RESTful de Animes

![Java](https://img.shields.io/badge/java-%236DB33F.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white)

Este projeto é uma API RESTful completa para o gerenciamento de um catálogo de Animes, desenvolvida com uma pilha de tecnologias modernas e uma arquitetura totalmente containerizada. A aplicação, o banco de dados e a pilha de monitoramento são orquestrados via **Docker Compose**.

A API implementa operações CRUD, paginação e busca, com endpoints protegidos por **Spring Security e JWT**. A documentação é gerada dinamicamente com **Springdoc OpenAPI**, e a aplicação é monitorada em tempo real com **Prometheus e Grafana**.

## 🛠️ Tecnologias Utilizadas

A arquitetura do projeto é totalmente containerizada e utiliza uma pilha de tecnologias modernas para desenvolvimento, monitoramento e execução. A seguir, a lista detalhada com base nos arquivos `pom.xml` e `docker-compose.yml`.

| Categoria | Tecnologia | Versão/Detalhe | Propósito |
| :--- | :--- | :--- | :--- |
| **Backend & Framework** | Java | 17 | Linguagem principal da aplicação. |
| | Spring Boot | 3.3.1 | Framework principal para criação da API. |
| | Spring Web (MVC) | - | Criação de endpoints RESTful. |
| | Spring Data JPA | - | Camada de persistência e comunicação com o banco. |
| **Banco de Dados** | MySQL | 8.0 | Banco de dados de produção/desenvolvimento, gerenciado via Docker. |
| **Segurança** | Spring Security | 6.x | Gerenciamento de autenticação e autorização. |
| | JWT (JSON Web Tokens) | `jjwt` | Implementação de autenticação stateless. |
| **Observability** | Prometheus | `latest` | Coleta de métricas da aplicação via Actuator. |
| | Grafana | `latest` | Visualização de métricas e criação de dashboards. |
| | Spring Boot Actuator | - | Exposição de métricas e endpoints de gerenciamento. |
| **Build & Containerização** | Maven | 3.9+ | Gerenciamento de dependências e build do projeto. |
| | Docker | - | Plataforma de containerização da aplicação e serviços. |
| | Docker Compose | v3.8 | Orquestração do ambiente de desenvolvimento local. |
| | Jib (Plugin Maven) | - | Otimização da criação de imagens Docker sem Dockerfile. |
| **Documentação da API**| Springdoc OpenAPI | `2.5.0` | Geração automática de documentação interativa (Swagger UI). |
| **Testes** | H2 Database | - | Banco de dados em memória para testes rápidos e isolados. |
| | JUnit 5, Mockito, AssertJ | - | Ferramentas para testes unitários e de integração. |
| **Utilitários** | MapStruct | `1.5.5.Final`| Mapeamento eficiente e seguro entre DTOs e Entidades. |

## 🎯 Pré-requisitos

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior (Para build local com Maven)
- [Apache Maven](https://maven.apache.org/download.cgi) 3.9+ (Para build local com Maven)
- [Docker](https://www.docker.com/products/docker-desktop/) e **Docker Compose**

## 🚀 Como Executar (Ambiente Completo)

O método recomendado para executar o projeto é utilizando Docker Compose, que irá orquestrar todos os serviços necessários (API, Banco de Dados, Prometheus, Grafana) de forma automática.

**1. Clone o Repositório**
```bash
git clone [https://github.com/juaovictor0101/springboot-essentials.git](https://github.com/juaovictor0101/springboot-essentials.git)
cd springboot-essentials
```

**2. Crie a Imagem Docker da Aplicação**

O `docker-compose.yml` utiliza uma imagem Docker da aplicação. Primeiro, você precisa criá-la usando o plugin Jib do Maven.

```bash
mvn compile jib:dockerBuild
```

**3. Inicie a Pilha de Serviços**

Com a imagem criada, suba todos os containers com um único comando:

```bash
docker-compose up -d
```
O parâmetro `-d` executa os containers em segundo plano (detached mode).

**4. Acesse os Serviços**

Após a inicialização, os seguintes serviços estarão disponíveis:

- **API RESTful de Animes**: `http://localhost:8080`
- **Documentação (Swagger UI)**: `http://localhost:8080/swagger-ui.html`
- **Prometheus**: `http://localhost:9090`
- **Grafana**: `http://localhost:3000` (login padrão: `admin`/`admin`)
- **MySQL (via cliente externo)**: `localhost:3307`

## 🔬 Executando Apenas a API (Para Desenvolvimento)

Se você precisa depurar a aplicação Java diretamente na sua IDE, pode executar apenas a API localmente e conectar aos outros serviços rodando no Docker.

1.  **Inicie os serviços de dependência:**
    ```bash
    docker-compose up -d db prometheus grafana
    ```
2.  **Ajuste a configuração local:**
    No seu arquivo `src/main/resources/application.properties`, garanta que a URL do banco de dados aponte para a porta `3307` (exposta no host pelo Docker Compose).
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3307/anime
    spring.datasource.username=root
    spring.datasource.password=root
    ```
3.  **Inicie a aplicação com Maven:**
    Lembre-se de passar o segredo JWT como argumento.
    ```bash
    # Substitua 'MY_ULTRA_SECRET_KEY' por um segredo de sua escolha
    mvn spring-boot:run -Dspring-boot.run.arguments="--jwt.secret=MY_ULTRA_SECRET_KEY"
    ```

## 📊 Observability (Prometheus + Grafana)

A pilha de monitoramento já está pré-configurada.

-   **Prometheus**: Acesse `http://localhost:9090`. No menu *Status -> Targets*, você verá que o Prometheus já está coletando métricas (`scraping`) do serviço `springboot-essentials`.
-   **Grafana**: Acesse `http://localhost:3000`. Você pode configurar um Data Source do tipo "Prometheus" apontando para `http://prometheus:9090` e importar dashboards prontos para "Spring Boot" ou "JVM (Micrometer)" para visualizar as métricas da API.

## ✅ Testes

O projeto adota uma estratégia de testes em duas camadas para garantir velocidade e confiabilidade:

1.  **Testes com H2 (Banco em Memória)**: Ao executar a fase de testes padrão do Maven, o Spring Boot autoconfigura o H2, um banco de dados em memória, para rodar os testes de forma rápida e isolada, sem a necessidade de um ambiente externo.
    ```bash
    # Executa testes unitários e de integração usando H2
    mvn test
    ```
2.  **Testes de Integração com MySQL**: Para garantir que a aplicação funciona com o banco de dados de produção, um profile do Maven (`integration-tests`) foi criado. Ao ativá-lo, os testes de integração são executados contra o banco de dados **MySQL** real (que deve estar rodando, por exemplo, via Docker Compose).
    ```bash
    # Executa TODOS os testes, incluindo os de integração com o MySQL
    mvn verify -Pintegration-tests
    ```