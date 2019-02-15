package guru.springframework.services;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService
{
    InputStream fetchImageData(Long recipeId);

    void saveImageFile(Long recipeId, MultipartFile imageFile);
}