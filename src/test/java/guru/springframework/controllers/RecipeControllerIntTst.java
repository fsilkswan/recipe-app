package guru.springframework.controllers;

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

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public final class RecipeControllerIntTst
{
    @Autowired
    private RecipeController cut;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @Transactional
    public void testSaveOrUpdate()
    {
        final Long recipeId = 2L;
        final int expectedIngredientCount = 20;

        // PRE-CHECK:
        final Recipe recipeBeforeUpdate = recipeRepository.findById(recipeId).get();
        assertThat(recipeBeforeUpdate.getDescription(), is(equalTo("Spicy Grilled Chicken Tacos")));
        assertThat(recipeBeforeUpdate.getIngredients(), hasSize(equalTo(expectedIngredientCount)));

        // GIVEN:
        final String newDescription = "Updated Description";
        final RecipeDto dto = new RecipeDto();
        dto.setId(recipeId);
        dto.setDescription(newDescription);

        // WHEN:
        cut.saveOrUpdate(dto);

        // THEN:
        final Recipe recipeAfterUpdate = recipeRepository.findById(recipeId).get();
        assertThat(recipeAfterUpdate.getDescription(), is(equalTo(newDescription)));
        assertThat(recipeAfterUpdate.getIngredients(), hasSize(equalTo(expectedIngredientCount)));
    }
}