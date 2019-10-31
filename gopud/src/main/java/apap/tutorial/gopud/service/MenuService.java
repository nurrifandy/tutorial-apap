package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.rest.MenuDetail;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface MenuService{
    void addMenu(MenuModel menu);
    
    List<MenuModel> findAllMenuByIdRestoran(Long idRestoran);

    Optional<MenuModel> getMenuByIdMenu(Long id);

    MenuModel changeMenu(MenuModel menuModel);

    void deleteMenu(MenuModel menu);
    
}