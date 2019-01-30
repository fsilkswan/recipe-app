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

public final class UnitOfMeasureDtoToUnitOfMeasureConverterTest
{
    private static final String DESCRIPTION = "description";
    private static final Long   LONG_VALUE  = Long.valueOf(1L);

    private UnitOfMeasureDtoToUnitOfMeasureConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new UnitOfMeasureDtoToUnitOfMeasureConverter();
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final UnitOfMeasureDto uomDto = new UnitOfMeasureDto();
        uomDto.setId(LONG_VALUE);
        uomDto.setDescription(DESCRIPTION);

        // WHEN:
        final UnitOfMeasure uom = cut.convert(uomDto);

        // THEN:
        assertThat(uom, is(not(nullValue())));
        assertThat(uom.getId(), is(equalTo(LONG_VALUE)));
        assertThat(uom.getDescription(), is(equalTo(DESCRIPTION)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new UnitOfMeasureDto()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }
}