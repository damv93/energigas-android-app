package pe.focusit.energigas.controller.mapper.user;

import pe.focusit.energigas.data.entity.UserEntity;
import pe.focusit.energigas.data.net.dto.UserDto;
import pe.focusit.energigas.util.ParseUtil;

public class UserDtoEntityMapper {

    public static UserEntity transform(UserDto userDto) {
        UserEntity userEntity = null;
        if (userDto != null) {
            userEntity = new UserEntity();
            ParseUtil.parseObject(userDto, userEntity);
        }
        return userEntity;
    }
    
}
