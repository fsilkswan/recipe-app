package guru.springframework.bootstrap;

import static java.text.MessageFormat.format;

import java.math.BigDecimal;
import java.util.Optional;

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

@Component
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
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        if( 0 < recipeRepository.count() )
        {
            System.out.println("Data already exists! No additional test data is created.");
            return;
        }

        try
        {
            createRecipes();
        }
        catch( final Throwable t )
        {
            System.err.println(t.getMessage());
        }
    }

    private void createRecipeIngredient(final Recipe recipe, final BigDecimal amount, final UnitOfMeasure unitOfMeasure, final String ingredientDescription)
    {
        final Ingredient ingredient = new Ingredient(amount, unitOfMeasure, ingredientDescription);
        ingredient.setRecipe(recipe);

        recipe.getIngredients().add(ingredient);
    }

    private void createRecipes()
    {
        final Category catAmerican = fetchCategory("American");
        final Category catMexican = fetchCategory("Mexican");

        final UnitOfMeasure uomCup = fetchUoM("Cup");
        final UnitOfMeasure uomDash = fetchUoM("Dash");
        final UnitOfMeasure uomPiece = fetchUoM("Piece");
        final UnitOfMeasure uomPint = fetchUoM("Pint");
        final UnitOfMeasure uomTableSpoon = fetchUoM("Tablespoon");
        final UnitOfMeasure uomTeaSpoon = fetchUoM("Teaspoon");

        /* [PERFECT_GUACAMOLE] */
        final Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.getCategories().add(catAmerican);
        guacamole.getCategories().add(catMexican);
        // TODO: guacamole.setImage(image);
        guacamole.setServings(2);
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("Step 1: Cut avocado, remove flesh: Cut the avocados in half. Remove seed.\n"
                                + "\n"
                                + "...");

        createRecipeIngredient(guacamole, new BigDecimal(2), uomPiece, "ripe avocado");
        createRecipeIngredient(guacamole, new BigDecimal(0.5), uomTeaSpoon, "Kosher salt");
        createRecipeIngredient(guacamole, new BigDecimal(1), uomTableSpoon, "fresh lime juice or lemon juice");
        createRecipeIngredient(guacamole, new BigDecimal(2), uomTableSpoon, "minced red onion or thinly sliced green onion");
        createRecipeIngredient(guacamole, new BigDecimal(2), uomPiece, "serrano chiles, stems and seeds removed, minced");
        createRecipeIngredient(guacamole, new BigDecimal(2), uomTableSpoon, "cilantro (leaves and tender stems), finely chopped");
        createRecipeIngredient(guacamole, new BigDecimal(1), uomDash, "freshly grated black pepper");
        createRecipeIngredient(guacamole, new BigDecimal(0.5), uomPiece, "ripe tomato, seeds and pulp removed, chopped");

        final Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Variations: For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n"
                                      + "\n"
                                      + "...");
        guacamoleNotes.setRecipe(guacamole);
        guacamole.setNotes(guacamoleNotes);

        final Recipe savedGuacamole = recipeRepository.save(guacamole);
        System.out.println(format(TPL_SYSOUT_MSG, savedGuacamole.getDescription(), savedGuacamole.getId()));
        /* [/PERFECT_GUACAMOLE] */

        /* [SPICY_GRILLED_CHICKEN_TACOS] */
        final Recipe chickenTacos = new Recipe();
        chickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        chickenTacos.setSource("Simply Recipes");
        chickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacos.getCategories().add(catAmerican);
        chickenTacos.getCategories().add(catMexican);
        // TODO: chickenTacos.setImage(image);
        chickenTacos.setServings(4);
        chickenTacos.setPrepTime(20);
        chickenTacos.setCookTime(15);
        chickenTacos.setDifficulty(Difficulty.EASY);
        chickenTacos.setDirections("Step 1: Prepare a gas or charcoal grill for medium-high, direct heat. ...");

        createRecipeIngredient(chickenTacos, new BigDecimal(2), uomTableSpoon, "ancho chili powder");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomTeaSpoon, "dried oregano");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomTeaSpoon, "dried cumin");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomTeaSpoon, "sugar");
        createRecipeIngredient(chickenTacos, new BigDecimal(0.5), uomTeaSpoon, "salt");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomPiece, "clove garlic, finely chopped");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomTableSpoon, "finely grated orange zest");
        createRecipeIngredient(chickenTacos, new BigDecimal(3), uomTableSpoon, "fresh-squeezed orange juice");
        createRecipeIngredient(chickenTacos, new BigDecimal(2), uomTableSpoon, "olive oil");
        createRecipeIngredient(chickenTacos, new BigDecimal(5), uomPiece, "skinless, boneless chicken thighs (1 1/4 pounds)");
        createRecipeIngredient(chickenTacos, new BigDecimal(8), uomPiece, "small corn tortillas");
        createRecipeIngredient(chickenTacos, new BigDecimal(3), uomCup, "cups packed baby arugula (3 ounces)");
        createRecipeIngredient(chickenTacos, new BigDecimal(2), uomPiece, "medium ripe avocados, sliced");
        createRecipeIngredient(chickenTacos, new BigDecimal(4), uomPiece, "radishes, thinly sliced");
        createRecipeIngredient(chickenTacos, new BigDecimal(0.5), uomPint, "cherry tomatoes, halved");
        createRecipeIngredient(chickenTacos, new BigDecimal(0.25), uomPiece, "red onion, thinly sliced");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomPiece, "Roughly chopped cilantro");
        createRecipeIngredient(chickenTacos, new BigDecimal(0.5), uomCup, "sour cream");
        createRecipeIngredient(chickenTacos, new BigDecimal(0.25), uomCup, "milk");
        createRecipeIngredient(chickenTacos, new BigDecimal(1), uomPiece, "lime, cut into wedges");

        final Notes chickenTacosNotes = new Notes();
        chickenTacosNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla. ...");
        chickenTacosNotes.setRecipe(chickenTacos);
        chickenTacos.setNotes(chickenTacosNotes);

        final Recipe savedChickenTacos = recipeRepository.save(chickenTacos);
        System.out.println(format(TPL_SYSOUT_MSG, savedChickenTacos.getDescription(), savedChickenTacos.getId()));
        /* [/SPICY_GRILLED_CHICKEN_TACOS] */
    }

    private Category fetchCategory(final String description)
    {
        final Optional<Category> queryResult = categoryRepository.findByDescription(description);

        return queryResult.orElseThrow(() -> new RuntimeException(format("Could not find category \"{0}\"! Check \"data.sql\" script!", description)));
    }

    private UnitOfMeasure fetchUoM(final String description)
    {
        final Optional<UnitOfMeasure> queryResult = unitOfMeasureRepository.findByDescription(description);

        return queryResult.orElseThrow(() -> new RuntimeException(format("Could not find unit-of-measure \"{0}\"! Check \"data.sql\" script!", description)));
    }
}