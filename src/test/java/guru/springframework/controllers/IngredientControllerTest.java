package guru.springframework.controllers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;

public final class IngredientControllerTest
{
    private IngredientController cut;

    @Mock
    private IngredientService ingredientServiceMock;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeServiceMock;

    @Mock
    private UnitOfMeasureService unitOfMeasureServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new IngredientController(recipeServiceMock, ingredientServiceMock, unitOfMeasureServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
    }

    @Test
    public void testDeleteById()
        throws Exception
    {
        // GIVEN:
        // Nothing given.

        // WHEN:
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(status().is(equalTo(302)))
               .andExpect(view().name(is(equalTo("redirect:/recipe/1/ingredients"))));

        // THEN:
        verify(ingredientServiceMock, times(1)).deleteById(eq(1L), eq(2L));
    }

    @Test
    public void testSaveOrUpdate()
        throws Exception
    {
        // GIVEN:
        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(3L);
        ingredientDto.setRecipeId(2L);

        // WHEN:
        when(ingredientServiceMock.saveOrUpdateIngredientDto(any(IngredientDto.class))).thenReturn(ingredientDto);

        // THEN:
        mockMvc.perform(post("/recipe/2/ingredient/saveOrUpdate")
                                                                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                                 .param("id", "")
                                                                 .param("description", "My Description"))
               .andExpect(status().is3xxRedirection())
               .andExpect(status().is(equalTo(302)))
               .andExpect(view().name(is(equalTo("redirect:/recipe/2/ingredient/3/show"))));
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

        verify(ingredientServiceMock, times(1)).fetchByRecipeIdAndIngredientId(eq(1L), eq(2L));
    }

    @Test
    public void testShowIngredientCreationForm()
        throws Exception
    {
        // GIVEN:
        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        // WHEN:
        when(recipeServiceMock.fetchDtoById(anyLong())).thenReturn(recipeDto);
        when(unitOfMeasureServiceMock.fetchAllAsDto()).thenReturn(emptyList());

        // THEN:
        mockMvc.perform(get("/recipe/1/ingredient/new"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/ingredient/ingredient_form"))))
               .andExpect(model().attributeExists("ingredientDto"))
               .andExpect(model().attributeExists("uomDtoList"));

        verify(recipeServiceMock, times(1)).fetchDtoById(eq(1L));
        verify(unitOfMeasureServiceMock, times(1)).fetchAllAsDto();
    }

    @Test
    public void testShowIngredientsList()
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
    public void testShowIngredientUpdateForm()
        throws Exception
    {
        // GIVEN:
        final IngredientDto ingredientDto = new IngredientDto();
        final List<UnitOfMeasureDto> uomDtoList = asList(new UnitOfMeasureDto());

        // WHEN:
        when(ingredientServiceMock.fetchByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientDto);
        when(unitOfMeasureServiceMock.fetchAllAsDto()).thenReturn(uomDtoList);

        // THEN:
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("recipe/ingredient/ingredient_form"))))
               .andExpect(model().attributeExists("ingredientDto"))
               .andExpect(model().attribute("ingredientDto", is(sameInstance(ingredientDto))))
               .andExpect(model().attributeExists("uomDtoList"))
               .andExpect(model().attribute("uomDtoList", is(sameInstance(uomDtoList))));

        verify(ingredientServiceMock, times(1)).fetchByRecipeIdAndIngredientId(eq(1L), eq(2L));
        verify(unitOfMeasureServiceMock, times(1)).fetchAllAsDto();
    }
}