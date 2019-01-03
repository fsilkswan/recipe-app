package guru.springframework.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = { "recipe" })
@NoArgsConstructor
@Entity
public final class Ingredient
{
    private BigDecimal amount;

    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER/* Show the intent although this is default for One-To-One in Hibernate. */)
    private UnitOfMeasure unitOfMeasure;

    public Ingredient(final BigDecimal amount, final UnitOfMeasure unitOfMeasure, final String description)
    {
        this.amount = amount;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient(final double amount, final UnitOfMeasure unitOfMeasure, final String description)
    {
        this(new BigDecimal(amount), unitOfMeasure, description);
    }

    public Ingredient(final int amount, final UnitOfMeasure unitOfMeasure, final String description)
    {
        this(new BigDecimal(amount), unitOfMeasure, description);
    }
}