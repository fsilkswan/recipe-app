package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public final class IndexControllerTest
{
    private IndexController cut;

    @Mock
    private Model modelMock;

    @Mock
    private RecipeService recipeServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new IndexController(recipeServiceMock);
    }

    @Test
    public void testGetIndexPage()
        throws Exception
    {
        final String attributeName = "recipesList";
        final Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(new Recipe());

        when(recipeServiceMock.fetchAll()).thenReturn(recipeData);
        when(modelMock.addAttribute(attributeName, recipeData)).thenReturn(modelMock);

        final String templateName = cut.getIndexPage(modelMock);
        assertThat(templateName, is(equalTo("index")));

        verify(recipeServiceMock, times(1)).fetchAll();
        verify(modelMock, times(1)).addAttribute(attributeName, recipeData);
    }
}