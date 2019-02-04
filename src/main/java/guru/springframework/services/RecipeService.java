package guru.springframework.services;

import java.util.Set;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;

public interface RecipeService
{
    void deleteById(Long id);

    Set<Recipe> fetchAll();

    Recipe fetchById(Long id);

    RecipeDto fetchDtoById(Long id);

    RecipeDto saveRecipeDto(RecipeDto dto);
}