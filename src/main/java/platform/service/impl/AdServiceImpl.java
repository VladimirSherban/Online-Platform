package platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.AdsCreateDto;
import platform.dto.FullAdsDto;
import platform.dto.model_dto.AdsDto;
import platform.dto.response_wrapper.ResponseWrapperAdsDto;
import platform.exception.*;
import platform.mapper.AdsMapper;
import platform.mapper.FullAdMapper;
import platform.mapper.PictureMapper;
import platform.model.Ads;
import platform.model.Picture;
import platform.model.User;
import platform.repository.AdsRepository;
import platform.repository.UserRepository;
import platform.security.AuthenticationFacade;
import platform.service.AdService;
import platform.service.PictureService;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService, AuthenticationFacade {
    private final AdsMapper adMapper;
    private final FullAdMapper fullAdMapper;
    private final AdsRepository adRepository;
    private final UserRepository userRepository;
    @Qualifier("pictureDiskServiceImpl")
    private final PictureService pictureService;
    private final PictureMapper pictureMapper;

    @Transactional
    @Override
    public AdsDto addAd(AdsCreateDto adCreateDto, MultipartFile file) {
        try {
            User user = getUser();
            Picture picture = pictureMapper.mapToPicture(file);
            picture = pictureService.save(picture);
            Ads ad = adMapper.buildAd(user, adCreateDto, picture);
            Ads adSaved = adRepository.save(ad);
            return adMapper.toDto(adSaved);
        } catch (IOException e) {
            throw new CustomIOException("Something went wrong while picture file reading ");
        }
    }

    @Override
    public byte[] getImageById(String uuid) {
        return pictureService.download(UUID.fromString(uuid));
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<Ads> ads = adRepository.findAll();
        List<AdsDto> adDtoList = adMapper.toDtoList(ads);
        return ResponseWrapperAdsDto.builder().results(adDtoList).count(adDtoList.size()).build();
    }

    @Transactional
    @Override
    public ResponseWrapperAdsDto getMyAds() {
        List<Ads> adList = adRepository.findAllByUser(getUser());
        List<AdsDto> adDtoList = adMapper.toDtoList(adList);
        return ResponseWrapperAdsDto.builder().results(adDtoList).count(adDtoList.size()).build();
    }

    @Override
    public FullAdsDto getFullAdDto(Integer id) {
        Ads ad = adRepository.findById(id).orElseThrow(() -> new AdsNotFoundException(id));
        User user = userRepository.findById(ad.getUser().getId()).orElseThrow(() -> new UserNotFoundException(id));
        return fullAdMapper.toDto(user, ad);
    }

    @Override
    public void removeAd(Integer id) {
        Ads ad = adRepository.findById(id).orElseThrow(() -> new AdsNotFoundException(id));
        adRepository.delete(ad);
    }

    @Override
    public AdsDto updateAd(Integer id, AdsCreateDto adCreateDto) {
        Ads ad = adRepository.findById(id).orElseThrow(() -> new AdsNotFoundException(id));
        ad = adMapper.updateAds(adCreateDto, ad);
        return adMapper.toDto(adRepository.save(ad));
    }

    @Override
    public AdsDto updatePicture(Integer id, MultipartFile file) {
        Ads ad = adRepository.findById(id).orElseThrow(() -> new AdsNotFoundException(id));
        Picture picture;
        try {
            picture = pictureMapper.mapToPicture(file);
        } catch (IOException e) {
            throw new CustomIOException("Something went wrong wile picture reading");
        }
        if (ad.getPicture() == null) {
            Picture pictureNew = pictureService.save(picture);
            ad.setPicture(pictureNew);
            adRepository.save(ad);
        } else {
            UUID uuid = ad.getPicture().getUuid();
            pictureService.update(uuid, picture);
            adRepository.save(ad);
        }
        return adMapper.toDto(ad);
    }

    private User getUser() {
        String login = getLogin();
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }
}
