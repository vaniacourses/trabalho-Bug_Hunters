# Análise da Classe ViewProductDetailsTest

## 📋 Visão Geral

Classe de testes unitários desenvolvida para validar as funcionalidades da `ViewProductDetailsTest`, que lida com a visualização de detalhes de produtos, gestão de usuários, carrinho de compras e pedidos. Utiliza **JUnit 5** e **Mockito** para simulação de dependências, com foco na verificação de interações, retornos esperados e tratamento de exceções.

---

## Diagrama de Classe após as técnicas
![Diagrama](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/diagramaViewProductDetailsTest.png)

## 🛠️ Técnicas Utilizadas

### **1. Caixa Branca com Mocks**

- Adoção de mocks para `Connection`, `PreparedStatement` e `ResultSet`, isolando a lógica da DAO.
- Testes diretos nos métodos do `DAO2`, explorando diferentes fluxos e interações.

### **2. Partições de Equivalência e Valores Limite**

- Validação de comportamentos com inputs válidos, nulos, vazios e extremos
- Testes com `@NullAndEmptySource` e `@ValueSource` para inputs inválidos

---

### **Evidência Pitest**
![Pitest Imagem](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/pitestViewProductDetailsTest.png)


## 📊 Estratégias de Cobertura

### **1. Visualização de Produtos**

```java
getSelecteditem_shouldReturnListWhenProductExists();  // Produto encontrado
getSelecteditem_shouldReturnEmptyListForInvalidInput(); // Entradas nulas, vazias
getAllviewlist_shouldReturnMultipleProducts();        // Simulação com múltiplos registros
getAllviewlist_shouldReturnEmptyListOnSqlException(); // Tratamento de exceções SQL
```

### **2. Gestão de Usuários**

```java
checkcust_shouldReturnTrueForValidCredentials();      // Login válido
checkcust_shouldReturnFalseForInvalidCredentials();   // Login inválido
addcustomer_shouldReturnOneOnSuccess();               // Cadastro bem-sucedido
addcustomer_shouldReturnZeroOnSqlException();         // Exceção ao inserir
checkadmin_shouldReturnTrueForValidAdmin();           // Autenticação de admin
checkcust2_shouldReturnTrueWhenUserExists();          // Verificação de existência
```

### **3. Operações de Carrinho**

```java
checkaddtocartnull_shouldDelegateAndReturnBoolean();  // Verificação de item no carrinho
updateaddtocartnull_shouldDelegateAndReturnInt();     // Atualização de item
addtocartnull_shouldDelegateAndReturnInt();           // Adição de item
getSelectedcart_shouldDelegateAndReturnList();        // Obtenção de itens do carrinho
getcart_shouldDelegateAndReturnList();                // Obtenção por usuário
removecart_shouldDelegateAndReturnInt();              // Remoção do carrinho
```

### **4. Gestão de Pedidos**

```java
removeorders_shouldReturnOneOnSuccess();              // Remoção bem-sucedida
removeorders_shouldReturnZeroOnFailure();             // Falha na remoção
removeorders_shouldReturnZeroOnException();           // Exceção ao remover
```

---

## 🎲 Técnicas de Teste de Mutação

### **Mutantes Relevantes Detectáveis**

- **Inversão de condições**: simulações com `next()` retornando true/false
- **Remoção de chamadas de método**: como `setObject` e `executeUpdate`
- **Ignorar tratamento de exceções SQL**: testado com `thenThrow(new SQLException())`

---

## 🔍 Padrões de Teste Identificados

### **1. Padrão Arrange-Act-Assert (AAA)**

```java
when(mockResultSet.next()).thenReturn(true);         // Arrange
boolean result = dao.checkcust(customer);            // Act
assertTrue(result);                                  // Assert
```

### **2. Testes Aninhados com @Nested**

- Agrupamento lógico de testes em:
  - `ItemViewTests`
  - `UserManagementTests`
  - `CartDelegationTests`
  - `OrderManagementTests`

---

## 📈 Métricas de Qualidade

| Métrica    | Valor Estimado                               |
| ---------- | -------------------------------------------- |
| Statements | Alto (próximo de 100%)                       |
| Branches   | 80% (onde aplicável) |
| Caminhos   | Moderado-alto (vários fluxos testados)       |

---

## 🔧 Ferramentas e Frameworks

| Ferramenta          | Propósito                         | Benefício                                        |
| ------------------- | --------------------------------- | ------------------------------------------------ |
| **JUnit 5**         | Testes unitários                  | Suporte moderno para testes estruturados         |
| **Mockito**         | Simulação de dependências         | Controle total sobre `Connection`, `ResultSet`   |
| **Maven**           | Gerenciador de dependências       | Automatização e integração de testes             |
| **PIT (opcional)**  | Teste de mutação                  | Identificação de fraquezas na lógica de teste    |
