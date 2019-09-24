package apap.tutorial.gopud.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.tutorial.gopud.service.RestoranService;
import apap.tutorial.gopud.service.RestoranInMemoryService;
import apap.tutorial.gopud.model.RestoranModel;

@Controller
public class RestoranController{

    @Autowired
    private RestoranService restoranService;

    @RequestMapping("/restoran/add")
    public String add(
        // Request parameter untuk di pars
        @RequestParam(value = "idRestoran", required=true) String idRestoran,
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
        @RequestParam(value = "idRestoran") String idRestoran, Model model
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
        @PathVariable(value = "idRestoran") String idRestoran,
        Model model
    ){
        //Mengambil objek RestoranModel yag dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran);

        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("resto", restoran);

        //return view template
        return "view-restoran";
    }

    //URL mapping update notelp
    @GetMapping("/restoran/update/id-restoran/{idRestoran}/nomor-telepon/{nomorTelepon}")
    public String viewWithVariable(
        @PathVariable(value = "idRestoran") String idRestoran,
        @PathVariable(value = "nomorTelepon") String nomorTelepon,
        Model model
    ){
        //Mengambil objek RestoranModel yag dituju
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran);
        
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
        @PathVariable(value = "idRestoran") String idRestoran,
        Model model
    ){
        //mengambil restoran yang akan di delete
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran);

        //deleting object
        if (restoran != null){
            restoranService.deleteRestoran(restoran);
        }

        model.addAttribute("resto", restoran);

        return "delete-view";
    }

    // URL mapping viewAll
    @RequestMapping("/restoran/viewall")
    public String viewAll(Model model){

        // mengambil semua objek Restoran Model yang ada
        List<RestoranModel> listRestoran = restoranService.getRestoranList();

        // Add model restoran ke resto untuk di render
        model.addAttribute("restoList", listRestoran);

        // return view template
        return "viewall-restoran";

    }
}
