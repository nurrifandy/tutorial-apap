/** 
package apap.tutorial.gopud.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import apap.tutorial.gopud.model.RestoranModel;


@Service
public class RestoranInMemoryService implements RestoranService{

    private List<RestoranModel> listRestoran;
    private RestoranModel restoran = null;

    //constructor
    public RestoranInMemoryService(){
        listRestoran = new ArrayList<>();
    }

    @Override
    public void addRestoran(RestoranModel restoran){
        listRestoran.add(restoran);
    }

    @Override
    public List<RestoranModel> getRestoranList(){
        return listRestoran;
    } 

    @Override
    public Optional<RestoranModel> getRestoranByIdRestoran(Long idRestoran){
        for (RestoranModel restoranModel : listRestoran){
            if (restoranModel.getIdRestoran() == idRestoran){
                return Optional.of(restoranModel);
            }
        }
        return Optional.of(restoran);
    }

    @Override
    public RestoranModel changeRestoran(RestoranModel restoran){
        return null;
    }

    @Override
    public void deleteRestoran(RestoranModel restoran){
        listRestoran.remove(restoran);
    }
}
*/
