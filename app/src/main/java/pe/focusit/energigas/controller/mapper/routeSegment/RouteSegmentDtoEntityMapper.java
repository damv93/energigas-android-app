package pe.focusit.energigas.controller.mapper.routeSegment;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.RouteSegmentEntity;
import pe.focusit.energigas.data.net.dto.RouteSegmentDto;
import pe.focusit.energigas.util.ParseUtil;

public class RouteSegmentDtoEntityMapper {

    public static RouteSegmentEntity transform(RouteSegmentDto routeSegmentDto) {
        RouteSegmentEntity routeSegmentEntity = null;
        if (routeSegmentDto != null) {
            routeSegmentEntity = new RouteSegmentEntity();
            ParseUtil.parseObject(routeSegmentDto, routeSegmentEntity);
        }
        return routeSegmentEntity;
    }

    public static List<RouteSegmentEntity> transform(List<RouteSegmentDto> routeSegmentDtoList) {
        final List<RouteSegmentEntity> routeSegmentEntityList;

        if (routeSegmentDtoList != null && !routeSegmentDtoList.isEmpty()) {
            routeSegmentEntityList = new ArrayList<>(routeSegmentDtoList.size());
            for (RouteSegmentDto routeSegmentDto : routeSegmentDtoList) {
                final RouteSegmentEntity routeSegmentEntity = transform(routeSegmentDto);
                if (routeSegmentEntity != null) {
                    routeSegmentEntityList.add(routeSegmentEntity);
                }
            }
        } else {
            routeSegmentEntityList = new ArrayList<>();
        }
        return routeSegmentEntityList;
    }
    
}
