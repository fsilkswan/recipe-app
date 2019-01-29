package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.CategoryDto;
import guru.springframework.domain.Category;
import lombok.Synchronized;

@Component
public class CategoryToCategoryDtoConverter
    implements Converter<Category, CategoryDto>
{
    @Synchronized
    @Nullable
    @Override
    public CategoryDto convert(final Category source)
    {
        if( source == null )
        {
            return null;
        }

        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(source.getId());
        categoryDto.setDescription(source.getDescription());

        return categoryDto;
    }
}