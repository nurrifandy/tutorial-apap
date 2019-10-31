package apap.tutorial.gopud.service;

import java.util.List;

import apap.tutorial.gopud.model.MenuModel;

public interface MenuRestService{
    MenuModel createMenu(MenuModel restoran);

    List<MenuModel> retriveListMenu();

    MenuModel getMenuByIdMenu(Long idMenu);

    MenuModel changeMenu(Long idMenu, MenuModel menuUpdate);

    void deleteMenu(Long idMenu);
}