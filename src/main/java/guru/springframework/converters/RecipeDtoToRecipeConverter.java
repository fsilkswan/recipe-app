package guru.springframework.converters;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeDtoToRecipeConverter
    implements Converter<RecipeDto, Recipe>
{
    private final CategoryDtoToCategoryConverter     categoryDtoToCategoryConverter;
    private final IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter;
    private final NotesDtoToNotesConverter           notesDtoToNotesConverter;

    public RecipeDtoToRecipeConverter(final NotesDtoToNotesConverter notesDtoToNotesConverter,
                                      /**/final IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter,
                                      /**/ final CategoryDtoToCategoryConverter categoryDtoToCategoryConverter)
    {
        this.notesDtoToNotesConverter = notesDtoToNotesConverter;
        this.ingredientDtoToIngredientConverter = ingredientDtoToIngredientConverter;
        this.categoryDtoToCategoryConverter = categoryDtoToCategoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(final RecipeDto source)
    {
        if( source == null )
        {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setDirections(source.getDirections());
        recipe.setSource(source.getSource());
        // TODO: recipe.setImage(source.getImage());
        recipe.setUrl(source.getUrl());

        final Notes notes = notesDtoToNotesConverter.convert(source.getNotes());
        recipe.setNotes(notes);

        final Set<Ingredient> ingredientsSet = source.getIngredients().stream()
                                                     .map(currIngredientDto -> ingredientDtoToIngredientConverter.convert(currIngredientDto))
                                                     .collect(toSet());
        recipe.setIngredients(ingredientsSet);

        final Set<Category> categoriesSet = source.getCategories().stream()
                                                  .map(currCategoryDto -> categoryDtoToCategoryConverter.convert(currCategoryDto))
                                                  .collect(toSet());
        recipe.setCategories(categoriesSet);

        return recipe;
    }
}