package pe.focusit.energigas.controller.mapper.route;

import pe.focusit.energigas.controller.mapper.routeSegment.RouteSegmentModelDtoMapper;
import pe.focusit.energigas.controller.mapper.vehicle.VehicleModelDtoMapper;
import pe.focusit.energigas.data.net.dto.RouteDto;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.util.ParseUtil;

public class RouteModelDtoMapper {

    public static Route transform(RouteDto routeDto) {
        Route route = null;
        if (routeDto != null) {
            route = new Route();
            ParseUtil.parseObject(routeDto, route);
            if (routeDto.getVehicle() != null) {
                route.setVehicle(VehicleModelDtoMapper.transform(routeDto.getVehicle()));
            }
            if (routeDto.getRouteSegments() != null) {
                route.setRouteSegments(RouteSegmentModelDtoMapper.transform(routeDto.getRouteSegments()));
            }
        }
        return route;
    }

}
