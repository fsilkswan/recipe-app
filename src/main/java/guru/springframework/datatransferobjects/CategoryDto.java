package guru.springframework.datatransferobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto
{
    private String description;
    private Long   id;
}