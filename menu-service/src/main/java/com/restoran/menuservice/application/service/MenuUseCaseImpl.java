package com.restoran.menuservice.application.service;

import com.restoran.menuservice.application.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.application.usecase.MenuUseCase;
import com.restoran.menuservice.domain.model.JenisMakanan;
import com.restoran.menuservice.domain.model.MenuMakanan;
import com.restoran.menuservice.domain.repository.CategoryRepository;
import com.restoran.menuservice.domain.repository.MenuRepository;
import com.restoran.menuservice.domain.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuUseCaseImpl implements MenuUseCase {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileService;

    @Override
    public List<MenuMakananResponseDTO> getAllMenus() {
        return menuRepository.findAllMenus().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuMakananResponseDTO getMenuById(Integer id) {
        MenuMakanan menu = menuRepository.findMenuById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
        return mapToDTO(menu);
    }

    @Override
    @Transactional
    public MenuMakananResponseDTO createMenu(MenuMakanan request, MultipartFile image) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileService.store(image);
        } else if (request.getNamaMenu() != null) {
            String slug = request.getNamaMenu().toLowerCase().replace(" ", "-");
            imageUrl = "http://localhost:9000/menu-images/" + slug + ".jpg";
        }

        if (request.getJenis() != null && request.getJenis().getId() != null) {
            request.setJenis(categoryRepository.findCategoryById(request.getJenis().getId()).orElse(null));
        }

        request.setImageUrl(imageUrl);
        if (request.getIsAvailable() == null) request.setIsAvailable(true);

        MenuMakanan saved = menuRepository.saveMenu(request);
        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public MenuMakananResponseDTO updateMenu(Integer id, MenuMakanan request, MultipartFile image) {
        MenuMakanan menu = menuRepository.findMenuById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

        menu.setNamaMenu(request.getNamaMenu());
        menu.setDeskripsi(request.getDeskripsi());
        menu.setHarga(request.getHarga());
        menu.setIsAvailable(request.getIsAvailable());
        
        if (request.getJenis() != null && request.getJenis().getId() != null) {
            menu.setJenis(categoryRepository.findCategoryById(request.getJenis().getId()).orElse(null));
        }
        
        if (image != null && !image.isEmpty()) {
            menu.setImageUrl(fileService.store(image));
        } else if (request.getNamaMenu() != null) {
            String slug = request.getNamaMenu().toLowerCase().replace(" ", "-");
            menu.setImageUrl("http://localhost:9000/menu-images/" + slug + ".jpg");
        }

        MenuMakanan updated = menuRepository.saveMenu(menu);
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
