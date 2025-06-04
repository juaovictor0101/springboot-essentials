package academy.devdojo.springboot_essentials.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePostRequestBody {
    //validações de campos com Jakarta validation
    @NotEmpty(message = "The anime name cannot be empty")
    private String name;
}
