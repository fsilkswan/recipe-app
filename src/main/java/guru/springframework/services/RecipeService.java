package guru.springframework.services;

import java.util.Set;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;

public interface RecipeService
{
    Set<Recipe> fetchAll();

    Recipe fetchById(Long id);

    RecipeDto saveRecipeDto(RecipeDto dto);
}