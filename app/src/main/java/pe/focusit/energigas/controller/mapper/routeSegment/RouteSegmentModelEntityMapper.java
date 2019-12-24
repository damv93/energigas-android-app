package pe.focusit.energigas.controller.mapper.routeSegment;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.controller.mapper.client.ClientModelEntityMapper;
import pe.focusit.energigas.controller.mapper.route.RouteModelEntityMapper;
import pe.focusit.energigas.data.entity.RouteSegmentEntity;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.ParseUtil;

public class RouteSegmentModelEntityMapper {

    public static RouteSegment transform(RouteSegmentEntity routeSegmentEntity) {
        RouteSegment routeSegment = null;
        if (routeSegmentEntity != null) {
            routeSegment = new RouteSegment();
            ParseUtil.parseObject(routeSegmentEntity, routeSegment);
            if (routeSegmentEntity.getClient() != null) {
                routeSegment.setClient(ClientModelEntityMapper.transform(routeSegmentEntity.getClient()));
            }
        }
        return routeSegment;
    }

    public static List<RouteSegment> transform(List<RouteSegmentEntity> routeSegmentEntityList) {
        final List<RouteSegment> routeSegmentList;
        if (routeSegmentEntityList != null && !routeSegmentEntityList.isEmpty()) {
            routeSegmentList = new ArrayList<>(routeSegmentEntityList.size());
            for (RouteSegmentEntity routeSegmentEntity : routeSegmentEntityList) {
                final RouteSegment routeSegment = transform(routeSegmentEntity);
                if (routeSegment != null) {
                    routeSegmentList.add(routeSegment);
                }
            }
        } else {
            routeSegmentList = new ArrayList<>();
        }
        return routeSegmentList;
    }

    public static RouteSegmentEntity transform(RouteSegment routeSegment) {
        RouteSegmentEntity routeSegmentEntity = null;
        if (routeSegment != null) {
            routeSegmentEntity = new RouteSegmentEntity();
            ParseUtil.parseObject(routeSegment, routeSegmentEntity);
        }
        return routeSegmentEntity;
    }
    
}
