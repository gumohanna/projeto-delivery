# Delivery Manager

Aplicação full stack para gerenciamento de um delivery. Permite organizar cardápio, cadastrar clientes, criar pedidos, adicionar itens e acompanhar pagamentos.

## Demonstração

- [Acessar aplicação](https://projeto-delivery-qpf8pjh2-mohanna.vercel.app)

## Funcionalidades

- Dashboard com vendas, pedidos e pagamentos.
- Gerenciamento de categorias e produtos.
- Cadastro de clientes.
- Criação de pedidos e inclusão de itens.
- Controle de pagamentos pendentes, pagos e cancelados.
- Tema claro e modo escuro em tons terrosos.

## Tecnologias

**Backend**

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

**Frontend**

- React
- Vite
- CSS

**Deploy**

- Railway: API e banco de dados.
- Vercel: frontend.

## Estrutura

```text
delivery/
├── src/                 # API Spring Boot
├── frontend/            # Interface React
├── pom.xml
└── README.md
```

## Executar localmente

### Backend

Configure as variáveis de ambiente:

```text
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha
```

Execute:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

### Frontend

```bash
cd frontend
npm install
npm run dev
```

O frontend normalmente ficará disponível em `http://localhost:5173`.

## Endpoints principais

| Recurso | Caminho |
| --- | --- |
| Categorias | `/categorias` |
| Produtos | `/produtos` |
| Clientes | `/clientes` |
| Pedidos | `/pedidos` |
| Itens de pedido | `/itenspedido` |
| Pagamentos | `/pagamentos` |

## Desenvolvimento assistido por IA

O frontend foi desenvolvido com assistência de IA, usada para apoiar a criação da interface, organização das telas, estilização e integração com a API. O projeto foi revisado, testado e integrado pelo autor.

## Autor

Desenvolvido por Mohanna.