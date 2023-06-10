package platform.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.api.AdsApi;
import platform.dto.AdsCreateDto;
import platform.dto.FullAdsDto;
import platform.dto.model_dto.AdsDto;
import platform.dto.response_wrapper.ResponseWrapperAdsDto;
import platform.security.service.PermissionService;
import platform.service.AdService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdsController implements  AdsApi{

    private final AdService adService;
    private final PermissionService permissionService;


    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdsDto> addAd(AdsCreateDto adCreateDto, MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(adCreateDto, file));
    }

    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdsDto> editPicture(Integer id, MultipartFile file) {
        permissionService.checkAllowedForbidden(id);
        return ResponseEntity.ok(adService.updatePicture(id, file));
    }

    public ResponseEntity<byte[]> getAdImage(String uuid) {
        return ResponseEntity.ok(adService.getImageById(uuid));
    }

    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds() {
        return ResponseEntity.ok(adService.getMyAds());
    }

    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<FullAdsDto> getFullAd(Integer id) {
        return ResponseEntity.ok(adService.getFullAdDto(id));
    }


    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<Void> removeAd(Integer id) {
        permissionService.checkAllowedForbidden(id);
        adService.removeAd(id);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdsDto> updateAd(Integer id, AdsCreateDto adCreateDto) {
        permissionService.checkAllowedForbidden(id);
        return ResponseEntity.ok(adService.updateAd(id, adCreateDto));
    }

}
