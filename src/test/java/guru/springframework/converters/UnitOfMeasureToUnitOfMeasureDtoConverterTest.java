package guru.springframework.converters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.UnitOfMeasure;

public final class UnitOfMeasureToUnitOfMeasureDtoConverterTest
{
    private static final String DESCRIPTION = "description";
    private static final Long   LONG_VALUE  = Long.valueOf(1L);

    private UnitOfMeasureToUnitOfMeasureDtoConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new UnitOfMeasureToUnitOfMeasureDtoConverter();
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        // WHEN:
        final UnitOfMeasureDto uomDto = cut.convert(uom);

        // THEN:
        assertThat(uomDto.getId(), is(equalTo(LONG_VALUE)));
        assertThat(uomDto.getDescription(), is(equalTo(DESCRIPTION)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new UnitOfMeasure()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }
}