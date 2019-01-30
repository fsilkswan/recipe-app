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

public final class NotesDtoToNotesConverterTest
{
    private static final Long   ID_VALUE     = Long.valueOf(1L);
    private static final String RECIPE_NOTES = "Notes";

    private NotesDtoToNotesConverter converter;

    @Before
    public void setUp()
        throws Exception
    {
        converter = new NotesDtoToNotesConverter();
    }

    @Test
    public void testConvert()
        throws Exception
    {
        // GIVEN:
        final NotesDto notesDto = new NotesDto();
        notesDto.setId(ID_VALUE);
        notesDto.setRecipeNotes(RECIPE_NOTES);

        // WHEN:
        final Notes notes = converter.convert(notesDto);

        // THEN:
        assertThat(notes, is(not(nullValue())));
        assertThat(notes.getId(), is(equalTo(ID_VALUE)));
        assertThat(notes.getRecipeNotes(), is(equalTo(RECIPE_NOTES)));
    }

    @Test
    public void testConvertEmptyObject()
        throws Exception
    {
        assertThat(converter.convert(new NotesDto()), is(not(nullValue())));
    }

    @Test
    public void testConvertNullObject()
        throws Exception
    {
        assertThat(converter.convert(null), is(nullValue()));
    }
}