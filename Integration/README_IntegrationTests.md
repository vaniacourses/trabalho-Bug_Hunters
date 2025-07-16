# Integration Tests Overview

This directory contains the split integration tests from the original Postman collection. Each test has been separated into individual files with clear divisions between the request specifications and test implementations.

## Test Files Structure

### 1. Fluxo de Autenticação do Cliente
- **01_CustomerLogin_Test.md** - Login do cliente com armazenamento de cookie
- **02_AddItemToCart_Test.md** - Adicionar produto ao carrinho de compras

### 2. Fluxo de Processamento de Pedido
- **03a_GetShippingAddressPage_Test.md** - Carregar formulário de endereço de entrega
- **03b_SubmitShippingAddress_Test.md** - Enviar detalhes de entrega (Pagamento na Entrega)
- **03c_GetConfirmPaymentPage_Test.md** - Carregar página de confirmação de pagamento
- **03d_SuccessfulPayment_Test.md** - Completar processamento de pagamento

### 3. Testes de Performance
- **09_PerformanceTest_Test.md** - Validação de tempo de resposta e performance

## Test Flow Sequence

```
1. Customer Login
   ↓
2. Add Item to Cart
   ↓
3a. Get Shipping Address Page
   ↓
3b. Submit Shipping Address
   ↓
3c. Get Confirm Payment Page
   ↓
3d. Successful Payment
   ↓
9. Performance Test (Optional)
```

## File Format

Each test file follows this structure:

### Request (Postman)
- **Method**: HTTP method (GET, POST, etc.)
- **URL**: Complete endpoint URL
- **Headers**: Required headers including authentication
- **Body/Parameters**: Request data and query parameters
- **Request Details**: Additional context and configuration

---

### Test Implementation
- **Test Script**: JavaScript code for Postman tests
- **Test Description**: Detailed explanation of each test
- **Expected Behavior**: What should happen when test passes

## Variables Used

- `{{customerCookie}}` - Cookie de autenticação do cliente armazenado
- `{{adminCookie}}` - Cookie de autenticação do administrador armazenado (não usado nos testes atuais)

## Running the Tests

1. Import the original Postman collection: `Teste integracao PayProcessIntegration.postman_collection.json`
2. Set up the environment variables
3. Run tests in sequence as they depend on previous test results
4. Monitor test results and performance metrics

## Notes

- Tests are designed to run in sequence due to cookie dependencies
- Performance test can be run independently or as part of the full flow
- All tests target the local development environment (localhost:8080)
- Tests cover the complete e-commerce purchase flow from login to payment 