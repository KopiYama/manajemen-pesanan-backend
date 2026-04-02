package com.restoran.menuservice.service;

import com.restoran.menuservice.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.entity.MenuMakanan;
import com.restoran.menuservice.repository.MenuMakananRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMakananRepository menuRepository;

    public List<MenuMakananResponseDTO> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MenuMakananResponseDTO getMenuById(Integer id) {
        MenuMakanan menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
        return mapToDTO(menu);
    }

    @Transactional
    public MenuMakananResponseDTO updateMenu(Integer id, MenuMakanan request) {
        MenuMakanan menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        menu.setNamaMenu(request.getNamaMenu());
        menu.setDeskripsi(request.getDeskripsi());
        menu.setHarga(request.getHarga());
        menu.setIsAvailable(request.getIsAvailable());
        
        // Slugify image URL if name changed
        String slug = request.getNamaMenu().toLowerCase().replace(" ", "-");
        menu.setImageUrl("http://localhost:9000/menu-images/" + slug + ".jpg");

        MenuMakanan updated = menuRepository.save(menu);
        return mapToDTO(updated);
    }

    private MenuMakananResponseDTO mapToDTO(MenuMakanan menu) {
        return MenuMakananResponseDTO.builder()
                .id(menu.getId())
                .namaMenu(menu.getNamaMenu())
                .deskripsi(menu.getDeskripsi())
                .harga(menu.getHarga())
                .namaJenis(menu.getJenis() != null ? menu.getJenis().getNamaJenis() : null)
                .imageUrl(menu.getImageUrl())
                .isAvailable(menu.getIsAvailable())
                .build();
    }
}
