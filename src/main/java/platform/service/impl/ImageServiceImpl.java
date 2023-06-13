package platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import platform.model.Image;
import platform.repository.ImageRepository;
import platform.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {
    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;


    @Override
    public Image upload(MultipartFile multipartFile) throws IOException {
        logger.info("Метод загрузки картинки");
        Image image = new Image();
        image.setImage(multipartFile.getBytes());
        image.setFileSize(multipartFile.getSize());
        image.setMediaType(multipartFile.getContentType());
        image.setImage(multipartFile.getBytes());
        return imageRepository.save(image);
    }

    @Override
    public Image getImageById(Integer id) {
        logger.info("Метод получения картинки по id");
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
