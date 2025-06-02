package academy.devdojo.springboot_essentials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Anime {
    private String name;
    private Long id;
}
