## 😎 Sobre o projeto

Trata-se de uma aplicação criada para realizar a busca de informações em páginas web, também conhecidos como [Crawler](https://globalad.com.br/blog/o-que-e-crawler/), este do projeto em específico busca as informações dos top 10 piores filmes do [Imdb](https://www.imdb.com/chart/bottom) e as disponibiliza em uma API Rest.

Created with [Quarkus](https://quarkus.io/), the Supersonic Subatomic Java Framework.

---

## 🤓 Tecnologias utilizadas

* 🔤 Lang - [JAVA](https://www.java.com/pt-BR/)
* ⚛️ Framework - [Quarkus](https://quarkus.io/)
* 📃 Doc - [Swagger](https://swagger.io/)
* ✅ Test - [JUnit5](https://junit.org/junit5/)
* ✅ Test - [Approval Tests](https://approvaltests.com/)

---

## 🎯 Desafios do Projeto

* ✅ A Lista dos 10 filmes com pior nota no site, em ordem decrescente de melhor para pior nota.
* ✅ Nome do Filme (em inglês).
* ✅ Nota (com uma casa decimal).
* ✅ Diretor(es).
* ✅ Elenco principal.
* ✅ Ao menos um comentário POSITIVO sobre o filme (comentário que deu uma nota >= 5).

Extras adicionados:
* ✅ Adição do Header Param "review_star" para ser possível o usuário definir a quantidade de estrelas do primeiro comentário possitivo.
* ✅ Documentação da API com Swagger.
* ✅ Teste unitário da rota.
---

## 🧑‍💻 Como iniciar a aplicação

### Executando o aplicativo no dev mode

Você pode executar em dev mode utilizando os seguinte comando:
```script de shell
./mvnw quarkus:dev
```

> **_NOTE:_** Quarkus vem com uma Dev UI disponível em dev mode em http://localhost:8080/q/dev/.

Adicionada também documentação da API com **Swagger** disponível em: **http://localhost:8080/q/swagger-ui/#**.

Exemplo da documentação da API:
![Swagger Exemple](https://user-images.githubusercontent.com/61207420/184554981-302ba4f1-a5f7-491a-a262-57221b9c9b5c.png)


### Empacotando e executando a aplicação

A aplicação pode ser empacotada usando:
```script de shell
./mvnw package
```
O comando produz o arquivo `quarkus-run.jar` no diretório `target/quarkus-app/`.

Esteja ciente de que não é um _über-jar_ pois as dependências são copiadas para o diretório `target/quarkus-app/lib/`.

A aplicação pode ser executada com o comando: 
```bash
java -jar target/quarkus-app/quarkus-run.jar
```

Se você deseja construir um _über-jar_, execute o seguinte comando:
```bash
./mvnw package -Dquarkus.package.type=uber-jar
```

A aplicação empacotada como um _über-jar_ pode ser executada com: 
```bash
java -jar target/*-runner.jar
```

---

## 📃 Licença

Este projeto está sobre a licença [MIT](LICENSE).