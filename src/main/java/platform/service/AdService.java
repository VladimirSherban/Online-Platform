package platform.service;


import platform.dto.AdsCreateDto;
import platform.dto.FullAdsDto;
import platform.dto.model_dto.AdsDto;
import platform.dto.response_wrapper.ResponseWrapperAdsDto;
import org.springframework.web.multipart.MultipartFile;


/**
 * Сервис класс для CRUD операций с объявлением
 */
public interface AdService {
    /**
     * Добавление обьявления
     *
     * @param adCreateDto Обьект с данными о объявлении
     * @param file        Файл с данными
     * @return Объявление
     */
    AdsDto addAd(AdsCreateDto adCreateDto, MultipartFile file);

    /**
     * Получение всех объявлений
     *
     * @return Обёртка с объявлениями
     */
    ResponseWrapperAdsDto getAllAds();

    /**
     * Получение всех объявлений по отдельному пользователю
     *
     * @return Обёртка для обьявлений по пользователю
     */
    ResponseWrapperAdsDto getMyAds();

    /**
     * Получени полной информации по объявлению
     *
     * @param id Идентификатор
     * @return Объект с развёрнутой информацией по объявлению
     */
    FullAdsDto getFullAdDto(Integer id);

    /**
     * Удаление объявления
     *
     * @param id Идентификатор
     */
    void removeAd(Integer id);

    /**
     * Обновление объявления
     *
     * @param id          Идентификатор
     * @param adCreateDto Обьект с данными о объявлении
     * @return Объявление
     */
    AdsDto updateAd(Integer id, AdsCreateDto adCreateDto);

    /**
     * Обновление картинки в объявлении
     *
     * @param adId Идентификатор
     * @param file Файл картинка
     * @return Объявление
     */
    AdsDto updatePicture(Integer adId, MultipartFile file);

    /**
     * Получение картинки
     *
     * @param uuid Идентификатор картинки
     * @return Файл-картинка
     */
    byte[] getImageById(String uuid);

}
