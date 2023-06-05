package platform.mapper;


import platform.exception.CustomIOException;
import platform.model.Picture;
import org.apache.commons.codec.digest.DigestUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Преобразователь сущьностей
 */
@Mapper(componentModel = "spring")
public interface PictureMapper {

    @Mapping(target = "fileName", expression = "java(buildFileName(file))")
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "data", source = "file.bytes")
    Picture mapToPicture(MultipartFile file) throws IOException;

    default String buildFileName(MultipartFile multipartFile) {
        try {
            String sha512 = DigestUtils.sha512Hex(multipartFile.getBytes());
            String pattern = "%s%s";
            String dot = ".";
            String originalName =
                    Optional.ofNullable(multipartFile.getOriginalFilename()).orElseThrow(IOException::new);
            return String.format(pattern, sha512, originalName.substring(originalName.lastIndexOf(dot)));
        } catch (IOException | StringIndexOutOfBoundsException e) {
            throw new CustomIOException("Something went wrong while file name building.");
        }
    }
}
