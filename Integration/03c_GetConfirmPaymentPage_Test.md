# 3c. Get Confirm Payment Page Test

## Request (Postman)

### Method
```
GET
```

### URL
```
http://localhost:8080/EcommerceApp/confirmpayment.jsp?CName=Daniel Customer&City=TestCity&Total=100&CusName=empty
```

### Headers
```
Cookie: {{customerCookie}}
```

### Query Parameters
```
CName: Daniel Customer
City: TestCity
Total: 100
CusName: empty
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/confirmpayment.jsp
- **Authentication**: Customer cookie from previous login
- **Parameters**:
  - `CName`: Customer name from shipping form
  - `City`: Customer city from shipping form
  - `Total`: Order total amount
  - `CusName`: Customer name (empty)

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Confirm payment page loaded", function () {
    pm.response.to.have.status(200);
    pm.expect(pm.response.text()).to.include("Confirm Payment");
});
```

### Test Description
1. **Teste de Código de Status**: Verifica se a página de confirmação de pagamento carrega com status HTTP 200
2. **Teste de Validação de Conteúdo**: 
   - Verifica se a resposta contém o texto "Confirm Payment"
   - Valida se a página correta de confirmação de pagamento é exibida
   - Garante que o fluxo de pagamento está prosseguindo corretamente

### Expected Behavior
- A página de confirmação de pagamento deve carregar com sucesso
- A página deve exibir os detalhes de confirmação de pagamento
- O cliente deve ser capaz de revisar e confirmar o pagamento 