# Testes de Performance com Selenium

Este documento descreve os testes de performance implementados para validar os requisitos não-funcionais da aplicação Ecommerce.

## Requisitos Não-Funcionais Testados

### 1. Performance (Tempo de Resposta)
- **Home Page**: Deve carregar em menos de 3 segundos
- **Login Page**: Deve carregar em menos de 2 segundos
- **Login Process**: Deve completar em menos de 4 segundos
- **Product List**: Deve carregar em menos de 3 segundos
- **Product Detail**: Deve carregar em menos de 2.5 segundos
- **Add to Cart**: Deve responder em menos de 2 segundos

### 2. Concorrência (Load Testing)
- **Concurrent Users**: 3 usuários simultâneos
- **Average Response Time**: Deve ser menor que 5 segundos
- **Maximum Response Time**: Deve ser menor que 10 segundos
- **Success Rate**: Pelo menos 80% das operações devem ser bem-sucedidas

### 3. Estabilidade do Sistema
- **Memory Usage**: Deve ser menor que 50MB
- **System Stability**: Deve manter performance consistente durante múltiplas operações
- **Error Handling**: Deve lidar graciosamente com falhas

### 4. Escalabilidade
- **Resource Management**: Deve gerenciar recursos eficientemente
- **Concurrent Operations**: Deve suportar múltiplas operações simultâneas
- **Performance Degradation**: Deve manter performance aceitável sob carga

## Classes de Teste

### 1. PerformanceTest.java
Testa métricas de performance individuais:

#### Testes Incluídos:
- `testHomePageLoadTime()` - Tempo de carregamento da página inicial
- `testCustomerLoginPageLoadTime()` - Tempo de carregamento da página de login
- `testLoginProcessTime()` - Tempo do processo de login
- `testProductListPageLoadTime()` - Tempo de carregamento da lista de produtos
- `testProductDetailPageLoadTime()` - Tempo de carregamento da página de detalhes
- `testAddToCartResponseTime()` - Tempo de resposta do "Add to Cart"
- `testMultiplePageNavigationStressTest()` - Teste de estresse de navegação
- `testConcurrentUserSimulation()` - Simulação de usuário concorrente
- `testMemoryAndResourceUsage()` - Uso de memória e recursos

### 2. LoadTest.java
Testa comportamento sob carga:

#### Testes Incluídos:
- `testConcurrentHomePageAccess()` - Acesso concorrente à página inicial
- `testConcurrentLoginOperations()` - Operações de login concorrentes
- `testConcurrentProductBrowsing()` - Navegação concorrente de produtos
- `testSystemStabilityUnderLoad()` - Estabilidade do sistema sob carga

## Métricas Coletadas

### 1. Tempos de Resposta
- **Page Load Times**: Tempo para carregar páginas
- **Navigation Times**: Tempo para navegar entre páginas
- **Login Times**: Tempo para completar login
- **Product Load Times**: Tempo para carregar produtos

### 2. Estatísticas de Carga
- **Average Response Time**: Tempo médio de resposta
- **Maximum Response Time**: Tempo máximo de resposta
- **Minimum Response Time**: Tempo mínimo de resposta
- **Success Rate**: Taxa de sucesso das operações

### 3. Uso de Recursos
- **Memory Usage**: Uso de memória em bytes
- **CPU Usage**: Uso de CPU (indireto via tempo de resposta)
- **Network Latency**: Latência de rede (indireto via tempo de resposta)

## Executando os Testes

### 1. Teste de Performance Individual
```bash
# Executar todos os testes de performance
mvn test -Dtest=PerformanceTest

# Executar teste específico
mvn test -Dtest=PerformanceTest#testHomePageLoadTime
```

### 2. Teste de Carga
```bash
# Executar todos os testes de carga
mvn test -Dtest=LoadTest

# Executar teste específico
mvn test -Dtest=LoadTest#testConcurrentHomePageAccess
```

### 3. Executar Ambos os Testes
```bash
# Executar performance e load tests
mvn test -Dtest=PerformanceTest,LoadTest
```

## Configuração para Performance Testing

### 1. Configurações do Chrome para Performance
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless"); // Modo headless para performance
options.addArguments("--disable-images"); // Desabilitar imagens
options.addArguments("--disable-extensions"); // Desabilitar extensões
options.addArguments("--disable-plugins"); // Desabilitar plugins
```

### 2. Timeouts Configurados
- **Default Wait**: 15 segundos (aumentado para performance testing)
- **Page Load**: 30 segundos
- **Script Timeout**: 30 segundos

### 3. Configurações de Concorrência
- **Concurrent Users**: 3 usuários simultâneos
- **Thread Pool**: ExecutorService com pool fixo
- **Synchronization**: Sincronização para métricas compartilhadas

## Interpretação dos Resultados

### 1. Critérios de Sucesso
- **Green**: Todos os requisitos de performance atendidos
- **Yellow**: Alguns requisitos não atendidos, mas aceitável
- **Red**: Requisitos de performance não atendidos

### 2. Análise de Performance
- **< 1s**: Excelente
- **1-3s**: Bom
- **3-5s**: Aceitável
- **> 5s**: Precisa de otimização

### 3. Análise de Carga
- **Success Rate > 95%**: Excelente
- **Success Rate 80-95%**: Bom
- **Success Rate < 80%**: Precisa de investigação

## Exemplo de Saída

```
=== PERFORMANCE TEST SUMMARY ===
Average Page Load Time: 1250ms
Average Login Time: 2100ms
Average Product Load Time: 1800ms
=== END PERFORMANCE SUMMARY ===

=== LOAD TEST SUMMARY ===
Average Response Time: 3200ms
Maximum Response Time: 8500ms
Minimum Response Time: 1200ms
Total Operations Tested: 12
Concurrent Users Simulated: 3
=== END LOAD TEST SUMMARY ===
```

## Troubleshooting

### 1. Testes Falhando por Timeout
- Aumentar timeouts em `TestConfig.java`
- Verificar se a aplicação está rodando
- Verificar conectividade de rede

### 2. Testes de Carga Falhando
- Reduzir número de usuários concorrentes
- Aumentar timeouts de execução
- Verificar recursos do sistema

### 3. Performance Degradada
- Verificar uso de CPU e memória
- Verificar logs da aplicação
- Considerar otimizações no código

## Próximos Passos

### 1. Melhorias Sugeridas
- Adicionar testes de stress com mais usuários
- Implementar testes de endurance (longa duração)
- Adicionar monitoramento de recursos em tempo real
- Implementar testes de spike (picos de carga)

### 2. Integração com CI/CD
- Configurar execução automática dos testes
- Implementar alertas para falhas de performance
- Gerar relatórios de performance
- Configurar thresholds para build failure

### 3. Monitoramento Contínuo
- Implementar APM (Application Performance Monitoring)
- Configurar alertas de performance
- Implementar dashboards de métricas
- Configurar testes de performance automatizados 