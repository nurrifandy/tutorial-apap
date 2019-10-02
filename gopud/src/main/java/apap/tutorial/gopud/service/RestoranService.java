package apap.tutorial.gopud.service;

import java.util.List;
import java.util.Optional;

import apap.tutorial.gopud.model.RestoranModel;

public interface RestoranService{

    //Method untuk menambah restoran
    void addRestoran(RestoranModel restoran);

    //Method untuk mendapatkan semua data Restoran yang tersimpan
    List<RestoranModel> getRestoranList();

    //
    Optional<RestoranModel> getRestoranByIdRestoran(Long idRestoran);

    //Method untuk mendapatkan data sebuah Restoran berdasarkan idRestoran
    RestoranModel changeRestoran(RestoranModel restoranModel);

    //Method untuk menghapus data Restoran
    void deleteRestoran(RestoranModel restoran);

}