package academy.devdojo.springboot_essentials.repository;

import academy.devdojo.springboot_essentials.domain.Anime;
import academy.devdojo.springboot_essentials.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persist anime when Successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();


        Assertions.assertThat(animeSaved.getId()).isNotNull();


        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Save updates anime when Successful")
    void save_UpdatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Overlord");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();

        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

    }

    @Test
    @DisplayName("Remove anime when Successful")
    void delete_RemoveAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by Name return list of anime when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findAnimeByName(name);

        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeToBeSaved);

    }

    @Test
    @DisplayName("Find by Name return a empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findAnimeByName("zzzz");

        Assertions.assertThat(animes).isEmpty();

    }

    @Test
    @DisplayName("Save throw ConstrainValidationException when name is empty")
    void save_ThrowConstrainValidationException_WhenNameIsEmpty() {
        Anime anime = new Anime();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(()-> this.animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");
    }


}
