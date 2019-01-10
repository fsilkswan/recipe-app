package guru.springframework.repositories;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataJpaTest /* Also loads records defined in "src/main/resources/data.sql"! */
public final class UnitOfMeasureRepositoryIntTst
{
    @Autowired
    private UnitOfMeasureRepository cut;

    @Before
    public void beforeEach()
        throws Exception
    {
    }

    @Test
    public void testFindByDescriptionCup()
        throws Exception
    {
        testFindByDescription("Cup");
    }

    @Test
    // @DirtiesContext
    public void testFindByDescriptionTeaspoon()
        throws Exception
    {
        testFindByDescription("Teaspoon");
    }

    private void testFindByDescription(final String description)
        throws Exception
    {
        final Optional<UnitOfMeasure> findByDescriptionResult = cut.findByDescription(description);
        assertThat(findByDescriptionResult.isPresent(), is(true));

        final UnitOfMeasure uom = findByDescriptionResult.get();
        assertThat(uom.getDescription(), is(equalTo(description)));
    }
}