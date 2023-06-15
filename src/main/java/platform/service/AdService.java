package platform.service;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
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
     * Ищем объявление по его id
     * @param adsId
     * @return {@link Ads}
     */

    Ads getAdsById(int adsId);
}