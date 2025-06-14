package academy.devdojo.springboot_essentials.repository;

import academy.devdojo.springboot_essentials.domain.Anime;
import academy.devdojo.springboot_essentials.domain.SpringBootEssentialsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringBootEssentialsUserRepository extends JpaRepository<SpringBootEssentialsUser, Long> {

    SpringBootEssentialsUser findByUsername(String username);
}
