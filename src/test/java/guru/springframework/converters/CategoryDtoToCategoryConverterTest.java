package guru.springframework.converters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.datatransferobjects.CategoryDto;
import guru.springframework.domain.Category;

public final class CategoryDtoToCategoryConverterTest
{
    private static final String DESCRIPTION = "description";
    private static final Long   ID_VALUE    = Long.valueOf(1L);

    private CategoryDtoToCategoryConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new CategoryDtoToCategoryConverter();
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID_VALUE);
        categoryDto.setDescription(DESCRIPTION);

        // WHEN:
        final Category category = cut.convert(categoryDto);

        // THEN:
        assertThat(category.getId(), is(equalTo(ID_VALUE)));
        assertThat(category.getDescription(), is(equalTo(DESCRIPTION)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new CategoryDto()), is(not(nullValue())));
    }

    @Test
    public void testConvertNull()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }
}