INSERT INTO clients (name, email, phone_number, address, registered_at, active) VALUES
('João Silva', 'joao@email.com', '(11) 99999-1111', 'Rua A, 123 - São Paulo/SP', CURRENT_TIMESTAMP, true),
('Maria Santos', 'maria@email.com', '(11) 99999-2222', 'Rua B, 456 - São Paulo/SP', CURRENT_TIMESTAMP, true),
('Pedro Oliveira', 'pedro@email.com', '(11) 99999-3333', 'Rua C, 789 - São Paulo/SP', CURRENT_TIMESTAMP, true);

INSERT INTO restaurants (name, category, address, phone_number, delivery_fee, rating,  active) VALUES
('Pizzaria Bella', 'Italiana', 'Av. Paulista, 1000 - São Paulo/SP', '(11) 3333-1111', 5.00, 4.5, true),
('Burger House', 'Hamburgueria', 'Rua Augusta, 500 - São Paulo/SP', '(11) 3333-2222', 3.50, 4.2, true),
('Sushi Master', 'Japonesa', 'Rua Liberdade, 200 - São Paulo/SP', '(11) 3333-3333', 8.00, 4.8, true);


INSERT INTO products (
    id,
    name,
    description,
    category,
    price,
    available,
    restaurant_id
) VALUES
(1, 'Pizza Margherita', 'Molho de tomate, mussarela e manjericão', 'Pizza', 35.90, true, 1),
(2, 'Pizza Calabresa', 'Molho de tomate, mussarela e calabresa', 'Pizza', 38.90, true, 1),
(3, 'Lasanha Bolonhesa', 'Lasanha tradicional com molho bolonhesa', 'Massa', 28.90, true, 1),

(4, 'X-Burger', 'Hambúrguer, queijo, alface e tomate', 'Hambúrguer', 18.90, true, 2),
(5, 'X-Bacon', 'Hambúrguer, queijo, bacon, alface e tomate', 'Hambúrguer', 22.90, true, 2),

(6, 'Batata Frita', 'Porção de batata frita crocante', 'Acompanhamento', 12.90, true, 2),

(7, 'Combo Sashimi', '15 peças de sashimi variado', 'Sashimi', 45.90, true, 3),
(8, 'Hot Roll Salmão', '8 peças de hot roll de salmão', 'Hot Roll', 32.90, true, 3);


INSERT INTO orders (
    id,
    order_date,
    delivery_address,
    order_number,
    delivery_fee,
    total,
    status,
    client_id,
    restaurant_id
) VALUES
(1, CURRENT_TIMESTAMP, 'Rua A, 123', 'PED1234567890', 5.00, 64.80, 'PENDING', 1, 1),

(2, CURRENT_TIMESTAMP, 'Rua B, 456', 'PED1234567891', 7.00, 41.80, 'CONFIRMED', 2, 2),

(3, CURRENT_TIMESTAMP, 'Rua C, 789', 'PED1234567892', 6.00, 78.80, 'DELIVERED', 3, 3);




INSERT INTO ordered_items (quantity, item_price, total, order_id, product_id) VALUES
(1, 35.90, 35.90, 1, 1),
(1, 28.90, 28.90, 1, 3),

(1, 22.90, 22.90, 2, 5),
(1, 18.90, 18.90, 2, 4),

(1, 45.90, 45.90, 3, 7),
(1, 32.90, 32.90, 3, 8);