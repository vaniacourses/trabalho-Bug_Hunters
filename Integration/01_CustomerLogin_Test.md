# 1. Customer Login Test

## Request (Postman)

### Method
```
POST
```

### URL
```
http://localhost:8080/EcommerceApp/checkcustomer
```

### Headers
```
Content-Type: application/x-www-form-urlencoded
```

### Body (URL Encoded)
```
Email_Id: daniel@gmail.com
Password: 12345
Total: 100
CusName: empty
```

### Request Details
- **Protocol**: HTTP
- **Host**: localhost
- **Port**: 8080
- **Path**: /EcommerceApp/checkcustomer
- **Authentication**: None (login attempt)

---

## Test Implementation

### Test Script (JavaScript)
```javascript
pm.test("Customer login status is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Customer login executed and cookie stored", function () {
    var cookies = pm.response.headers.get('Set-Cookie');
    pm.expect(cookies).to.not.be.null;
    if (cookies) {
        var cnameCookie = cookies.match(/cname=([^;]+)/);
        if (cnameCookie) {
            pm.collectionVariables.set('customerCookie', 'cname=' + cnameCookie[1]);
            console.log("Customer cookie cname saved:", cnameCookie[1]);
        } else {
            console.warn("Customer cookie 'cname' not found in Set-Cookie header.");
        }
    }
});
```

### Test Description
1. **Teste de Código de Status**: Verifica se a requisição de login retorna status HTTP 200
2. **Teste de Armazenamento de Cookie**: 
   - Verifica se o cabeçalho Set-Cookie está presente na resposta
   - Extrai o valor do cookie 'cname' usando regex
   - Armazena o cookie em variáveis da coleção para requisições subsequentes
   - Registra mensagens de sucesso ou aviso para depuração

### Expected Behavior
- O login do cliente deve ser bem-sucedido com credenciais válidas
- A resposta deve incluir cookie de autenticação
- O cookie deve ser armazenado para gerenciamento de sessão em requisições subsequentes 