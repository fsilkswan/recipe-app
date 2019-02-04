package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public final class RecipeControllerTest
{
    private RecipeController cut;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new RecipeController(recipeServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
    }

    @Test
    public void testDeleteRecipeByIdUsingMockMvc()
        throws Exception
    {
        mockMvc.perform(get("/recipe/2/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(status().is(equalTo(302)))
               .andExpect(view().name(is(equalTo("redirect:/"))));

        verify(recipeServiceMock, times(1)).deleteById(eq(2L));
    }

    @Test
    public void testProcessRecipeFormPostDataForNewRecipeUsingMockMvc()
        throws Exception
    {
        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(23L);

        when(recipeServiceMock.saveRecipeDto(any())).thenReturn(recipeDto);

        mockMvc.perform(post("/recipe/saveOrUpdate")
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("id", "")
                                                    .param("description", "A New Recipe"))
               .andExpect(status().is3xxRedirection())
               .andExpect(status().is(302))
               .andExpect(view().name(is(equalTo("redirect:/recipe/23/show"))))
               .andExpect(header().string("location", is(equalTo("/recipe/23/show"))));

        verify(recipeServiceMock, times(1)).saveRecipeDto(any());
    }

    @Test
    public void testShowRecipeCreationFormUsingMockMvc()
        throws Exception
    {
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
        mockMvc.perform(get("/recipe/new"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/recipe_form"))))
               .andExpect(model().attributeExists("recipeDto"))
               .andExpect(model().attribute("recipeDto", isA(RecipeDto.class)));

        verifyZeroInteractions(recipeServiceMock);
    }

    @Test
    public void testShowRecipeUpdateFormUsingMockMvc()
        throws Exception
    {
        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(42L);

        when(recipeServiceMock.fetchDtoById(anyLong())).thenReturn(recipeDto);

        mockMvc.perform(get("/recipe/42/update"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/recipe_form"))))
               .andExpect(model().attributeExists("recipeDto"));

        verify(recipeServiceMock, times(1)).fetchDtoById(eq(42L));
    }

    @Test
    public void testShowRecipeUsingMockMvc()
        throws Exception
    {
        final Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeServiceMock.fetchById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/show"))))
               .andExpect(model().attributeExists("recipe"))
               .andExpect(model().attribute("recipe", is(sameInstance(recipe))));

        verify(recipeServiceMock, times(1)).fetchById(eq(1L));
    }
}