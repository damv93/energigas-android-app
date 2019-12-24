package pe.focusit.energigas.controller.mapper.expenseType;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.controller.mapper.supplier.SupplierModelEntityMapper;
import pe.focusit.energigas.data.entity.ExpenseTypeEntity;
import pe.focusit.energigas.model.ExpenseType;
import pe.focusit.energigas.util.ParseUtil;

public class ExpenseTypeModelEntityMapper {

    public static ExpenseType transform(ExpenseTypeEntity expenseTypeEntity) {
        ExpenseType expenseType = null;
        if (expenseTypeEntity != null) {
            expenseType = new ExpenseType();
            ParseUtil.parseObject(expenseTypeEntity, expenseType);
            expenseTypeEntity.resetChildren();
            if (expenseTypeEntity.getChildren() != null) {
                expenseType.setChildren(ExpenseTypeModelEntityMapper.transform(expenseTypeEntity.getChildren()));
            }
            if (expenseTypeEntity.getSuppliers() != null) {
                expenseType.setSuppliers(SupplierModelEntityMapper.transform(expenseTypeEntity.getSuppliers()));
            }
        }
        return expenseType;
    }

    public static List<ExpenseType> transform(List<ExpenseTypeEntity> expenseTypeEntityList) {
        final List<ExpenseType> expenseTypeList;
        if (expenseTypeEntityList != null && !expenseTypeEntityList.isEmpty()) {
            expenseTypeList = new ArrayList<>(expenseTypeEntityList.size());
            for (ExpenseTypeEntity expenseTypeEntity : expenseTypeEntityList) {
                final ExpenseType expenseType = transform(expenseTypeEntity);
                if (expenseType != null) {
                    expenseTypeList.add(expenseType);
                }
            }
        } else {
            expenseTypeList = new ArrayList<>();
        }
        return expenseTypeList;
    }
    
}
