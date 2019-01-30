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
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.Ingredient;

public final class IngredientDtoToIngredientConverterTest
{
    private static final BigDecimal AMOUNT      = new BigDecimal("1");
    private static final String     DESCRIPTION = "Cheeseburger";
    private static final Long       ID_VALUE    = Long.valueOf(1L);
    private static final Long       UOM_ID      = Long.valueOf(2L);

    private IngredientDtoToIngredientConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new IngredientDtoToIngredientConverter(new UnitOfMeasureDtoToUnitOfMeasureConverter());
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ID_VALUE);
        ingredientDto.setAmount(AMOUNT);
        ingredientDto.setDescription(DESCRIPTION);
        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(UOM_ID);
        ingredientDto.setUnitOfMeasure(unitOfMeasureDto);

        // WHEN:
        final Ingredient ingredient = cut.convert(ingredientDto);

        // THEN:
        assertThat(ingredient, is(not(nullValue())));
        assertThat(ingredient.getId(), is(equalTo(ID_VALUE)));
        assertThat(ingredient.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredient.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(ingredient.getUnitOfMeasure(), is(not(nullValue())));
        assertThat(ingredient.getUnitOfMeasure().getId(), is(equalTo(UOM_ID)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new IngredientDto()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }

    @Test
    public void testConvertWithNullUnitOfMeasure()
        throws Exception
    {
        // GIVEN:
        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ID_VALUE);
        ingredientDto.setAmount(AMOUNT);
        ingredientDto.setDescription(DESCRIPTION);

        // WHEN:
        final Ingredient ingredient = cut.convert(ingredientDto);

        // THEN:
        assertThat(ingredient, is(not(nullValue())));
        assertThat(ingredient.getId(), is(equalTo(ID_VALUE)));
        assertThat(ingredient.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredient.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(ingredient.getUnitOfMeasure(), is(nullValue()));
    }
}