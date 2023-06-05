package platform.service;

import platform.dto.model_dto.CommentDto;
import platform.dto.response_wrapper.ResponseWrapperCommentDto;

public interface CommentService {
    CommentDto addAdsComment(Integer adId, CommentDto comment);

    CommentDto deleteAdsComment(Integer adId, Integer id);

    CommentDto getAdsComment(Integer adId, Integer id);

    ResponseWrapperCommentDto getAdsComments(Integer adId);

    CommentDto updateAdsComment(Integer adId, Integer id, CommentDto comment);
}
