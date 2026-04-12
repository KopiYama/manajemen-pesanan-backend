package com.restoran.menuservice.application.usecase;

import com.restoran.menuservice.application.dto.MenuMakananResponseDTO;
import com.restoran.menuservice.domain.model.MenuMakanan;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MenuUseCase {
    List<MenuMakananResponseDTO> getAllMenus();
    MenuMakananResponseDTO getMenuById(Integer id);
    MenuMakananResponseDTO createMenu(MenuMakanan request, MultipartFile image);
    MenuMakananResponseDTO updateMenu(Integer id, MenuMakanan request, MultipartFile image);
}
