package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.anyLong;
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

import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;

public final class IngredientControllerTest
{
    private IngredientController cut;

    @Mock
    private IngredientService ingredientServiceMock;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new IngredientController(recipeServiceMock, ingredientServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
    }

    @Test
    public void testListIngredients()
        throws Exception
    {
        // GIVEN:
        final RecipeDto recipeDto = new RecipeDto();
        when(recipeServiceMock.fetchDtoById(anyLong())).thenReturn(recipeDto);

        // WHEN:
        mockMvc.perform(get("/recipe/1/ingredients"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/ingredient/list"))))
               .andExpect(model().attributeExists("recipeDto"))
               .andExpect(model().attribute("recipeDto", is(sameInstance(recipeDto))));

        // THEN:
        verify(recipeServiceMock, times(1)).fetchDtoById(anyLong());
    }

    @Test
    public void testShowIngredient()
        throws Exception
    {
        // GIVEN:
        final IngredientDto ingredientDto = new IngredientDto();

        // WHEN:
        when(ingredientServiceMock.fetchByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientDto);

        // THEN:
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/ingredient/show"))))
               .andExpect(model().attributeExists("ingredientDto"))
               .andExpect(model().attribute("ingredientDto", is(sameInstance(ingredientDto))));
    }
}