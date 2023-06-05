package platform.service;

import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.dto.response_wrapper.ResponseWrapperUserDto;
import platform.exception.ForbiddenException;
import platform.exception.UserNotFoundException;

public interface UserService {
    /**
     * Поиск пользователя по id
     * @param id
     * @throws UserNotFoundException
     * @return UserDto
     */
    UserDto findUser(Integer id);
    /**
     * Поиск пользователя по логину
     * @param login
     * @throws UserNotFoundException
     * @return UserDto
     */
    UserDto findUser(String login);
    /**
     * Получение всех пользователей
     * @return ResponseWrapperUserDto
     */
    ResponseWrapperUserDto getUsers();
    /**
     * Обновление пароля пользователя
     * @param newPasswordDto
     * @param login
     * @throws ForbiddenException
     * @return NewPasswordDto
     */
    NewPasswordDto setPassword(NewPasswordDto newPasswordDto, String login);
    /**
     * Обновления профиля пользователя
     * @param userDto
     * @throws UserNotFoundException
     * @return UserDto
     */
    UserDto updateUser(UserDto userDto);

}
