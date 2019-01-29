package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.CategoryDto;
import guru.springframework.domain.Category;
import lombok.Synchronized;

@Component
public class CategoryDtoToCategoryConverter
    implements Converter<CategoryDto, Category>
{
    @Synchronized
    @Nullable
    @Override
    public Category convert(final CategoryDto source)
    {
        if( source == null )
        {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());

        return category;
    }
}