package guru.springframework.services;

import guru.springframework.datatransferobjects.IngredientDto;

public interface IngredientService
{
    IngredientDto fetchByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDto saveOrUpdateIngredientDto(IngredientDto ingredientDto);
}