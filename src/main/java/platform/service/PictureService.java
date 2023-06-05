package platform.service;

import platform.model.Picture;

import java.util.UUID;

/**
 * Интерфейс для CRUD операций с картинкой
 */
public interface PictureService {

    /**
     * Сохранение картинки
     *
     * @param picture Обьект картинка
     * @return Обьект картинка
     */
    Picture save(Picture picture);

    /**
     * Обновление картинки по идентификатору
     *
     * @param uuid    Идентификатор
     * @param picture Обьект картинка
     */
    void update(UUID uuid, Picture picture);

    /**
     * Отправка файла картинки
     *
     * @param uuid Идентификатор
     * @return Обьект картинка
     */
    byte[] download(UUID uuid);
}
