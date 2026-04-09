package com.restoran.menuservice.controller;

import com.restoran.menuservice.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.entity.MenuMakanan;
import com.restoran.menuservice.service.MenuService;
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

    private final MenuService menuService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<MenuMakananResponseDTO> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public MenuMakananResponseDTO getMenuById(@PathVariable Integer id) {
        return menuService.getMenuById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuMakananResponseDTO createMenu(
            @RequestPart("data") String menuDataJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        
        MenuMakanan request = objectMapper.readValue(menuDataJson, MenuMakanan.class);
        return menuService.createMenu(request, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MenuMakananResponseDTO updateMenu(
            @PathVariable Integer id,
            @RequestPart("data") String menuDataJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        
        MenuMakanan request = objectMapper.readValue(menuDataJson, MenuMakanan.class);
        return menuService.updateMenu(id, request, image);
    }
}
