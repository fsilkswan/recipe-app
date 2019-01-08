package guru.springframework.services;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public final class RecipeServiceImplTest
{
    private RecipeServiceImpl cut;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new RecipeServiceImpl(recipeRepositoryMock);
    }

    @Test
    public void testFetchAll()
        throws Exception
    {
        final Recipe recipe = new Recipe();
        final Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepositoryMock.findAll()).thenReturn(recipesData);

        final Set<Recipe> allRecipes = cut.fetchAll();
        assertThat(allRecipes, is(not(nullValue())));
        assertThat(allRecipes, is(hasSize(1)));

        verify(recipeRepositoryMock, times(1)).findAll();
    }
}