package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;
import apap.tutorial.gopud.rest.MenuDetail;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    private MenuDb menuDb;

    @Override
    public void addMenu(MenuModel menu){
        menuDb.save(menu);
    }

    @Override
    public List<MenuModel> findAllMenuByIdRestoran(Long idRestoran){
        return menuDb.findByRestoranIdRestoran(idRestoran);
    }

    @Override
    public Optional<MenuModel> getMenuByIdMenu(Long id){
        return menuDb.findById(id);
    }

    @Override
    public MenuModel changeMenu(MenuModel menuModel){
        //mengambil menu yang diinginkan berdasarkan idMenu
        MenuModel targetMenu = getMenuByIdMenu(menuModel.getId()).orElse(null);
        
            targetMenu.setNama(menuModel.getNama());
            targetMenu.setDeskripsi(menuModel.getDeskripsi());
            targetMenu.setDurasiMasak(menuModel.getDurasiMasak());
            targetMenu.setHarga(menuModel.getHarga());
            menuDb.save(targetMenu);
            return targetMenu;
    }

    @Override
    public void deleteMenu(MenuModel menu){
        menuDb.delete(menu);
    }

    
}