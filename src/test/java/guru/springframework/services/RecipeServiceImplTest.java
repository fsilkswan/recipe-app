package guru.springframework.services;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.RecipeDtoToRecipeConverter;
import guru.springframework.converters.RecipeToRecipeDtoConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public final class RecipeServiceImplTest
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private RecipeServiceImpl cut;

    @Mock
    private RecipeDtoToRecipeConverter recipeDtoToRecipeConverter;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Mock
    private RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new RecipeServiceImpl(recipeRepositoryMock, recipeToRecipeDtoConverter, recipeDtoToRecipeConverter);
    }

    @Test
    public void testDeleteById()
        throws Exception
    {
        // GIVEN:
        final Long idToDelete = Long.valueOf(2L);
        cut.deleteById(idToDelete);

        // No 'WHEN', since method has void return type!

        // THEN:
        verify(recipeRepositoryMock, times(1)).deleteById(anyLong());
    }

    @Test
    public void testFetchAll()
        throws Exception
    {
        final Recipe recipe1;
        recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setDescription("Hamburger");

        final Recipe recipe2;
        recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setDescription("Cheeseburger");

        final Set<Recipe> recipesData;
        recipesData = new HashSet<>();
        recipesData.add(recipe1);
        recipesData.add(recipe2);

        when(recipeRepositoryMock.findAll()).thenReturn(recipesData);

        final Set<Recipe> allRecipes = cut.fetchAll();
        assertThat(allRecipes, is(not(nullValue())));
        assertThat(allRecipes, is(hasSize(2)));

        verify(recipeRepositoryMock, times(1)).findAll();
        verify(recipeRepositoryMock, never()).findById(anyLong());
        verifyZeroInteractions(recipeDtoToRecipeConverter);
        verifyZeroInteractions(recipeToRecipeDtoConverter);
    }

    @Test
    public void testFetchById()
        throws Exception
    {
        final long id = 3L;
        final String description = "McRib";

        final Recipe recipe;
        recipe = new Recipe();
        recipe.setId(id);
        recipe.setDescription(description);

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipe));

        final Recipe foundRecipe = cut.fetchById(id);
        assertThat(foundRecipe, is(not(nullValue())));
        assertThat(foundRecipe.getDescription(), is(equalTo(description)));

        verify(recipeRepositoryMock, times(1)).findById(eq(id));
        verify(recipeRepositoryMock, never()).findAll();
        verifyZeroInteractions(recipeDtoToRecipeConverter);
        verifyZeroInteractions(recipeToRecipeDtoConverter);
    }

    @Test
    public void testFetchByIdThrowsRuntimeExceptionForNullId()
        throws Exception
    {
        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(is(equalTo("Recipe with ID null does not exist!")));

        try
        {
            cut.fetchById(null);
        }
        finally
        {
            verify(recipeRepositoryMock, times(1)).findById(isNull());
            verify(recipeRepositoryMock, never()).findAll();
            verifyZeroInteractions(recipeDtoToRecipeConverter);
            verifyZeroInteractions(recipeToRecipeDtoConverter);
        }
    }

    @Test
    public void testFetchByIdThrowsRuntimeExceptionForUnknownId()
        throws Exception
    {
        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(is(equalTo("Recipe with ID 3 does not exist!")));

        try
        {
            cut.fetchById(3L);
        }
        finally
        {
            verify(recipeRepositoryMock, times(1)).findById(eq(3L));
            verify(recipeRepositoryMock, never()).findAll();
            verifyZeroInteractions(recipeDtoToRecipeConverter);
            verifyZeroInteractions(recipeToRecipeDtoConverter);
        }
    }
}