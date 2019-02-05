package guru.springframework.services;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.IngredientToIngredientDtoConverter;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureDtoConverter;
import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public final class IngredientServiceImplTest
{
    private IngredientServiceImpl cut;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new IngredientServiceImpl(recipeRepositoryMock, new IngredientToIngredientDtoConverter(new UnitOfMeasureToUnitOfMeasureDtoConverter()));
    }

    @Test
    public void testFetchByRecipeIdAndIngredientId()
        throws Exception
    {
        // GIVEN:
        final Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        final Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        final Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        final Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipe));

        // WHEN:
        final IngredientDto ingredientDto = cut.fetchByRecipeIdAndIngredientId(1L, 3L);

        // THEN:
        assertThat(ingredientDto, is(not(nullValue())));
        assertThat(ingredientDto.getId(), is(equalTo(3L)));
        assertThat(ingredientDto.getRecipeId(), is(equalTo(1L)));
        verify(recipeRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void testFetchByRecipeIdAndIngredientIdFailsForInvalidIngredientId()
        throws Exception
    {
        fail("Not yet implemented!");
    }

    @Test
    public void testFetchByRecipeIdAndIngredientIdFailsForInvalidRecipeId()
        throws Exception
    {
        fail("Not yet implemented!");
    }
}