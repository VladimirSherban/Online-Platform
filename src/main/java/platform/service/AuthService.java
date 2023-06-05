package platform.service;

import platform.dto.RegReqDto;
import platform.exception.RegisterException;

public interface AuthService {
    /**
     * Аутендификация пользователей
     * @param userName
     * @param password
     * @return boolean
     */
    boolean login(String userName, String password);
    /**
     * Регистраций пользователей. Сохранение в нового пользователя в базу
     * Если пользователь с такой почтой сущестует  выкидывает RegisterException
     * @param registerReq
     * @throws RegisterException
     * @return boolean
     */
    boolean register(RegReqDto registerReq);
}
