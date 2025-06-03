package academy.devdojo.springboot_essentials.mapper;

import academy.devdojo.springboot_essentials.domain.Anime;
import academy.devdojo.springboot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot_essentials.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
