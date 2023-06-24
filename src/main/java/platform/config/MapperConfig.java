package platform.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.mapper.UserMapper;

@Configuration
public class MapperConfig {
    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public AdMapper adMapper() {
        return Mappers.getMapper(AdMapper.class);
    }

    @Bean
    public AdCommentMapper adCommentMapper() {
        return Mappers.getMapper(AdCommentMapper.class);
    }
}
