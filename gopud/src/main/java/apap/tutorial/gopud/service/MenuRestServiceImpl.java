package apap.tutorial.gopud.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;
import apap.tutorial.gopud.rest.MenuDetail;
import apap.tutorial.gopud.rest.SettingMenu;
import reactor.core.publisher.Mono;

@Service
@Transactional

public class MenuRestServiceImpl implements MenuRestService{
    private final WebClient webClient;

    private final String apiKey = "87353a1ecc2a4a969af5cd2ace5d7171"; 
    
    @Autowired
    private MenuDb menuDb;

    @Override
    public MenuModel createMenu(MenuModel menu){
        return menuDb.save(menu);
    }

    @Override
    public List<MenuModel> retriveListMenu(){
        return menuDb.findAllByOrderByNamaAsc();
    }

    @Override
    public MenuModel getMenuByIdMenu(Long idMenu){
        Optional<MenuModel> menu = menuDb.findById(idMenu);
        if(menu.isPresent()){
            return menu.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public MenuModel changeMenu(Long idMenu, MenuModel menuUpdate){
        MenuModel menu = getMenuByIdMenu(idMenu);
        menu.setNama(menuUpdate.getNama());
        menu.setHarga(menuUpdate.getHarga());
        menu.setDurasiMasak(menuUpdate.getDurasiMasak());
        menu.setDeskripsi(menuUpdate.getDeskripsi());
        return menuDb.save(menu);
    }

    @Override
    public void deleteMenu(Long idMenu){
        MenuModel menu = getMenuByIdMenu(idMenu);
        menuDb.delete(menu);
    }

    public MenuRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(SettingMenu.restoranUrl).build();
    }

    @Override
    public Mono<String> getReceipe(String excludeIngredients){
        /** 
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("title", "Kale and Quinoa Salad with Black Beans");
        data.add("readyInMinutes", "50");
        data.add("servings", "6")
        */
        return this.webClient.get().uri("/recipes/search?excludeIngredients=" + excludeIngredients + "&apiKey=" + apiKey + "&cuisine=German").retrieve().bodyToMono(String.class);
    }
}