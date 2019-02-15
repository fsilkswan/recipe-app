package guru.springframework.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

/**
 * Unit tests for class: {@linkplain ImageServiceImpl}
 *
 * @author <a href="mailto:ferdinand@seidenschwan.de">Ferdinand Seidenschwan</a>
 */
public final class ImageServiceImplTest
{
    private ImageServiceImpl cut;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    /**
     * Creates the test fixtures.
     *
     * @throws Exception
     *             On errors.
     */
    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new ImageServiceImpl(recipeRepositoryMock);
    }

    /**
     * Test for method: {@linkplain ImageServiceImpl#fetchImageData(Long)}
     *
     * @throws Exception
     *             On errors.
     */
    @Test
    public void testFetchImageData()
        throws Exception
    {
        // GIVEN:
        final byte[] imageBytes = "fake image data".getBytes();
        final Byte[] imageBytesBoxed = new Byte[imageBytes.length];
        for( int i = 0; i < imageBytes.length; i++ )
        {
            imageBytesBoxed[i] = Byte.valueOf(imageBytes[i]);
        }

        final Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setImage(imageBytesBoxed);

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipe));

        // WHEN:
        final InputStream inputStream = cut.fetchImageData(1L);

        // THEN:
        verify(recipeRepositoryMock, times(1)).findById(eq(1L));
        assertThat(inputStream, is(not(nullValue())));
        assertThat(inputStream, is(instanceOf(ByteArrayInputStream.class)));
        assertThat(inputStream.skip(imageBytes.length), is(equalTo(Long.valueOf(imageBytes.length))));
        assertThat(inputStream.read(), is(equalTo(-1)));
    }

    /**
     * Test for method: {@linkplain ImageServiceImpl#saveImageFile(Long, org.springframework.web.multipart.MultipartFile)}
     *
     * @throws Exception
     *             On errors.
     */
    @Test
    public void testSaveImageFile()
        throws Exception
    {
        // GIVEN:
        final Long recipeId = 1L;
        final MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", MediaType.TEXT_PLAIN_VALUE, "Spring Framework Guru".getBytes());

        final Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        final Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(recipeOptional);

        // WHEN:
        cut.saveImageFile(recipeId, multipartFile);

        // THEN:
        verify(recipeRepositoryMock, times(1)).save(same(recipe));
        assertThat(recipe.getImage().length, is(equalTo(multipartFile.getBytes().length)));
    }
}