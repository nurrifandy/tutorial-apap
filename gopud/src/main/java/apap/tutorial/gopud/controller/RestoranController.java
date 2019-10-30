package apap.tutorial.gopud.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import apap.tutorial.gopud.service.RestoranService;
import apap.tutorial.gopud.service.MenuService;
import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.model.MenuModel;

@Controller
public class RestoranController{
    @Qualifier("restoranServiceImpl")
    
    @Autowired
    private RestoranService restoranService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/")
    public String home(){
        return "home";
    }

    //URL mapping yang digunakan untuk mengakses halaman add restoran
    @RequestMapping(value="/restoran/add", method= RequestMethod.GET)
    public String addRestoranFormPage(Model model){
        RestoranModel newRestoran = new RestoranModel();
        model.addAttribute("restoran", newRestoran);
        return "form-add-restoran";
    }


    //URL mapping yang digunakan untuk submit form yang telah anda masukkan pada halaman add restoran
    @PostMapping(value="/restoran/add")
    public String addRestoranSubmit(@ModelAttribute RestoranModel restoran, Model model){
        restoranService.addRestoran(restoran);
        model.addAttribute("namaResto", restoran.getNama());
        return "add-restoran";
    }

    //Url untuk menampilkan sebuah restoran beserta menu yang terdapat di dalamnya.
    @GetMapping(path = "/restoran/view")
    public String view(
        //restoran yang ditampilkan berdasarkan parameter idRestoran
        @RequestParam(value = "idRestoran") Long idRestoran, Model model
    ){
        //Untuk mendapatkan restoran berdasarkan idRestoran
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).orElse(null);

        //umpan balik yang akan dikirimkan kepada view
        List<MenuModel> menuList = null;
        if(restoran != null){
            menuList = menuService.findAllMenuByIdRestoran(restoran.getIdRestoran());
        }

        restoran.setListMenu(menuList);

        model.addAttribute("resto", restoran);

    
        return "view-restoran";
    }

    //Url untuk menampilkan form yang digunkan untuk mengubah sebuah restoran
    @GetMapping(value = "restoran/change/{idRestoran}")
    public String changeRestoranFormPage(@PathVariable Long idRestoran, Model model){
        //Digunakan untuk melakukan pengambilan object restoran berdasarkan idRestoran
        RestoranModel existingRestoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        
        model.addAttribute("restoran", existingRestoran);

        return "form-change-restoran";
    }

    //Url yang memiliki fungsi untuk menjalankan perintah perngubahan data pada restoran
    @PostMapping(value = "restoran/change/{idRestoran}")
    public String changeRestoranFormSubmit(@PathVariable Long idRestoran, @ModelAttribute RestoranModel restoran, Model model){
        RestoranModel newRestoranData = restoranService.changeRestoran(restoran);
        model.addAttribute("restoran", newRestoranData);
        return "change-restoran";
    }

    //url untuk melakukan deleting pada restoran
    @RequestMapping(value="restoran/delete/{idRestoran}")
    public String deleteRestoran(@PathVariable Long idRestoran, Model model){
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        List<MenuModel> menuList = menuService.findAllMenuByIdRestoran(restoran.getIdRestoran());
        Boolean isDelete = false;
        if(menuList==null){
            restoranService.deleteRestoran(restoran);
            isDelete = true;
        }
        model.addAttribute("delete", isDelete);
        model.addAttribute("resto", restoran);
        return "delete-restoran";
    }

    // URL mapping viewAll
    @GetMapping(value = "/restoran/view-all")
    public String viewAll(Model model){

        // mengambil semua objek Restoran Model yang ada
        List<RestoranModel> listRestoran = restoranService.getRestoranList();
        // Add model restoran ke resto untuk di render
        model.addAttribute("restoranList", listRestoran);

        // return view template
        return "viewall-restoran";
    }

    @RequestMapping(value = "/error-400")
    public String error(Model model){
        return "error/404";
    }

    @RequestMapping(value = "/error-500")
    public String errorHandling(Model model){
        return "error/500";
    }
//-------------------------------------------------------------------
/**
    @RequestMapping("/restoran/add")
    public String add(
        // Request parameter untuk di pars
        @RequestParam(value = "idRestoran", required=true) Long idRestoran,
        @RequestParam(value = "nama", required = true) String nama,
        @RequestParam(value = "alamat", required = true) String alamat,
        @RequestParam(value = "nomorTelepon", required = true) String nomorTelepon,
        Model model
    ){
        // membuat objek restoran baru
        RestoranModel restoran = new RestoranModel(idRestoran, nama, alamat, nomorTelepon);

        // memanggil service addRestoran
        restoranService.addRestoran(restoran);

        // add variabel nama restoran ke "namaResto" untuk dirender
        model.addAttribute("namaResto", nama);

        // return view template
        return "add-restoran";
    }

    // URL mapping view
 
    @RequestMapping("/restoran/view")
    public String view(
        // Request parameter untuk dipass
        @RequestParam(value = "idRestoran") Long idRestoran, Model model
        ){
            //Mengambil objek RestoranModel yag dituju
            RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran);

            // Add model restoran ke "resto" untuk dirender
            model.addAttribute("resto", restoran);

            //return view template
            return "view-restoran";
        }
 
    @GetMapping("/restoran/view/id-restoran/{idRestoran}")
    public String viewWithVariable(
        @PathVariable(value = "idRestoran") Long idRestoran,
        Model model
    ){
        //Mengambil objek RestoranModel yag dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).orElse(null);

        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("resto", restoran);

        //return view template
        return "view-restoran";
    }

    //URL mapping update notelp
    @GetMapping("/restoran/update/id-restoran/{idRestoran}/nomor-telepon/{nomorTelepon}")
    public String viewWithVariable(
        @PathVariable(value = "idRestoran") Long idRestoran,
        @PathVariable(value = "nomorTelepon") String nomorTelepon,
        Model model
    ){
        //Mengambil objek RestoranModel yag dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).orElse(null);
        
        //Updating nomor telepon
        if (restoran != null){
            restoran.setNomorTelepon(nomorTelepon);
        }
        
        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("resto", restoran);

        //return view template
        return "info-update";
    }

    //URL mapping delete Restoran
    @RequestMapping("/restoran/delete/id/{idRestoran}")
    public String deleteRestoran(
        @PathVariable(value = "idRestoran") Long idRestoran,
        Model model
    ){
        //mengambil restoran yang akan di delete
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).orElse(null);

        //deleting object
        if (restoran != null){
            restoranService.deleteRestoran(restoran);
        }

        model.addAttribute("resto", restoran);

        return "delete-view";
    }
*/

}
