package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.converters.RecipeDtoToRecipeConverter;
import guru.springframework.converters.RecipeToRecipeDtoConverter;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl
    implements RecipeService
{
    private final RecipeDtoToRecipeConverter recipeDtoToRecipeConverter;
    private final RecipeRepository           recipeRepository;
    private final RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;

    public RecipeServiceImpl(final RecipeRepository recipeRepository,
                             /**/final RecipeToRecipeDtoConverter recipeToRecipeDtoConverter,
                             /**/final RecipeDtoToRecipeConverter recipeDtoToRecipeConverter)
    {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeDtoConverter = recipeToRecipeDtoConverter;
        this.recipeDtoToRecipeConverter = recipeDtoToRecipeConverter;
    }

    @Override
    @Transactional
    public void deleteById(final Long id)
    {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Set<Recipe> fetchAll()
    {
        log.debug("I'm in the service - Sponsored by Project Lombok");

        final Set<Recipe> recipesSet = new HashSet<>();
        recipeRepository.findAll()
                        .forEach(recipesSet::add);

        return recipesSet;
    }

    @Override
    @Transactional
    public Recipe fetchById(final Long id)
    {
        final Optional<Recipe> findByIdResult = recipeRepository.findById(id);
        if( findByIdResult.isPresent() == false )
        {
            throw new RuntimeException(format("Recipe with ID {0} does not exist!", (id != null ? id.toString() : null)));
        }

        return findByIdResult.get();
    }

    @Override
    @Transactional
    public RecipeDto fetchDtoById(final Long id)
    {
        return recipeToRecipeDtoConverter.convert(fetchById(id));
    }

    @Override
    @Transactional
    public RecipeDto saveRecipeDto(final RecipeDto dto)
    {
        final Recipe detachedRecipe = recipeDtoToRecipeConverter.convert(dto);
        final Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug(format("Recipe with ID {0} was saved successfully.", savedRecipe.getId().toString()));

        return recipeToRecipeDtoConverter.convert(savedRecipe);
    }
}