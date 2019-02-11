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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public final class Recipe
{
    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = { @JoinColumn(name = "recipe_id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<Category> categories = new HashSet<>();

    private Integer cookTime;

    private String description;

    @Enumerated(value = EnumType.STRING/* Override of default behavior of ORDINAL! */)
    private Difficulty difficulty;

    @Lob
    private String directions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob /* Stored in BLOB on DB side. */
    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe"/* , orphanRemoval = true */)
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    private Integer prepTime;

    private Integer servings;

    private String source;

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

    public void setNotes(final Notes notes)
    {
        this.notes = notes;
        if( notes != null )
        {
            notes.setRecipe(this);
        }
    }
}