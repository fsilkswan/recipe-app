package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.datatransferobjects.NotesDto;
import guru.springframework.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesDtoConverter
    implements Converter<Notes, NotesDto>
{
    @Synchronized
    @Nullable
    @Override
    public NotesDto convert(final Notes source)
    {
        if( source == null )
        {
            return null;
        }

        final NotesDto notesDto = new NotesDto();
        notesDto.setId(source.getId());
        notesDto.setRecipeNotes(source.getRecipeNotes());

        return notesDto;
    }
}