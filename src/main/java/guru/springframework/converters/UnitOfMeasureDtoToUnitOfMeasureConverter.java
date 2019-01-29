package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureDtoToUnitOfMeasureConverter
    implements Converter<UnitOfMeasureDto, UnitOfMeasure>
{
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(final UnitOfMeasureDto source)
    {
        if( source == null )
        {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());

        return uom;
    }
}