package apap.tutorial.gopud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import apap.tutorial.gopud.service.*;
import apap.tutorial.gopud.model.*;

@Controller
public class MenuController{
    @Autowired
    MenuService menuService;

    @Qualifier("restoranServiceImpl")
    @Autowired
    RestoranService restoranService;

    //Url untuk menampilkan form penambahan restoran
    @GetMapping(value="/menu/add/{idRestoran}")
    private String addProductFormPage(@PathVariable(value="idRestoran")Long idRestoran, Model model){
        MenuModel menu = new MenuModel();
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        menu.setRestoran(restoran);
        model.addAttribute("menu", menu);
        return "form-add-menu";
    }

    //Url untuk melakukan penyimpan restoran baru
    @PostMapping(value="menu/add")
    private String addProductSubmit(@ModelAttribute MenuModel menu, Model model){
        menuService.addMenu(menu);

        model.addAttribute("nama", menu.getNama());

        return "add-menu";
    }

    //Url untuk menampilkan form change menu
    @GetMapping(value = "menu/change/{idRestoran}/{id}")
    public String changeMenuFormPage(@PathVariable Long idRestoran, @PathVariable Long id, Model model){
        //
        RestoranModel existingRestoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        
        model.addAttribute("restoran", existingRestoran);

        MenuModel existingMenu = menuService.getMenuByIdMenu(idRestoran,id).orElse(null);
        
        model.addAttribute("menu", existingMenu);

        return "form-change-menu";
    }

    //Url untuk mengubah menu yang sebelumnya telah dilakukan perubahan.
    @PostMapping(value = "menu/change/{idRestoran}/{id}")
    public String changeMenuFormSubmit(@PathVariable Long idRestoran, @PathVariable Long id, @ModelAttribute MenuModel menu, Model model){
        MenuModel newMenuData = menuService.changeMenu(idRestoran, menu);
        model.addAttribute("menu", newMenuData);
        return "change-menu";
    }

    //Url untuk melakukan delete pada sebuah menu
    @RequestMapping(value="menu/delete/")
    public String deleteMenu(@ModelAttribute RestoranModel restoran, Model model){
        for (MenuModel menu : restoran.getListMenu()){
            menuService.deleteMenu(menu);
        }        
        
        return "delete-menu";
    }
    
}