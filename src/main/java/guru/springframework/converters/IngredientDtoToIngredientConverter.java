package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.IngredientDto;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class IngredientDtoToIngredientConverter
    implements Converter<IngredientDto, Ingredient>
{
    private final UnitOfMeasureDtoToUnitOfMeasureConverter unitOfMeasureDtoToUnitOfMeasureConverter;

    public IngredientDtoToIngredientConverter(final UnitOfMeasureDtoToUnitOfMeasureConverter unitOfMeasureDtoToUnitOfMeasureConverter)
    {
        this.unitOfMeasureDtoToUnitOfMeasureConverter = unitOfMeasureDtoToUnitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(final IngredientDto source)
    {
        if( source == null )
        {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());

        final UnitOfMeasure uom = unitOfMeasureDtoToUnitOfMeasureConverter.convert(source.getUnitOfMeasure());
        ingredient.setUnitOfMeasure(uom);

        return ingredient;
    }
}