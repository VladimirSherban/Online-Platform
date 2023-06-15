package platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.model_dto.AdsDto;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.repository.AdsRepository;
import platform.service.AdService;
import platform.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")

public class AdController {
    private final Logger logger = LoggerFactory.getLogger(AdController.class);
    private final AdService adservice;
    private final AdMapper adMapper;
    private final AdCommentMapper commentMapper;
    private final ImageService imageService;
    private final AdsRepository adsRepository;



//    @GetMapping
//    public ResponseEntity<Collection<AdsDto>> getAllAds() {
//
//        придумать что делать с этим
//
//    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") int id, @NotNull @RequestBody MultipartFile image) {
        adservice.updateAdsImage(id, image);
        return ResponseEntity.ok().build();
    }
}
