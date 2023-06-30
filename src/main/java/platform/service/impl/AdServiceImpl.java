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
import platform.dto.FullAdDto;
import platform.dto.model_dto.CommentDto;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.model.Ads;
import platform.model.Comment;
import platform.model.User;
import platform.repository.AdsCommentRepository;
import platform.repository.AdsRepository;
import platform.repository.ImageRepository;
import platform.repository.UserRepository;
import platform.security.service.impl.SecurityUtils;
import platform.service.AdService;
import platform.service.ImageService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

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
    private final SecurityUtils securityUtils;


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
        securityUtils.checkPermissionToAds(ads);
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

        logger.info("Метод получения объявлений авторизованного пользователя");
        User user = userRepository.findByEmail(Email).orElseThrow();
        return adRepository.findAllByAdsAuthorId(user.getId());

    }

    @Override
    public FullAdDto getFullAd(int id) throws Exception {

        logger.info("Метод получения full ad");
        return adMapper.toFullAdsDto(adRepository.findById(id).orElseThrow(() -> new Exception("Объявление не найдено")));

    }

    @Override
    public Collection<Comment> getComments(int adPk) {

        logger.info("Метод получения комментариев");
        return commentRepository.findAllByAdId(adPk);

    }

    @Override
    public Comment getAdsComment(int adPk, int id) {

        logger.info("Метод получения комментария по id");
        return commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Ad %d " +
                                "belonging to an ad with id %d not found", id, adPk)));

    }

    @Override
    public Ads updateAds(int adId, AdCreateDto adCreateDto) {

        logger.info("Метод обновления объявления");
        Ads ads = getAdsById(adId);
        securityUtils.checkPermissionToAds(ads);
        ads.setTitle(adCreateDto.getTitle());
        ads.setDescription(adCreateDto.getDescription());
        ads.setPrice(adCreateDto.getPrice());
        return adRepository.save(ads);

    }

    @Override
    public Ads deleteAdsById(int adId) {

        logger.info("Метод удаления объявления по id");
        Ads ads = getAdsById(adId);
        securityUtils.checkPermissionToAds(ads);
        commentRepository.deleteCommentByAdId(adId);
        adRepository.delete(ads);
        return ads;

    }

    @Override
    public Comment deleteComment(int adPk, int id) {

        logger.info("Метод удаления комментария по id");
        Comment comment = getAdsComment(adPk, id);
        securityUtils.checkPermissionToComment(comment);
        commentRepository.delete(comment);
        return comment;

    }

    @Override
    public Comment updateComment(int adPk, int id, Comment commentUpdated) {

        logger.info("Метод редоктирования комментария по id");
        Comment comment = getAdsComment(adPk, id);
        securityUtils.checkPermissionToComment(comment);
        comment.setText(commentUpdated.getText());
        return commentRepository.save(comment);

    }

    @Override
    public Comment addComment(int adPk, CommentDto adCommentDto, String Email) throws Exception {

        User user = userRepository.findByEmail(Email).orElseThrow(() -> new Exception("User not found"));
        Comment comment = commentMapper.toEntity(adCommentDto);
        comment.setCommentAuthor(user);
        comment.setAd(adRepository.findById(adPk).orElseThrow(() -> new Exception("Ad not found")));
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);

    }
}
