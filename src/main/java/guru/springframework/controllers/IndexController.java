package guru.springframework.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

@Controller
public class IndexController
{
    private final RecipeService recipeService;

    public IndexController(final RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @RequestMapping({ "", "/", "index" })
    public String getIndexPage(final Model model)
    {
        final Set<Recipe> allRecipes = recipeService.fetchAll();
        model.addAttribute("recipesList", allRecipes);

        return "index";
    }
}