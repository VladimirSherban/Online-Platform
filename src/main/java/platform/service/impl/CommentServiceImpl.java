package platform.service.impl;

import platform.dto.model_dto.CommentDto;
import platform.dto.response_wrapper.ResponseWrapperCommentDto;
import platform.exception.*;
import platform.mapper.CommentMapper;
import platform.mapper.ResponseWrapperCommentMapper;
import platform.model.Ads;
import platform.model.Comment;
import platform.model.User;
import platform.repository.AdsCommentRepository;
import platform.repository.AdsRepository;
import platform.repository.UserRepository;
import platform.security.enumki.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import platform.security.AuthenticationFacade;
import platform.service.CommentService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Сервис CRUD для AdsCommentController
 * Проверка прав
 * Пользователя берем из контекста, AuthenticationFacade.getLogin()
 * Сделаны проверки на предмет принадлежности коментариев к объявлению.
 */
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService, AuthenticationFacade {
    private final ResponseWrapperCommentMapper responseWrapperAdCommentMapper;
    private final CommentMapper adCommentMapper;
    private final AdsCommentRepository adCommentRepository;
    private final AdsRepository adRepository;
    private final UserRepository userRepository;

    /**
     * Добавление комментария к объявлению.
     * @param adId
     * @param comment
     * @throws UserNotFoundException
     * @throws AdsNotFoundException
     * @return AdCommentDto
     */
    @Override
    public CommentDto addAdsComment(Integer adId, CommentDto comment) {
        Comment newComment = adCommentMapper.toEntity(comment);
        newComment.setId(null);
        newComment.setUser(userRepository.findFirstByEmail(getLogin())
                .orElseThrow(() -> new UserNotFoundException(getLogin())));
        newComment.setCreatedAt(OffsetDateTime.now());
        newComment.setAds(adRepository.findById(adId)
                .orElseThrow(() -> new AdsNotFoundException(adId)));
        return adCommentMapper.toDto(adCommentRepository.save(newComment));
    }

    /**
     * Удаление коментария пользователя
     * @param adId
     * @param id
     * @throws CommentNotFoundException
     * @throws AdsNotFoundException
     * @return AdCommentDto
     */
    @Override
    public CommentDto deleteAdsComment(Integer adId, Integer id) {
        Ads ad = adRepository.findById(adId).orElseThrow(() -> new AdsNotFoundException(adId));
        Comment adComment = adCommentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        checkUserHasAccessToEditComment(ad, adComment);
        adCommentRepository.deleteById(id);
        return adCommentMapper.toDto(adComment);
    }

    /**
     * Получение коментария
     * @param adId
     * @param id
     * @throws CommentNotFoundException
     * @throws AdsNotFoundException
     * @return AdCommentDto
     */
    @Override
    public CommentDto getAdsComment(Integer adId, Integer id) {
        Ads ad = adRepository.findById(adId).orElseThrow(() -> new AdsNotFoundException(adId));
        Comment adComment = adCommentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        checkAdsHasComment(ad, adComment);
        return adCommentMapper.toDto(adComment);
    }

    /**
     * Получение коментариев
     * @param adId
     * @throws AdsNotFoundException
     * @return ResponseWrapperAdsCommentDto
     */
    @Override
    public ResponseWrapperCommentDto getAdsComments(Integer adId) {
        Ads ad = adRepository.findById(adId).orElseThrow(() -> new AdsNotFoundException(adId));
        List<Comment> adComments = ad.getComments();
        return responseWrapperAdCommentMapper.toDto(adComments.size(),
                adCommentMapper.toDtos(adComments));
    }

    /**
     * Обнолвение коментария
     * @param adId
     * @param id
     * @param comment
     * @throws AdsNotFoundException
     * @throws CommentNotFoundException
     * @return AdCommentDto
     */
    @Override
    public CommentDto updateAdsComment(Integer adId, Integer id, CommentDto comment) {
        Ads ad = adRepository.findById(adId).orElseThrow(() -> new AdsNotFoundException(adId));
        Comment adComment = adCommentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        checkUserHasAccessToEditComment(ad, adComment);
        adComment.setText(comment.getText());
        adComment = adCommentRepository.save(adComment);
        return adCommentMapper.toDto(adComment);
    }

    /**
     * Проверка принадлежности коментария к объявлению.
     * @param ad
     * @param adComment
     * @throws AdWithCommentNotFoundException
     */
    private void checkAdsHasComment(Ads ad, Comment adComment) {
        if (!ad.getComments().contains(adComment)) {
            throw new AdWithCommentNotFoundException(ad.getId(), adComment.getId());
        }
    }

    /**
     * Проверка прав пользователя на редактирование объявления.
     * Обычный пользователь редактирует только свое, администраторы - всё
     * @param ad
     * @param adComment
     * @throws ForbiddenException
     * @throws UserNotFoundException
     */
    private void checkUserHasAccessToEditComment(Ads ad, Comment adComment) {
        // С фронта может прийти запрос на удаление/редактирование коментария, но он не относится к этому объявлению
        if (!Objects.equals(ad.getId(), adComment.getAds().getId())) {
            throw new ForbiddenException();
        }

        String login = getLogin();
        User user = userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
        if (adComment.getUser().getId().equals(user.getId())) {
            return;
        }
        if (Role.ROLE_ADMIN.equals(user.getRoleGroup())) {
            return;
        }
        throw new ForbiddenException("Access id denied, someone else's comment. ");

    }
}
