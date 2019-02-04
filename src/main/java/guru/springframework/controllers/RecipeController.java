package guru.springframework.controllers;

import static java.text.MessageFormat.format;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController
{
    private final RecipeService recipeService;

    public RecipeController(final RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @GetMapping({ "/recipe/{id}/delete" })
    public String deleteRecipe(@PathVariable final String id)
    {
        log.debug(format("Trying to delete recipe with ID {0} ...", id));
        recipeService.deleteById(Long.valueOf(id));
        log.debug(format("Successfully deleted recipe with ID {0}!", id));

        return "redirect:/";
    }

    @GetMapping({ "/recipe/new" })
    public String newRecipe(final Model model)
    {
        model.addAttribute("recipeDto", new RecipeDto());

        return "recipe/recipe_form";
    }

    @PostMapping({ "/recipe/saveOrUpdate" })
    public String saveOrUpdate(@ModelAttribute final RecipeDto recipeDto)
    {
        final RecipeDto savedRecipeDto = recipeService.saveRecipeDto(recipeDto);

        return "redirect:" + "/recipe/" + savedRecipeDto.getId() + "/show";
    }

    @GetMapping({ "/recipe/{id}/show" })
    public String showById(@PathVariable final String id, final Model model)
    {
        log.debug("Serving recipe page ...");

        final Recipe foundRecipe = recipeService.fetchById(Long.valueOf(id));
        model.addAttribute("recipe", foundRecipe);

        return "recipe/show";
    }

    @GetMapping({ "/recipe/{id}/update" })
    public String updateRecipe(@PathVariable final String id, final Model model)
    {
        final RecipeDto recipeDto = recipeService.fetchDtoById(Long.valueOf(id));
        model.addAttribute("recipeDto", recipeDto);

        return "recipe/recipe_form";
    }
}