package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.converters.IngredientToIngredientDtoConverter;
import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl
    implements IngredientService
{
    private final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;
    private final RecipeRepository                   recipeRepository;

    public IngredientServiceImpl(final RecipeRepository recipeRepository,
                                 /**/final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter)
    {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientDtoConverter = ingredientToIngredientDtoConverter;
    }

    @Override
    public IngredientDto fetchByRecipeIdAndIngredientId(final Long recipeId, final Long ingredientId)
    {
        final Optional<Recipe> recipeByIdResult = recipeRepository.findById(recipeId);
        if( recipeByIdResult.isPresent() == false )
        {
            // TODO: Implement error handling!
            log.error(format("Recipe with ID {0} could not be found!", recipeId));
            return null;
        }

        final Recipe recipe = recipeByIdResult.get();
        final Optional<IngredientDto> ingredientDtoByIdResult = recipe.getIngredients().stream()
                                                                      .filter(currIngredient -> currIngredient.getId().equals(ingredientId))
                                                                      .map(currIngredient -> ingredientToIngredientDtoConverter.convert(currIngredient))
                                                                      .findFirst();

        if( ingredientDtoByIdResult.isPresent() == false )
        {
            // TODO: Implement error handling!
            log.error(format("Ingredient with ID {0} could not be found for recipe with ID {1}!", ingredientId, recipeId));
            return null;
        }

        return ingredientDtoByIdResult.get();
    }
}