package guru.springframework.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Recipe
{
    private Integer cookTime;
    private String  description;
    private String  directions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob /* Stored in BLOB on DB side. */
    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    private Integer prepTime;
    private Integer servings;
    private String  source;
    private String  url;

    /* TODO: Add 'difficulty'. */
    // private Difficulty difficulty;

    public Integer getCookTime()
    {
        return cookTime;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDirections()
    {
        return directions;
    }

    public Long getId()
    {
        return id;
    }

    public Byte[] getImage()
    {
        return image;
    }

    public Set<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public Notes getNotes()
    {
        return notes;
    }

    public Integer getPrepTime()
    {
        return prepTime;
    }

    public Integer getServings()
    {
        return servings;
    }

    public String getSource()
    {
        return source;
    }

    public String getUrl()
    {
        return url;
    }

    public void setCookTime(final Integer cookTime)
    {
        this.cookTime = cookTime;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public void setDirections(final String directions)
    {
        this.directions = directions;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public void setImage(final Byte[] image)
    {
        this.image = image;
    }

    public void setIngredients(final Set<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public void setNotes(final Notes notes)
    {
        this.notes = notes;
    }

    public void setPrepTime(final Integer prepTime)
    {
        this.prepTime = prepTime;
    }

    public void setServings(final Integer servings)
    {
        this.servings = servings;
    }

    public void setSource(final String source)
    {
        this.source = source;
    }

    public void setUrl(final String url)
    {
        this.url = url;
    }
}