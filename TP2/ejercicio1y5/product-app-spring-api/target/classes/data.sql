-- Initial user for API Basic Auth (username: kindson, password: 2424)
INSERT IGNORE INTO user (id, firstname, lastname, username, password, password_hash) VALUES
(1, 'Kindson', 'Genius', 'kindson', '$2a$10$fFOUKJNiTC7R4aelRaxLf.dnSGKjEjzUTL3R/HFU.1JCTCiKm3Z6C', '$2a$10$fFOUKJNiTC7R4aelRaxLf.dnSGKjEjzUTL3R/HFU.1JCTCiKm3Z6C');

-- Categories
INSERT IGNORE INTO category (id, title, meta_title, slug, content) VALUES
(1, 'Electronics', 'Electronics', 'electronics', 'Electronic devices and gadgets'),
(2, 'Home & Living', 'Home & Living', 'home-living', 'Furniture and home accessories'),
(3, 'Sports & Fitness', 'Sports & Fitness', 'sports-fitness', 'Sports equipment and workout gear');

-- SubCategories
INSERT IGNORE INTO sub_category (id, categoryid, description) VALUES
(1, 1, 'Computers & Accessories'),
(2, 1, 'Audio & Video'),
(3, 2, 'Furniture'),
(4, 2, 'Kitchen Appliances'),
(5, 3, 'Fitness Gear');

-- Initial Products
INSERT IGNORE INTO product (id, title, summary, content, created_at, categoryid, subcategoryid) VALUES
(1, 'UltraSoft Pillow', 'Comfortable pillow', 'A luxurious pillow made with the finest down feathers.', NOW(), 2, 3),
(2, 'ProGaming Mouse', 'Ergonomic gaming mouse', 'Ergonomic gaming mouse with customizable RGB lighting.', NOW(), 1, 1),
(3, 'SoundWave Bluetooth Speaker', 'Portable speaker', 'Portable speaker with high-definition sound and deep bass.', NOW(), 1, 2),
(4, 'FlexiYoga Mat', 'Eco-friendly yoga mat', 'Non-slip yoga mat made from eco-friendly materials.', NOW(), 3, 5);
