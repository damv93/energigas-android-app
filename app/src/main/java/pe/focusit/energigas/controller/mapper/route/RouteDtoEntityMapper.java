package pe.focusit.energigas.controller.mapper.route;

import pe.focusit.energigas.data.entity.RouteEntity;
import pe.focusit.energigas.data.net.dto.RouteDto;
import pe.focusit.energigas.util.ParseUtil;

public class RouteDtoEntityMapper {

    public static RouteEntity transform(RouteDto routeDto) {
        RouteEntity routeEntity = null;
        if (routeDto != null) {
            routeEntity = new RouteEntity();
            ParseUtil.parseObject(routeDto, routeEntity);
            if (routeDto.getVehicle() != null) {
                routeEntity.setVehicleId(routeDto.getVehicle().getId());
            }
        }
        return routeEntity;
    }
    
}
