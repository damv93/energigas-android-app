package pe.focusit.energigas.controller.mapper.vehicle;

import pe.focusit.energigas.data.net.dto.VehicleDto;
import pe.focusit.energigas.model.Vehicle;
import pe.focusit.energigas.util.ParseUtil;

public class VehicleModelDtoMapper {

    public static Vehicle transform(VehicleDto vehicleDto) {
        Vehicle vehicle = null;
        if (vehicleDto != null) {
            vehicle = new Vehicle();
            ParseUtil.parseObject(vehicleDto, vehicle);
        }
        return vehicle;
    }
    
}
