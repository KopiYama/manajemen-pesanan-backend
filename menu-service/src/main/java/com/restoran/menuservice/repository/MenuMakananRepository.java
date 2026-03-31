package com.restoran.menuservice.repository;

import com.restoran.menuservice.entity.MenuMakanan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuMakananRepository extends JpaRepository<MenuMakanan, Integer> {
    Optional<MenuMakanan> findByNamaMenu(String namaMenu);
}
