package pe.focusit.energigas.controller.mapper.routeSegment;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.net.dto.RouteSegmentDto;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.ParseUtil;

public class RouteSegmentModelDtoMapper {

    public static RouteSegment transform(RouteSegmentDto routeSegmentDto) {
        RouteSegment routeSegment = null;
        if (routeSegmentDto != null) {
            routeSegment = new RouteSegment();
            ParseUtil.parseObject(routeSegmentDto, routeSegment);
        }
        return routeSegment;
    }

    public static List<RouteSegment> transform(List<RouteSegmentDto> routeSegmentDtoList) {
        final List<RouteSegment> routeSegmentList;
        if (routeSegmentDtoList != null && !routeSegmentDtoList.isEmpty()) {
            routeSegmentList = new ArrayList<>(routeSegmentDtoList.size());
            for (RouteSegmentDto routeSegmentDto : routeSegmentDtoList) {
                final RouteSegment routeSegmentEntity = transform(routeSegmentDto);
                if (routeSegmentEntity != null) {
                    routeSegmentList.add(routeSegmentEntity);
                }
            }
        } else {
            routeSegmentList = new ArrayList<>();
        }
        return routeSegmentList;
    }
    
}
