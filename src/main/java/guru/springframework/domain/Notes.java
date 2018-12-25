package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Notes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob /* Stored in CLOB on DB side. */
    private String recipeNotes;

    public Long getId()
    {
        return id;
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public String getRecipeNotes()
    {
        return recipeNotes;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public void setRecipe(final Recipe recipe)
    {
        this.recipe = recipe;
    }

    public void setRecipeNotes(final String recipeNotes)
    {
        this.recipeNotes = recipeNotes;
    }
}