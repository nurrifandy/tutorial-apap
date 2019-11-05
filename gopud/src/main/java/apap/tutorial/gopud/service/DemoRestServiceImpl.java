package apap.tutorial.gopud.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tutorial.gopud.rest.ChefDetail;
import apap.tutorial.gopud.rest.ListMenu;
import apap.tutorial.gopud.rest.SettingDemo;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class DemoRestServiceImpl implements DemoRestService{
    
    private final WebClient webClient;

    public DemoRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(SettingDemo.restoranUrl).build();
    }


    @Override
    public Mono<String> getChefRestoran(String nama){
        return this.webClient.get().uri("/api/v1/restoran/chef?nama=" + nama).retrieve().bodyToMono(String.class);
    }

    @Override
    public Mono<ChefDetail> postChefRestoran(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("nama", "Juni");
        data.add("spesialisasi", "pastry");
        return this.webClient.post().uri("/api/v1/restoran/chef").syncBody(data).retrieve().bodyToMono(ChefDetail.class);
    }
}