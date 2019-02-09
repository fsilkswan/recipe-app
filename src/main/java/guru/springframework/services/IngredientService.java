package guru.springframework.services;

import guru.springframework.datatransferobjects.IngredientDto;

public interface IngredientService
{
    void deleteById(Long recipeId, Long ingredientId);

    IngredientDto fetchByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDto saveOrUpdateIngredientDto(IngredientDto ingredientDto);
}