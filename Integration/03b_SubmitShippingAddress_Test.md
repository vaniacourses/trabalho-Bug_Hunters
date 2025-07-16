# 3b. Submit Shipping Address (Cash on Delivery) Test

## Request (Postman)

### Method
```
POST
```

### URL
```
http://localhost:8080/EcommerceApp/ShippingAddress2
```

### Headers
```
Content-Type: application/x-www-form-urlencoded
Cookie: {{customerCookie}}
```

### Body (URL Encoded)
```
Total: 100
CusName: empty
CName: Daniel Customer
Address: 123 Test Street
City: TestCity
State: TestState
Country: TestCountry
Pincode: 12345
cash: Cash on Delivery
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/ShippingAddress2
- **Authentication**: Customer cookie from previous login
- **Payment Method**: Cash on Delivery
- **Form Data**:
  - Customer information (name, address, city, state, country, pincode)
  - Order total and customer name
  - Payment method selection

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Shipping address submitted successfully", function () {
    pm.response.to.have.status(200);
    const responseText = pm.response.text();
    pm.expect(responseText).to.include("confirmpayment.jsp");
});
```

### Test Description
1. **Teste de Código de Status**: Verifica se o envio do endereço de entrega retorna status HTTP 200
2. **Teste de Validação de Redirecionamento**: 
   - Verifica se a resposta contém "confirmpayment.jsp"
   - Valida se o usuário é redirecionado para a página de confirmação de pagamento
   - Garante o fluxo adequado do envio para a confirmação de pagamento

### Expected Behavior
- O endereço de entrega deve ser enviado com sucesso
- O usuário deve ser redirecionado para a página de confirmação de pagamento
- O fluxo do pedido deve continuar para o próximo passo 