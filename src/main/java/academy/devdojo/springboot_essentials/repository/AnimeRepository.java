package academy.devdojo.springboot_essentials.repository;

import academy.devdojo.springboot_essentials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
