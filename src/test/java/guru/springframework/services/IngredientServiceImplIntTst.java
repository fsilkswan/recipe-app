package guru.springframework.services;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.converters.IngredientDtoToIngredientConverter;
import guru.springframework.converters.IngredientToIngredientDtoConverter;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public final class IngredientServiceImplIntTst
{
    @Autowired
    IngredientService cut;

    @Autowired
    IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    @Transactional
    public void testDeleteById()
        throws Exception
    {
        final Function<Iterable<Ingredient>, List<Ingredient>> iterableToList = iterable -> StreamSupport.stream(iterable.spliterator(), false)
                                                                                                         .collect(toList());

        assertThat(recipeRepository.findById(2L).get().getIngredients(), hasSize(equalTo(20)));
        assertThat(iterableToList.apply(ingredientRepository.findAll()), hasSize(equalTo(28)));

        cut.deleteById(2L, 28L);

        assertThat("Association of ingredient to recipe was not deleted in database.", recipeRepository.findById(2L).get().getIngredients(), hasSize(equalTo(19)));
        assertThat("Record was not deleted from ingredient database table!", iterableToList.apply(ingredientRepository.findAll()), hasSize(equalTo(27)));
    }
}