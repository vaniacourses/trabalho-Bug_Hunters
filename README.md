
# Ecommerce App

O objetivo do e-commerce App é oferecer uma plataforma completa e intuitiva para a compra e venda de produtos online, atendendo tanto clientes quanto administradores de forma eficiente e segura. O sistema permite que visitantes criem contas, acessem e visualizem detalhes dos produtos disponíveis, e entrem em contato com a loja para tirar dúvidas ou enviar mensagens. Após o registro, os clientes podem adicionar produtos ao carrinho, realizar compras, consultar o histórico de pedidos, visualizar detalhes dos produtos e gerenciar suas próprias compras, incluindo a possibilidade de deletar compras conforme a política da loja.

## Funcionalidades

- Adicionar Produtos no carrinho
- Registrar Usuário
- Realizar Login
- Realizar Logout
- Comprar um Produto
- Visualizar Compras recentes
- Deletar uma Compra
- Visualizar detalhes de produtos
- Preencher Formulário de Contato
- Adicionar Produto
- Gerenciar Clientes
- Deletar uma compra que não seja sua




## Plano de Teste
- [Plano de Teste](https://docs.google.com/document/d/1Iq4roJnVg1pyr0kOp4hbxOn9nQsFTtcIsNx82GgtNsU/edit?usp=sharing)


## Casos de Testes
- [Verificação de Registro e Login do Cliente](https://docs.google.com/document/d/1D0CXA5cJf06jZ1IqneT4Cv-6EB-2OaQXYwr8Lp4sgHo/edit?usp=sharing)
- [Verificação de Adição de um Novo Produto](https://docs.google.com/document/d/1joRWYE2tIPf17xQe9yUBcslsZ5TAlI-x4PoXRQNPxrQ/edit?usp=sharing)
- [Verificação de adição de item ao "carrinho"](https://docs.google.com/document/d/e/2PACX-1vRiXSeqF9upLPE8vMwhl3udrIxsjTUHNiDmUqB6Q2QEFhUVt8jY1T_K9PkCQl43LV3f3bnukUN_r6HT/pub)
- [Visualização dos detalhes do produto](https://docs.google.com/document/d/e/2PACX-1vSaOrRfbRXk9mD4TWbu2GyppyJgFEyHu5bA5hPh_1cfgsYkxolSxqJVbkMyrJe1OFe9HX3Ns7NFGmCP/pub)

- [Adicionar um pagamento em dinheiro com a opção de pagar na entrega e finalizar um pedido](https://docs.google.com/document/d/1E_41Txj8uJANZUCJw7R20RmnQptgiBqvcovQkw_CIg4/edit?tab=t.0)


## Testes Unitários
### Path
```
projeto
├── test
│   └── java
│       └── AddCustomerTest.java
|       └── AddNewProductTest.java
|       └── AddProdToCartTest.java

```

## Testes de Sistema Selenium
### Path
```
Os testes de selenium estão no repositório 
>>>> src/test/java/com/selenium <<<

Sendo eles:
 - AddCashPaymentAndOrderConfirmationTest.java 
    Testando o caso de Adicionar um pagamento em dinheiro com a opção de pagar na entrega e finalizar um pedido

- BasicNavigationTest.java
   Teste da configuração básica do selenium e navegação

- PerformanceTest.java
    Testes de performance do sistema.

Page Objects Model estão no repositório
    src/test/java/com/pages

```

## Testes de Integração
### Path
```

>>> src/integration
Testes de integração se encontram na pasta integration, foram criados direto no postman e a coleção foi exportada no arquivo  
- Teste integracao PayProcessIntegration.postman_collection.json

```

## Diagramas de Classe
- [AddCustomerTest.java](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/Diagram/AddCustomerTest.png?raw=true)

## Guia Detalhado Classes de Teste
- [AddCustomerTest](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/guides/AddCustomerTestGuide.md)
- [AddProdToCartTest](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/guides/AddProdToCartTestGuide.md)
- [AddNewProductTest](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/guides/AddNewProductTestGuide.md)



## Evidências Sonar
Evidências das melhorias de problemas apontados pelo Sonar 
![sonar_1](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/Analise_Sonar.png)
![sonar_2](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/sonar_graph.png)

## Detalhes das Melhorias Sonar 
- [Fix Sonar](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/guides/commits-sonar-table.md)

## Selenium (Testes de Sistema)
- [Execução Selenium](https://www.loom.com/share/e8f0f4ed01a44f2b8e61786166ca1fb3?sid=7667d04a-4ebc-4a26-b03d-f60f5cac0a2c)

## Entrega 2 Doc
- [Execução Selenium](https://docs.google.com/document/d/1pJSGkV3vPNXcux9QeKqKA9fqOVPWeQhE2hWuBJX_0mg/edit?usp=sharing)

## Stack utilizada

**Front-end:** HTML,CSS, Bootstrap,  JSP & Javascript

**Back-end:** Java

**DB:** MySQL

**Server:** TomCat

**Dependency Management:** Maven

**IDLE:** Intellij
