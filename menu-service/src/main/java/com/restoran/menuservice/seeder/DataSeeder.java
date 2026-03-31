package com.restoran.menuservice.seeder;

import com.restoran.menuservice.entity.JenisMakanan;
import com.restoran.menuservice.entity.MenuMakanan;
import com.restoran.menuservice.repository.JenisMakananRepository;
import com.restoran.menuservice.repository.MenuMakananRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final JenisMakananRepository jenisRepository;
    private final MenuMakananRepository menuRepository;

    @Override
    public void run(String... args) {
        if (jenisRepository.count() == 0) {
            seedJenisMakanan();
        }
        if (menuRepository.count() == 0) {
            seedMenuMakanan();
        }
    }

    private void seedJenisMakanan() {
        log.info("Seeding Jenis Makanan data...");
        List<JenisMakanan> types = Arrays.asList(
                JenisMakanan.builder().namaJenis("Makanan").build(),
                JenisMakanan.builder().namaJenis("Minuman").build()
        );
        jenisRepository.saveAll(types);
        log.info("Inserted 2 Jenis Makanan records");
    }

    private void seedMenuMakanan() {
        log.info("Seeding Menu Makanan data...");
        JenisMakanan makanan = jenisRepository.findByNamaJenis("Makanan").orElseThrow();
        JenisMakanan minuman = jenisRepository.findByNamaJenis("Minuman").orElseThrow();

        // Makanan
        createMenu("Nasi Goreng Spesial", "Nasi goreng dengan telur, ayam, dan sayuran", new BigDecimal("25000.00"), makanan);
        createMenu("Mie Goreng", "Mie goreng dengan bumbu spesial rumah", new BigDecimal("22000.00"), makanan);
        createMenu("Ayam Bakar", "Ayam bakar dengan sambal dan lalapan", new BigDecimal("35000.00"), makanan);
        createMenu("Soto Ayam", "Soto ayam kuah bening dengan suwiran ayam", new BigDecimal("28000.00"), makanan);
        createMenu("Gado-gado", "Sayuran rebus dengan saus kacang", new BigDecimal("20000.00"), makanan);
        createMenu("Nasi Uduk", "Nasi uduk dengan lauk pelengkap", new BigDecimal("23000.00"), makanan);
        createMenu("Ayam Geprek", "Ayam goreng crispy dengan sambal bawang", new BigDecimal("30000.00"), makanan);
        createMenu("Bakso", "Bakso sapi dengan mie dan kuah kaldu", new BigDecimal("25000.00"), makanan);
        createMenu("Rendang", "Rendang daging sapi dengan bumbu rempah", new BigDecimal("40000.00"), makanan);
        createMenu("Capcay", "Tumis sayuran dengan saus tiram", new BigDecimal("22000.00"), makanan);

        // Minuman
        createMenu("Es Teh Manis", "Teh manis dingin segar", new BigDecimal("8000.00"), minuman);
        createMenu("Lemon Tea", "Teh dengan perasan lemon segar", new BigDecimal("12000.00"), minuman);
        createMenu("Es Jeruk", "Jeruk peras dingin", new BigDecimal("10000.00"), minuman);
        createMenu("Jus Alpukat", "Jus alpukat dengan susu kental manis", new BigDecimal("18000.00"), minuman);
        createMenu("Es Kopi Susu", "Kopi susu dingin khas Indonesia", new BigDecimal("20000.00"), minuman);
        createMenu("Air Mineral", "Air mineral botol 600ml", new BigDecimal("5000.00"), minuman);
        createMenu("Jus Mangga", "Jus mangga segar tanpa pengawet", new BigDecimal("15000.00"), minuman);
        createMenu("Es Cincau", "Cincau hitam dengan santan dan gula merah", new BigDecimal("12000.00"), minuman);
        createMenu("Teh Tarik", "Teh susu khas melayu", new BigDecimal("13000.00"), minuman);
        createMenu("Milkshake Coklat", "Milkshake coklat creamy dengan whipped", new BigDecimal("22000.00"), minuman);

        log.info("Inserted 20 Menu Makanan records");
    }

    private void createMenu(String nama, String deskripsi, BigDecimal harga, JenisMakanan jenis) {
        String slug = nama.toLowerCase().replace(" ", "-");
        String imageUrl = "http://localhost:9000/menu-images/" + slug + ".jpg";
        
        MenuMakanan menu = MenuMakanan.builder()
                .namaMenu(nama)
                .deskripsi(deskripsi)
                .harga(harga)
                .jenis(jenis)
                .imageUrl(imageUrl)
                .isAvailable(true)
                .build();
        
        menuRepository.save(menu);
        log.info("Inserted menu: {}", nama);
    }
}
