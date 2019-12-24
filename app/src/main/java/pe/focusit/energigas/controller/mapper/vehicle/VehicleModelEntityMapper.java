package pe.focusit.energigas.controller.mapper.vehicle;

import pe.focusit.energigas.data.entity.VehicleEntity;
import pe.focusit.energigas.model.Vehicle;
import pe.focusit.energigas.util.ParseUtil;

public class VehicleModelEntityMapper {

    public static Vehicle transform(VehicleEntity vehicleEntity) {
        Vehicle vehicle = null;
        if (vehicleEntity != null) {
            vehicle = new Vehicle();
            ParseUtil.parseObject(vehicleEntity, vehicle);
        }
        return vehicle;
    }
    
}
