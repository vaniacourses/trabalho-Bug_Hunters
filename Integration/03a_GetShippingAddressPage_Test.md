# 3a. Get Shipping Address Page Test

## Request (Postman)

### Method
```
GET
```

### URL
```
http://localhost:8080/EcommerceApp/ShippingAddress.jsp?Total=100&CusName=empty
```

### Headers
```
Cookie: {{customerCookie}}
```

### Query Parameters
```
Total: 100
CusName: empty
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/ShippingAddress.jsp
- **Authentication**: Customer cookie from previous login
- **Parameters**:
  - `Total`: Order total amount (100)
  - `CusName`: Customer name (empty)

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Shipping address page loaded", function () {
    pm.response.to.have.status(200);
    pm.expect(pm.response.text()).to.include("Shipping Address");
});
```

### Test Description
1. **Teste de Código de Status**: Verifica se a página de endereço de entrega carrega com status HTTP 200
2. **Teste de Validação de Conteúdo**: 
   - Verifica se a resposta contém o texto "Shipping Address"
   - Valida se a página correta de endereço de entrega é exibida

### Expected Behavior
- A página de endereço de entrega deve carregar com sucesso
- A página deve exibir o formulário de endereço de entrega
- O cliente deve ser capaz de prosseguir com a conclusão do pedido 