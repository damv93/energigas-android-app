package pe.focusit.energigas.controller.mapper.supplier;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.SupplierEntity;
import pe.focusit.energigas.model.Supplier;
import pe.focusit.energigas.util.ParseUtil;

public class SupplierModelEntityMapper {

    public static Supplier transform(SupplierEntity supplierEntity) {
        Supplier supplier = null;
        if (supplierEntity != null) {
            supplier = new Supplier();
            ParseUtil.parseObject(supplierEntity, supplier);
        }
        return supplier;
    }

    public static List<Supplier> transform(List<SupplierEntity> supplierEntityList) {
        final List<Supplier> supplierList;
        if (supplierEntityList != null && !supplierEntityList.isEmpty()) {
            supplierList = new ArrayList<>(supplierEntityList.size());
            for (SupplierEntity supplierEntity : supplierEntityList) {
                final Supplier supplier = transform(supplierEntity);
                if (supplier != null) {
                    supplierList.add(supplier);
                }
            }
        } else {
            supplierList = new ArrayList<>();
        }
        return supplierList;
    }
    
}
