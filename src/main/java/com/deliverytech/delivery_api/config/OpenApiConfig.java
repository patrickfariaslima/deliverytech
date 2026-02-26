package com.deliverytech.delivery_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI deliveryApiOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DeliveryTech API")
                .description("""
                    # 🚀 API REST para Sistema de Delivery
                    
                    Esta API fornece endpoints completos para gerenciamento de:
                    
                    ## 📋 Funcionalidades
                    - 👥 **Clientes**: Cadastro, consulta e gerenciamento de perfis
                    - 🍽️ **Restaurantes**: Gerenciamento de parceiros e seus cardápios
                    - 🍕 **Produtos**: Catálogo completo de produtos e categorias
                    - 📦 **Pedidos**: Processamento e acompanhamento de pedidos
                    - 📊 **Relatórios**: Análises de vendas e estatísticas
                    
                    ## 🔐 Códigos HTTP Utilizados
                    - `200 OK`: Requisição bem-sucedida
                    - `201 Created`: Recurso criado com sucesso
                    - `204 No Content`: Operação bem-sucedida sem retorno de dados
                    - `400 Bad Request`: Dados de entrada inválidos
                    - `404 Not Found`: Recurso não encontrado
                    - `409 Conflict`: Conflito de dados (ex: duplicação)
                    - `500 Internal Server Error`: Erro interno do servidor
                    
                    ## 📄 Paginação
                    Endpoints de listagem suportam parâmetros:
                    - `page`: Número da página (inicia em 0)
                    - `size`: Quantidade de itens por página
                    - `sort`: Campo e direção de ordenação (ex: name,asc)
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("DeliveryTech Team")
                    .email("dev@deliverytech.com")
                    .url("https://github.com/patrickfariaslima/deliverytech"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT"))
            )
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Servidor de Desenvolvimento"),
                new Server()
                    .url("https://api.deliverytech.com")
                    .description("Servidor de Produção")
            ));
    }
}