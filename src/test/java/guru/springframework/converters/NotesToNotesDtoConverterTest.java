package guru.springframework.converters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.datatransferobjects.NotesDto;
import guru.springframework.domain.Notes;

public final class NotesToNotesDtoConverterTest
{
    private static final Long   ID_VALUE     = Long.valueOf(1L);
    private static final String RECIPE_NOTES = "Notes";

    private NotesToNotesDtoConverter cut;

    @Before
    public void setUp()
        throws Exception
    {
        cut = new NotesToNotesDtoConverter();
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        // WHEN:
        final NotesDto notesDto = cut.convert(notes);

        // THEN:
        assertThat(notesDto.getId(), is(equalTo(ID_VALUE)));
        assertThat(notesDto.getRecipeNotes(), is(equalTo(RECIPE_NOTES)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(cut.convert(new Notes()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(cut.convert(null), is(nullValue()));
    }
}