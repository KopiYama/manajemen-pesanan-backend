package com.restoran.menuservice.infrastructure.persistence;

import com.restoran.menuservice.domain.model.JenisMakanan;
import com.restoran.menuservice.domain.model.MenuMakanan;
import com.restoran.menuservice.domain.repository.CategoryRepository;
import com.restoran.menuservice.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRepositoryAdapter implements MenuRepository, CategoryRepository {

    private final SpringDataMenuRepository springMenuRepo;
    private final SpringDataJenisRepository springJenisRepo;

    // MenuRepository
    @Override
    public List<MenuMakanan> findAllMenus() {
        return springMenuRepo.findAll().stream().map(this::toMenuDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<MenuMakanan> findMenuById(Integer id) {
        return springMenuRepo.findById(id).map(this::toMenuDomain);
    }

    @Override
    public MenuMakanan saveMenu(MenuMakanan menu) {
        MenuMakananEntity entity = toMenuEntity(menu);
        return toMenuDomain(springMenuRepo.save(entity));
    }

    @Override
    public long countMenus() {
        return springMenuRepo.count();
    }

    // CategoryRepository
    @Override
    public List<JenisMakanan> findAllCategories() {
        return springJenisRepo.findAll().stream().map(this::toCategoryDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<JenisMakanan> findCategoryById(Integer id) {
        return springJenisRepo.findById(id).map(this::toCategoryDomain);
    }

    @Override
    public Optional<JenisMakanan> findCategoryByNama(String name) {
        return springJenisRepo.findByNamaJenis(name).map(this::toCategoryDomain);
    }

    @Override
    public JenisMakanan saveCategory(JenisMakanan jenis) {
        JenisMakananEntity entity = toCategoryEntity(jenis);
        return toCategoryDomain(springJenisRepo.save(entity));
    }

    @Override
    public long countCategories() {
        return springJenisRepo.count();
    }

    // Mappers
    private MenuMakanan toMenuDomain(MenuMakananEntity entity) {
        return MenuMakanan.builder()
                .id(entity.getId())
                .namaMenu(entity.getNamaMenu())
                .deskripsi(entity.getDeskripsi())
                .harga(entity.getHarga())
                .imageUrl(entity.getImageUrl())
                .isAvailable(entity.getIsAvailable())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .jenis(entity.getJenis() != null ? toCategoryDomain(entity.getJenis()) : null)
                .build();
    }

    private MenuMakananEntity toMenuEntity(MenuMakanan domain) {
        return MenuMakananEntity.builder()
                .id(domain.getId())
                .namaMenu(domain.getNamaMenu())
                .deskripsi(domain.getDeskripsi())
                .harga(domain.getHarga())
                .imageUrl(domain.getImageUrl())
                .isAvailable(domain.getIsAvailable())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .jenis(domain.getJenis() != null ? toCategoryEntity(domain.getJenis()) : null)
                .build();
    }

    private JenisMakanan toCategoryDomain(JenisMakananEntity entity) {
        return JenisMakanan.builder()
                .id(entity.getId())
                .namaJenis(entity.getNamaJenis())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private JenisMakananEntity toCategoryEntity(JenisMakanan domain) {
        return JenisMakananEntity.builder()
                .id(domain.getId())
                .namaJenis(domain.getNamaJenis())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
