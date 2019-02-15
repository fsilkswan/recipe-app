package guru.springframework.datatransferobjects;

import java.util.HashSet;
import java.util.Set;

import guru.springframework.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RecipeDto
{
    private Set<CategoryDto>   categories  = new HashSet<>();
    private Integer            cookTime;
    private String             description;
    private Difficulty         difficulty;
    private String             directions;
    private Long               id;
    private Byte[]             image;
    private Set<IngredientDto> ingredients = new HashSet<>();
    private NotesDto           notes;
    private Integer            prepTime;
    private Integer            servings;
    private String             source;
    private String             url;
}