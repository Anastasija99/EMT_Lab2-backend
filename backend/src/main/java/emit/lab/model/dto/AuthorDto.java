package emit.lab.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorDto {

    private String nameAuthor;
    private String surname;
    private Long country;
}
