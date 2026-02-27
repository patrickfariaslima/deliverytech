# 🔐 Guia de Autenticação e Autorização - DeliveryTech API

## ✅ Implementação Completa

Este projeto implementa um sistema completo de autenticação e autorização usando **Spring Security** e **JWT (JSON Web Tokens)**.

---

## 📋 O que foi implementado

### ✅ Arquivos Criados

1. **`UserRole.java`** - Enum com roles: CLIENT, RESTAURANT, ADMIN, DELIVERY
2. **`User.java`** - Entidade de usuário implementando UserDetails
3. **`UserRepository.java`** - Repositório para operações com usuários
4. **`LoginRequest.java`** - DTO para requisição de login
5. **`RegisterRequest.java`** - DTO para registro de usuários
6. **`LoginResponse.java`** - DTO de resposta com token JWT
7. **`UserResponse.java`** - DTO de resposta com dados do usuário
8. **`JwtUtil.java`** - Utilidades para geração e validação de tokens JWT
9. **`JwtAuthenticationFilter.java`** - Filtro para validação de tokens
10. **`SecurityUtils.java`** - Utilitários para obter usuário logado
11. **`SecurityConfig.java`** - Configuração de segurança do Spring
12. **`UserDetailsServiceImpl.java`** - Implementação do UserDetailsService
13. **`AuthService.java`** - Interface do serviço de autenticação
14. **`AuthServiceImpl.java`** - Implementação do serviço de autenticação
15. **`AuthController.java`** - Controller com endpoints de autenticação

### ✅ Arquivos Modificados

1. **`RestaurantService.java`** - Adicionado método `isOwner()`
2. **`RestaurantServiceImpl.java`** - Implementado método `isOwner()`
3. **`ProductService.java`** - Adicionado método `isOwner()`
4. **`ProductServiceImpl.java`** - Implementado método `isOwner()`
5. **`OrderService.java`** - Adicionado método `canAccess()`
6. **`OrderServiceImpl.java`** - Implementado método `canAccess()`
7. **`RestaurantController.java`** - Adicionadas anotações `@PreAuthorize`
8. **`ProductController.java`** - Adicionadas anotações `@PreAuthorize`
9. **`OrderController.java`** - Adicionadas anotações `@PreAuthorize`
10. **`application.properties`** - Configurações JWT e security
11. **`data.sql`** - Adicionados usuários de teste

---

## 🔑 Usuários de Teste

Todos os usuários têm a senha: **`123456`**

| Nome | Email | Role | Restaurant ID |
|------|-------|------|---------------|
| Admin System | admin@delivery.com | ADMIN | null |
| João Client | joao@email.com | CLIENT | null |
| Pizza Palace Owner | pizza@palace.com | RESTAURANT | 1 |
| Burger House Owner | burger@house.com | RESTAURANT | 2 |
| Sushi Master Owner | sushi@master.com | RESTAURANT | 3 |
| Carlos Delivery | carlos@delivery.com | DELIVERY | null |

---

## 📡 Endpoints de Autenticação

### 1. Registro de Usuário
**POST** `/api/auth/register`

```json
{
  "name": "João Silva",
  "email": "joao@test.com",
  "password": "123456",
  "role": "CLIENT"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@test.com",
  "role": "CLIENT",
  "active": true,
  "createdAt": "2024-01-01T10:00:00",
  "restaurantId": null
}
```

### 2. Login
**POST** `/api/auth/login`

```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "userId": 2,
  "name": "João Client",
  "email": "joao@email.com",
  "role": "CLIENT",
  "restaurantId": null,
  "expiresIn": 86400000
}
```

