package platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.AdCreateDto;
import platform.dto.model_dto.AdsDto;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.model.Ads;
import platform.repository.AdsRepository;
import platform.service.AdService;
import platform.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")

public class AdController {
    private final AdService adservice;
    private final AdMapper adMapper;
    private final AdCommentMapper commentMapper;
    private final ImageService imageService;
    private final AdsRepository adsRepository;



//    @GetMapping
//    public ResponseEntity<List<AdsDto>> getAllAds() {
//        List<Ads> adsDtos =(List<Ads>) adservice.getAllAds();
//        return new ResponseEntity<List<AdsDto>>(HttpStatus.OK);
//  не вышло, подумать ещё
//    }


    @Operation(summary = "Add new AD", operationId = "addAds",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ads.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@RequestPart("image") MultipartFile adsImage,
                                         @Valid @RequestPart("properties") AdCreateDto adCreateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(adMapper.toDto(adservice.addAds(adCreateDto, adsImage, authentication.getName())));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") int id, @NotNull @RequestBody MultipartFile image) {
        adservice.updateAdsImage(id, image);
        return ResponseEntity.ok().build();
    }


}
