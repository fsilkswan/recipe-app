package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.converters.IngredientDtoToIngredientConverter;
import guru.springframework.converters.IngredientToIngredientDtoConverter;
import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl
    implements IngredientService
{
    private final IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter;
    private final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;
    private final RecipeRepository                   recipeRepository;
    private final UnitOfMeasureRepository            unitOfMeasureRepository;

    public IngredientServiceImpl(final RecipeRepository recipeRepository,
                                 /**/ final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter,
                                 /**/ final IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter,
                                 /**/ final UnitOfMeasureRepository unitOfMeasureRepository)
    {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientDtoConverter = ingredientToIngredientDtoConverter;
        this.ingredientDtoToIngredientConverter = ingredientDtoToIngredientConverter;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public IngredientDto saveIngredientDto(final IngredientDto ingredientDto)
    {
        final Long recipeId = ingredientDto.getRecipeId();
        final Optional<Recipe> recipeByIdResult = recipeRepository.findById(recipeId);
        if( recipeByIdResult.isPresent() == false )
        {
            // TODO: Toss error if not found!
            log.error(format("Could not find recipe with ID {0}!", recipeId));
            return new IngredientDto();
        }

        final Recipe recipe = recipeByIdResult.get();
        final Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                                                              .filter(currIngredient -> currIngredient.getId().equals(ingredientDto.getId()))
                                                              .findFirst();
        if( ingredientOptional.isPresent() == true )
        {
            // UPDATE EXISTING INGREDIENT:
            final Ingredient ingredient = ingredientOptional.get();
            ingredient.setDescription(ingredientDto.getDescription());
            ingredient.setAmount(ingredientDto.getAmount());
            ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientDto.getUnitOfMeasure().getId())
                                                               .orElseThrow(() -> new RuntimeException("UOM NOT FOUND!"))); // TODO: Address this!
        }
        else
        {
            // ADD NEW INGREDIENT:
            recipe.addIngredient(ingredientDtoToIngredientConverter.convert(ingredientDto));
        }

        final Recipe savedRecipe = recipeRepository.save(recipe);

        // TODO: Check for fail!
        return ingredientToIngredientDtoConverter.convert(savedRecipe.getIngredients().stream()
                                                                     /* FIXME: Filter will no work for adding a new ingredient, because ingredientDto.getId() should be null! */
                                                                     .filter(currIngredient -> currIngredient.getId().equals(ingredientDto.getId()))
                                                                     .findFirst()
                                                                     .get());
    }
}