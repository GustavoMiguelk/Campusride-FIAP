RM5666666 - Gustavo Miguel Martins de Oliveira
RM566850 - Mariana de Paula Aguiar

# CampusRide

API REST para caronas solidárias entre alunos de uma universidade (Projeto Diamante — Java Advanced).

## Stack

- Java 17
- Spring Boot 4 (Web MVC, Data JPA, Validation)
- H2 (banco em memória)
- Gradle

## Como executar

Pré-requisitos: JDK 17+.

```bash
./gradlew bootRun
```

A aplicação sobe em `http://localhost:8080`. O console do H2 fica em `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:campusride`, usuário `sa`, senha em branco).

## Testando manualmente

O arquivo `requests.http` tem uma coleção de requisições cobrindo o fluxo feliz e os principais erros
esperados — vagas incompatíveis com o veículo, carona/reserva inexistente, carona lotada, cancelamentos
repetidos, etc.

## Endpoints

| Método | Rota                              | Descrição                          |
|--------|------------------------------------|-------------------------------------|
| POST   | /api/caronas                      | Publica uma carona                  |
| GET    | /api/caronas                      | Lista caronas (padrão: disponíveis) |
| GET    | /api/caronas/{id}                 | Detalha uma carona com reservas     |
| DELETE | /api/caronas/{id}                 | Cancela uma carona                  |
| POST   | /api/caronas/{caronaId}/reservas  | Reserva uma vaga                    |
| DELETE | /api/reservas/{id}                | Cancela uma reserva                 |

## Validação avançada

`@VagasCompativeisComVeiculo` impede que o número de vagas oferecidas ultrapasse a capacidade máxima
do tipo de veículo escolhido.

## Tratamento de erros

Todo erro volta no mesmo formato, sem stack trace, com status coerente: 400 (validação), 404 (não
encontrado), 409 (regra de negócio violada), 500 (erro não mapeado).