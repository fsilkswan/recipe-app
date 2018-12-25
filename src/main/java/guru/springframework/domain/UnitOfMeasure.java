package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UnitOfMeasure
{
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getDescription()
    {
        return description;
    }

    public Long getId()
    {
        return id;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }
}