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

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public final class RecipeServiceImplTest
{
    private static final String RECIPE_DESCR_CHEESEBURGER = "Cheeseburger";
    private static final String RECIPE_DESCR_HAMBURGER    = "Hamburger";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private RecipeServiceImpl cut;

    private Recipe recipe1;

    private Recipe recipe2;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    private Set<Recipe> recipesData;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setDescription(RECIPE_DESCR_HAMBURGER);

        recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setDescription(RECIPE_DESCR_CHEESEBURGER);

        recipesData = new HashSet<>();
        recipesData.add(recipe1);
        recipesData.add(recipe2);

        cut = new RecipeServiceImpl(recipeRepositoryMock);
    }

    @Test
    public void testFetchAll()
        throws Exception
    {
        when(recipeRepositoryMock.findAll()).thenReturn(recipesData);

        final Set<Recipe> allRecipes = cut.fetchAll();
        assertThat(allRecipes, is(not(nullValue())));
        assertThat(allRecipes, is(hasSize(2)));

        verify(recipeRepositoryMock, times(1)).findAll();
        verify(recipeRepositoryMock, never()).findById(anyLong());
    }

    @Test
    public void testFetchById()
        throws Exception
    {
        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipe2));

        final Recipe foundRecipe = cut.fetchById(2L);
        assertThat(foundRecipe, is(not(nullValue())));
        assertThat(foundRecipe.getDescription(), is(equalTo(RECIPE_DESCR_CHEESEBURGER)));

        verify(recipeRepositoryMock, times(1)).findById(eq(2L));
        verify(recipeRepositoryMock, never()).findAll();
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
        }
    }
}