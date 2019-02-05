package guru.springframework.converters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;

public final class IngredientToIngredientDtoConverterTest
{
    private static final BigDecimal AMOUNT      = new BigDecimal("1");
    private static final String     DESCRIPTION = "Cheeseburger";
    private static final Long       ID_VALUE    = Long.valueOf(1L);
    private static final Recipe     RECIPE      = new Recipe();
    private static final Long       UOM_ID      = Long.valueOf(2L);

    private IngredientToIngredientDtoConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new IngredientToIngredientDtoConverter(new UnitOfMeasureToUnitOfMeasureDtoConverter());
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new Ingredient()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }

    @Test
    public void testConvertNullUnitOfMeasure()
        throws Exception
    {
        // GIVEN:
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUnitOfMeasure(null);
        ingredient.setRecipe(RECIPE);

        // WHEN:
        final IngredientDto ingredientDto = cut.convert(ingredient);

        // THEN:
        assertThat(ingredientDto.getId(), is(equalTo(ID_VALUE)));
        assertThat(ingredientDto.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredientDto.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(ingredientDto.getUnitOfMeasure(), is(nullValue()));
        assertThat(ingredientDto.getRecipeId(), is(equalTo(RECIPE.getId())));
    }

    @Test
    public void testConvertWithUnitOfMeasure()
        throws Exception
    {
        // GIVEN:
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUnitOfMeasure(uom);
        ingredient.setRecipe(RECIPE);

        // WHEN:
        final IngredientDto ingredientDto = cut.convert(ingredient);

        // THEN:
        assertThat(ingredientDto.getId(), is(equalTo(ID_VALUE)));
        assertThat(ingredientDto.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredientDto.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(ingredientDto.getUnitOfMeasure(), is(not(nullValue())));
        assertThat(ingredientDto.getUnitOfMeasure().getId(), is(equalTo(UOM_ID)));
        assertThat(ingredientDto.getRecipeId(), is(equalTo(RECIPE.getId())));
    }
}