<h1 align="center">
    Desafio Backend BTG Pactual
</h1>

solução do desafio [backend BTG Pactual](https://github.com/buildrun-tech/buildrun-desafio-backend-btg-pactual/blob/main/problem.md)
## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [RabbitMQ](https://www.rabbitmq.com/)
- [Docker](https://www.docker.com/)




## Como Executar

- Clonar repositório git
```
git clone git@github.com:igorbarret0/btgPactualChallenge.git
```

- Construir o projeto:
```
./mvnw clean package
```


A API poderá ser acessada em [localhost:8080](http://localhost:8080).

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/)

- ORDER
```
GET /customers/{customerId}/orders - Obter dados do cliente
```

