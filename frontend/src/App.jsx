import { useEffect, useState } from "react";

const emptyProduct = {
  idCategoria: "",
  nome: "",
  descricao: "",
  preco: ""
};

const emptyClient = {
  nome: "",
  email: "",
  telefone: "",
  endereco: ""
};

const emptyOrder = { idCliente: "" };

const emptyOrderItem = {
  idPedido: "",
  idProduto: "",
  quantidade: "1"
};

const emptyPayment = {
  idPedido: "",
  metodoPagamento: ""
};

const formatCurrency = (value) =>
  new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL"
  }).format(value);

async function request(path, options) {
  const response = await fetch(`/api${path}`, options);
  const isJson = response.headers.get("content-type")?.includes("application/json");
  const body = isJson ? await response.json() : null;

  if (!response.ok) {
    throw new Error(body?.message || "Não foi possível concluir a operação.");
  }

  return body;
}

export default function App() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [clients, setClients] = useState([]);
  const [orders, setOrders] = useState([]);
  const [orderItems, setOrderItems] = useState([]);
  const [payments, setPayments] = useState([]);
  const [activeScreen, setActiveScreen] = useState("dashboard");
  const [form, setForm] = useState(emptyProduct);
  const [editingId, setEditingId] = useState(null);
  const [categoryName, setCategoryName] = useState("");
  const [editingCategoryId, setEditingCategoryId] = useState(null);
  const [clientForm, setClientForm] = useState(emptyClient);
  const [editingClientId, setEditingClientId] = useState(null);
  const [orderForm, setOrderForm] = useState(emptyOrder);
  const [orderItemForm, setOrderItemForm] = useState(emptyOrderItem);
  const [paymentForm, setPaymentForm] = useState(emptyPayment);
  const [paymentModal, setPaymentModal] = useState(null);
  const [darkMode, setDarkMode] = useState(() => window.localStorage.getItem("delivery-theme") === "earth-dark");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");

  const loadData = async () => {
    setLoading(true);
    setError("");

    try {
      const [productData, categoryData, clientData, orderData, orderItemData, paymentData] = await Promise.all([
        request("/produtos"),
        request("/categorias"),
        request("/clientes"),
        request("/pedidos"),
        request("/itenspedido"),
        request("/pagamentos")
      ]);
      setProducts(productData);
      setCategories(categoryData);
      setClients(clientData);
      setOrders(orderData);
      setOrderItems(orderItemData);
      setPayments(paymentData);
    } catch (loadError) {
      setError("Não foi possível carregar a API. Confirme se o Spring Boot está rodando em http://localhost:8080.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    document.documentElement.dataset.theme = darkMode ? "earth-dark" : "light";
    window.localStorage.setItem("delivery-theme", darkMode ? "earth-dark" : "light");
  }, [darkMode]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((currentForm) => ({ ...currentForm, [name]: value }));
  };

  const resetForm = () => {
    setForm(emptyProduct);
    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setNotice("");

    try {
      await request(editingId ? `/produtos/${editingId}` : "/produtos", {
        method: editingId ? "PUT" : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idCategoria: Number(form.idCategoria),
          nome: form.nome.trim(),
          descricao: form.descricao.trim(),
          preco: Number(form.preco)
        })
      });

      setNotice(editingId ? "Produto atualizado com sucesso." : "Produto cadastrado com sucesso.");
      resetForm();
      await loadData();
    } catch (submitError) {
      setError(submitError.message);
    } finally {
      setSubmitting(false);
    }
  };

  const startEdit = (product) => {
    setEditingId(product.id);
    setForm({
      idCategoria: "",
      nome: product.nome,
      descricao: product.descricao,
      preco: product.preco
    });
    setNotice("Selecione a categoria do produto antes de salvar a edição.");
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const removeProduct = async (product) => {
    if (!window.confirm(`Excluir o produto “${product.nome}”?`)) {
      return;
    }

    setError("");
    setNotice("");

    try {
      await request(`/produtos/${product.id}`, { method: "DELETE" });
      setNotice("Produto excluído com sucesso.");
      await loadData();
    } catch (removeError) {
      setError(removeError.message);
    }
  };

  const resetCategoryForm = () => {
    setCategoryName("");
    setEditingCategoryId(null);
  };

  const handleCategorySubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setNotice("");

    try {
      await request(editingCategoryId ? `/categorias/${editingCategoryId}` : "/categorias", {
        method: editingCategoryId ? "PUT" : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nome: categoryName.trim() })
      });
      setNotice(editingCategoryId ? "Categoria atualizada com sucesso." : "Categoria cadastrada com sucesso.");
      resetCategoryForm();
      await loadData();
    } catch (categoryError) {
      setError(categoryError.message);
    } finally {
      setSubmitting(false);
    }
  };

  const startCategoryEdit = (category) => {
    setEditingCategoryId(category.id);
    setCategoryName(category.nome);
  };

  const removeCategory = async (category) => {
    if (!window.confirm(`Excluir a categoria “${category.nome}”?`)) {
      return;
    }

    setError("");
    setNotice("");

    try {
      await request(`/categorias/${category.id}`, { method: "DELETE" });
      setNotice("Categoria excluída com sucesso.");
      await loadData();
    } catch (categoryError) {
      setError(categoryError.message);
    }
  };

  const handleClientChange = (event) => {
    const { name, value } = event.target;
    setClientForm((currentForm) => ({ ...currentForm, [name]: value }));
  };

  const resetClientForm = () => {
    setClientForm(emptyClient);
    setEditingClientId(null);
  };

  const handleClientSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setNotice("");

    try {
      await request(editingClientId ? `/clientes/${editingClientId}` : "/clientes", {
        method: editingClientId ? "PUT" : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          nome: clientForm.nome.trim(),
          email: clientForm.email.trim(),
          telefone: clientForm.telefone.trim(),
          endereco: clientForm.endereco.trim()
        })
      });
      setNotice(editingClientId ? "Cliente atualizado com sucesso." : "Cliente cadastrado com sucesso.");
      resetClientForm();
      await loadData();
    } catch (clientError) {
      setError(clientError.message);
    } finally {
      setSubmitting(false);
    }
  };

  const startClientEdit = (client) => {
    setEditingClientId(client.id);
    setClientForm({
      nome: client.nome,
      email: "",
      telefone: "",
      endereco: client.endereco
    });
    setNotice("Por segurança, a API não devolve e-mail e telefone. Informe esses dois campos novamente para salvar.");
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const removeClient = async (client) => {
    if (!window.confirm(`Excluir o cliente “${client.nome}”?`)) {
      return;
    }

    setError("");
    setNotice("");

    try {
      await request(`/clientes/${client.id}`, { method: "DELETE" });
      setNotice("Cliente excluído com sucesso.");
      await loadData();
    } catch (clientError) {
      setError(clientError.message);
    }
  };

  const handleOrderSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setNotice("");

    try {
      await request("/pedidos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idCliente: Number(orderForm.idCliente) })
      });
      setOrderForm(emptyOrder);
      setNotice("Pedido criado com total R$ 0,00. Agora selecione esse pedido para adicionar itens.");
      await loadData();
    } catch (orderError) {
      setError(orderError.message);
    } finally {
      setSubmitting(false);
    }
  };

  const handleOrderItemSubmit = async (event) => {
    event.preventDefault();
    const selectedOrder = orders.find((order) => order.id === Number(orderItemForm.idPedido));

    if (selectedOrder?.status === "CANCELADO") {
      setError("Não é possível adicionar itens a um pedido cancelado.");
      return;
    }

    setSubmitting(true);
    setError("");
    setNotice("");

    try {
      await request("/itenspedido", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idPedido: Number(orderItemForm.idPedido),
          idProduto: Number(orderItemForm.idProduto),
          quantidade: Number(orderItemForm.quantidade)
        })
      });
      setOrderItemForm((currentForm) => ({ ...currentForm, idProduto: "", quantidade: "1" }));
      setNotice("Item adicionado e total do pedido atualizado.");
      await loadData();
    } catch (itemError) {
      setError(itemError.message);
    } finally {
      setSubmitting(false);
    }
  };

  const cancelOrder = async (order) => {
    if (!window.confirm(`Cancelar o pedido #${order.id}?`)) {
      return;
    }

    setError("");
    setNotice("");

    try {
      await request(`/pedidos/${order.id}/cancelar`, { method: "PUT" });
      setNotice(`Pedido #${order.id} cancelado.`);
      await loadData();
    } catch (orderError) {
      setError(orderError.message);
    }
  };

  const clientName = (clientId) => clients.find((client) => client.id === clientId)?.nome || `Cliente #${clientId}`;

  const itemsForOrder = (orderId) => orderItems.filter((item) => item.idPedido === orderId);

  const paidRevenue = payments
    .filter((payment) => payment.status === "PAGO")
    .reduce((total, payment) => total + Number(payment.valor), 0);

  const pendingPaymentValue = payments
    .filter((payment) => payment.status === "PENDENTE")
    .reduce((total, payment) => total + Number(payment.valor), 0);

  const activeOrders = orders.filter((order) => order.status !== "CANCELADO").length;
  const recentOrders = [...orders].sort((first, second) => second.id - first.id).slice(0, 5);

  const availableOrdersForPayment = orders.filter(
    (order) => order.status !== "CANCELADO" && !payments.some((payment) => payment.idPedido === order.id)
  );

  const handlePaymentSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setNotice("");

    try {
      await request("/pagamentos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idPedido: Number(paymentForm.idPedido),
          metodoPagamento: paymentForm.metodoPagamento
        })
      });
      setPaymentForm(emptyPayment);
      setNotice("Pagamento criado como pendente. Confirme-o quando receber o valor.");
      await loadData();
    } catch (paymentError) {
      setError(paymentError.message);
    } finally {
      setSubmitting(false);
    }
  };

  const updatePaymentStatus = async (action) => {
    if (!paymentModal) {
      return;
    }

    const payment = paymentModal;
    const isPayment = action === "pagar";

    setError("");
    setNotice("");

    try {
      await request(`/pagamentos/${payment.id}/${action}`, { method: "PUT" });
      setNotice(isPayment ? "Pagamento confirmado com sucesso." : "Pagamento cancelado.");
      setPaymentModal(null);
      await loadData();
    } catch (paymentError) {
      setError(paymentError.message);
    }
  };

  return (
    <main className="app-shell">
      <header className="topbar">
        <div>
          <p className="eyebrow">PAINEL DE DELIVERY</p>
          <h1>{activeScreen === "dashboard" ? "Visão geral" : activeScreen === "products" ? "Produtos" : activeScreen === "categories" ? "Categorias" : activeScreen === "clients" ? "Clientes" : activeScreen === "orders" ? "Pedidos" : "Pagamentos"}</h1>
          <p className="subtitle">
            {activeScreen === "dashboard"
              ? "Acompanhe seu delivery em um só lugar."
              : activeScreen === "products"
              ? "Cadastre e organize o cardápio da sua loja."
              : activeScreen === "categories"
                ? "Organize os grupos que classificam seu cardápio."
                : activeScreen === "clients"
                  ? "Cadastre as pessoas que fazem pedidos na sua loja."
                  : activeScreen === "orders"
                    ? "Crie pedidos e adicione produtos ao pedido."
                    : "Crie e acompanhe os pagamentos dos pedidos."}
          </p>
        </div>
        <div className="topbar-actions">
          <button
            className="theme-toggle"
            type="button"
            aria-pressed={darkMode}
            onClick={() => setDarkMode((currentMode) => !currentMode)}
          >
            <span>{darkMode ? "☀" : "◐"}</span>
            {darkMode ? "Modo claro" : "Modo escuro"}
          </button>
          <button className="secondary-button" onClick={loadData} disabled={loading}>
            Atualizar lista
          </button>
        </div>
      </header>

      <nav className="navigation" aria-label="Seções do painel">
        <button
          className={activeScreen === "dashboard" ? "navigation-item active" : "navigation-item"}
          onClick={() => setActiveScreen("dashboard")}
        >
          Início
        </button>
        <button
          className={activeScreen === "products" ? "navigation-item active" : "navigation-item"}
          onClick={() => setActiveScreen("products")}
        >
          Produtos
        </button>
        <button
          className={activeScreen === "categories" ? "navigation-item active" : "navigation-item"}
          onClick={() => setActiveScreen("categories")}
        >
          Categorias
        </button>
        <button
          className={activeScreen === "clients" ? "navigation-item active" : "navigation-item"}
          onClick={() => setActiveScreen("clients")}
        >
          Clientes
        </button>
        <button
          className={activeScreen === "orders" ? "navigation-item active" : "navigation-item"}
          onClick={() => setActiveScreen("orders")}
        >
          Pedidos
        </button>
        <button
          className={activeScreen === "payments" ? "navigation-item active" : "navigation-item"}
          onClick={() => setActiveScreen("payments")}
        >
          Pagamentos
        </button>
      </nav>

      {activeScreen === "dashboard" ? (
        <section className="dashboard">
          <div className="metric-grid">
            <article className="metric-card accent-card">
              <p>Vendas confirmadas</p>
              <strong>{formatCurrency(paidRevenue)}</strong>
              <span>Somente pagamentos pagos</span>
            </article>
            <article className="metric-card">
              <p>Pedidos em andamento</p>
              <strong>{activeOrders}</strong>
              <span>De {orders.length} pedidos cadastrados</span>
            </article>
            <article className="metric-card">
              <p>Pagamentos pendentes</p>
              <strong>{formatCurrency(pendingPaymentValue)}</strong>
              <span>{payments.filter((payment) => payment.status === "PENDENTE").length} aguardando confirmação</span>
            </article>
            <article className="metric-card">
              <p>Clientes cadastrados</p>
              <strong>{clients.length}</strong>
              <span>{products.length} produtos no cardápio</span>
            </article>
          </div>

          <div className="dashboard-grid">
            <section className="dashboard-panel recent-orders-panel">
              <div className="list-heading">
                <div>
                  <h2>Pedidos recentes</h2>
                  <p>Os cinco últimos pedidos criados.</p>
                </div>
                <button className="text-button" onClick={() => setActiveScreen("orders")}>Ver pedidos</button>
              </div>

              {loading ? (
                <p className="empty-state">Carregando dados...</p>
              ) : recentOrders.length === 0 ? (
                <p className="empty-state">Nenhum pedido criado ainda.</p>
              ) : (
                <div className="recent-order-list">
                  {recentOrders.map((order) => (
                    <div className="recent-order" key={order.id}>
                      <div>
                        <strong>Pedido #{order.id}</strong>
                        <span>{clientName(order.idCliente)}</span>
                      </div>
                      <div>
                        <strong>{formatCurrency(order.valor)}</strong>
                        <span className={`status-badge ${order.status.toLowerCase()}`}>{order.status.replaceAll("_", " ")}</span>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </section>

            <section className="dashboard-panel status-panel">
              <h2>Resumo de pagamentos</h2>
              <p className="panel-description">Acompanhe a situação financeira dos pedidos.</p>
              <div className="payment-status-summary">
                <div><span className="summary-dot paid-dot" />Pagos <strong>{payments.filter((payment) => payment.status === "PAGO").length}</strong></div>
                <div><span className="summary-dot pending-dot" />Pendentes <strong>{payments.filter((payment) => payment.status === "PENDENTE").length}</strong></div>
                <div><span className="summary-dot cancelled-dot" />Cancelados <strong>{payments.filter((payment) => payment.status === "CANCELADO").length}</strong></div>
              </div>
              <button className="secondary-button dashboard-button" onClick={() => setActiveScreen("payments")}>Ver pagamentos</button>
            </section>
          </div>
        </section>
      ) : activeScreen === "products" ? (
      <section className="content-grid">
        <form className="product-form" onSubmit={handleSubmit}>
          <div className="form-heading">
            <h2>{editingId ? "Editar produto" : "Novo produto"}</h2>
            {editingId && (
              <button className="link-button" type="button" onClick={resetForm}>
                Cancelar edição
              </button>
            )}
          </div>

          <label>
            Categoria
            <select name="idCategoria" value={form.idCategoria} onChange={handleChange} required>
              <option value="">Selecione uma categoria</option>
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.nome}
                </option>
              ))}
            </select>
          </label>

          <label>
            Nome
            <input name="nome" value={form.nome} onChange={handleChange} required placeholder="Ex.: Pizza margherita" />
          </label>

          <label>
            Descrição
            <textarea name="descricao" value={form.descricao} onChange={handleChange} required placeholder="Descreva o produto" rows="4" />
          </label>

          <label>
            Preço
            <input name="preco" value={form.preco} onChange={handleChange} required min="0.01" step="0.01" type="number" placeholder="0,00" />
          </label>

          <button className="primary-button" type="submit" disabled={submitting || categories.length === 0}>
            {submitting ? "Salvando..." : editingId ? "Salvar alterações" : "Cadastrar produto"}
          </button>

          {categories.length === 0 && !loading && <p className="helper-text">Crie ao menos uma categoria pela API antes de cadastrar produtos.</p>}
        </form>

        <section className="product-list" aria-live="polite">
          <div className="list-heading">
            <div>
              <h2>Cardápio</h2>
              <p>{products.length} produto{products.length === 1 ? "" : "s"} cadastrado{products.length === 1 ? "" : "s"}</p>
            </div>
          </div>

          {error && <p className="message error-message">{error}</p>}
          {notice && <p className="message success-message">{notice}</p>}

          {loading ? (
            <p className="empty-state">Carregando produtos...</p>
          ) : products.length === 0 ? (
            <p className="empty-state">Ainda não há produtos cadastrados.</p>
          ) : (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>Produto</th>
                    <th>Descrição</th>
                    <th>Preço</th>
                    <th aria-label="Ações" />
                  </tr>
                </thead>
                <tbody>
                  {products.map((product) => (
                    <tr key={product.id}>
                      <td className="product-name">{product.nome}</td>
                      <td>{product.descricao}</td>
                      <td>{formatCurrency(product.preco)}</td>
                      <td className="actions">
                        <button className="text-button" onClick={() => startEdit(product)}>Editar</button>
                        <button className="text-button danger" onClick={() => removeProduct(product)}>Excluir</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </section>
      </section>
      ) : activeScreen === "categories" ? (
        <section className="content-grid category-layout">
          <form className="product-form" onSubmit={handleCategorySubmit}>
            <div className="form-heading">
              <h2>{editingCategoryId ? "Editar categoria" : "Nova categoria"}</h2>
              {editingCategoryId && (
                <button className="link-button" type="button" onClick={resetCategoryForm}>
                  Cancelar edição
                </button>
              )}
            </div>

            <label>
              Nome da categoria
              <input
                value={categoryName}
                onChange={(event) => setCategoryName(event.target.value)}
                required
                placeholder="Ex.: Pizzas"
              />
            </label>

            <button className="primary-button" type="submit" disabled={submitting}>
              {submitting ? "Salvando..." : editingCategoryId ? "Salvar alterações" : "Cadastrar categoria"}
            </button>
          </form>

          <section className="product-list category-list" aria-live="polite">
            <div className="list-heading">
              <div>
                <h2>Categorias cadastradas</h2>
                <p>{categories.length} categoria{categories.length === 1 ? "" : "s"}</p>
              </div>
            </div>

            {error && <p className="message error-message">{error}</p>}
            {notice && <p className="message success-message">{notice}</p>}

            {loading ? (
              <p className="empty-state">Carregando categorias...</p>
            ) : categories.length === 0 ? (
              <p className="empty-state">Cadastre a primeira categoria do seu cardápio.</p>
            ) : (
              <ul className="category-items">
                {categories.map((category) => (
                  <li key={category.id}>
                    <span>{category.nome}</span>
                    <div className="actions">
                      <button className="text-button" onClick={() => startCategoryEdit(category)}>Editar</button>
                      <button className="text-button danger" onClick={() => removeCategory(category)}>Excluir</button>
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </section>
        </section>
      ) : activeScreen === "clients" ? (
        <section className="content-grid client-layout">
          <form className="product-form" onSubmit={handleClientSubmit}>
            <div className="form-heading">
              <h2>{editingClientId ? "Editar cliente" : "Novo cliente"}</h2>
              {editingClientId && (
                <button className="link-button" type="button" onClick={resetClientForm}>
                  Cancelar edição
                </button>
              )}
            </div>

            <label>
              Nome
              <input name="nome" value={clientForm.nome} onChange={handleClientChange} required placeholder="Nome completo" />
            </label>

            <label>
              E-mail
              <input name="email" value={clientForm.email} onChange={handleClientChange} required type="email" placeholder="cliente@email.com" />
            </label>

            <label>
              Telefone
              <input name="telefone" value={clientForm.telefone} onChange={handleClientChange} required placeholder="(11) 99999-9999" />
            </label>

            <label>
              Endereço
              <textarea name="endereco" value={clientForm.endereco} onChange={handleClientChange} required rows="3" placeholder="Rua, número, bairro e complemento" />
            </label>

            <button className="primary-button" type="submit" disabled={submitting}>
              {submitting ? "Salvando..." : editingClientId ? "Salvar alterações" : "Cadastrar cliente"}
            </button>
          </form>

          <section className="product-list" aria-live="polite">
            <div className="list-heading">
              <div>
                <h2>Clientes cadastrados</h2>
                <p>{clients.length} cliente{clients.length === 1 ? "" : "s"}</p>
              </div>
            </div>

            {error && <p className="message error-message">{error}</p>}
            {notice && <p className="message success-message">{notice}</p>}

            {loading ? (
              <p className="empty-state">Carregando clientes...</p>
            ) : clients.length === 0 ? (
              <p className="empty-state">Ainda não há clientes cadastrados.</p>
            ) : (
              <div className="table-wrapper">
                <table>
                  <thead>
                    <tr>
                      <th>Cliente</th>
                      <th>Endereço</th>
                      <th aria-label="Ações" />
                    </tr>
                  </thead>
                  <tbody>
                    {clients.map((client) => (
                      <tr key={client.id}>
                        <td className="product-name">{client.nome}</td>
                        <td>{client.endereco}</td>
                        <td className="actions">
                          <button className="text-button" onClick={() => startClientEdit(client)}>Editar</button>
                          <button className="text-button danger" onClick={() => removeClient(client)}>Excluir</button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </section>
        </section>
      ) : activeScreen === "orders" ? (
        <section className="orders-screen">
          <div className="order-forms">
            <form className="product-form" onSubmit={handleOrderSubmit}>
              <div className="form-heading"><h2>1. Criar pedido</h2></div>
              <label>
                Cliente
                <select
                  value={orderForm.idCliente}
                  onChange={(event) => setOrderForm({ idCliente: event.target.value })}
                  required
                >
                  <option value="">Selecione um cliente</option>
                  {clients.map((client) => <option key={client.id} value={client.id}>{client.nome}</option>)}
                </select>
              </label>
              <button className="primary-button" type="submit" disabled={submitting || clients.length === 0}>
                Criar pedido vazio
              </button>
            </form>

            <form className="product-form" onSubmit={handleOrderItemSubmit}>
              <div className="form-heading"><h2>2. Adicionar item</h2></div>
              <label>
                Pedido
                <select
                  value={orderItemForm.idPedido}
                  onChange={(event) => setOrderItemForm((currentForm) => ({ ...currentForm, idPedido: event.target.value }))}
                  required
                >
                  <option value="">Selecione um pedido</option>
                  {orders.map((order) => <option key={order.id} value={order.id}>#{order.id} — {clientName(order.idCliente)} ({order.status})</option>)}
                </select>
              </label>
              <label>
                Produto
                <select
                  value={orderItemForm.idProduto}
                  onChange={(event) => setOrderItemForm((currentForm) => ({ ...currentForm, idProduto: event.target.value }))}
                  required
                >
                  <option value="">Selecione um produto</option>
                  {products.map((product) => <option key={product.id} value={product.id}>{product.nome} — {formatCurrency(product.preco)}</option>)}
                </select>
              </label>
              <label>
                Quantidade
                <input
                  min="1"
                  type="number"
                  value={orderItemForm.quantidade}
                  onChange={(event) => setOrderItemForm((currentForm) => ({ ...currentForm, quantidade: event.target.value }))}
                  required
                />
              </label>
              <button className="primary-button" type="submit" disabled={submitting || orders.length === 0 || products.length === 0}>
                Adicionar item
              </button>
            </form>
          </div>

          <section className="product-list" aria-live="polite">
            <div className="list-heading">
              <div>
                <h2>Pedidos cadastrados</h2>
                <p>{orders.length} pedido{orders.length === 1 ? "" : "s"}</p>
              </div>
            </div>
            {error && <p className="message error-message">{error}</p>}
            {notice && <p className="message success-message">{notice}</p>}

            {loading ? (
              <p className="empty-state">Carregando pedidos...</p>
            ) : orders.length === 0 ? (
              <p className="empty-state">Crie o primeiro pedido para começar.</p>
            ) : (
              <div className="order-cards">
                {orders.map((order) => (
                  <article className="order-card" key={order.id}>
                    <div className="order-card-heading">
                      <div><p className="order-code">PEDIDO #{order.id}</p><h3>{clientName(order.idCliente)}</h3></div>
                      <span className={`status-badge ${order.status.toLowerCase()}`}>{order.status.replaceAll("_", " ")}</span>
                    </div>
                    <p className="order-total">{formatCurrency(order.valor)}</p>
                    <p className="order-items">{itemsForOrder(order.id).length} item{itemsForOrder(order.id).length === 1 ? "" : "ns"} adicionado{itemsForOrder(order.id).length === 1 ? "" : "s"}</p>
                    {order.status !== "CANCELADO" && <button className="text-button danger" onClick={() => cancelOrder(order)}>Cancelar pedido</button>}
                  </article>
                ))}
              </div>
            )}
          </section>
        </section>
      ) : (
        <section className="content-grid payment-layout">
          <form className="product-form" onSubmit={handlePaymentSubmit}>
            <div className="form-heading"><h2>Novo pagamento</h2></div>
            <label>
              Pedido disponível
              <select
                value={paymentForm.idPedido}
                onChange={(event) => setPaymentForm((currentForm) => ({ ...currentForm, idPedido: event.target.value }))}
                required
              >
                <option value="">Selecione um pedido</option>
                {availableOrdersForPayment.map((order) => (
                  <option key={order.id} value={order.id}>
                    #{order.id} — {clientName(order.idCliente)} — {formatCurrency(order.valor)}
                  </option>
                ))}
              </select>
            </label>
            <label>
              Método de pagamento
              <select
                value={paymentForm.metodoPagamento}
                onChange={(event) => setPaymentForm((currentForm) => ({ ...currentForm, metodoPagamento: event.target.value }))}
                required
              >
                <option value="">Selecione o método</option>
                <option value="PIX">PIX</option>
                <option value="CARTAO">Cartão</option>
                <option value="DINHEIRO">Dinheiro</option>
              </select>
            </label>
            <button className="primary-button" type="submit" disabled={submitting || availableOrdersForPayment.length === 0}>
              Criar pagamento
            </button>
            {availableOrdersForPayment.length === 0 && !loading && <p className="helper-text">Não há pedidos sem pagamento disponíveis.</p>}
          </form>

          <section className="product-list" aria-live="polite">
            <div className="list-heading">
              <div>
                <h2>Pagamentos</h2>
                <p>{payments.length} pagamento{payments.length === 1 ? "" : "s"} registrado{payments.length === 1 ? "" : "s"}</p>
              </div>
            </div>

            {error && <p className="message error-message">{error}</p>}
            {notice && <p className="message success-message">{notice}</p>}

            {loading ? (
              <p className="empty-state">Carregando pagamentos...</p>
            ) : payments.length === 0 ? (
              <p className="empty-state">Ainda não há pagamentos registrados.</p>
            ) : (
              <div className="payment-items">
                {payments.map((payment) => (
                  <article className="payment-card" key={payment.id}>
                    <div>
                      <p className="order-code">PAGAMENTO #{payment.id}</p>
                      <h3>Pedido #{payment.idPedido} — {clientName(orders.find((order) => order.id === payment.idPedido)?.idCliente)}</h3>
                      <p className="payment-method">{payment.metodoPagamento}</p>
                    </div>
                    <div className="payment-summary">
                      <p className="order-total">{formatCurrency(payment.valor)}</p>
                      <span className={`status-badge ${payment.status.toLowerCase()}`}>{payment.status}</span>
                      {payment.status === "PENDENTE" && <button className="text-button" onClick={() => setPaymentModal(payment)}>Alterar status</button>}
                    </div>
                  </article>
                ))}
              </div>
            )}
          </section>
        </section>
      )}

      {paymentModal && (
        <div className="modal-backdrop" onMouseDown={() => setPaymentModal(null)}>
          <section
            className="status-modal"
            role="dialog"
            aria-modal="true"
            aria-labelledby="payment-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className="modal-heading">
              <div>
                <p className="eyebrow">PAGAMENTO #{paymentModal.id}</p>
                <h2 id="payment-modal-title">Alterar status</h2>
              </div>
              <button className="modal-close" type="button" aria-label="Fechar" onClick={() => setPaymentModal(null)}>×</button>
            </div>
            <p className="modal-description">
              Pedido #{paymentModal.idPedido} · {formatCurrency(paymentModal.valor)} · {paymentModal.metodoPagamento}
            </p>
            <div className="status-options">
              <button className="status-option paid-option" type="button" onClick={() => updatePaymentStatus("pagar")}>
                <span>✓</span>
                <strong>Pago</strong>
                <small>Confirma que o valor foi recebido.</small>
              </button>
              <button className="status-option cancelled-option" type="button" onClick={() => updatePaymentStatus("cancelar")}>
                <span>×</span>
                <strong>Cancelado</strong>
                <small>Encerra este pagamento sem confirmação.</small>
              </button>
            </div>
            <button className="link-button modal-cancel" type="button" onClick={() => setPaymentModal(null)}>Voltar sem alterar</button>
          </section>
        </div>
      )}
    </main>
  );
}
