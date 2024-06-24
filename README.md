# Microserviço de Gerenciamento de Compras de Vinhos

Este microserviço foi desenvolvido para teste onde gerencia informações de compras de vinhos, utilizando uma arquitetura hexagonal e consumindo dados de duas APIs externas para produtos e clientes.


### Java
![Java](images/java.png)

### Spring Boot
![Spring Boot](images/spring-boot.png)
## Funcionalidades

O microserviço oferece os seguintes endpoints:

1. **GET /api/compras**
   - Retorna uma lista de compras ordenada por valor, contendo informações detalhadas sobre cada compra, incluindo nome do cliente, CPF do cliente, detalhes do produto, quantidade e valor total da compra.
   - URL: `http://localhost:8080/api/compras`

2. **GET /api/maior-compra/{ano}**
   - Retorna a maior compra do ano informado, com detalhes como nome do cliente, CPF do cliente, detalhes do produto, quantidade da compra e seu valor total.
   - Exemplo de URL: `http://localhost:8080/api/maior-compra/2016`

3. **GET /api/clientes-fieis**
   - Retorna os top 3 clientes mais fiéis, baseado no valor total das compras realizadas.
   - URL: `http://localhost:8080/api/clientes-fieis`

4. **GET /api/recomendacao/{cliente}/{tipo}**
   - Retorna uma recomendação de vinho baseada no tipo de vinho mais comprado pelo cliente especificado.
   - Exemplo de URL: `http://localhost:8080/api/recomendacao/{cliente}/{tipo}`

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.1
- Feign Client para integração com APIs externas
- Maven para gerenciamento de dependências
- Jackson para mapeamento JSON

### Spring Boot

Este projeto utiliza o Spring Boot como framework base para aplicativos Java. A versão específica utilizada é a 3.3.1. O Spring Boot simplifica o desenvolvimento de aplicativos Java, fornecendo configurações padrão e facilitando a criação de aplicativos independentes e prontos para produção.

## Estrutura do Projeto

A estrutura do projeto segue uma arquitetura hexagonal (ou arquitetura de portas e adaptadores), organizada da seguinte forma:


- **MicroserviceApplication.java:** Ponto de entrada da aplicação Spring Boot.
- **domain/model/:** Modelos de dados que representam Clientes, Produtos e Compras.
- **domain/service/:** Lógica de negócio da aplicação, incluindo serviços para manipulação de compras.
- **application/rest/:** Controladores REST que definem os endpoints da API.
- **infrastructure/client/:** Clientes Feign para integração com APIs externas de Produtos e Clientes.
- **config/:** Configurações do Feign e outras configurações de aplicação.

## Configuração

Para executar o microserviço localmente, é necessário ter o Java 17 e Maven instalados. Clone o repositório e execute os seguintes comandos:

```bash
mvn clean install
mvn spring-boot:run


