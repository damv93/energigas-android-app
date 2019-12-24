package pe.focusit.energigas.controller.mapper.declaration;

import pe.focusit.energigas.data.entity.DeclarationEntity;
import pe.focusit.energigas.data.net.dto.DeclarationDto;
import pe.focusit.energigas.util.ParseUtil;

public class DeclarationDtoEntityMapper {

    public static DeclarationEntity transform(DeclarationDto declarationDto) {
        DeclarationEntity declarationEntity = null;
        if (declarationDto != null) {
            declarationEntity = new DeclarationEntity();
            ParseUtil.parseObject(declarationDto, declarationEntity);
        }
        return declarationEntity;
    }

    public static DeclarationDto transform(DeclarationEntity declarationEntity) {
        DeclarationDto declarationDto = null;
        if (declarationEntity != null) {
            declarationDto = new DeclarationDto();
            ParseUtil.parseObject(declarationEntity, declarationDto);
        }
        return declarationDto;
    }
    
}
