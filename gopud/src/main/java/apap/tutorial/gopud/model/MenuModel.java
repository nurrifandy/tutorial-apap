package apap.tutorial.gopud.model;

import java.util.Optional;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDeleteAction;
import java.math.BigInteger;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;

import java.util.List;
import apap.tutorial.gopud.model.RestoranModel;

@Entity
@Table(name="menu")
@JsonIgnoreProperties(value = "restoran",allowSetters = true)
public class MenuModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=20)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name="harga", nullable = false)
    private BigInteger harga;

    @NotNull
    @Column(name = "durasiMasak",nullable=false)
    private Integer durasiMasak;

    @NotNull
    @Size(max=50)
    @Column(name="deskripsi", nullable = false)
    private String deskripsi;

    @ManyToOne(fetch = FetchType.EAGER,optional=false)
    @JoinColumn(name="restoranId", referencedColumnName = "idRestoran", nullable = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    //@JsonIgnore
    private RestoranModel restoran;
    
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getNama(){
        return this.nama;
    }

    public void setNama(String nama){
        this.nama=nama;
    }

    public BigInteger getHarga(){
        return this.harga;
    }

    public void setHarga(BigInteger harga){
        this.harga = harga;
    }

    public Integer getDurasiMasak(){
        return this.durasiMasak;
    }

    public void setDurasiMasak(Integer durasiMasak){
        this.durasiMasak = durasiMasak;
    }

    public String getDeskripsi(){
        return this.deskripsi;
    }

    public void setDeskripsi(String deskripsi){
        this.deskripsi = deskripsi;
    }

    public RestoranModel getRestoran(){
        return this.restoran;
    }

    public void setRestoran(RestoranModel restoran){
        this.restoran = restoran;
    }
}