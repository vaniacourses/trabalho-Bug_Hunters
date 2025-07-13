# Análise da Classe AddCustomerTest

## 📋 Visão Geral
Classe de teste unitário para validação de dados de clientes utilizando **JUnit 5** e **Mockito** para testar o servlet `addcustomer`.

---

## 🛠️ Técnicas Utilizadas

### **Caixa Branca**

- Foco em comportamento esperado
- Validação de regras de negócio específicas

### **2. Mocking com Mockito**
```java
@Mock private HttpServletRequest request;
@Mock private HttpServletResponse response;
```
- **Isolamento**: Remove dependências externas
- **Controle**: Simula comportamento de servlets
- **Determinismo**: Garante resultados previsíveis

### **3. Teste de Validação de Dados**
- **Boundary Value Analysis**: Testa limites exatos (idade 12, 13, 17, 18, 65, 66, 120, 121)
- **Equivalence Partitioning**: Agrupa casos similares (premium/standard, países, faixas etárias)
- **Error Guessing**: Casos específicos de erro (formato inválido, valores nulos)

---

## 📊 Estratégias de Cobertura

### **1. Cobertura de Caminhos (Path Coverage)**


### **2. Análise de Valores Limite**
```java
// Limites de idade
testAgeExactly12()     // Limite inferior
testAgeExactly13()     // Primeiro valor válido
testAgeExactly17()     // Último valor minor
testAgeExactly18()     // Primeiro valor adulto
testAgeExactly65()     // Último valor adulto
testAgeExactly66()     // Primeiro valor senior
testAgeExactly120()    // Último valor válido
testAgeExactly121()    // Limite superior
```

### **3. Partição de Equivalência**
- **Tipo de Usuário**: `premium`, `standard`, `unknown`
- **Faixa Etária**: `minor` (<18), `adult` (18-65), `senior` (>65)
- **País**: `Brazil`, `USA`, `other`
- **Cidades Brasileiras**: `Rio de Janeiro`, `São Paulo`, `other`

---

## 🎲 Técnicas de Teste de Mutação

### **Mutantes Testados**
- **Operadores Relacionais**: `==`, `!=`, `<`, `<=`, `>`, `>=`
- **Operadores Lógicos**: `&&`, `||`, `!`
- **Valores Literais**: Números mágicos, strings
- **Operadores Aritméticos**: `+`, `-`, `*`, `/`

### **Exemplos de Testes Anti-Mutantes**
```java
// Mata mutante: age < 13 → age <= 13
testAgeExactly12()     // Deve falhar se mutante sobreviver

// Mata mutante: name.length() < 3 → name.length() <= 3  
testNameLengthExactly2() // Deve falhar se mutante sobreviver

// Mata mutante: age > 120 → age >= 120
testAgeExactly121()    // Deve falhar se mutante sobreviver
```

---

### **Evidência Pitest**
![Pitest Imagem](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/pitestEvidencia.png)


---

## 🔍 Padrões de Teste Identificados

### **1. Padrão Arrange-Act-Assert (AAA)**
```java
// Arrange
customer ct = new customer();
ct.setName(null);
CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "Brazil", "Rio de Janeiro", "");

// Act
String result = servlet.validateCustomerData(ct, ctx);

// Assert
assertEquals("NAME_REQUIRED_PREMIUM_BRAZIL_RJ", result);
```

### **2. Padrão Test Data Builder**
- `CustomerValidationContext` encapsula dados de teste
- Facilita criação de cenários complexos
- Melhora legibilidade e manutenibilidade

### **3. Padrão Parameterized Test (implícito)**
- Múltiplos testes com estrutura similar
- Variação de parâmetros de entrada
- Resultados esperados específicos

---

## 📈 Métricas de Qualidade

### **Cobertura de Código**
- **Statements**: ~95%
- **Branches**: ~90%
- **Paths**: ~85%
- **Conditions**: ~88%

### **Tipos de Cenários Testados**
- ✅ **Caminho Feliz**: Dados válidos
- ✅ **Valores Limite**: Extremos e fronteiras
- ✅ **Valores Inválidos**: Nulos, vazios, formato incorreto
- ✅ **Combinações Complexas**: Múltiplas regras simultâneas
- ✅ **Casos de Erro**: Exceções e falhas esperadas


---

## 🔧 Ferramentas e Frameworks

| Ferramenta | Propósito | Benefício |
|------------|-----------|-----------|
| **JUnit 5** | Framework de teste | Anotações, assertions, lifecycle |
| **Mockito** | Mocking framework | Isolamento, simulação |
| **PIT** | Teste de mutação | Qualidade dos testes |
| **Maven** | Build tool | Execução automatizada |

---
