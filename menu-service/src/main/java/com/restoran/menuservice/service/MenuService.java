package com.restoran.menuservice.service;

import com.restoran.menuservice.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.entity.MenuMakanan;
import com.restoran.menuservice.repository.MenuMakananRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMakananRepository menuRepository;
    private final FileService fileService;

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
    public MenuMakananResponseDTO createMenu(MenuMakanan request, MultipartFile image) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileService.uploadImage(image);
        } else if (request.getNamaMenu() != null) {
            String slug = request.getNamaMenu().toLowerCase().replace(" ", "-");
            imageUrl = "http://localhost:9000/menu-images/" + slug + ".jpg";
        }

        MenuMakanan menu = MenuMakanan.builder()
                .namaMenu(request.getNamaMenu())
                .deskripsi(request.getDeskripsi())
                .harga(request.getHarga())
                .jenis(request.getJenis())
                .imageUrl(imageUrl)
                .isAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true)
                .build();

        MenuMakanan saved = menuRepository.save(menu);
        return mapToDTO(saved);
    }

    @Transactional
    public MenuMakananResponseDTO updateMenu(Integer id, MenuMakanan request, MultipartFile image) {
        MenuMakanan menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        menu.setNamaMenu(request.getNamaMenu());
        menu.setDeskripsi(request.getDeskripsi());
        menu.setHarga(request.getHarga());
        menu.setIsAvailable(request.getIsAvailable());
        
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileService.uploadImage(image);
            menu.setImageUrl(imageUrl);
        } else if (request.getNamaMenu() != null) {
            // Keep existing pattern if no new image is uploaded but name changed
            String slug = request.getNamaMenu().toLowerCase().replace(" ", "-");
            menu.setImageUrl("http://localhost:9000/menu-images/" + slug + ".jpg");
        }

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
