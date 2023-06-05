package platform.api;


import platform.dto.model_dto.CommentDto;
import platform.dto.response_wrapper.ResponseWrapperCommentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@Tag(name = "Comments", description = "the Ads API")
public interface CommentApi {

    @Operation(operationId = "addAdsComment", summary = "addAdsComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PostMapping(value = "/ads/{ad_id}/comments", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<CommentDto> addAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "comments", description = "comments", required = true) @Valid @RequestBody
            CommentDto comment);

    @Operation(operationId = "deleteAdsComment", summary = "deleteAdsComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping(value = "/ads/{ad_id}/comments/{id}")
    ResponseEntity<CommentDto> deleteAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "getAdComment", summary = "getAdComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(value = "/ads/{ad_id}/comments/{id}", produces = { "application/json" })
    ResponseEntity<CommentDto> getAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "getAdsComments", summary = "getAdsComments", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapperCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(value = "/ads/{ad_id}/comments", produces = { "application/json" })
    ResponseEntity<ResponseWrapperCommentDto> getAdsComments(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId);

    @Operation(operationId = "updateAdsComment", summary = "updateAdsComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping(value = "/ads/{ad_id}/comments/{id}",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<CommentDto> updateAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "comments", description = "comments", required = true) @Valid @RequestBody
            CommentDto comments);
}
