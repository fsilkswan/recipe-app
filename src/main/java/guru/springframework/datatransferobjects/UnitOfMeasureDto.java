package guru.springframework.datatransferobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UnitOfMeasureDto
{
    private String description;
    private Long   id;
}