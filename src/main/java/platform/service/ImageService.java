package platform.service;

import org.springframework.web.multipart.MultipartFile;
import platform.model.Image;

import java.io.IOException;

public interface ImageService {

    /**
     * @param multipartFile
     * @return {@link Image}
     */
    Image upload(MultipartFile multipartFile) throws IOException;

    /**
     *
     * @param id (id_image)
     * @return {@link Image}
     */
    Image getImageById(Integer id);
}
