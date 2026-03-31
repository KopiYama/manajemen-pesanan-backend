package com.restoran.menuservice.repository;

import com.restoran.menuservice.entity.JenisMakanan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JenisMakananRepository extends JpaRepository<JenisMakanan, Integer> {
    Optional<JenisMakanan> findByNamaJenis(String namaJenis);
}
