package platform.security.service;

import platform.dto.model_dto.UserDto;
import platform.exception.AdsNotFoundException;
import platform.exception.ForbiddenException;
import platform.exception.UserNotFoundException;
import platform.model.Ads;
import platform.model.User;
import platform.repository.AdsRepository;
import platform.repository.UserRepository;
import platform.security.AuthenticationFacade;
import platform.security.enumki.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionService implements AuthenticationFacade {
    private final UserRepository userRepository;
    private final AdsRepository adRepository;


    /**
     * Проверка прав пользователя для UserController. Перегруженный метод
     * @param login
     * @param userDto
     * @throws ForbiddenException
     * @return boolean
     */
    public boolean checkPermissionForUserController(String login, UserDto userDto) {
        if (isAdminRole(login) || isCurrentUser(login, userDto)) {
            return true;
        }
        throw new ForbiddenException();
    }

    /**
     * Проверка прав пользователя для UserController. Перегруженный метод
     * @param login
     * @param userId
     * @throws ForbiddenException
     * @return boolean
     */
    public boolean checkPermissionForUserController(String login, Integer userId) {
        if (isAdminRole(login) || isCurrentUser(login, userId)) {
            return true;
        }
        throw new ForbiddenException();
    }

    /**
     * @param login
     * @throws UserNotFoundException
     * @return User
     */
    private User findUserByLogin(String login) {
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    public boolean checkAllowedForbidden(Integer id) {
        Ads ads = adRepository.findById(id).orElseThrow(() -> new AdsNotFoundException(id));
        User currentUser = userRepository.findFirstByEmail(getLogin())
                .orElseThrow(() -> new UserNotFoundException("Current user not found"));
        if (ads.getUser().equals(currentUser) || currentUser.getRoleGroup().equals(Role.ROLE_ADMIN)) {
            return true;
        }
        throw new ForbiddenException();
    }

    /**
     * @param login
     * @throws UserNotFoundException
     * @return
     */
    private boolean isAdminRole(String login) {
        return Role.ROLE_ADMIN.equals(findUserByLogin(login).getRoleGroup());
    }

    /**
     * @param login
     * @param userId
     * @return boolean
     */
    private boolean isCurrentUser(String login, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getEmail().equals(login);
    }

    /**
     * @param login
     * @param userDto
     * @return boolean
     */
    private boolean isCurrentUser(String login, UserDto userDto) {
        User userByLogin = findUserByLogin(login);
        return userDto.getEmail().equals(userByLogin.getEmail());
    }
}
