package guru.springframework.controllers;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.datatransferobjects.RecipeDto;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ImageController
{
    private final ImageService  imageService;
    private final RecipeService recipeService;

    public ImageController(final ImageService imageService, final RecipeService recipeService)
    {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @PostMapping({ "/recipe/{recipeId}/handle_image_upload" })
    public String handleImageUpload(@PathVariable final String recipeId, @RequestParam("imageFile") final MultipartFile imageFile)
    {
        log.info(format("Handling image upload for recipe with ID {0} ...", recipeId));
        imageService.saveImageFile(Long.valueOf(recipeId), imageFile);

        return "redirect:/" + "recipe/" + recipeId + "/show";
    }

    @GetMapping({ "/recipe/{recipeId}/image" })
    public void renderImageFromDatabase(@PathVariable final String recipeId, final HttpServletResponse response)
        throws IOException
    {
        final InputStream inputStream = imageService.fetchImageData(Long.valueOf(recipeId));

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping({ "/recipe/{recipeId}/image_upload_form" })
    public String showImageUploadForm(@PathVariable final String recipeId, final Model model)
    {
        final RecipeDto recipeDto = recipeService.fetchDtoById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeDto);

        return "/recipe/image_upload_form";
    }
}