package com.example.onlineplatform.service;

import com.example.onlineplatform.model.dto.AdsDto;
import com.example.onlineplatform.model.dto.AdsPropertiesDto;
import com.example.onlineplatform.model.dto.ResponseWrapperComment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdsService {
    AdsDto createAds(AdsPropertiesDto adsPropertiesDto, MultipartFile image);

    List<AdsDto> getCurrentUserAds();

    ResponseWrapperComment getMyAdsInStrangeForm();

    ResponseWrapperComment getAllAds();
}
