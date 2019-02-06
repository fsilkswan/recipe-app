package guru.springframework.services;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureDtoConverter;
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;

public final class UnitOfMeasureServiceImplTest
{
    private UnitOfMeasureServiceImpl cut;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepositoryMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new UnitOfMeasureServiceImpl(unitOfMeasureRepositoryMock, new UnitOfMeasureToUnitOfMeasureDtoConverter());
    }

    @Test
    public void testFetchAllAsDto()
        throws Exception
    {
        // GIVEN:
        final UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        final UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        final UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId(3L);

        when(unitOfMeasureRepositoryMock.findAll()).thenReturn(asList(uom1, uom2, uom3));

        // WHEN:
        final List<UnitOfMeasureDto> uomDtoList = cut.fetchAllAsDto();

        // THEN:
        assertThat(uomDtoList, is(not(nullValue())));
        assertThat(uomDtoList, hasSize(equalTo(3)));
        assertThat(uomDtoList.get(0).getId(), is(equalTo(1L)));
        assertThat(uomDtoList.get(1).getId(), is(equalTo(2L)));
        assertThat(uomDtoList.get(2).getId(), is(equalTo(3L)));
        verify(unitOfMeasureRepositoryMock, times(1)).findAll();
    }
}