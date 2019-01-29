package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;

@Component
public class IngredientToIngredientDtoConverter
    implements Converter<Ingredient, IngredientDto>
{
    private final UnitOfMeasureToUnitOfMeasureDtoConverter unitOfMeasureToUnitOfMeasureDtoConverter;

    public IngredientToIngredientDtoConverter(final UnitOfMeasureToUnitOfMeasureDtoConverter unitOfMeasureToUnitOfMeasureDtoConverter)
    {
        this.unitOfMeasureToUnitOfMeasureDtoConverter = unitOfMeasureToUnitOfMeasureDtoConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientDto convert(final Ingredient source)
    {
        if( source == null )
        {
            return null;
        }

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(source.getId());
        ingredientDto.setDescription(source.getDescription());
        ingredientDto.setAmount(source.getAmount());

        final UnitOfMeasureDto uomDto = unitOfMeasureToUnitOfMeasureDtoConverter.convert(source.getUnitOfMeasure());
        ingredientDto.setUnitOfMeasure(uomDto);

        return ingredientDto;
    }
}