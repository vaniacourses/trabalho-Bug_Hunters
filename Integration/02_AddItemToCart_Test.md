# 2. Add Item to Cart (Customer) Test

## Request (Postman)

### Method
```
GET
```

### URL
```
http://localhost:8080/EcommerceApp/addtocartnull?id=samsung&ie=watch&ig=TestProduct&ih=100&ii=1&ij=test.jpg
```

### Headers
```
Cookie: {{customerCookie}}
```

### Query Parameters
```
id: samsung
ie: watch
ig: TestProduct
ih: 100
ii: 1
ij: test.jpg
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/addtocartnull
- **Authentication**: Customer cookie from previous login
- **Parameters**:
  - `id`: Product brand (samsung)
  - `ie`: Product category (watch)
  - `ig`: Product name (TestProduct)
  - `ih`: Product price (100)
  - `ii`: Product quantity (1)
  - `ij`: Product image (test.jpg)

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Add to cart status is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response contains cart content", function () {
    pm.expect(pm.response.text()).to.include("Cart");
});
```

### Test Description
1. **Teste de Código de Status**: Verifica se a requisição de adicionar ao carrinho retorna status HTTP 200
2. **Teste de Validação de Conteúdo**: 
   - Verifica se a resposta contém o texto "Cart"
   - Valida se a funcionalidade do carrinho está funcionando corretamente

### Expected Behavior
- O produto deve ser adicionado ao carrinho com sucesso
- A resposta deve indicar a conclusão da operação do carrinho
- A página deve exibir conteúdo relacionado ao carrinho 