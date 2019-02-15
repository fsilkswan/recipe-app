package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl
    implements ImageService
{
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(final RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public InputStream fetchImageData(final Long recipeId)
    {
        final Optional<Recipe> recipeQueryResult = recipeRepository.findById(recipeId);
        if( recipeQueryResult.isPresent() == false )
        {
            log.error(format("Recipe with ID {0} does not exist, so no image is available!", recipeId));
            return null; // FIXME: Use an error image!
        }

        final Byte[] imageBytesBoxed = recipeQueryResult.get().getImage();
        if( imageBytesBoxed == null )
        {
            log.warn(format("There''s no image for recipe with ID {0} available!", recipeId));
            return getClass().getClassLoader().getResourceAsStream(/* TODO: Use a placeholder image! */"static/images/perfect_guacamole_flashed.jpg");
        }

        final byte[] imageBytes = new byte[imageBytesBoxed.length];
        for( int i = 0; i < imageBytesBoxed.length; i++ )
        {
            imageBytes[i] = imageBytesBoxed[i].byteValue();
        }

        return new ByteArrayInputStream(imageBytes);
    }

    @Override
    public void saveImageFile(final Long recipeId, final MultipartFile imageFile)
    {
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if( recipeOptional.isPresent() == false )
        {
            log.error(format("Can not save image file, because recipe with ID {0} does not exist!", recipeId));
            return;
        }

        try
        {
            final byte[] imageBytes = imageFile.getBytes();
            final Byte[] imageData = new Byte[imageBytes.length];
            for( int i = 0; i < imageBytes.length; i++ )
            {
                imageData[i] = Byte.valueOf(imageBytes[i]);
            }

            final Recipe recipe = recipeOptional.get();
            recipe.setImage(imageData);

            recipeRepository.save(recipe);
        }
        catch( final IOException ioEx )
        {
            log.error("Could not extract byte data from image file!", ioEx);
        }
    }
}