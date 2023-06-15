package platform.service;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.AdCreateDto;
import platform.model.Ads;

import java.util.Collection;

public interface AdService {
    Collection<Ads> getAllAds();

    /**
     * Обновляем картинку по id Объявления
     * @param id
     * @param image
     */

    @SneakyThrows
    void updateAdsImage(int id, MultipartFile image);


    /**
     * Метод добавления объявления
     * @param adCreateDto
     * @param adsImage
     * @param Email
     * @return {@link Ads}
     */

    @SneakyThrows
    Ads addAds(AdCreateDto adCreateDto, MultipartFile adsImage, String Email);

    /**
     * Показывает объявления авторизованного польхователя
     * @param Email
     * @return Collection<Ads>
     * </Ads>
     */
    Collection<Ads> getMyAds(String Email);
}
