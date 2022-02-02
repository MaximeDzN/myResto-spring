package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class FileService {


    @Value("${upload.path}")
    public String uploadPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            //TODO Manage exception
        }
    }

    public void saveImage(Image image, String filename) {
        String path = String.format("%s",uploadPath);
        Path root = Paths.get(path);
        try {
            Files.createDirectories(root);
            byte[] imageByte = Base64.getDecoder().decode(image.getPath());
            Path imagePath = Paths.get(String.format("%s/%s",path,filename));
            Files.write(imagePath,imageByte);
        } catch (IOException e){
            //TODO Manage exception
        }
    }

    public void deleteImage(Image image) {
        Path imagePath = Paths.get(image.getPath());
        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            //TODO Manage exception
        }

    }

}
