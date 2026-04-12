package com.restoran.menuservice.presentation.controller;

import com.restoran.menuservice.application.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.application.usecase.MenuUseCase;
import com.restoran.menuservice.domain.model.MenuMakanan;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/menu/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuUseCase menuUseCase;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<MenuMakananResponseDTO> getAllMenus() {
        return menuUseCase.getAllMenus();
    }

    @GetMapping("/{id}")
    public MenuMakananResponseDTO getMenuById(@PathVariable Integer id) {
        return menuUseCase.getMenuById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuMakananResponseDTO createMenu(
            @RequestPart("data") String menuDataJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        
        MenuMakanan request = objectMapper.readValue(menuDataJson, MenuMakanan.class);
        return menuUseCase.createMenu(request, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuMakananResponseDTO updateMenu(
            @PathVariable Integer id,
            @RequestPart("data") String menuDataJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        
        MenuMakanan request = objectMapper.readValue(menuDataJson, MenuMakanan.class);
        return menuUseCase.updateMenu(id, request, image);
    }
}
