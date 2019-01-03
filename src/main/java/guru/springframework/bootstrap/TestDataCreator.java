package guru.springframework.bootstrap;

import static java.text.MessageFormat.format;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestDataCreator
    implements ApplicationListener<ContextRefreshedEvent>
{
    private static final String TPL_SYSOUT_MSG = "Created and saved \"{0}\" (ID={1}).";

    private final CategoryRepository      categoryRepository;
    private final RecipeRepository        recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public TestDataCreator(final CategoryRepository categoryRepository, final RecipeRepository recipeRepository, final UnitOfMeasureRepository unitOfMeasureRepository)
    {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        if( 0 < recipeRepository.count() )
        {
            log.debug("Data already exists! No additional test data is created.");
            return;
        }

        try
        {
            log.debug("Creating data ...");
            createRecipes();
        }
        catch( final Throwable t )
        {
            log.error("An unexpected error has occurred!", t);
        }
    }

    private void createRecipes()
    {
        final Category catAmerican = fetchCategory("American");
        final Category catMexican = fetchCategory("Mexican");

        final UnitOfMeasure uomCup = fetchUnitOfMeasure("Cup");
        final UnitOfMeasure uomDash = fetchUnitOfMeasure("Dash");
        final UnitOfMeasure uomPiece = fetchUnitOfMeasure("Piece");
        final UnitOfMeasure uomPint = fetchUnitOfMeasure("Pint");
        final UnitOfMeasure uomTableSpoon = fetchUnitOfMeasure("Tablespoon");
        final UnitOfMeasure uomTeaSpoon = fetchUnitOfMeasure("Teaspoon");

        /* [PERFECT_GUACAMOLE] */
        final Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setSource("Simply Recipes");
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.getCategories().add(catAmerican);
        guacamoleRecipe.getCategories().add(catMexican);
        // TODO: guacamole.setImage(image);
        guacamoleRecipe.setServings(2);
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("Step 1: Cut avocado, remove flesh: Cut the avocados in half. Remove seed.\n"
                                      + "\n"
                                      + "...");

        guacamoleRecipe.addIngredient(new Ingredient(2, uomPiece, "ripe avocado"));
        guacamoleRecipe.addIngredient(new Ingredient(0.5, uomTeaSpoon, "Kosher salt"));
        guacamoleRecipe.addIngredient(new Ingredient(1, uomTableSpoon, "fresh lime juice or lemon juice"));
        guacamoleRecipe.addIngredient(new Ingredient(2, uomTableSpoon, "minced red onion or thinly sliced green onion"));
        guacamoleRecipe.addIngredient(new Ingredient(2, uomPiece, "serrano chiles, stems and seeds removed, minced"));
        guacamoleRecipe.addIngredient(new Ingredient(2, uomTableSpoon, "cilantro (leaves and tender stems), finely chopped"));
        guacamoleRecipe.addIngredient(new Ingredient(1, uomDash, "freshly grated black pepper"));
        guacamoleRecipe.addIngredient(new Ingredient(0.5, uomPiece, "ripe tomato, seeds and pulp removed, chopped"));

        final Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Variations: For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n"
                                      + "\n"
                                      + "...");
        guacamoleRecipe.setNotes(guacamoleNotes);

        final Recipe savedGuacamoleRecipe = recipeRepository.save(guacamoleRecipe);
        log.debug(format(TPL_SYSOUT_MSG, savedGuacamoleRecipe.getDescription(), savedGuacamoleRecipe.getId()));
        /* [/PERFECT_GUACAMOLE] */

        /* [SPICY_GRILLED_CHICKEN_TACOS] */
        final Recipe chickenTacosRecipe = new Recipe();
        chickenTacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        chickenTacosRecipe.setSource("Simply Recipes");
        chickenTacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacosRecipe.getCategories().add(catAmerican);
        chickenTacosRecipe.getCategories().add(catMexican);
        // TODO: chickenTacos.setImage(image);
        chickenTacosRecipe.setServings(4);
        chickenTacosRecipe.setPrepTime(20);
        chickenTacosRecipe.setCookTime(15);
        chickenTacosRecipe.setDifficulty(Difficulty.EASY);
        chickenTacosRecipe.setDirections("Step 1: Prepare a gas or charcoal grill for medium-high, direct heat. ...");

        chickenTacosRecipe.addIngredient(new Ingredient(2, uomTableSpoon, "ancho chili powder"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomTeaSpoon, "dried oregano"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomTeaSpoon, "dried cumin"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomTeaSpoon, "sugar"));
        chickenTacosRecipe.addIngredient(new Ingredient(0.5, uomTeaSpoon, "salt"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomPiece, "clove garlic, finely chopped"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomTableSpoon, "finely grated orange zest"));
        chickenTacosRecipe.addIngredient(new Ingredient(3, uomTableSpoon, "fresh-squeezed orange juice"));
        chickenTacosRecipe.addIngredient(new Ingredient(2, uomTableSpoon, "olive oil"));
        chickenTacosRecipe.addIngredient(new Ingredient(5, uomPiece, "skinless, boneless chicken thighs (1 1/4 pounds)"));
        chickenTacosRecipe.addIngredient(new Ingredient(8, uomPiece, "small corn tortillas"));
        chickenTacosRecipe.addIngredient(new Ingredient(3, uomCup, "cups packed baby arugula (3 ounces)"));
        chickenTacosRecipe.addIngredient(new Ingredient(2, uomPiece, "medium ripe avocados, sliced"));
        chickenTacosRecipe.addIngredient(new Ingredient(4, uomPiece, "radishes, thinly sliced"));
        chickenTacosRecipe.addIngredient(new Ingredient(0.5, uomPint, "cherry tomatoes, halved"));
        chickenTacosRecipe.addIngredient(new Ingredient(0.25, uomPiece, "red onion, thinly sliced"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomPiece, "Roughly chopped cilantro"));
        chickenTacosRecipe.addIngredient(new Ingredient(0.5, uomCup, "sour cream"));
        chickenTacosRecipe.addIngredient(new Ingredient(0.25, uomCup, "milk"));
        chickenTacosRecipe.addIngredient(new Ingredient(1, uomPiece, "lime, cut into wedges"));

        final Notes chickenTacosNotes = new Notes();
        chickenTacosNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla. ...");
        chickenTacosRecipe.setNotes(chickenTacosNotes);

        final Recipe savedChickenTacosRecipe = recipeRepository.save(chickenTacosRecipe);
        log.debug(format(TPL_SYSOUT_MSG, savedChickenTacosRecipe.getDescription(), savedChickenTacosRecipe.getId()));
        /* [/SPICY_GRILLED_CHICKEN_TACOS] */
    }

    private Category fetchCategory(final String description)
    {
        log.debug(format("Fetching Category \"{0}\" ...", description));
        final Optional<Category> queryResult = categoryRepository.findByDescription(description);

        return queryResult.orElseThrow(() -> new RuntimeException(format("Could not find category \"{0}\"! Check \"data.sql\" script!", description)));
    }

    private UnitOfMeasure fetchUnitOfMeasure(final String description)
    {
        log.debug(format("Fetching Unit-Of-Measure \"{0}\" ...", description));
        final Optional<UnitOfMeasure> queryResult = unitOfMeasureRepository.findByDescription(description);

        return queryResult.orElseThrow(() -> new RuntimeException(format("Could not find unit-of-measure \"{0}\"! Check \"data.sql\" script!", description)));
    }
}