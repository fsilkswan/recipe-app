package guru.springframework.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public final class CategoryTest
{
    private Category cut;

    @Before
    public void beforeEach()
        throws Exception
    {
        cut = new Category();
    }

    @Test
    public void testGetDescription()
        throws Exception
    {
        final String description = "MyCategory";
        cut.setDescription(description);

        assertThat(cut.getDescription(), is(equalTo(description)));
    }

    @Test
    public void testGetId()
        throws Exception
    {
        final Long idValue = 4L;
        cut.setId(idValue);

        assertThat(cut.getId(), is(equalTo(idValue)));
    }

    @Test
    public void testGetRecipes()
        throws Exception
    {
        final Set<Recipe> recipes = new HashSet<>();
        cut.setRecipes(recipes);

        assertThat(cut.getRecipes(), is(sameInstance(recipes)));
    }
}