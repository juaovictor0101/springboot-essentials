package academy.devdojo.springboot_essentials.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private String name;
    private Long id;
}
