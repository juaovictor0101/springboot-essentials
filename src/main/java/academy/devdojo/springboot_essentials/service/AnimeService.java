package academy.devdojo.springboot_essentials.service;

import academy.devdojo.springboot_essentials.domain.Anime;
import academy.devdojo.springboot_essentials.exception.BadRequestException;
import academy.devdojo.springboot_essentials.mapper.AnimeMapper;
import academy.devdojo.springboot_essentials.repository.AnimeRepository;
import academy.devdojo.springboot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot_essentials.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNoPageable() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findAnimeByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Anime not found"));

    }

    @Transactional //anotação usada para dar rollback em uma ação caso aconteça algum tipo de erro no decorrer do fluxo
    public  Anime save(AnimePostRequestBody animePostRequestBody) {
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
        return animeRepository.save(anime);
    }

    public void delete (Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace (AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}
