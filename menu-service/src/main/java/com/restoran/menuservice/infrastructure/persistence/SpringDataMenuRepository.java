package com.restoran.menuservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataMenuRepository extends JpaRepository<MenuMakananEntity, Integer> {
    Optional<MenuMakananEntity> findByNamaMenu(String namaMenu);
}
