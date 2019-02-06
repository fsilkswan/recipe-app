package guru.springframework.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureDtoConverter;
import guru.springframework.datatransferobjects.UnitOfMeasureDto;
import guru.springframework.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl
    implements UnitOfMeasureService
{
    private final UnitOfMeasureRepository                  unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureDtoConverter unitOfMeasureToUnitOfMeasureDtoConverter;

    public UnitOfMeasureServiceImpl(final UnitOfMeasureRepository unitOfMeasureRepository,
                                    /**/ final UnitOfMeasureToUnitOfMeasureDtoConverter unitOfMeasureToUnitOfMeasureDtoConverter)
    {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureDtoConverter = unitOfMeasureToUnitOfMeasureDtoConverter;
    }

    @Override
    public List<UnitOfMeasureDto> fetchAllAsDto()
    {
        final List<UnitOfMeasureDto> uomList = StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                                                            .map(unitOfMeasureToUnitOfMeasureDtoConverter::convert)
                                                            .collect(toList());

        return uomList;
    }
}