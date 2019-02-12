package guru.springframework.controllers;

import static java.text.MessageFormat.format;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ImageController
{
    private final ImageService imageService;

    public ImageController(final ImageService imageService)
    {
        this.imageService = imageService;
    }

    @PostMapping({ "/recipe/{recipeId}/image/upload" })
    public String handleImageUpload(@PathVariable final String recipeId, @RequestParam("file") final MultipartFile imageFile)
    {
        log.info(format("Handling image upload for recipe with ID {0} ...", recipeId));
        imageService.saveImageFile(Long.valueOf(recipeId), imageFile);

        return "redirect:/" + "recipe/" + recipeId + "/show";
    }
}