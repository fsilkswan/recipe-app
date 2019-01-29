package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureDtoConverter
    implements Converter<UnitOfMeasure, UnitOfMeasureDto>
{
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureDto convert(final UnitOfMeasure source)
    {
        if( source == null )
        {
            return null;
        }

        final UnitOfMeasureDto uomDto = new UnitOfMeasureDto();
        uomDto.setId(source.getId());
        uomDto.setDescription(source.getDescription());

        return uomDto;
    }
}