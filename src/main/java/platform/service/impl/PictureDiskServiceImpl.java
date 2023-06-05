package platform.service.impl;

import platform.exception.CustomIOException;
import platform.exception.PictureNotFoundException;
import platform.model.Picture;
import platform.repository.PictureRepository;
import platform.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PictureDiskServiceImpl implements PictureService {
    @Value("${platform.ads.picture.storage.directory}")
    private String dir;
    private static final String EX_FORMAT = "%nException class name :: %s%nException message :: %s";
    private final PictureRepository pictureRepository;

    @Override
    public Picture save(Picture picture) {
        Path path = Path.of(dir, picture.getFileName());
        saveToDisk(path, picture);
        picture.setData(null);
        return pictureRepository.save(picture);
    }

    @Override
    public void update(UUID uuid, Picture picture) {
        Picture pictureToSave =
                pictureRepository.findByUuid(uuid).orElseThrow(() -> new PictureNotFoundException(uuid));
        Path path = Path.of(dir, picture.getFileName());
        saveToDisk(path, picture);
        pictureToSave.setFileName(picture.getFileName());
        pictureRepository.save(pictureToSave);
    }

    @Override
    public byte[] download(UUID uuid) {
        try {
            Picture picture = pictureRepository.findByUuid(uuid).orElseThrow(() -> new PictureNotFoundException(uuid));
            Path path = buildPath(picture);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new CustomIOException(String.format(EX_FORMAT, e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    private void saveToDisk(Path path, Picture picture) {
        try {
            Files.createDirectories(path.toAbsolutePath().getParent());
            Files.deleteIfExists(path.toAbsolutePath());
            Files.write(path, picture.getData());
        } catch (IOException e) {
            throw new CustomIOException(String.format(EX_FORMAT, e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    private Path buildPath(Picture picture) {
        String locationPattern = "%s%s%s";
        return Paths.get(String.format(locationPattern,
                dir,
                FileSystems.getDefault().getSeparator(),
                picture.getFileName()));
    }
}
