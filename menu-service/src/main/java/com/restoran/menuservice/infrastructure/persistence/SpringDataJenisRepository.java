package com.restoran.menuservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataJenisRepository extends JpaRepository<JenisMakananEntity, Integer> {
    Optional<JenisMakananEntity> findByNamaJenis(String namaJenis);
}
