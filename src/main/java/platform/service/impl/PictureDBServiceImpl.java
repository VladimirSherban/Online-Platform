package platform.service.impl;

import platform.exception.PictureNotFoundException;
import platform.model.Picture;
import platform.repository.PictureRepository;
import platform.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureDBServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    @Override
    public Picture save(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public void update(UUID uuid, Picture picture) {
        Picture oldPicture = pictureRepository.findByUuid(uuid).orElseThrow(() -> new PictureNotFoundException(uuid));
        picture.setUuid(oldPicture.getUuid());
        pictureRepository.save(picture);
    }

    @Override
    public byte[] download(UUID uuid) {
        Picture picture = pictureRepository.findByUuid(uuid).orElseThrow(() -> new PictureNotFoundException(uuid));
        return picture.getData();
    }
}
