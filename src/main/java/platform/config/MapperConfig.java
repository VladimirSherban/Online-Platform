package platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import platform.dto.AdCreateDto;
import platform.dto.CreateUserDto;
import platform.dto.FullAdDto;
import platform.dto.RegReqDto;
import platform.dto.model_dto.AdsDto;
import platform.dto.model_dto.CommentDto;
import platform.dto.model_dto.UserDto;
import platform.mapper.AdCommentMapper;
import platform.mapper.AdMapper;
import platform.mapper.UserMapper;
import platform.model.Ads;
import platform.model.Comment;
import platform.model.User;

import java.util.Collection;
import java.util.List;

@Configuration
public class MapperConfig {

    @Bean
    public AdMapper adMapper() {
        return new AdMapper() {
            @Override
            public Ads toEntity(AdsDto dto) {
                return null;
            }

            @Override
            public AdsDto toDto(Ads entity) {
                return null;
            }

            @Override
            public List<AdsDto> toDto(Collection<Ads> entity) {
                return null;
            }

            @Override
            public List<Ads> toEntity(Collection<AdsDto> dto) {
                return null;
            }

            @Override
            public Ads toEntity(AdCreateDto dto) {
                return null;
            }

            @Override
            public FullAdDto toFullAdsDto(Ads entity) {
                return null;
            }
        };
    }

    @Bean
    public AdCommentMapper adCommentMapper() {
        return new AdCommentMapper() {
            @Override
            public Comment toEntity(CommentDto dto) {
                return null;
            }

            @Override
            public CommentDto toDto(Comment entity) {
                return null;
            }

            @Override
            public List<CommentDto> toDto(Collection<Comment> entity) {
                return null;
            }

            @Override
            public List<Comment> toEntity(Collection<CommentDto> dto) {
                return null;
            }
        };
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper() {
            @Override
            public CreateUserDto toCreateUserDto(User entity) {
                return null;
            }

            @Override
            public User createUserDtoToEntity(CreateUserDto dto) {
                return null;
            }

            @Override
            public User toEntity(RegReqDto dto) {
                return null;
            }

            @Override
            public User toEntity(UserDto dto) {
                return null;
            }

            @Override
            public UserDto toDto(User entity) {
                return null;
            }

            @Override
            public List<UserDto> toDto(Collection<User> entity) {
                return null;
            }

            @Override
            public List<User> toEntity(Collection<UserDto> dto) {
                return null;
            }
        };
    }
}
