package pe.focusit.energigas.controller.mapper.deposit;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.DepositEntity;
import pe.focusit.energigas.data.net.dto.DepositDto;
import pe.focusit.energigas.util.ParseUtil;

public class DepositEntityDtoMapper {

    public static DepositEntity transform(DepositDto depositDto) {
        DepositEntity depositEntity = null;
        if (depositDto != null) {
            depositEntity = new DepositEntity();
            ParseUtil.parseObject(depositDto, depositEntity);
        }
        return depositEntity;
    }

    public static List<DepositEntity> transform(List<DepositDto> depositDtoList) {
        final List<DepositEntity> depositEntityList;

        if (depositDtoList != null && !depositDtoList.isEmpty()) {
            depositEntityList = new ArrayList<>(depositDtoList.size());
            for (DepositDto depositDto : depositDtoList) {
                final DepositEntity depositEntity = transform(depositDto);
                if (depositEntity != null) {
                    depositEntityList.add(depositEntity);
                }
            }
        } else {
            depositEntityList = new ArrayList<>();
        }
        return depositEntityList;
    }
    
}
