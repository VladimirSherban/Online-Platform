package platform.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import platform.dto.*;
import platform.dto.model_dto.*;
import platform.dto.response_wrapper.ResponseWrapperAdsDto;

        /**
         * Интерфейс для обьявлений
         */
        @Validated
        @Tag(name = "Ads", description = "EndPoints related to Ads")
        public interface AdsApi {

            /**
             * Добавление обьявления
             *
             * @param adCreateDto Обьект для передачи данных о создаваемом обьявлении
             * @param file        Картинка для обьявления
             * @return Созданный обьект с данными
             */
            @Operation(operationId = "addAd", summary = "adding an advertisement", tags = {"Ads"}, responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            })
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(encoding = @Encoding(name = "properties", contentType = MediaType.APPLICATION_JSON_VALUE)),
                    @Content(encoding = @Encoding(name = "image", contentType = "image/*"))
            })
            @PostMapping(value = "/ads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
            ResponseEntity<AdsDto> addAd(@RequestPart(value = "properties") AdsCreateDto adCreateDto,
                                        @RequestParam(value = "image") MultipartFile file);

            @Operation(operationId = "Edit picture", summary = "Edit picture by advertisement id", tags = {"Ads"},
                    responses = @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))))
            @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
            ResponseEntity<AdsDto> editPicture(@PathVariable Integer id, @RequestParam(value = "image") MultipartFile file);

            /**
             * Получение всех обьявлений
             *
             * @return Все существующие обьявления
             */
            @Operation(operationId = "getAllAds", summary = "getting all ads wrapped into ResponseWrapperAds", tags = {"Ads"})
            @GetMapping(value = "/ads", produces = {"application/json"})
            ResponseEntity<ResponseWrapperAdsDto> getAllAds();

            @Operation(operationId = "getMyAds", summary = "get all ads of me", tags = {"Ads"},
                    responses = @ApiResponse(responseCode = "401", description = "Unauthorized"))
            @GetMapping(value = "/ads/me", produces = {"application/json"})
            ResponseEntity<ResponseWrapperAdsDto> getMyAds();

            @Operation(operationId = "getFullAd", summary = "get certain full ad", tags = {"Ads"})
            @GetMapping(value = "/ads/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
            ResponseEntity<FullAdsDto> getFullAd(
                    @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

            /**
             * Получение картинки
             *
             * @param uuid Идентификатор картинки
             * @return Файл картинки
             */
            @Operation(operationId = "getImage", summary = "get ad image", tags = {"Ads"})
            @GetMapping(value = "/ads/image/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
            ResponseEntity<byte[]> getAdImage(@PathVariable("uuid") String uuid);

            /**
             * Удаление обьявления
             *
             * @param id Идентификатор обьявления
             * @return Пустое значение и статус 203
             */
            @Operation(operationId = "removeAd", summary = "remove an advertisement", tags = {"Ads"},
                    responses = @ApiResponse(responseCode = "203", description = "No content"))
            @DeleteMapping(value = "/ads/{id}", produces = {"application/json"})
            ResponseEntity<Void> removeAd(
                    @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

            /**
             * Обновление обьявления
             *
             * @param id          Идентификатор обьявления
             * @param adCreateDto Обьект для передачи данных о обновляемом обьявлении
             * @return Обновлённый обьект с данными
             */
            @Operation(operationId = "updateAd", summary = "update an advertisement", tags = {"Ads"})
            @PatchMapping(value = "/ads/{id}", produces = {"application/json"}, consumes = {"application/json"})
            ResponseEntity<AdsDto> updateAd(
                    @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id,
                    @Parameter(name = "ads", description = "ads", required = true) @RequestBody AdsCreateDto adCreateDto);
        }