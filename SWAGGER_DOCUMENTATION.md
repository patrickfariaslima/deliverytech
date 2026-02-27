# 📚 Documentação Swagger - DeliveryTech API

## 🎯 Visão Geral

Esta API foi documentada utilizando **OpenAPI 3.0** (Swagger) para facilitar a integração e o teste dos endpoints disponíveis.

## 🌐 Acessando a Documentação

### Interface Swagger UI (Recomendado)
A interface interativa está disponível em:
```
http://localhost:8080/swagger-ui.html
```

### Especificação OpenAPI JSON
A especificação da API em formato JSON está disponível em:
```
http://localhost:8080/api-docs
```

### Especificação OpenAPI YAML
Você também pode acessar em formato YAML:
```
http://localhost:8080/api-docs.yaml
```

## 🚀 Como Usar o Swagger UI

### 1. Iniciar a Aplicação
```bash
# Via Maven
./mvnw spring-boot:run

# Via JAR
java -jar target/delivery-api-0.0.1-SNAPSHOT.jar
```

### 2. Acessar a Interface
Abra seu navegador e acesse: `http://localhost:8080/swagger-ui.html`

### 3. Autenticação JWT

Para testar endpoints protegidos, você precisa autenticar:

#### Passo 1: Fazer Login
1. Localize o endpoint `POST /api/auth/login`
2. Clique em **"Try it out"**
3. Preencha com credenciais válidas:
```json
{
  "email": "admin@example.com",
  "password": "senha123"
}
```
4. Clique em **"Execute"**
5. Copie o valor do campo `token` da resposta

#### Passo 2: Autorizar
1. Clique no botão **"Authorize"** 🔓 no topo da página
2. Cole o token JWT no campo (não precisa adicionar "Bearer")
3. Clique em **"Authorize"**
4. Clique em **"Close"**

Agora você está autenticado e pode testar os endpoints protegidos! 🎉

### 4. Testando Endpoints

1. Selecione o endpoint que deseja testar
2. Clique em **"Try it out"**
3. Preencha os parâmetros necessários
4. Clique em **"Execute"**
5. Veja a resposta no campo **"Response"**

## 📋 Estrutura da Documentação

A API está organizada nas seguintes seções:

### 🔐 Authentication
- Login de usuários
- Registro de novos usuários
- Obter dados do usuário autenticado

### 👥 Clientes
- Cadastro e gerenciamento de clientes
- Consulta de pedidos por cliente
- Busca por nome ou email

### 🍽️ Restaurantes
- Cadastro e gerenciamento de restaurantes
- Consulta por categoria
- Cálculo de taxa de entrega
- Busca de restaurantes próximos por CEP

### 🍕 Produtos
- Cadastro e gerenciamento de produtos
- Consulta por categoria
- Controle de disponibilidade
- Busca por nome

### 📦 Pedidos
- Criação de pedidos
- Acompanhamento de status
- Histórico de pedidos
- Cálculo de totais

### 📊 Relatórios
- Total de vendas por restaurante
- Ranking de clientes
- Produtos mais vendidos
- Pedidos por período

### 🩺 Health
- Status da aplicação
- Informações do sistema

## 🔑 Roles e Permissões

A API utiliza três perfis de usuário:

### CLIENT
- Criar e visualizar próprios pedidos
- Consultar restaurantes e produtos
- Gerenciar próprio perfil

### RESTAURANT
- Gerenciar produtos do próprio restaurante
- Visualizar pedidos recebidos
- Atualizar status de pedidos

### ADMIN
- Acesso total ao sistema
- Gerenciar todos os recursos
- Visualizar relatórios completos

## 📝 Exemplos de Uso

### Exemplo 1: Criar um Pedido

```json
POST /api/pedidos
{
  "clientId": 1,
  "restaurantId": 1,
  "deliveryAddress": "Rua das Flores, 123",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

### Exemplo 2: Buscar Restaurantes por Categoria

```http
GET /api/restaurantes/categoria/Pizza
```

### Exemplo 3: Atualizar Status do Pedido

```http
PATCH /api/pedidos/1/status?status=IN_PROGRESS
```

## 🔐 Códigos HTTP

| Código | Descrição |
|--------|-----------|
| 200 | Requisição bem-sucedida |
| 201 | Recurso criado com sucesso |
| 204 | Operação bem-sucedida sem retorno |
| 400 | Dados de entrada inválidos |
| 401 | Não autenticado |
| 403 | Sem permissão para acessar |
| 404 | Recurso não encontrado |
| 409 | Conflito de dados |
| 500 | Erro interno do servidor |

## 🛠️ Configurações Avançadas

### Personalizar o Caminho do Swagger

Edite `application.properties`:

```properties
# Caminho da documentação JSON
springdoc.api-docs.path=/api-docs

# Caminho da interface Swagger UI
springdoc.swagger-ui.path=/swagger-ui.html

# Ordenar operações por método HTTP
springdoc.swagger-ui.operationsSorter=method

# Ordenar tags alfabeticamente
springdoc.swagger-ui.tagsSorter=alpha

# Habilitar botão "Try it out"
springdoc.swagger-ui.tryItOutEnabled=true
```

### Desabilitar o Swagger em Produção

Para desabilitar a documentação em ambiente de produção:

```properties
springdoc.api-docs.enabled=false
springdoc.swagger-ui.enabled=false
```

Ou via variável de ambiente:
```bash
SPRINGDOC_API-DOCS_ENABLED=false
SPRINGDOC_SWAGGER-UI_ENABLED=false
```

## 🔄 Exportar Documentação

### Gerar OpenAPI JSON
```bash
curl http://localhost:8080/api-docs > openapi.json
```

### Gerar OpenAPI YAML
```bash
curl http://localhost:8080/api-docs.yaml > openapi.yaml
```

## 📚 Recursos Adicionais

### Ferramentas Úteis

- **Swagger Editor**: https://editor.swagger.io/
- **Postman**: Importe a especificação OpenAPI para criar coleção
- **Swagger Codegen**: Gere clientes da API em várias linguagens

### Links de Referência

- [Springdoc OpenAPI Documentation](https://springdoc.org/)
- [OpenAPI Specification 3.0](https://swagger.io/specification/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## 🤝 Suporte

Para dúvidas ou problemas com a API:

- 📧 Email: dev@deliverytech.com
- 🐛 Issues: https://github.com/patrickfariaslima/deliverytech/issues
- 📖 Wiki: https://github.com/patrickfariaslima/deliverytech/wiki

## ✅ Checklist de Validação

Antes de considerar a documentação completa, verifique:

- [x] Swagger UI acessível em `/swagger-ui.html`
- [x] Todos os endpoints documentados com `@Operation`
- [x] Controllers agrupados com `@Tag`
- [x] DTOs documentados com `@Schema`
- [x] Autenticação JWT configurada
- [x] Exemplos de requisição/resposta funcionando
- [x] Códigos de erro documentados
- [x] Descrições claras e objetivas

---

**Desenvolvido com ❤️ pela equipe DeliveryTech**
