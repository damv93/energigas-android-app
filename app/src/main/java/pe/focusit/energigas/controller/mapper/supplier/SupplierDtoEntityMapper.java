package pe.focusit.energigas.controller.mapper.supplier;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.SupplierEntity;
import pe.focusit.energigas.data.net.dto.SupplierDto;
import pe.focusit.energigas.util.ParseUtil;

public class SupplierDtoEntityMapper {

    public static SupplierEntity transform(SupplierDto supplierDto) {
        SupplierEntity supplierEntity = null;
        if (supplierDto != null) {
            supplierEntity = new SupplierEntity();
            ParseUtil.parseObject(supplierDto, supplierEntity);
        }
        return supplierEntity;
    }

    public static List<SupplierEntity> transform(List<SupplierDto> supplierDtoList) {
        final List<SupplierEntity> supplierEntityList;

        if (supplierDtoList != null && !supplierDtoList.isEmpty()) {
            supplierEntityList = new ArrayList<>(supplierDtoList.size());
            for (SupplierDto supplierDto : supplierDtoList) {
                final SupplierEntity supplierEntity = transform(supplierDto);
                if (supplierEntity != null) {
                    supplierEntityList.add(supplierEntity);
                }
            }
        } else {
            supplierEntityList = new ArrayList<>();
        }
        return supplierEntityList;
    }
    
}
