-- The BCryptPasswordEncoder has encoded the password 'password123'
INSERT INTO users (id, username, email, password_hash, role, created_at, updated_at)
VALUES (1, 'alice', 'alice@example.com', '$2a$10$9JB0yUlymHFA23jzoN9VWOPF6UIjLPCWFxwYV02kI/MlYdNJsDlBW', 'USER', NOW(), NOW());

INSERT INTO users (id, username, email, password_hash, role, created_at, updated_at)
VALUES (2, 'jakob', 'jakob@mail.com', '$2a$10$9JB0yUlymHFA23jzoN9VWOPF6UIjLPCWFxwYV02kI/MlYdNJsDlBW', 'ADMIN', NOW(), NOW());

INSERT INTO categories (name, description)
VALUES ('Test', 'Test');

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at)
VALUES (1, 1, 'Test', 'Test', 500.00, 'ACTIVE', NOW(), NOW());

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at)
VALUES (1, 1, 'Test', 'Test', 200.00, 'ACTIVE', NOW(), NOW());