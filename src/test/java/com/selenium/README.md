# Testes Selenium com Page Object Model

Este diretório contém testes Selenium implementados usando o padrão Page Object Model (POM) para a Aplicação Ecommerce.

## Estrutura dos Testes

### Objetos de Página
- `BasePage.java` - Classe base abstrata com funcionalidades comuns
- `HomePage.java` - Representa a página principal
- `CustomerLoginPage.java` - Representa o formulário de login do cliente
- `ProductListPage.java` - Representa a página de listagem de produtos
- `ProductDetailPage.java` - Representa a página de detalhes do produto

### Classes de Teste
- `CustomerLoginAndProductSelectionTest.java` - Classe principal de teste implementando o caso de teste
- `TestConfig.java` - Classe de configuração e utilitários

## Implementação do Caso de Teste

O teste implementa os seguintes passos:

1. **Navegar para a página inicial** - Acessar `http://localhost:8080/EcommerceApp/`
2. **Clicar em Login do Cliente** - Clicar no link "Customer Login" no cabeçalho
3. **Preencher formulário de login** - Inserir email `daniel@gmail.com` e senha `12345`
4. **Enviar login** - Clicar no botão de login e verificar redirecionamento para a página inicial
5. **Clicar em "View all product"** - Navegar para a página de listagem de produtos
6. **Selecionar primeiro produto** - Clicar no produto Samsung Neo QLED

## Executando os Testes

### Pré-requisitos
1. Certifique-se de que a Aplicação Ecommerce está rodando em `http://localhost:8080/EcommerceApp/`
2. Certifique-se de que o navegador Chrome está instalado (ou modifique TestConfig para usar Firefox)
3. As dependências do Maven devem estar baixadas

### Executando com Maven
```bash
# Executar todos os testes Selenium
mvn test -Dtest=CustomerLoginAndProductSelectionTest

# Executar método de teste específico
mvn test -Dtest=CustomerLoginAndProductSelectionTest#testCompleteLoginAndProductSelectionFlow

# Executar testes com saída verbosa
mvn test -Dtest=CustomerLoginAndProductSelectionTest -X
```

### Executando do IDE
1. Abra a classe de teste no seu IDE
2. Clique com o botão direito na classe de teste ou métodos de teste individuais
3. Selecione "Run Test" ou "Debug Test"

## Configuração dos Testes

### Configuração do Navegador
- Padrão: Navegador Chrome
- Modo headless: Descomente a opção headless em `TestConfig.createChromeDriver()`
- Firefox: Use `TestConfig.createFirefoxDriver()` em vez disso

### Dados de Teste
- Email de teste: `daniel@gmail.com`
- Senha de teste: `12345`
- URL base: `http://localhost:8080/EcommerceApp/`

## Benefícios do Page Object Model

1. **Manutenibilidade** - Mudanças na UI só requerem atualizações nos objetos de página
2. **Reutilização** - Objetos de página podem ser reutilizados em múltiplos testes
3. **Legibilidade** - Testes são mais legíveis e auto-documentados
4. **Separação de Responsabilidades** - Lógica de teste é separada da lógica de interação com a página

## Métodos de Teste

### Testes de Passos Individuais
- `testNavigateToHomePage()` - Passo 1
- `testClickCustomerLogin()` - Passo 2
- `testFillLoginForm()` - Passo 3
- `testSubmitLoginForm()` - Passo 4
- `testClickViewAllProduct()` - Passo 5
- `testClickFirstProduct()` - Passo 6

### Teste de Fluxo Completo
- `testCompleteLoginAndProductSelectionFlow()` - Teste end-to-end

## Solução de Problemas

### Problemas Comuns
1. **Elemento não encontrado** - Verifique se a aplicação está rodando e acessível
2. **Erros de timeout** - Aumente o tempo de espera em BasePage ou adicione esperas explícitas
3. **Navegador não inicia** - Verifique a configuração do WebDriverManager e instalação do navegador

### Modo Debug
Para executar testes em modo debug (navegador visível):
1. Comente a opção headless em `TestConfig.createChromeDriver()`
2. Adicione chamadas `TestConfig.waitForPageLoad(driver)` nos métodos de teste se necessário

## Estendendo os Testes

Para adicionar novos casos de teste:
1. Crie novos objetos de página para novas páginas
2. Adicione novos métodos de teste às classes de teste existentes
3. Siga o mesmo padrão POM para consistência
4. Use TestConfig para configuração centralizada 