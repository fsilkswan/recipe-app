package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.NotesDto;
import guru.springframework.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesDtoToNotesConverter
    implements Converter<NotesDto, Notes>
{
    @Synchronized
    @Nullable
    @Override
    public Notes convert(final NotesDto source)
    {
        if( source == null )
        {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());

        return notes;
    }
}