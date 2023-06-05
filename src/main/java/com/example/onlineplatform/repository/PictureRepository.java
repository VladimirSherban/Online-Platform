package com.example.onlineplatform.repository;

import com.example.onlineplatform.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {

        /**
         * Поиск картинки по индентификатору
         *
         * @param uuid Идентификатор картинки
         * @return Обьект-картинка
         */
        Optional<Picture> findByUuid(UUID uuid);
}
