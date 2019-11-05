package apap.tutorial.gopud.service;

import apap.tutorial.gopud.rest.ChefDetail;
import apap.tutorial.gopud.rest.ListMenu;
import reactor.core.publisher.Mono;

public interface DemoRestService{
    Mono<String> getChefRestoran(String nama);

    Mono<ChefDetail> postChefRestoran();
}