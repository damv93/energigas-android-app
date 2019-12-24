package pe.focusit.energigas.controller.mapper.vehicle;

import pe.focusit.energigas.data.entity.VehicleEntity;
import pe.focusit.energigas.data.net.dto.VehicleDto;
import pe.focusit.energigas.util.ParseUtil;

public class VehicleDtoEntityMapper {

    public static VehicleEntity transform(VehicleDto vehicleDto) {
        VehicleEntity vehicleEntity = null;
        if (vehicleDto != null) {
            vehicleEntity = new VehicleEntity();
            ParseUtil.parseObject(vehicleDto, vehicleEntity);
        }
        return vehicleEntity;
    }
    
}
