package com.restoran.menuservice.domain.repository;

import com.restoran.menuservice.domain.model.JenisMakanan;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<JenisMakanan> findAllCategories();
    Optional<JenisMakanan> findCategoryById(Integer id);
    Optional<JenisMakanan> findCategoryByNama(String name);
    JenisMakanan saveCategory(JenisMakanan jenis);
    long countCategories();
}
