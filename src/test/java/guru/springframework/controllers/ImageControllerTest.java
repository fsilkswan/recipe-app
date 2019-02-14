package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;

public final class ImageControllerTest
{
    private ImageController cut;

    @Mock
    private ImageService imageServiceMock;

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeServiceMock;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new ImageController(imageServiceMock, recipeServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(cut)
                                 .build();
    }

    /**
     * Test for method: {@linkplain ImageController#handleImageUpload(String, org.springframework.web.multipart.MultipartFile)}
     *
     * @throws Exception
     *             On errors.
     */
    @Test
    public void testHandleImageUpload()
        throws Exception
    {
        // GIVEN:
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("imageFile", "test_file.txt", MediaType.TEXT_PLAIN.getType(), "Spring Framework Guru".getBytes());

        // WHEN:
        mockMvc.perform(multipart("/recipe/1/handle_image_upload")
                                                                  .file(mockMultipartFile))
               .andExpect(status().isFound())
               .andExpect(header().string("location", is(equalTo("/recipe/1/show"))));

        // THEN:
        verify(imageServiceMock, times(1)).saveImageFile(eq(1L), any());
    }

    /**
     * Test for method: {@linkplain ImageController#showImageUploadForm(String, org.springframework.ui.Model)}
     *
     * @throws Exception
     *             On errors.
     */
    @Test
    public void testShowImageUploadForm()
        throws Exception
    {
        // GIVEN:
        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        when(recipeServiceMock.fetchDtoById(anyLong())).thenReturn(recipeDto);

        // WHEN:
        mockMvc.perform(get("/recipe/1/image_upload_form"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("recipe"));

        // THEN:
        verify(recipeServiceMock, times(1)).fetchDtoById(eq(1L));
    }
}