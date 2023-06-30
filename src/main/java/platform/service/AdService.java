package platform.service;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.AdCreateDto;
import platform.dto.FullAdDto;
import platform.dto.model_dto.CommentDto;
import platform.model.Ads;
import platform.model.Comment;

import java.util.Collection;

public interface AdService {
    Collection<Ads> getAllAds();

    /**
     * Обновляем картинку по id Объявления
     *
     * @param id
     * @param image
     */

    @SneakyThrows
    void updateAdsImage(int id, MultipartFile image);


    /**
     * Метод добавления объявления
     *
     * @param adCreateDto
     * @param adsImage
     * @param Email
     * @return {@link Ads}
     */

    @SneakyThrows
    Ads addAds(AdCreateDto adCreateDto, MultipartFile adsImage, String Email);

    /**
     * Показывает объявления авторизованного польхователя
     *
     * @param Email
     * @return Collection<Ads>
     * </Ads>
     */
    Collection<Ads> getMyAds(String Email);

    /**
     * @param id
     * @return {@link FullAdDto}
     * @throws Exception
     */
    FullAdDto getFullAd(int id) throws Exception;

    /**
     * Метод получения комментариев
     *
     * @param adPk
     * @return Collection<Comment>
     * </Comment>
     */
    Collection<Comment> getComments(int adPk);

    /**
     * Получить комментарий по id
     *
     * @param adPk
     * @param id
     * @return
     */
    Comment getAdsComment(int adPk, int id);

    /**
     * Обновить Объявление
     *
     * @param adId
     * @param adCreateDto
     * @return {@link Ads}
     */

    Ads updateAds(int adId, AdCreateDto adCreateDto);

    /**
     * Удаление объявления по id
     *
     * @param adId
     * @return {@link Ads}
     */

    Ads deleteAdsById(int adId);

    /**
     * Удалить коментарий по id
     *
     * @param adPk
     * @param id
     * @return {@link Comment}
     */

    Comment deleteComment(int adPk, int id);

    /**
     * Редактирование комментария по id
     *
     * @param adPk
     * @param id
     * @param commentUpdated
     * @return {@link Comment}
     */

    Comment updateComment(int adPk, int id, Comment commentUpdated);

    /**
     * Добавление нового комментария
     *
     * @param adPk
     * @param adCommentDto
     * @param Email
     * @return {@link Comment}
     * @throws Exception
     */
    Comment addComment(int adPk, CommentDto adCommentDto, String Email) throws Exception;
}
