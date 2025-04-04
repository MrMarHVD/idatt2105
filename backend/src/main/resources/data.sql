-- The BCryptPasswordEncoder has encoded the password 'password123'
INSERT INTO users (email, display_name, password_hash)
VALUES ('alice@example.com', 'alice', '$2a$10$9JB0yUlymHFA23jzoN9VWOPF6UIjLPCWFxwYV02kI/MlYdNJsDlBW');

INSERT INTO users (email, display_name, password_hash, role)
VALUES ('jakob@mail.com', 'jakob', '$2a$10$9JB0yUlymHFA23jzoN9VWOPF6UIjLPCWFxwYV02kI/MlYdNJsDlBW', 'ADMIN');

INSERT INTO categories (name, description)
VALUES ('Travel', 'travel');

INSERT INTO categories (name, description)
VALUES ('Appliance', 'appliance');

INSERT INTO categories (name, description)
VALUES ('Boat', 'boat');

INSERT INTO categories (name, description)
VALUES ('Book', 'book');

INSERT INTO categories (name, description)
VALUES ('Camera', 'camera');

INSERT INTO categories (name, description)
VALUES ('Car', 'car');

INSERT INTO categories (name, description)
VALUES ('Clothes', 'clothes');

INSERT INTO categories (name, description)
VALUES ('Computer', 'computer');

INSERT INTO categories (name, description)
VALUES ('Furniture', 'furniture');

INSERT INTO categories (name, description)
VALUES ('Motorcycle', 'motorcycle');

INSERT INTO categories (name, description)
VALUES ('Phone', 'phone');

INSERT INTO categories (name, description)
VALUES ('Art', 'art');

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (1, 1, 'Test', 'Test', 500.00, 'ACTIVE', NOW(), NOW(),62,5.7);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (1, 1, 'Lambi Deluxe', 'Test', 200.00, 'ACTIVE', NOW(), NOW(),62,8.1);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (1, 2, 'Mills Majones', 'Test', 500.00, 'ACTIVE', NOW(), NOW(),59,7.5);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (1, 2, 'Ny bil!!!!', 'Test', 100.00, 'ACTIVE', NOW(), NOW(),31,98);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (1, 1, 'Vaskepulver med jordbærsmak', 'Et fantastisk vaskepulver med smak av jordbær! Mmm!', 240.00, 'ACTIVE', NOW(), NOW(),60,6.5);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (2, 1, 'Dinosaurkjæledyr', 'Han heter Ronald.', 240.00, 'ACTIVE', NOW(), NOW(),60,5.5);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (2, 1, 'Femten planker', 'Femten planker jeg ikke trenger. Høy sentimental verdi!', 15000.00, 'ACTIVE', NOW(), NOW(),59,5.5);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (2, 1, 'Bringebærsyltetøy', 'Ikke så mye mer å si', 59.0, 'ACTIVE', NOW(), NOW(),62,7.5);

INSERT INTO items (seller_id, category_id, brief_description, full_description, price, status, published_at, updated_at, latitude, longitude)
VALUES (2, 1, 'Diskotek', 'Selger gammelt diskotek; familiebusiness.', 9000000.00, 'ACTIVE', NOW(), NOW(),62.4,6.15);

-- Images for items

-- Insert mock images for item 2 (Lambi Deluxe)
INSERT INTO item_images (item_id, image_url, position)
VALUES (2, 'https://i.ibb.co/wrBBYXmw/lambi.jpg', 0);

-- Insert mock images for item 3 (Mills Majones)
INSERT INTO item_images (item_id, image_url, position)
VALUES (3, 'https://i.ibb.co/VYxptvrF/mills.jpg', 0);

-- Insert mock images for item 4 (Ny bil!!!!)
INSERT INTO item_images (item_id, image_url, position)
VALUES (4, 'https://i.ibb.co/FkRNqvG3/lambo.jpg', 0);

-- Insert mock images for item 5 (Vaskepulver med jordbærsmak)
INSERT INTO item_images (item_id, image_url, position)
VALUES (5, 'https://i.ibb.co/HfHbnGNs/vaskepulver.webp', 0);

-- Insert mock images for item 6 (Dinosaurkjæledyr)
INSERT INTO item_images (item_id, image_url, position)
VALUES (6, 'https://i.ibb.co/h1922gsJ/velociraptor.webp', 0);

-- Insert mock images for item 7 (Femten planker)
INSERT INTO item_images (item_id, image_url, position)
VALUES (7, 'https://i.ibb.co/Y5mZ4DJ/15-planks.jpg', 0);


-- Insert mock images for item 8 (Bringebærsyltetøy)
INSERT INTO item_images (item_id, image_url, position)
VALUES (8, 'https://i.ibb.co/ktPs8NS/syltetoy.png', 0);

-- Insert mock images for item 9 (Diskotek)
INSERT INTO item_images (item_id, image_url, position)
VALUES (9, 'https://i.ibb.co/HTFJ6ky9/discoteque.jpg', 0);


