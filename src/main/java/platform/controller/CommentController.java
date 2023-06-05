package platform.controller;

import platform.dto.model_dto.CommentDto;
import platform.dto.response_wrapper.ResponseWrapperCommentDto;
import platform.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import platform.api.CommentApi;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CommentController implements CommentApi {

    private final CommentService adCommentService;

    @Override
    @PreAuthorize("hasAnyAuthority('ads.comment.crud')")
    public ResponseEntity<CommentDto> addAdsComment(Integer adId, CommentDto comment) {
        return ResponseEntity.ok(adCommentService.addAdsComment(adId, comment));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ads.comment.crud')")
    public ResponseEntity<CommentDto> deleteAdsComment(Integer adId, Integer id) {
        return ResponseEntity.ok(adCommentService.deleteAdsComment(adId, id));
    }

    @Override
    public ResponseEntity<CommentDto> getAdsComment(Integer adId, Integer id) {
        return ResponseEntity.ok(adCommentService.getAdsComment(adId, id));
    }

    @Override
    public ResponseEntity<ResponseWrapperCommentDto> getAdsComments(Integer adId) {
        return ResponseEntity.ok(adCommentService.getAdsComments(adId));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ads.comment.crud')")
    public ResponseEntity<CommentDto> updateAdsComment(Integer adId, Integer id, CommentDto comment) {
        return ResponseEntity.ok(adCommentService.updateAdsComment(adId, id, comment));
    }
}
