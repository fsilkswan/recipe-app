package guru.springframework.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController
{
    private final RecipeService recipeService;

    public IndexController(final RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @GetMapping({ "", "/", "index" })
    public String getIndexPage(final Model model)
    {
        log.debug("Serving the index page ...");

        final Set<Recipe> allRecipes = recipeService.fetchAll();
        model.addAttribute("recipesList", allRecipes);

        return "index";
    }
}