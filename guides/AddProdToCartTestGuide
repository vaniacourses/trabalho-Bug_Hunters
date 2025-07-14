# Análise da Classe AddProdToCartTest

## 📋 Visão Geral

Classe de testes unitários criada para validar o comportamento do servlet `addtocartnull`, responsável por adicionar itens ao carrinho. Utiliza **JUnit 5** e **Mockito** para simular requisições HTTP e a interação com o DAO.  

O foco está em garantir que, conforme os dados de entrada e as respostas do DAO, o servlet execute corretamente as operações de adicionar ou atualizar o carrinho, gerando os redirecionamentos e cookies apropriados.

---

## 🛠️ Técnicas Utilizadas

### **1. Caixa Branca**

- Testa diretamente o método `doGet` do servlet, cobrindo diferentes fluxos condicionais
- Uso de mocks para simular objetos HTTP e DAO, possibilitando testar sem ambiente real

### **2. Testes de Caminhos Principais**

- Item já existe e update é bem-sucedido
- Item já existe e update falha
- Item não existe e add é bem-sucedido
- Item não existe e add falha
- Entrada inválida (parâmetros que geram exceção)

### **3. Uso de ArgumentCaptor**

- Validação do objeto `cart` criado e passado ao DAO, garantindo que todos os campos são setados corretamente
- Validação do cookie criado no response (nome, valor e tempo de vida)

---

## 📊 Estratégias de Cobertura

### **1. Cobertura de Fluxos Condicionais**

```java
// Exemplos dos testes mais relevantes
testDoGet_ItemExists_UpdateSuccess();
testDoGet_ItemExists_UpdateFail();
testDoGet_ItemDoesNotExist_AddSuccess();
testDoGet_ItemDoesNotExist_AddFail();
testDoGet_InvalidParameters_ThrowsException();
