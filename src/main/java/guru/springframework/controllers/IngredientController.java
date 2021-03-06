package guru.springframework.controllers;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController
{
    private final IngredientService    ingredientService;
    private final RecipeService        recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(final RecipeService recipeService, final IngredientService ingredientService, final UnitOfMeasureService unitOfMeasureService)
    {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping({ "/recipe/{recipeId}/ingredient/{ingredientId}/delete" })
    public String deleteIngredient(@PathVariable final String recipeId, @PathVariable final String ingredientId)
    {
        log.info(format("Trying to delete ingredient with ID {1} from recipe with ID {0} ...", recipeId, ingredientId));
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        log.info(format("Successfully deleted ingredient with ID {1} from recipe with ID {0}.", recipeId, ingredientId));

        return "redirect:/" + "recipe/" + recipeId + "/ingredients";
    }

    @PostMapping({ "/recipe/{recipeId}/ingredient/saveOrUpdate" })
    public String saveOrUpdate(@ModelAttribute final IngredientDto ingredientDto)
    {
        final IngredientDto savedIngredientDto = ingredientService.saveOrUpdateIngredientDto(ingredientDto);

        return "redirect:/" + "recipe/" + savedIngredientDto.getRecipeId() + "/ingredient/" + savedIngredientDto.getId() + "/show";
    }

    @GetMapping({ "/recipe/{recipeId}/ingredient/new" })
    public String showFormIngredientCreation(@PathVariable final String recipeId, final Model model)
    {
        final Long numericRecipeId = Long.valueOf(recipeId);
        // Make sure the recipe for the given ID does exist:
        recipeService.fetchDtoById(numericRecipeId); // Throws a RuntimeException in case no recipe for ID exists.
        // TODO: Handle invalid recipe ID!

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipeId(numericRecipeId); // Required for hidden form property!

        // Initialize unit of measure in order to prevent NPE in form:
        ingredientDto.setUnitOfMeasure(new UnitOfMeasureDto());

        return showForm(model, ingredientDto);
    }

    @GetMapping({ "/recipe/{recipeId}/ingredient/{ingredientId}/update" })
    public String showFormIngredientUpdate(@PathVariable final String recipeId, @PathVariable final String ingredientId, final Model model)
    {
        final IngredientDto ingredientDto = ingredientService.fetchByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return showForm(model, ingredientDto);
    }

    @GetMapping({ "/recipe/{recipeId}/ingredient/{ingredientId}/show" })
    public String showIngredient(@PathVariable final String recipeId, @PathVariable final String ingredientId, final Model model)
    {
        log.debug(format("Getting ingredient with ID {1} for recipe with ID {0} ...", recipeId, ingredientId));

        final IngredientDto ingredientDto = ingredientService.fetchByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredientDto", ingredientDto);

        return "recipe/ingredient/show";
    }

    @GetMapping({ "/recipe/{recipeId}/ingredients" })
    public String showIngredientsList(@PathVariable final String recipeId, final Model model)
    {
        log.debug(format("Getting ingredient list for recipe with ID {0} ...", recipeId));

        final RecipeDto recipeDto = recipeService.fetchDtoById(Long.valueOf(recipeId));
        model.addAttribute("recipeDto", recipeDto);

        return "recipe/ingredient/list";
    }

    private String showForm(final Model model, final IngredientDto ingredientDto)
    {
        model.addAttribute("ingredientDto", ingredientDto);

        final List<UnitOfMeasureDto> uomDtoList = unitOfMeasureService.fetchAllAsDto();
        model.addAttribute("uomDtoList", uomDtoList);

        return "recipe/ingredient/ingredient_form";
    }

}