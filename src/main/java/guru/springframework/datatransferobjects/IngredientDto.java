package guru.springframework.datatransferobjects;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class IngredientDto
{
    private BigDecimal       amount;
    private String           description;
    private Long             id;
    private Long             recipeId;
    private UnitOfMeasureDto unitOfMeasure;
}