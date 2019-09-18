package apap.tutorial.gopud.model;

public class RestoranModel{
    private String idRestoran;
    private String nama;
    private String alamat;
    private String nomorTelepon;

    public RestoranModel(String idRestoran, String nama, String alamat, String nomorTelepon){
        this.idRestoran = idRestoran;
        this.nama = nama;
        this.alamat= alamat;
        this.nomorTelepon = nomorTelepon;
    }

    public String getIdRestoran(){
        return this.idRestoran;
    }

    public String getNama(){
        return this.nama;
    }

    public String getAlamat(){
        return this.alamat;
    }

    public String getNomorTelepon(){
        return this.nomorTelepon;
    }

    public void setNomorTelepon(String newNomorTelepon){
        this.nomorTelepon = newNomorTelepon;
    }
}