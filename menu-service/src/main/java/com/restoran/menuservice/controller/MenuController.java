package com.restoran.menuservice.controller;

import com.restoran.menuservice.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.entity.MenuMakanan;
import com.restoran.menuservice.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuMakananResponseDTO> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public MenuMakananResponseDTO getMenuById(@PathVariable Integer id) {
        return menuService.getMenuById(id);
    }

    @PutMapping("/{id}")
    public MenuMakananResponseDTO updateMenu(@PathVariable Integer id, @RequestBody MenuMakanan request) {
        return menuService.updateMenu(id, request);
    }
}
