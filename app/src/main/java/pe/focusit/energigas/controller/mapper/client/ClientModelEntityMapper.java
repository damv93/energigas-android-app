package pe.focusit.energigas.controller.mapper.client;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.ClientEntity;
import pe.focusit.energigas.model.Client;
import pe.focusit.energigas.util.ParseUtil;

public class ClientModelEntityMapper {

    public static Client transform(ClientEntity clientEntity) {
        Client client = null;
        if (clientEntity != null) {
            client = new Client();
            ParseUtil.parseObject(clientEntity, client);
        }
        return client;
    }

    public static List<Client> transform(List<ClientEntity> clientEntityList) {
        final List<Client> clientList;
        if (clientEntityList != null && !clientEntityList.isEmpty()) {
            clientList = new ArrayList<>(clientEntityList.size());
            for (ClientEntity clientEntity : clientEntityList) {
                final Client client = transform(clientEntity);
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
