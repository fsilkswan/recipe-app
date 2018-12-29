package guru.springframework.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Recipe
{
    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = { @JoinColumn(name = "recipe_id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<Category> categories;

    private Integer cookTime;
    private String  description;

    @Enumerated(value = EnumType.STRING/* Override of default behavior of ORDINAL! */)
    private Difficulty difficulty;

    @Lob
    private String directions;

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

    private String url;

    public Recipe addIngredient(final Ingredient ingredient)
    {
        if( ingredient != null )
        {
            getIngredients().add(ingredient);
            ingredient.setRecipe(this);
        }

        return this;
    }

    public Set<Category> getCategories()
    {
        if( categories == null )
        {
            categories = new HashSet<>();
        }

        return categories;
    }

    public Integer getCookTime()
    {
        return cookTime;
    }

    public String getDescription()
    {
        return description;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
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
        if( ingredients == null )
        {
            ingredients = new HashSet<>();
        }

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

    public void setCategories(final Set<Category> categories)
    {
        this.categories = categories;
    }

    public void setCookTime(final Integer cookTime)
    {
        this.cookTime = cookTime;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public void setDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
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
        if( notes != null )
        {
            notes.setRecipe(this);
        }
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