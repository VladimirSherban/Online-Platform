package platform.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import platform.dto.AdCreateDto;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.model.Ads;
import platform.model.User;
import platform.repository.AdsCommentRepository;
import platform.repository.AdsRepository;
import platform.repository.ImageRepository;
import platform.repository.UserRepository;
import platform.service.AdService;
import platform.service.ImageService;

import javax.transaction.Transactional;
import java.util.Collection;

import static platform.security.service.impl.SecurityUtils.checkPermissionToAds;


@Transactional
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    //Логгер - для отладки. Будет выводить на панель то, что написано в инфо при запуске метода
    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    private final AdsRepository adRepository;
    private final AdsCommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;
    private final AdCommentMapper commentMapper;

    @Override
    public Collection<Ads> getAllAds() {
        logger.info("Метод поиска всех объявлений");
        return adRepository.findAll();

    }

    @SneakyThrows
    @Override
    public void updateAdsImage(int id, MultipartFile image) {

        logger.info("Метод обновления картинки у Объявления по его id");
//        Ads ads = adRepository.findById(id); не получилось, жаль этого добряка...
        Ads ads = getAdsById(id);
        checkPermissionToAds(ads);
        imageRepository.delete(ads.getImage());
        ads.setImage(imageService.upload(image));
        adRepository.save(ads);

    }
    private Ads getAdsById(int adsId) {

        logger.info("Метод получения объявления по id");
        return adRepository.findById(adsId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));

    }

    @SneakyThrows  //используем для скрытия исключений, где есть MultipartFile
    @Override
    public Ads addAds(AdCreateDto adCreateDto, MultipartFile adsImage, String Email) {

        logger.info("Метод добавления объявлений");
        User user = userRepository.findByEmail(Email).orElseThrow(() -> new Exception("Пользователь с таким email не найден"));
        Ads ads = adMapper.toEntity(adCreateDto);
        ads.setAdsAuthor(user);
        ads.setImage(imageService.upload(adsImage));
        return adRepository.save(ads);

    }

    @Override
    public Collection<Ads> getMyAds(String Email) {

        logger.info("Current Method is - getAdsMe");
        User user = userRepository.findByEmail(Email).orElseThrow();
        return adRepository.findAllByAdsAuthorId(user.getId());

    }


}
