package guru.springframework.controllers;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController
{
    private final RecipeService recipeService;

    @Autowired
    public IngredientController(final RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @GetMapping({ "/recipe/{recipeId}/ingredients" })
    public String showIngredientList(@PathVariable final String recipeId, final Model model)
    {
        log.debug(format("Getting ingredient list for recipe with ID {0} ...", recipeId));

        final RecipeDto recipeDto = recipeService.fetchDtoById(Long.valueOf(recipeId));
        model.addAttribute("recipeDto", recipeDto);

        return "recipe/ingredient/list";
    }
}