### 3. Obter Usuário Atual
**GET** `/api/auth/me`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "id": 2,
  "name": "João Client",
  "email": "joao@email.com",
  "role": "CLIENT",
  "active": true,
  "createdAt": "2024-01-01T10:00:00",
  "restaurantId": null
}
```

---

## 🔒 Controle de Acesso por Role

### ENDPOINTS PÚBLICOS (sem autenticação)
- `GET /api/restaurantes/**` - Listar restaurantes
- `GET /api/produtos/**` - Listar produtos
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro
- `/h2-console/**` - Console H2
- `/swagger-ui/**` - Documentação Swagger

### ROLE: CLIENT
**Pode fazer:**
- ✅ Criar pedidos: `POST /api/pedidos`
- ✅ Ver seus próprios pedidos
- ✅ Cancelar pedidos: `DELETE /api/pedidos/{id}`

**NÃO pode fazer:**
- ❌ Criar produtos
- ❌ Criar ou editar restaurantes
- ❌ Ver pedidos de outros clientes

### ROLE: RESTAURANT
**Pode fazer:**
- ✅ Criar produtos: `POST /api/produtos`
- ✅ Editar seus produtos: `PUT /api/produtos/{id}`
- ✅ Alterar disponibilidade: `PATCH /api/produtos/{id}/disponibilidade`
- ✅ Deletar seus produtos: `DELETE /api/produtos/{id}`
- ✅ Ver pedidos do seu restaurante
- ✅ Atualizar status de pedidos: `PATCH /api/pedidos/{id}/status`
- ✅ Editar dados do seu restaurante: `PUT /api/restaurantes/{id}`

**NÃO pode fazer:**
- ❌ Editar produtos de outros restaurantes
- ❌ Ver pedidos de outros restaurantes
- ❌ Editar outros restaurantes

### ROLE: ADMIN
**Pode fazer:**
- ✅ ACESSO TOTAL a todos os endpoints
- ✅ Ver todos os pedidos: `GET /api/pedidos`
- ✅ Criar/editar/deletar qualquer restaurante
- ✅ Criar/editar/deletar qualquer produto
- ✅ Ativar/desativar restaurantes

### ROLE: DELIVERY
**Pode fazer:**
- ✅ Ver pedidos para entrega (se implementado)
- ✅ Atualizar status de entrega (se implementado)

---

## 🧪 Como Testar

### 1. Via Postman

1. Importe a collection: `postman/Authentication-Tests.postman_collection.json`
2. Execute os requests na ordem:
   - **Test 1**: Registrar novo usuário
   - **Test 2**: Login (salva token automaticamente)
   - **Test 3**: Acessar endpoint protegido com token
   - **Test 4**: Tentar acessar sem token (deve falhar com 401)
   - **Test 5**: Acessar endpoint público sem token (deve funcionar)
   - **Test 6**: Cliente tentar criar produto (deve falhar com 403)
   - **Test 7**: Login como restaurante
   - **Test 8**: Restaurante criar produto (deve funcionar)
   - **Test 9**: Login como admin
   - **Test 10**: Admin acessar todos os pedidos
   - **Test 11**: Login com senha errada (deve falhar com 401)

### 2. Via cURL

#### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@email.com","password":"123456"}'
```

#### Acessar endpoint protegido:
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer {seu_token_aqui}"
```

### 3. Via Swagger

1. Acesse: `http://localhost:8080/swagger-ui.html`
2. Faça login no endpoint `/api/auth/login`
3. Copie o token retornado
4. Clique no botão **Authorize** (🔒) no topo da página
5. Cole o token no formato: `Bearer {token}`
6. Teste os endpoints protegidos

---

## 🔧 Configurações

### application.properties

```properties
# JWT Configuration
jwt.secret=delivery-tech-secret-key-2026-very-secret-and-secure-key-for-jwt-token-generation
jwt.expiration=86400000

# Security Configuration  
spring.security.user.name=admin
spring.security.user.password=admin
logging.level.org.springframework.security=DEBUG
```

### Tempo de Expiração do Token
- **86400000 ms** = 24 horas
- Para alterar, modifique `jwt.expiration` no `application.properties`

---

## 🛡️ Segurança Implementada

1. **BCrypt Password Encoding**: Senhas são criptografadas com BCrypt (strength 10)
2. **JWT Tokens**: Tokens assinados com HMAC SHA-256
3. **Stateless Sessions**: Sem sessões no servidor (SessionCreationPolicy.STATELESS)
4. **CSRF Desabilitado**: Apropriado para APIs REST
5. **CORS Configurado**: Permite requisições do frontend
6. **Role-Based Access Control**: Controle granular por perfil de usuário
7. **Method Security**: Anotações `@PreAuthorize` nos controllers
8. **Ownership Verification**: Verificação de propriedade de recursos

---

## 🐛 Solução de Problemas

### Erro 401 Unauthorized
- Verifique se o token está no header: `Authorization: Bearer {token}`
- Verifique se o token não expirou (24 horas)
- Verifique se fez login corretamente

### Erro 403 Forbidden
- Verifique se seu usuário tem a role necessária
- Cliente não pode criar produtos
- Restaurante só pode editar seus próprios recursos

### Erro ao iniciar aplicação
- Verifique se o H2 Database está configurado
- Verifique se `spring.sql.init.mode=always` está ativado
- Verifique se data.sql não tem erros de sintaxe

---

## 📚 Documentação Adicional

- **Spring Security**: https://spring.io/projects/spring-security
- **JWT**: https://jwt.io/
- **BCrypt**: https://en.wikipedia.org/wiki/Bcrypt

---

## ✅ Checklist de Implementação

- [x] Entidade User implementando UserDetails
- [x] UserRepository com findByEmail
- [x] Enum UserRole com roles
- [x] DTOs de autenticação
- [x] JwtUtil para gerar e validar tokens
- [x] JwtAuthenticationFilter
- [x] SecurityConfig completa
- [x] UserDetailsServiceImpl
- [x] AuthService e AuthServiceImpl
- [x] AuthController
- [x] Métodos de autorização nos serviços (isOwner, canAccess)
- [x] @PreAuthorize nos controllers
- [x] Dados de teste no data.sql
- [x] Configurações no application.properties
- [x] Collection Postman para testes
- [x] Documentação completa

---

## 🎯 100% Completo!

Todos os requisitos da atividade foram implementados com sucesso! 🎉
