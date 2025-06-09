package academy.devdojo.springboot_essentials.client;

import academy.devdojo.springboot_essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 3);
        log.info(entity);

        String object = new RestTemplate().getForObject("http://localhost:8080/animes/3", String.class);
        log.info(object);

        //usando getForObject se faz necessário manipular arrays
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        //usando exchange fica bem mais simples de manipular tendo uma list com um ResponseEntity
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());


//        Anime kingdom  = Anime.builder().name("Kingdom").build();
//        Anime KingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("saved anime {}",KingdomSaved);

        Anime vagabond  = Anime.builder().name("Vagabond").build();
        ResponseEntity <Anime>  vagabondSaved =new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                 new HttpEntity<>(vagabond, createJsonHeader()),
                 Anime.class);
        log.info("saved anime {}",vagabondSaved);

        Anime animeToUpdated = vagabondSaved.getBody();
        animeToUpdated.setName("Vagabond 2, atualizado e 100% filé");

        ResponseEntity<Void> vagabondUpdated = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToUpdated, createJsonHeader()),
                Void.class);
        log.info(vagabondUpdated);

        ResponseEntity<Void> vagabondDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToUpdated.getId());
        log.info(vagabondDeleted);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
