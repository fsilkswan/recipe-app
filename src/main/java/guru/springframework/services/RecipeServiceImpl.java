package guru.springframework.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

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
        final Set<Recipe> recipesSet = new HashSet<>();
        recipeRepository.findAll()
                        .forEach(recipesSet::add);
        // recipeRepository.findAll()
        // .iterator()
        // .forEachRemaining(recipesSet::add);

        return recipesSet;
    }
}