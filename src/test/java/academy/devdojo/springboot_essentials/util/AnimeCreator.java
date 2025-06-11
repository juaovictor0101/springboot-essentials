package academy.devdojo.springboot_essentials.util;

import academy.devdojo.springboot_essentials.domain.Anime;

public class AnimeCreator {


    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Cavaleiros do Zodíaco")
                .build();
    }

    public static Anime createdValidAnime() {
        return Anime.builder()
                .name("Cavaleiros do Zodíaco")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Cavaleiros do Zodíaco 2")
                .id(1L)
                .build();
    }
}
