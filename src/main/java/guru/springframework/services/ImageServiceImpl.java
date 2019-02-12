package guru.springframework.services;

import static java.text.MessageFormat.format;

import java.io.IOException;
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