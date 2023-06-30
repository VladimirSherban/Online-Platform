package com.example.onlineplatform.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.AdCreateDto;
import platform.dto.FullAdDto;
import platform.mapper.AdMapper;
import platform.model.Ads;
import platform.model.Comment;
import platform.model.User;
import platform.repository.AdsCommentRepository;
import platform.repository.AdsRepository;
import platform.repository.UserRepository;
import platform.service.ImageService;
import platform.service.impl.AdServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdServiceImplTest {

    @InjectMocks
    AdServiceImpl adService;

    @Mock
    private AdsRepository adRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageService imageService;
    @Mock
    private AdMapper adMapper;

    @Mock
    private AdsCommentRepository commentRepository;


    @Test
    public void testGetAllAds() {

        List<Ads> testAds = Arrays.asList(new Ads(), new Ads());
        when(adRepository.findAll()).thenReturn(testAds);
        Collection<Ads> result = adService.getAllAds();

        assertEquals(testAds, result);

        verify(adRepository, times(1)).findAll();
    }



    @Test
    public void testAddAds() throws Exception {

        AdCreateDto adCreateDto = new AdCreateDto();
        MultipartFile adsImage = new MockMultipartFile("image.jpg", new byte[]{});
        String email = "example@example.com";
        User user = new User();
        Ads ads = new Ads();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(adMapper.toEntity(adCreateDto)).thenReturn(ads);
        when(adRepository.save(ads)).thenReturn(ads);

        Ads result = adService.addAds(adCreateDto, adsImage, email);

        verify(userRepository).findByEmail(email);
        verify(adMapper).toEntity(adCreateDto);
        verify(imageService).upload(adsImage);
        verify(adRepository).save(ads);

        assertEquals(ads, result);
    }

    @Test
    public void testGetMyAds() {
        Ads ads = new Ads();
        User user = new User();
        user.setId(1);

        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        Mockito.when(adRepository.findAllByAdsAuthorId(ArgumentMatchers.anyInt())).thenReturn(Collections.singletonList(ads));

        Collection<Ads> result = adService.getMyAds("example_email@test.com");

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(ads, result.iterator().next());
    }

    @Test
    public void testGetFullAd() throws Exception {

        int adId = 1;
        FullAdDto expectedFullAdDto = new FullAdDto();

        when(adRepository.findById(adId)).thenReturn(Optional.of(new Ads()));
        when(adMapper.toFullAdsDto(any(Ads.class))).thenReturn(expectedFullAdDto);

        FullAdDto actualFullAdDto = adService.getFullAd(adId);

        assertEquals(expectedFullAdDto, actualFullAdDto);
    }
    @Test
    public void testGetComments() {

        int adPk = 1;
        List<Comment> comments = Arrays.asList(
                new Comment(),
                new Comment()
        );
        when(commentRepository.findAllByAdId(adPk)).thenReturn(comments);


        Collection<Comment> result = adService.getComments(adPk);

        assertEquals(comments, result);
        verify(commentRepository, times(1)).findAllByAdId(adPk);
    }

    @Test
    public void testGetAdsComment() {

        int adPk = 1;
        int id = 2;
        Comment expectedComment = new Comment();
        expectedComment.setId(id);

        Mockito.when(commentRepository.findByIdAndAdId(id, adPk)).thenReturn(Optional.of(expectedComment));

        Comment actualComment = adService.getAdsComment(adPk, id);

        Assert.assertEquals(expectedComment, actualComment);

        Mockito.verify(commentRepository).findByIdAndAdId(id, adPk);
    }


}
