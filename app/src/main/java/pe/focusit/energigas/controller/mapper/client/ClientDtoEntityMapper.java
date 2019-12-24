package pe.focusit.energigas.controller.mapper.client;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.ClientEntity;
import pe.focusit.energigas.data.net.dto.ClientDto;
import pe.focusit.energigas.util.ParseUtil;

public class ClientDtoEntityMapper {

    public static ClientEntity transform(ClientDto clientDto) {
        ClientEntity clientEntity = null;
        if (clientDto != null) {
            clientEntity = new ClientEntity();
            ParseUtil.parseObject(clientDto, clientEntity);
        }
        return clientEntity;
    }

    public static List<ClientEntity> transform(List<ClientDto> clientDtoList) {
        final List<ClientEntity> clientEntityList;

        if (clientDtoList != null && !clientDtoList.isEmpty()) {
            clientEntityList = new ArrayList<>(clientDtoList.size());
            for (ClientDto clientDto : clientDtoList) {
                final ClientEntity clientEntity = transform(clientDto);
                if (clientEntity != null) {
                    clientEntityList.add(clientEntity);
                }
            }
        } else {
            clientEntityList = new ArrayList<>();
        }
        return clientEntityList;
    }
    
}
