package pe.focusit.energigas.controller.mapper.gasStation;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.GasStationEntity;
import pe.focusit.energigas.data.net.dto.GasStationDto;
import pe.focusit.energigas.util.ParseUtil;

public class GasStationDtoEntityMapper {

    public static GasStationEntity transform(GasStationDto gasStationDto) {
        GasStationEntity gasStationEntity = null;
        if (gasStationDto != null) {
            gasStationEntity = new GasStationEntity();
            ParseUtil.parseObject(gasStationDto, gasStationEntity);
        }
        return gasStationEntity;
    }

    public static List<GasStationEntity> transform(List<GasStationDto> gasStationDtoList) {
        final List<GasStationEntity> gasStationEntityList;

        if (gasStationDtoList != null && !gasStationDtoList.isEmpty()) {
            gasStationEntityList = new ArrayList<>(gasStationDtoList.size());
            for (GasStationDto gasStationDto : gasStationDtoList) {
                final GasStationEntity gasStationEntity = transform(gasStationDto);
                if (gasStationEntity != null) {
                    gasStationEntityList.add(gasStationEntity);
                }
            }
        } else {
            gasStationEntityList = new ArrayList<>();
        }
        return gasStationEntityList;
    }
    
}
