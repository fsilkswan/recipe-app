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

import guru.springframework.datatransferobjects.CategoryDto;
import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.NotesDto;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;

public final class RecipeDtoToRecipeConverterTest
{
    private static final Long       CAT_ID_1    = 1L;
    private static final Long       CAT_ID2     = 2L;
    private static final Integer    COOK_TIME   = Integer.valueOf("5");
    private static final String     DESCRIPTION = "My Recipe";
    private static final Difficulty DIFFICULTY  = Difficulty.EASY;
    private static final String     DIRECTIONS  = "Directions";
    private static final Long       INGRED_ID_1 = 3L;
    private static final Long       INGRED_ID_2 = 4L;
    private static final Long       NOTES_ID    = 9L;
    private static final Integer    PREP_TIME   = Integer.valueOf("7");
    private static final Long       RECIPE_ID   = 1L;
    private static final Integer    SERVINGS    = Integer.valueOf("3");
    private static final String     SOURCE      = "Source";
    private static final String     URL         = "Some URL";

    private RecipeDtoToRecipeConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new RecipeDtoToRecipeConverter(new NotesDtoToNotesConverter(),
                                             new IngredientDtoToIngredientConverter(new UnitOfMeasureDtoToUnitOfMeasureConverter()),
                                             new CategoryDtoToCategoryConverter());
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(RECIPE_ID);
        recipeDto.setCookTime(COOK_TIME);
        recipeDto.setPrepTime(PREP_TIME);
        recipeDto.setDescription(DESCRIPTION);
        recipeDto.setDifficulty(DIFFICULTY);
        recipeDto.setDirections(DIRECTIONS);
        recipeDto.setServings(SERVINGS);
        recipeDto.setSource(SOURCE);
        recipeDto.setUrl(URL);

        final NotesDto notesDto = new NotesDto();
        notesDto.setId(NOTES_ID);

        recipeDto.setNotes(notesDto);

        final CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setId(CAT_ID_1);

        final CategoryDto categoryDto2 = new CategoryDto();
        categoryDto2.setId(CAT_ID2);

        recipeDto.getCategories().add(categoryDto1);
        recipeDto.getCategories().add(categoryDto2);

        final IngredientDto ingredientDto1 = new IngredientDto();
        ingredientDto1.setId(INGRED_ID_1);

        final IngredientDto ingredientDto2 = new IngredientDto();
        ingredientDto2.setId(INGRED_ID_2);

        recipeDto.getIngredients().add(ingredientDto1);
        recipeDto.getIngredients().add(ingredientDto2);

        // WHEN:
        final Recipe recipe = cut.convert(recipeDto);

        // THEN:
        assertThat(recipe, is(not(nullValue())));
        assertThat(recipe.getId(), is(equalTo(RECIPE_ID)));
        assertThat(recipe.getCookTime(), is(equalTo(COOK_TIME)));
        assertThat(recipe.getPrepTime(), is(equalTo(PREP_TIME)));
        assertThat(recipe.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(recipe.getDifficulty(), is(sameInstance(DIFFICULTY)));
        assertThat(recipe.getDirections(), is(equalTo(DIRECTIONS)));
        assertThat(recipe.getServings(), is(equalTo(SERVINGS)));
        assertThat(recipe.getSource(), is(equalTo(SOURCE)));
        assertThat(recipe.getUrl(), is(equalTo(URL)));
        assertThat(recipe.getNotes(), is(not(nullValue())));
        assertThat(recipe.getNotes().getId(), is(equalTo(NOTES_ID)));
        assertThat(recipe.getCategories(), hasSize(equalTo(2)));
        assertThat(recipe.getIngredients(), hasSize(equalTo(2)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new RecipeDto()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }
}