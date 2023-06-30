package com.example.onlineplatform.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import platform.model.Image;
import platform.repository.ImageRepository;
import platform.service.impl.ImageServiceImpl;

import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void testUpload() throws IOException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        byte[] fileContent = "test content".getBytes();
        Mockito.when(multipartFile.getBytes()).thenReturn(fileContent);
        Mockito.when(multipartFile.getSize()).thenReturn((long) fileContent.length);
        Mockito.when(multipartFile.getContentType()).thenReturn("image/jpeg");

        Image savedImage = new Image();
        savedImage.setId(1);
        savedImage.setImage(fileContent);
//        savedImage.setFileSize(fileContent.length);
        savedImage.setMediaType("image/jpeg");

        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenReturn(savedImage);

        Image uploadedImage = imageService.upload(multipartFile);

        Assert.assertEquals(savedImage.getId(), uploadedImage.getId());
        Assert.assertEquals(savedImage.getImage(), uploadedImage.getImage());
        Assert.assertEquals(savedImage.getFileSize(), uploadedImage.getFileSize());
        Assert.assertEquals(savedImage.getMediaType(), uploadedImage.getMediaType());

        Mockito.verify(multipartFile, Mockito.times(2)).getBytes();
        Mockito.verify(multipartFile).getSize();
        Mockito.verify(multipartFile).getContentType();
        Mockito.verify(imageRepository).save(Mockito.any(Image.class));
    }

    @Test
    public void testGetImageById() {
        Image image = new Image();
        image.setId(1);
        Mockito.when(imageRepository.findById(Mockito.eq(1))).thenReturn(Optional.of(image));

        Image retrievedImage = imageService.getImageById(1);

        Assert.assertEquals(image.getId(), retrievedImage.getId());

        Mockito.verify(imageRepository).findById(Mockito.eq(1));
    }
}