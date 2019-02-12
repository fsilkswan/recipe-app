package guru.springframework.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.services.ImageService;

public final class ImageControllerTest
{
    private ImageController cut;

    @Mock
    private ImageService imageServiceMock;

    private MockMvc mockMvc;

    @Before
    public void beforeEach()
        throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cut = new ImageController(imageServiceMock);

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
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test_file.txt", MediaType.TEXT_PLAIN.getType(), "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image/upload").file(mockMultipartFile))
               .andExpect(status().isFound())
               .andExpect(header().string("location", is(equalTo("/recipe/1/show"))));
    }
}