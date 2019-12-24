package pe.focusit.energigas.controller.mapper.declaration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pe.focusit.energigas.data.net.dto.DeclarationDto;
import pe.focusit.energigas.model.Declaration;
import pe.focusit.energigas.util.ParseUtil;

public class DeclarationModelDtoMapper {

    public static DeclarationDto transform(Declaration declaration) {
        DeclarationDto declarationDto = null;
        if (declaration != null) {
            declarationDto = new DeclarationDto();
            ParseUtil.parseObject(declaration, declarationDto);
        }
        return declarationDto;
    }

    public static Declaration transform(DeclarationDto declarationDto) {
        Declaration declaration = null;
        if (declarationDto != null) {
            declaration = new Declaration();
            ParseUtil.parseObject(declarationDto, declaration);
            if (declarationDto.getDateCreated() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    declaration.setDateCreated(dateFormat.parse(declarationDto.getDateCreated()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return declaration;
    }

    public static List<Declaration> transform(List<DeclarationDto> declarationDtoList) {
        final List<Declaration> declarationList;
        if (declarationDtoList != null && !declarationDtoList.isEmpty()) {
            declarationList = new ArrayList<>(declarationDtoList.size());
            for (DeclarationDto declarationDto : declarationDtoList) {
                final Declaration declaration = transform(declarationDto);
                if (declaration != null) {
                    declarationList.add(declaration);
                }
            }
        } else {
            declarationList = new ArrayList<>();
        }
        return declarationList;
    }
    
}
