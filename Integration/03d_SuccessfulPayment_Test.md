# 3d. Successful Payment (Submit Confirm Payment) Test

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
CName: Daniel Customer
City: TestCity
Total: 100
CusName: empty
N2: TestProduct
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/payprocess
- **Authentication**: Customer cookie from previous login
- **Form Data**:
  - `CName`: Customer name from previous steps
  - `City`: Customer city from shipping form
  - `Total`: Order total amount
  - `CusName`: Customer name (empty)
  - `N2`: Product name for order processing

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Status code is 200 (HTML response)", function () {
    pm.response.to.have.status(200);
});

pm.test("Response contains orders page content", function () {
    pm.expect(pm.response.text()).to.include("All Orders");
});
```

### Test Description
1. **Teste de Código de Status**: Verifica se o processamento de pagamento retorna status HTTP 200
2. **Teste de Conclusão do Pedido**: 
   - Verifica se a resposta contém o texto "All Orders"
   - Valida se o pagamento foi processado com sucesso
   - Garante que o usuário é redirecionado para a página de pedidos após pagamento bem-sucedido

### Expected Behavior
- O pagamento deve ser processado com sucesso
- O pedido deve ser criado e armazenado no banco de dados
- O usuário deve ser redirecionado para a página de pedidos
- O pedido deve aparecer no histórico de pedidos do cliente 