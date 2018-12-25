package guru.springframework.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Category
{
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    public String getDescription()
    {
        return description;
    }

    public Long getId()
    {
        return id;
    }

    public Set<Recipe> getRecipes()
    {
        return recipes;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public void setRecipes(final Set<Recipe> recipes)
    {
        this.recipes = recipes;
    }
}