package platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import platform.dto.FullAdDto;
import platform.dto.ResponseWrapper;
import platform.dto.ResponseWrapperCommentDto;
import platform.dto.model_dto.AdsDto;
import platform.dto.model_dto.CommentDto;
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



    @Operation(summary = "Показать все объявления",
            operationId = "getAllAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class))))
            },
            tags = "ADS")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        return ResponseWrapper.of(adMapper.toDto(adservice.getAllAds()));
    }


    @Operation(summary = "Добавить новое объявление", operationId = "addAds",
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
    @Operation(summary = "Показать объявления авторизованного пользователя", operationId = "getMyAds",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getMyAds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseWrapper.of(adMapper.toDto(adservice.getMyAds(authentication.getName())));
    }


    @Operation(summary = "Показать полное объявление", operationId = "getFullAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdDto> getFullAds(@PathVariable int id) throws Exception {
        FullAdDto fullAdDto = adservice.getFullAd(id);
        return ResponseEntity.ok(fullAdDto);
    }

    @Operation(summary = "Найти комментарии", operationId = "getComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperCommentDto.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "ADS")
    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapper<CommentDto> getComments(@PathVariable("ad_pk") int adPk) {
        return ResponseWrapper.of(commentMapper.toDto(adservice.getComments(adPk)));
    }

    @Operation(summary = "Получить комментарии по id", operationId = "getAdsComment",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = CommentDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "ADS")
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDto> getAdsComment(@PathVariable("ad_pk") int ad_pk,
                                                      @PathVariable("id") int id) {
        return ResponseEntity.ok(commentMapper.toDto(adservice.getAdsComment(ad_pk, id)));
    }

    @Operation(summary = "Получить картинку по id", operationId = "getAdsImage",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Byte.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") int id) {
        return ResponseEntity.ok(imageService.getImageById(id).getImage());
    }





}
