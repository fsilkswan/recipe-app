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

public final class CategoryToCategoryDtoConverterTest
{
    private static final String DESCRIPTION = "descript";
    private static final Long   ID_VALUE    = new Long(1L);

    private CategoryToCategoryDtoConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new CategoryToCategoryDtoConverter();
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        // WHEN:
        final CategoryDto categoryDto = cut.convert(category);

        // THEN:
        assertThat(categoryDto.getId(), is(equalTo(ID_VALUE)));
        assertThat(categoryDto.getDescription(), is(equalTo(DESCRIPTION)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new Category()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }
}