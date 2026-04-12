package com.restoran.menuservice.domain.repository;

import com.restoran.menuservice.domain.model.MenuMakanan;
import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    List<MenuMakanan> findAllMenus();
    Optional<MenuMakanan> findMenuById(Integer id);
    MenuMakanan saveMenu(MenuMakanan menu);
    long countMenus();
}
