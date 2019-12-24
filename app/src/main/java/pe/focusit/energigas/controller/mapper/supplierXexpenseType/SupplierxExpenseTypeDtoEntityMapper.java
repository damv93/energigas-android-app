package pe.focusit.energigas.controller.mapper.supplierXexpenseType;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.SupplierxExpenseTypeEntity;
import pe.focusit.energigas.data.net.dto.SupplierxExpenseTypeDto;
import pe.focusit.energigas.util.ParseUtil;

public class SupplierxExpenseTypeDtoEntityMapper {

    public static SupplierxExpenseTypeEntity transform(SupplierxExpenseTypeDto supplierxExpenseTypeDto) {
        SupplierxExpenseTypeEntity supplierxExpenseTypeEntity = null;
        if (supplierxExpenseTypeDto != null) {
            supplierxExpenseTypeEntity = new SupplierxExpenseTypeEntity();
            ParseUtil.parseObject(supplierxExpenseTypeDto, supplierxExpenseTypeEntity);
        }
        return supplierxExpenseTypeEntity;
    }

    public static List<SupplierxExpenseTypeEntity> transform(List<SupplierxExpenseTypeDto> supplierxExpenseTypeDtoList) {
        final List<SupplierxExpenseTypeEntity> supplierxExpenseTypeEntityList;

        if (supplierxExpenseTypeDtoList != null && !supplierxExpenseTypeDtoList.isEmpty()) {
            supplierxExpenseTypeEntityList = new ArrayList<>(supplierxExpenseTypeDtoList.size());
            for (SupplierxExpenseTypeDto supplierxExpenseTypeDto : supplierxExpenseTypeDtoList) {
                final SupplierxExpenseTypeEntity supplierxExpenseTypeEntity = transform(supplierxExpenseTypeDto);
                if (supplierxExpenseTypeEntity != null) {
                    supplierxExpenseTypeEntityList.add(supplierxExpenseTypeEntity);
                }
            }
        } else {
            supplierxExpenseTypeEntityList = new ArrayList<>();
        }
        return supplierxExpenseTypeEntityList;
    }
    
}
