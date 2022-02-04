package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Image;
import eu.ensup.my_resto.service.exception.FileNotDeleted;
import eu.ensup.my_resto.service.exception.FileNotSaved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * The type File service.
 */
@Service
public class FileService {

    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(FileService.class);

    /**
     * The Upload path.
     */
    @Value("${upload.path}")
    public String uploadPath;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Save image.
     *
     * @param image    the image
     * @param filename the filename
     * @throws FileNotSaved the file not saved
     */
    public void saveImage(Image image, String filename) throws FileNotSaved {
        Path root = Paths.get(uploadPath);
        try {
            Files.createDirectories(root);
            byte[] imageByte = Base64.getDecoder().decode(image.getPath());
            Path imagePath = Paths.get(String.format("%s/%s",uploadPath,filename));
            Files.write(imagePath,imageByte);
        } catch (IOException e){
            throw new FileNotSaved(String.format("Could not save %s",e.getMessage()));
        }
    }

    /**
     * Delete image.
     *
     * @param image the image
     * @throws FileNotDeleted the file not deleted
     */
    public void deleteImage(Image image) throws FileNotDeleted {
        Path imagePath = Paths.get(image.getPath());
        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            throw new FileNotDeleted(String.format("Could not delete %s",e.getMessage()));
        }

    }

}
