# Frontend do Delivery

Interface React para a API Spring Boot de delivery. O backend não foi modificado.

## Como usar

1. Inicie a API Spring Boot na porta `8080`.
2. Nesta pasta, instale as dependências com `npm install`.
3. Inicie o frontend com `npm run dev`.
4. Abra o endereço indicado no terminal, normalmente `http://localhost:5173`.

Durante o desenvolvimento, o Vite encaminha as chamadas feitas para `/api/*` à API em `http://localhost:8080`, sem exigir alteração de CORS no backend.

## Telas disponíveis

### Início

Resumo de vendas confirmadas, pedidos em andamento, pagamentos pendentes, clientes e pedidos recentes.

O botão no topo alterna entre o tema claro e um tema escuro em tons terrosos. A escolha fica salva no navegador.

A tela de produtos consome:

- `GET /produtos`
- `GET /categorias`
- `POST /produtos`
- `PUT /produtos/{id}`
- `DELETE /produtos/{id}`

Para cadastrar um produto, crie pelo menos uma categoria na API primeiro.

### Categorias

- `GET /categorias`
- `POST /categorias`
- `PUT /categorias/{id}`
- `DELETE /categorias/{id}`

### Clientes

- `GET /clientes`
- `POST /clientes`
- `PUT /clientes/{id}`
- `DELETE /clientes/{id}`

### Pedidos

- `GET /pedidos`
- `POST /pedidos`
- `PUT /pedidos/{id}/cancelar`
- `GET /itenspedido`
- `POST /itenspedido`

### Pagamentos

- `GET /pagamentos`
- `POST /pagamentos`
- `PUT /pagamentos/{id}/pagar`
- `PUT /pagamentos/{id}/cancelar`
