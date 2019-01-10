package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public final class IndexControllerTest
{
    private static final String MODEL_ATTRIBUTE_NAME = "recipesList";

    private IndexController cut;

    @Mock
    private Model modelMock;

    private Set<Recipe> recipeData;

    @Mock
    private RecipeService recipeServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        final Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        final Recipe recipe2 = new Recipe();
        recipe2.setId(2L);

        recipeData = new HashSet<>();
        recipeData.add(recipe1);
        recipeData.add(recipe2);

        cut = new IndexController(recipeServiceMock);
    }

    @Test
    public void testGetIndexPage()
        throws Exception
    {
        when(recipeServiceMock.fetchAll()).thenReturn(recipeData);
        when(modelMock.addAttribute(MODEL_ATTRIBUTE_NAME, recipeData)).thenReturn(modelMock);

        final String templateName = cut.getIndexPage(modelMock);
        assertThat(templateName, is(equalTo("index")));

        verify(recipeServiceMock, times(1)).fetchAll();
        verify(modelMock, times(1)).addAttribute(eq(MODEL_ATTRIBUTE_NAME), same(recipeData));
    }

    @Test
    public void testGetIndexPageUsingArgumentCaptor()
        throws Exception
    {
        when(recipeServiceMock.fetchAll()).thenReturn(recipeData);

        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        final String templateName = cut.getIndexPage(modelMock);
        assertThat(templateName, is(equalTo("index")));

        verify(recipeServiceMock, times(1)).fetchAll();
        verify(modelMock, times(1)).addAttribute(eq(MODEL_ATTRIBUTE_NAME), argumentCaptor.capture());

        final Set<Recipe> capturedRecipeData = argumentCaptor.getValue();
        assertThat(capturedRecipeData, hasSize(2));
    }
}