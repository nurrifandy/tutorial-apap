package apap.tutorial.gopud.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        ArrayList<MenuModel> listMenu = new ArrayList<MenuModel>();
        listMenu.add(menu);
        restoran.setListMenu(listMenu);

        model.addAttribute("restoran", restoran);
        model.addAttribute("menu", menu);
        return "form-add-menu";
    }

    @PostMapping(value = "/menu/add/{idRestoran}", params = {"save"})
    public String addMenuSubmit(@ModelAttribute RestoranModel restoran, Model model){
        RestoranModel curRestoran = restoranService.getRestoranByIdRestoran(restoran.getIdRestoran()).get();
        for(MenuModel menu : restoran.getListMenu()){
            menu.setRestoran(curRestoran);
            menuService.addMenu(menu);
        }
        return "add-menu";
    }

    @PostMapping(value = "/menu/add/{idRestoran}", params = {"addRow"})
    public String addRow(@ModelAttribute RestoranModel restoran, Model model){
        if(restoran.getListMenu() == null){
            restoran.setListMenu(new ArrayList<MenuModel>());
        }
        restoran.getListMenu().add(new MenuModel());
        model.addAttribute("restoran", restoran);
        return "form-add-menu";
    }

    @PostMapping(value = "/menu/add/{idRestoran}", params = {"removeRow"})
    public String removeRow(@ModelAttribute RestoranModel restoran, final HttpServletRequest request, Model model){
        final Integer rowId = Integer.valueOf(request.getParameter("removeRow"));
        restoran.getListMenu().remove(rowId.intValue());
        model.addAttribute("restoran", restoran);
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

        MenuModel existingMenu = menuService.getMenuByIdMenu(id).orElse(null);
        
        model.addAttribute("menu", existingMenu);

        return "form-change-menu";
    }

    //Url untuk mengubah menu yang sebelumnya telah dilakukan perubahan.
    @PostMapping(value = "menu/change/{idRestoran}/{id}")
    public String changeMenuFormSubmit(@PathVariable Long idRestoran, @PathVariable Long id, @ModelAttribute MenuModel menu, Model model){
        MenuModel newMenuData = menuService.changeMenu(menu);
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