package com.example.onlineplatform.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import platform.controller.AdController;
import platform.dto.AdCreateDto;
import platform.dto.FullAdDto;
import platform.dto.ResponseWrapper;
import platform.dto.model_dto.AdsDto;
import platform.dto.model_dto.CommentDto;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.model.Ads;
import platform.model.Comment;
import platform.model.Image;
import platform.service.impl.AdServiceImpl;
import platform.service.impl.ImageServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdControllerTest {

    @InjectMocks
    AdController adController;

    @Mock
    AdServiceImpl adService;
    @Mock
    AdMapper adMapper;

    @Mock
    AdCommentMapper commentMapper;

    @Mock
    ImageServiceImpl imageService;


    @Test
    public void getAllAds_shouldReturnListOfAdsDto() {

        List<Ads> ads = new ArrayList<>();
        ads.add(new Ads());
        ads.add(new Ads());

        List<AdsDto> adsDto = new ArrayList<>();
        adsDto.add(new AdsDto());
        adsDto.add(new AdsDto());

        when(adService.getAllAds()).thenReturn(ads);
        when(adMapper.toDto(any(Ads.class))).thenReturn(new AdsDto());

        ResponseWrapper<AdsDto> result = adController.getAllAds();

    }


    @Test
    public void updateAdsImage_shouldReturnOkStatus() {

        MultipartFile image = new MockMultipartFile("image", new byte[0]);

        ResponseEntity<?> result = adController.updateAdsImage(1, image);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(adService).updateAdsImage(1, image);
    }


    @Test
    public void getFullAds_shouldReturnFullAdDto() throws Exception {

        FullAdDto fullAdDto = new FullAdDto();

        when(adService.getFullAd(1)).thenReturn(fullAdDto);

        ResponseEntity<FullAdDto> result = adController.getFullAds(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(fullAdDto, result.getBody());
    }

    @Test
    public void getAdsComment_shouldReturnCommentDto() {

        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();

        when(adService.getAdsComment(1, 1)).thenReturn(comment);
        when(commentMapper.toDto(any(Comment.class))).thenReturn(commentDto);

        ResponseEntity<CommentDto> result = adController.getAdsComment(1, 1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(commentDto, result.getBody());
    }


    @Test
    public void getAdsImage_shouldReturnImageBytes() {

        when(imageService.getImageById(1)).thenReturn(new Image());

        ResponseEntity<byte[]> result = adController.getAdsImage(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void updateAdImage_shouldReturnOkStatus() {
        MultipartFile image = new MockMultipartFile("image", new byte[0]);

        ResponseEntity<?> result = adController.updateAdImage(1, image);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(adService).updateAdsImage(1, image);
    }

    @Test
    public void updateAds_shouldReturnUpdatedAdDto() {
        AdCreateDto adCreateDto = new AdCreateDto();
        Ads ad = new Ads();
        AdsDto adDto = new AdsDto();

        when(adMapper.toDto(any(Ads.class))).thenReturn(adDto);
        when(adService.updateAds(1, adCreateDto)).thenReturn(ad);

        ResponseEntity<AdsDto> result = adController.updateAds(1, adCreateDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(adDto, result.getBody());
    }

    @Test
    public void deleteAds_shouldReturnOkStatus() {
        ResponseEntity<Void> result = adController.deleteAds(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(adService).deleteAdsById(1);
    }

    @Test
    public void deleteComment_shouldReturnOkStatus() {

        ResponseEntity<HttpStatus> result = adController.deleteComment(1, 1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(adService).deleteComment(1, 1);
    }

    @Test
    public void updateComments_shouldReturnUpdatedCommentDto() {
        CommentDto commentDto = new CommentDto();
        Comment comment = new Comment();

        when(commentMapper.toEntity(any(CommentDto.class))).thenReturn(comment);
        when(commentMapper.toDto(any(Comment.class))).thenReturn(commentDto);
        when(adService.updateComment(1, 1, comment)).thenReturn(comment);

        ResponseEntity<CommentDto> result = adController.updateComments(1, 1, commentDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(commentDto, result.getBody());
    }
}
