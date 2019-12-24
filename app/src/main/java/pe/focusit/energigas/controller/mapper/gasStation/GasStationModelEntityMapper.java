package pe.focusit.energigas.controller.mapper.gasStation;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.GasStationEntity;
import pe.focusit.energigas.model.GasStation;
import pe.focusit.energigas.util.ParseUtil;

public class GasStationModelEntityMapper {

    public static GasStation transform(GasStationEntity clientEntity) {
        GasStation client = null;
        if (clientEntity != null) {
            client = new GasStation();
            ParseUtil.parseObject(clientEntity, client);
        }
        return client;
    }

    public static List<GasStation> transform(List<GasStationEntity> clientEntityList) {
        final List<GasStation> clientList;
        if (clientEntityList != null && !clientEntityList.isEmpty()) {
            clientList = new ArrayList<>(clientEntityList.size());
            for (GasStationEntity clientEntity : clientEntityList) {
                final GasStation client = transform(clientEntity);
                if (client != null) {
                    clientList.add(client);
                }
            }
        } else {
            clientList = new ArrayList<>();
        }
        return clientList;
    }
    
}
