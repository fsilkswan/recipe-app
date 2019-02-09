package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.util.Optional;
import java.util.Set;

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
    public void deleteById(final Long recipeId, final Long ingredientId)
    {
        final Optional<Recipe> recipeOptional = recipeRepository.findById(Long.valueOf(recipeId));
        if( recipeOptional.isPresent() == false )
        {
            log.debug(format("Recipe with ID {0} does not exist!", recipeId));
            return;
        }

        final Recipe recipe = recipeOptional.get();
        final Set<Ingredient> allIngredients = recipe.getIngredients();
        final Optional<Ingredient> ingredientOptional = allIngredients.stream()
                                                                      .filter(currIngredient -> Long.valueOf(ingredientId).equals(currIngredient.getId()))
                                                                      .findFirst();
        if( ingredientOptional.isPresent() == false )
        {
            log.debug(format("Recipe with ID {0} does not have an ingredient with ID {1}!", recipeId, ingredientId));
            return;
        }

        final Ingredient ingredientToDel = ingredientOptional.get();
        ingredientToDel.setRecipe(null);

        allIngredients.remove(ingredientToDel);
        recipeRepository.save(recipe);
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
    public IngredientDto saveOrUpdateIngredientDto(final IngredientDto ingredientDto)
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
            final Ingredient newIngredient = ingredientDtoToIngredientConverter.convert(ingredientDto);
            newIngredient.setRecipe(recipe);
            recipe.addIngredient(newIngredient);
        }

        final Recipe savedRecipe = recipeRepository.save(recipe);
        final Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                                                                        .filter(currIngredient -> currIngredient.getId().equals(ingredientDto.getId()))
                                                                        .findFirst();

        if( savedIngredientOptional.isPresent() == true )
        {
            // TODO: Check for fail!
            return ingredientToIngredientDtoConverter.convert(savedIngredientOptional.get());
        }

        return ingredientToIngredientDtoConverter.convert(savedRecipe.getIngredients().stream()
                                                                     /* Not totally save, but best guess ... */
                                                                     .filter(currIngredient -> currIngredient.getDescription().equals(ingredientDto.getDescription()))
                                                                     .filter(currIngredient -> currIngredient.getAmount().equals(ingredientDto.getAmount()))
                                                                     .filter(currIngredient -> currIngredient.getUnitOfMeasure().getId().equals(ingredientDto.getUnitOfMeasure().getId()))
                                                                     .findFirst().get());
    }
}