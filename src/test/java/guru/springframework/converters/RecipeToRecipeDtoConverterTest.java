package guru.springframework.converters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;

public final class RecipeToRecipeDtoConverterTest
{
    private static final Long       CAT_ID_1    = Long.valueOf(1L);
    private static final Long       CAT_ID2     = Long.valueOf(2L);
    private static final Integer    COOK_TIME   = Integer.valueOf("5");
    private static final String     DESCRIPTION = "My Recipe";
    private static final Difficulty DIFFICULTY  = Difficulty.EASY;
    private static final String     DIRECTIONS  = "Directions";
    private static final Long       INGRED_ID_1 = Long.valueOf(3L);
    private static final Long       INGRED_ID_2 = Long.valueOf(4L);
    private static final Long       NOTES_ID    = Long.valueOf(9L);
    private static final Integer    PREP_TIME   = Integer.valueOf("7");
    private static final Long       RECIPE_ID   = Long.valueOf(1L);
    private static final Integer    SERVINGS    = Integer.valueOf("3");
    private static final String     SOURCE      = "Source";
    private static final String     URL         = "Some URL";

    private RecipeToRecipeDtoConverter converter;

    @Before
    public void setUp()
        throws Exception
    {
        converter = new RecipeToRecipeDtoConverter(
                                                   new NotesToNotesDtoConverter(),
                                                   new IngredientToIngredientDtoConverter(new UnitOfMeasureToUnitOfMeasureDtoConverter()),
                                                   new CategoryToCategoryDtoConverter());
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        final Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        final Category category1 = new Category();
        category1.setId(CAT_ID_1);

        final Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);

        final Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED_ID_1);

        final Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        // WHEN:
        final RecipeDto recipeDto = converter.convert(recipe);

        // THEN:
        assertThat(recipeDto, is(not(nullValue())));
        assertThat(recipeDto.getId(), is(equalTo(RECIPE_ID)));
        assertThat(recipeDto.getCookTime(), is(equalTo(COOK_TIME)));
        assertThat(recipeDto.getPrepTime(), is(equalTo(PREP_TIME)));
        assertThat(recipeDto.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(recipeDto.getDifficulty(), is(sameInstance(DIFFICULTY)));
        assertThat(recipeDto.getDirections(), is(equalTo(DIRECTIONS)));
        assertThat(recipeDto.getServings(), is(equalTo(SERVINGS)));
        assertThat(recipeDto.getSource(), is(equalTo(SOURCE)));
        assertThat(recipeDto.getUrl(), is(equalTo(URL)));
        assertThat(recipeDto.getNotes(), is(not(nullValue())));
        assertThat(recipeDto.getNotes().getId(), is(equalTo(NOTES_ID)));
        assertThat(recipeDto.getCategories(), hasSize(equalTo(2)));
        assertThat(recipeDto.getIngredients(), hasSize(equalTo(2)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(converter.convert(new Recipe()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(converter.convert(null), is(nullValue()));
    }
}