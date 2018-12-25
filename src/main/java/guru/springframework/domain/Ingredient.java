package guru.springframework.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingredient
{
    private BigDecimal amount;
    private String     description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER/* Show the intent although this is default for One-To-One in Hibernate. */)
    private UnitOfMeasure unitOfMeasure;

    public BigDecimal getAmount()
    {
        return amount;
    }

    public String getDescription()
    {
        return description;
    }

    public Long getId()
    {
        return id;
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public UnitOfMeasure getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    public void setAmount(final BigDecimal amount)
    {
        this.amount = amount;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public void setRecipe(final Recipe recipe)
    {
        this.recipe = recipe;
    }

    public void setUnitOfMeasure(final UnitOfMeasure unitOfMeasure)
    {
        this.unitOfMeasure = unitOfMeasure;
    }
}