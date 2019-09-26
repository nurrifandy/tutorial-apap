package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;
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
    public Optional<MenuModel> getMenuByIdMenu(Long idRestoran, Long id){
        List<MenuModel> menuModel= menuDb.findByRestoranIdRestoran(idRestoran);
        MenuModel existingMenu = null;
        for (MenuModel menu : menuModel){
            if(menu.getId()==id){
                return Optional.of(menu);
            }
        }
        return Optional.of(existingMenu);
    }

    @Override
    public MenuModel changeMenu(Long idRestoran, MenuModel menuModel){
        //mengambil menu yang diinginkan berdasarkan idRestoran dan idMenu
        MenuModel targetMenu = getMenuByIdMenu(idRestoran, menuModel.getId()).orElse(null);

        try{
            targetMenu.setNama(menuModel.getNama());
            targetMenu.setDeskripsi(menuModel.getDeskripsi());
            targetMenu.setDurasiMasak(menuModel.getDurasiMasak());
            targetMenu.setHarga(menuModel.getHarga());
            menuDb.save(targetMenu);
            return targetMenu;
        }catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public void deleteMenu(MenuModel menu){
        menuDb.delete(menu);
    }
}