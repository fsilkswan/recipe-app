package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl
    implements RecipeService
{
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(final RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> fetchAll()
    {
        log.debug("I'm in the service - Sponsored by Project Lombok");

        final Set<Recipe> recipesSet = new HashSet<>();
        recipeRepository.findAll()
                        .forEach(recipesSet::add);

        return recipesSet;
    }

    @Override
    public Recipe fetchById(final Long id)
    {
        final Optional<Recipe> findByIdResult = recipeRepository.findById(id);
        if( findByIdResult.isPresent() == false )
        {
            throw new RuntimeException(format("Recipe with ID {0} does not exist!", (id != null ? id.toString() : null)));
        }

        return findByIdResult.get();
    }
}