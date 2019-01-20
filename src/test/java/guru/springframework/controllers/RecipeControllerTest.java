package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public final class RecipeControllerTest
{
    private RecipeController cut;

    @Mock
    private RecipeService recipeServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new RecipeController(recipeServiceMock);
    }

    @Test
    public void testMockMvc()
        throws Exception
    {
        final Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeServiceMock.fetchById(anyLong())).thenReturn(recipe);

        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
        mockMvc.perform(get("/recipe/show/1"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/show"))))
               .andExpect(model().attribute("recipe", is(sameInstance(recipe))));

        verify(recipeServiceMock, times(1)).fetchById(eq(1L));
    }
}