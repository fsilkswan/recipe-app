package guru.springframework.services;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.IngredientDtoToIngredientConverter;
import guru.springframework.converters.IngredientToIngredientDtoConverter;
import guru.springframework.converters.UnitOfMeasureDtoToUnitOfMeasureConverter;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureDtoConverter;
import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;

public final class IngredientServiceImplTest
{
    private IngredientServiceImpl cut;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepositoryMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new IngredientServiceImpl(recipeRepositoryMock,
                                        /**/ new IngredientToIngredientDtoConverter(new UnitOfMeasureToUnitOfMeasureDtoConverter()),
                                        /**/ new IngredientDtoToIngredientConverter(new UnitOfMeasureDtoToUnitOfMeasureConverter()),
                                        /**/ unitOfMeasureRepositoryMock);
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

    @Ignore
    @Test
    public void testFetchByRecipeIdAndIngredientIdFailsForInvalidIngredientId()
        throws Exception
    {
        // TODO
        fail("Not yet implemented!");
    }

    @Ignore
    @Test
    public void testFetchByRecipeIdAndIngredientIdFailsForInvalidRecipeId()
        throws Exception
    {
        // TODO
        fail("Not yet implemented!");
    }

    @Test
    public void testSaveIngredientDtoAddNew()
        throws Exception
    {
        // GIVEN:
        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(3L);

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(2L); // FIXME: ID should not be set on DTO for NEW ingredient!
        ingredientDto.setDescription("My Description");
        ingredientDto.setAmount(BigDecimal.valueOf(1.5));
        ingredientDto.setUnitOfMeasure(unitOfMeasureDto);
        ingredientDto.setRecipeId(1L);

        final Recipe savedRecipe = new Recipe();
        savedRecipe.setId(1L);
        savedRecipe.addIngredient(new IngredientDtoToIngredientConverter(new UnitOfMeasureDtoToUnitOfMeasureConverter()).convert(ingredientDto));
        savedRecipe.getIngredients().iterator().next().setId(2L);

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepositoryMock.save(any(Recipe.class))).thenReturn(savedRecipe);

        // WHEN:
        final IngredientDto savedIngredientDto = cut.saveIngredientDto(ingredientDto);

        // THEN:
        assertThat(savedIngredientDto, is(not(nullValue())));
        assertThat(savedIngredientDto, is(not(sameInstance(ingredientDto))));
        assertThat(savedIngredientDto.getId(), is(equalTo(2L)));
        assertThat(savedIngredientDto.getRecipeId(), is(equalTo(1L)));
        assertThat(savedIngredientDto.getDescription(), is(equalTo(ingredientDto.getDescription())));
        assertThat(savedIngredientDto.getAmount(), is(equalTo(ingredientDto.getAmount())));
        assertThat(savedIngredientDto.getUnitOfMeasure().getId(), is(equalTo(3L)));
        verify(recipeRepositoryMock, times(1)).findById(eq(1L));
        verify(recipeRepositoryMock, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testSaveIngredientDtoUpdateExisting()
        throws Exception
    {
        // GIVEN:
        final Ingredient existingIngredient = new Ingredient();
        existingIngredient.setId(2L);
        final Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(existingIngredient);

        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(3L);

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(2L);
        ingredientDto.setDescription("My New Description");
        ingredientDto.setAmount(BigDecimal.valueOf(1.5));
        ingredientDto.setUnitOfMeasure(unitOfMeasureDto);
        ingredientDto.setRecipeId(1L);

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(3L);

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(unitOfMeasureRepositoryMock.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        when(recipeRepositoryMock.save(any(Recipe.class))).thenReturn(recipe);

        // WHEN:
        final IngredientDto savedIngredientDto = cut.saveIngredientDto(ingredientDto);

        // THEN:
        assertThat(savedIngredientDto, is(not(nullValue())));
        assertThat(savedIngredientDto, is(not(sameInstance(ingredientDto))));
        assertThat(savedIngredientDto.getId(), is(equalTo(2L)));
        assertThat(savedIngredientDto.getRecipeId(), is(equalTo(1L)));
        assertThat(savedIngredientDto.getAmount(), is(equalTo(ingredientDto.getAmount())));
        assertThat(savedIngredientDto.getDescription(), is(equalTo(ingredientDto.getDescription())));
        assertThat(savedIngredientDto.getUnitOfMeasure().getId(), is(equalTo(3L)));
        verify(recipeRepositoryMock, times(1)).findById(eq(1L));
        verify(unitOfMeasureRepositoryMock, times(1)).findById(eq(3L));
        verify(recipeRepositoryMock, times(1)).save(same(recipe));
    }
}