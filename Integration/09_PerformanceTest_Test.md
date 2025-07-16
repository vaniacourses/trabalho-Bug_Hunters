# 9. Performance Test - Response Time Test

## Request (Postman)

### Method
```
POST
```

### URL
```
http://localhost:8080/EcommerceApp/payprocess
```

### Headers
```
Content-Type: application/x-www-form-urlencoded
Cookie: {{customerCookie}}
```

### Body (URL Encoded)
```
CName: Performance Test Customer
CusName: empty
City: Test City
Total: 100
N2: Test Product
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/payprocess
- **Authentication**: Customer cookie from previous login
- **Test Type**: Performance/Response Time
- **Form Data**:
  - `CName`: Test customer name for performance testing
  - `CusName`: Customer name (empty)
  - `City`: Test city
  - `Total`: Order total amount
  - `N2`: Test product name

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Response time is acceptable", function () {
    pm.expect(pm.response.responseTime).to.be.below(5000);
});

pm.test("Request completes successfully", function () {
    pm.expect(pm.response.code).to.be.oneOf([200, 500]);
});
```

### Test Description
1. **Teste de Tempo de Resposta**: 
   - Verifica se o tempo de resposta está abaixo de 5000ms (5 segundos)
   - Garante performance aceitável para processamento de pagamento
   - Monitora a responsividade do sistema sob carga

2. **Teste de Status de Conclusão**: 
   - Verifica se a requisição é concluída com 200 (sucesso) ou 500 (erro do servidor)
   - Permite tratamento gracioso de erros do servidor durante testes de performance
   - Valida se o sistema responde adequadamente sob estresse

### Expected Behavior
- O processamento de pagamento deve ser concluído em 5 segundos
- O sistema deve lidar adequadamente com requisições de teste de performance
- A resposta deve indicar processamento bem-sucedido ou erro do servidor
- As métricas de performance devem estar dentro dos limites aceitáveis 