package guru.springframework.services;

import java.util.List;

import guru.springframework.datatransferobjects.UnitOfMeasureDto;

public interface UnitOfMeasureService
{
    List<UnitOfMeasureDto> fetchAllAsDto();
}