# Análise da Classe ContactusTest

## 📋 Visão Geral

Classe de testes unitários criada para validar os métodos de acesso (`getters` e `setters`) da classe `contactus`, utilizando **JUnit 5**. O foco está em garantir a integridade dos dados manipulados, com atenção especial a valores limite, nulos, formatos inválidos e estresse.

---

## 🛠️ Técnicas Utilizadas

### **1. Caixa Branca**

- Foco direto nos métodos públicos (`set` e `get`) da classe `contactus`
- Exploração de diferentes entradas, incluindo casos típicos e atípicos

### **2. Testes de Aresta e Limites**

- Verificação de comportamento com entradas no limite da faixa de valores válidos
- Consideração de casos como `null`, strings vazias e valores extremos de `int`

---

### **Evidência Pitest**
![Pitest Imagem](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/pitestContactUsEvidencia.png)

## 📊 Estratégias de Cobertura

### **1. Valores Limite**

```java
testSetContactNoZero();         // Valor mínimo representativo
testSetContactNoMaxInt();       // Integer.MAX_VALUE
testSetContactNoMinInt();       // Integer.MIN_VALUE
testNameWithNull();             // null no nome
testEmailWithInvalidFormat();   // Email sem '@'
testSetMessageWithLongText();   // Estresse com texto longo
```

### **2. Casos de Equivalência**

- **Nomes**: nulo, vazio, comum
- **Emails**: válidos, nulo, inválido
- **Contato**: positivo, zero, negativo, extremos
- **Mensagens**: nula, vazia, texto muito longo

---

## 🎲 Técnicas de Teste de Mutação

### **Mutantes Relevantes Detectáveis**

- **Verificações de igualdade**: `==`, `.equals()`
- **Atribuições simples**
- **Ausência de validações (ex: email)**

---

## 🔍 Padrões de Teste Identificados

### **1. Padrão Arrange-Act-Assert (AAA)**

```java
contactus c = new contactus();   // Arrange
c.setName("Maria");              // Act
assertEquals("Maria", c.getName()); // Assert
```

### **2. Uso de **``** para múltiplas verificações**

```java
assertAll(
    () -> assertEquals(...),
    () -> assertEquals(...),
    ...
);
```

---

## 📈 Métricas de Qualidade

| Métrica    | Valor Estimado                                 |
| ---------- | ---------------------------------------------- |
| Statements | 100%                                           |
| Branches   | 100% (onde aplicável)                          |
| Caminhos   | Baixo (classe simples, sem lógica condicional) |

---

## 🔧 Ferramentas e Frameworks

| Ferramenta         | Propósito                   | Benefício                                  |
| ------------------ | --------------------------- | ------------------------------------------ |
| **JUnit 5**        | Testes unitários            | Estrutura clara e moderna                  |
| **Maven**          | Gerenciador de dependências | Execução automatizada de testes            |
| **PIT (opcional)** | Teste de mutação            | Ajuda a identificar fraquezas na cobertura |

---



