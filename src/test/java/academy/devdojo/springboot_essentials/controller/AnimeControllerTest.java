package academy.devdojo.springboot_essentials.controller;

import academy.devdojo.springboot_essentials.domain.Anime;
import academy.devdojo.springboot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot_essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot_essentials.service.AnimeService;
import academy.devdojo.springboot_essentials.util.AnimeCreator;
import academy.devdojo.springboot_essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot_essentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks //anotação usada para a classe princiapl que será testada
    private AnimeController animeController;

    @Mock
    //anotação usada nas demais classes que estão na classe principal, mas não são ela, para definir um comportamento padrão para os testes;
    private AnimeService animeServiceMock;

    @BeforeEach
    public void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createdValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNoPageable())
                .thenReturn(List.of(AnimeCreator.createdValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createdValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createdValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)));


        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WheSuccessful() {

        String expectedName = AnimeCreator.createdValidAnime().getName();

        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WheSuccessful() {

        String expectedName = AnimeCreator.createdValidAnime().getName();

        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);


        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WheSuccessful() {

        Long expectedId = AnimeCreator.createdValidAnime().getId();

        Anime anime = animeController.findById(1L).getBody();

        Assertions.assertThat(anime).isNotNull();


        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WheSuccessful() {

        String expectedName = AnimeCreator.createdValidAnime().getName();

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList()); //mudando o comportamento padrao do before each dentro do metodo

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WheSuccessful() {

        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createdValidAnime());
    }

    @Test
    @DisplayName("replace update anime when successful")
    void replace_UpdateAnime_WheSuccessful() {

        Assertions.assertThatCode(()-> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Anime> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WheSuccessful() {

        Assertions.assertThatCode(()-> animeController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }
}