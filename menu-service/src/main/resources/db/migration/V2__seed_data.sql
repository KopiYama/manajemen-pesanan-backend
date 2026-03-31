INSERT INTO jenis_makanan (id, nama_jenis) VALUES (1, 'Makanan');
INSERT INTO jenis_makanan (id, nama_jenis) VALUES (2, 'Minuman');

-- Makanan (jenis_id = 1)
INSERT INTO menu_makanan (nama_menu, deskripsi, harga, jenis_id, image_url) VALUES 
('Nasi Goreng Spesial', 'Nasi goreng dengan telur, ayam, dan sayuran', 25000.00, 1, 'http://localhost:9000/menu-images/nasi-goreng-spesial.jpg'),
('Mie Goreng', 'Mie goreng dengan bumbu spesial rumah', 22000.00, 1, 'http://localhost:9000/menu-images/mie-goreng.jpg'),
('Ayam Bakar', 'Ayam bakar dengan sambal dan lalapan', 35000.00, 1, 'http://localhost:9000/menu-images/ayam-bakar.jpg'),
('Soto Ayam', 'Soto ayam kuah bening dengan suwiran ayam', 28000.00, 1, 'http://localhost:9000/menu-images/soto-ayam.jpg'),
('Gado-gado', 'Sayuran rebus dengan saus kacang', 20000.00, 1, 'http://localhost:9000/menu-images/gado-gado.jpg'),
('Nasi Uduk', 'Nasi uduk dengan lauk pelengkap', 23000.00, 1, 'http://localhost:9000/menu-images/nasi-uduk.jpg'),
('Ayam Geprek', 'Ayam goreng crispy dengan sambal bawang', 30000.00, 1, 'http://localhost:9000/menu-images/ayam-geprek.jpg'),
('Bakso', 'Bakso sapi dengan mie dan kuah kaldu', 25000.00, 1, 'http://localhost:9000/menu-images/bakso.jpg'),
('Rendang', 'Rendang daging sapi dengan bumbu rempah', 40000.00, 1, 'http://localhost:9000/menu-images/rendang.jpg'),
('Capcay', 'Tumis sayuran dengan saus tiram', 22000.00, 1, 'http://localhost:9000/menu-images/capcay.jpg');

-- Minuman (jenis_id = 2)
INSERT INTO menu_makanan (nama_menu, deskripsi, harga, jenis_id, image_url) VALUES 
('Es Teh Manis', 'Teh manis dingin segar', 8000.00, 2, 'http://localhost:9000/menu-images/es-teh-manis.jpg'),
('Lemon Tea', 'Teh dengan perasan lemon segar', 12000.00, 2, 'http://localhost:9000/menu-images/lemon-tea.jpg'),
('Es Jeruk', 'Jeruk peras dingin', 10000.00, 2, 'http://localhost:9000/menu-images/es-jeruk.jpg'),
('Jus Alpukat', 'Jus alpukat dengan susu kental manis', 18000.00, 2, 'http://localhost:9000/menu-images/jus-alpukat.jpg'),
('Es Kopi Susu', 'Kopi susu dingin khas Indonesia', 20000.00, 2, 'http://localhost:9000/menu-images/es-kopi-susu.jpg'),
('Air Mineral', 'Air mineral botol 600ml', 5000.00, 2, 'http://localhost:9000/menu-images/air-mineral.jpg'),
('Jus Mangga', 'Jus mangga segar tanpa pengawet', 15000.00, 2, 'http://localhost:9000/menu-images/jus-mangga.jpg'),
('Es Cincau', 'Cincau hitam dengan santan dan gula merah', 12000.00, 2, 'http://localhost:9000/menu-images/es-cincau.jpg'),
('Teh Tarik', 'Teh susu khas melayu', 13000.00, 2, 'http://localhost:9000/menu-images/teh-tarik.jpg'),
('Milkshake Coklat', 'Milkshake coklat creamy dengan whipped', 22000.00, 2, 'http://localhost:9000/menu-images/milkshake-coklat.jpg');
