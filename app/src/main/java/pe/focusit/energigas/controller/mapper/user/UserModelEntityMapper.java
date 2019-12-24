package pe.focusit.energigas.controller.mapper.user;

import pe.focusit.energigas.data.entity.UserEntity;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.util.ParseUtil;

public class UserModelEntityMapper {

    public static User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User();
            ParseUtil.parseObject(userEntity, user);
        }
        return user;
    }
    
}
