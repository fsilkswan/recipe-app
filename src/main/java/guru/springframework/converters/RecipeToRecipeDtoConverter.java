package guru.springframework.converters;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.CategoryDto;
import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.NotesDto;
import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeToRecipeDtoConverter
    implements Converter<Recipe, RecipeDto>
{
    private final CategoryToCategoryDtoConverter     categoryToCategoryDtoConverter;
    private final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;
    private final NotesToNotesDtoConverter           notesToNotesDtoConverter;

    public RecipeToRecipeDtoConverter(final NotesToNotesDtoConverter notesToNotesDtoConverter,
                                      /**/final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter,
                                      /**/ final CategoryToCategoryDtoConverter categoryToCategoryDtoConverter)
    {
        this.notesToNotesDtoConverter = notesToNotesDtoConverter;
        this.ingredientToIngredientDtoConverter = ingredientToIngredientDtoConverter;
        this.categoryToCategoryDtoConverter = categoryToCategoryDtoConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeDto convert(final Recipe source)
    {
        if( source == null )
        {
            return null;
        }

        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(source.getId());
        recipeDto.setDescription(source.getDescription());
        recipeDto.setDifficulty(source.getDifficulty());
        recipeDto.setPrepTime(source.getPrepTime());
        recipeDto.setCookTime(source.getCookTime());
        recipeDto.setServings(source.getServings());
        recipeDto.setDirections(source.getDirections());
        recipeDto.setSource(source.getSource());
        recipeDto.setImage(source.getImage());
        recipeDto.setUrl(source.getUrl());

        final NotesDto notesDto = notesToNotesDtoConverter.convert(source.getNotes());
        recipeDto.setNotes(notesDto);

        final Set<IngredientDto> ingredientDtosSet = source.getIngredients().stream()
                                                           .map(currIngredient -> ingredientToIngredientDtoConverter.convert(currIngredient))
                                                           .collect(toSet());
        recipeDto.setIngredients(ingredientDtosSet);

        final Set<CategoryDto> categoryDtosSet = source.getCategories().stream()
                                                       .map(currCategory -> categoryToCategoryDtoConverter.convert(currCategory))
                                                       .collect(toSet());
        recipeDto.setCategories(categoryDtosSet);

        return recipeDto;
    }
}