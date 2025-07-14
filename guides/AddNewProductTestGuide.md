# Análise da Classe AddCustomerTest

## 📋 Visão Geral
Classe de teste unitário para validação de novos produtos no catálogo utilizando **JUnit 5** e **Mockito** para testar o dao `DAO`.
---

## Diagrama de Classe Após as técnicas
![Diagram](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/AddNewProductTest.png)

## 🛠️ Técnicas Utilizadas

### **Caixa Branca**

- Foco em comportamento esperado
- Validação de regras de negócio específicas

### **Caixa Preta**

- Teste Funcional:
    - Critérios de análise de valor limite
    - Particionamento de classes de Equivalência

### **2. Mocking com Mockito**
```java
@Mock private HttpServletRequest request;
@Mock private HttpServletResponse response;
```


## 📊 Estratégias de Cobertura

### **1. Cobertura de Caminhos (Path Coverage)**


### **2. Análise de Valores Limite**
```java
// Limites de idade
testShouldAddProductWithLowerBoundaryValues()     // Limite inferior
testShouldReturnZeroWhenValuesAreBelowLowerLimit()     // Valor Abaixo do Limite Inferiorr
testShouldReturnZeroWhenValuesAreAboveUpperLimit()    // Valor Acima  do Limite Superior
testShouldAddProductWithUpperBoundaryValues()    // Limite superior
```


## 🎲 Técnicas de Teste de Mutação

### **Mutantes Testados**
- **Operadores Relacionais**: `==`, `!=`, `<`, `<=`, `>`, `>=`
- **Operadores Lógicos**: `&&`, `||`, `!`
- **Valores Literais**: Números mágicos, strings
- **Operadores Aritméticos**: `+`, `-`, `*`, `/`

---

### **Evidência Pitest**
![Pitest Imagem](https://github.com/vaniacourses/trabalho-Bug_Hunters/blob/main/images/PitestCoverage_AddNewProductTest.png)

---

## 🔍 Padrões de Teste Identificados

### **1. Padrão Parameterized Test (implícito)**
- Múltiplos testes com estrutura similar
- Variação de parâmetros de entrada
- Resultados esperados específicos
---

## 📈 Métricas de Qualidade

### **Cobertura de Código**
- **Class**: ~100%
- **Method**: ~91%
- **Line**: ~88%
- **Branch**: ~72%

### **Tipos de Cenários Testados**
- ✅ **Caminho Feliz**: Dados válidos
- ✅ **Valores Limite**: Extremos e fronteiras
- ✅ **Valores Inválidos**: Nulos, vazios, formato incorreto
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

