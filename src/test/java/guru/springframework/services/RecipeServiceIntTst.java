package guru.springframework.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.converters.RecipeToRecipeDtoConverter;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIntTst
{
    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    private RecipeService cut;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;

    @Test
    @Transactional
    public void testSaveOfDescription()
        throws Exception
    {
        // GIVEN:
        final Iterable<Recipe> allRecipes = recipeRepository.findAll();
        // allRecipes.forEach(currRecipe -> System.out.println(currRecipe.getDescription()));
        final Recipe testRecipe = allRecipes.iterator().next();
        final RecipeDto testRecipeDto = recipeToRecipeDtoConverter.convert(testRecipe);

        // WHEN:
        testRecipeDto.setDescription(NEW_DESCRIPTION);
        final RecipeDto savedRecipeDto = cut.saveRecipeDto(testRecipeDto);

        // THEN:
        assertThat(savedRecipeDto.getDescription(), is(equalTo(NEW_DESCRIPTION)));
        assertThat(savedRecipeDto.getId(), is(equalTo(testRecipe.getId())));
        assertThat(savedRecipeDto.getCategories(), hasSize(equalTo(testRecipe.getCategories().size())));
        assertThat(savedRecipeDto.getIngredients(), hasSize(equalTo(testRecipe.getIngredients().size())));
    }
}