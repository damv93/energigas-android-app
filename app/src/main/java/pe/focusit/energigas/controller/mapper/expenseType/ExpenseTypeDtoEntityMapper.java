package pe.focusit.energigas.controller.mapper.expenseType;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.ExpenseTypeEntity;
import pe.focusit.energigas.data.net.dto.ExpenseTypeDto;
import pe.focusit.energigas.util.ParseUtil;

public class ExpenseTypeDtoEntityMapper {

    public static ExpenseTypeEntity transform(ExpenseTypeDto expenseTypeDto) {
        ExpenseTypeEntity expenseTypeEntity = null;
        if (expenseTypeDto != null) {
            expenseTypeEntity = new ExpenseTypeEntity();
            ParseUtil.parseObject(expenseTypeDto, expenseTypeEntity);
        }
        return expenseTypeEntity;
    }

    public static List<ExpenseTypeEntity> transform(List<ExpenseTypeDto> expenseTypeDtoList) {
        final List<ExpenseTypeEntity> expenseTypeEntityList;

        if (expenseTypeDtoList != null && !expenseTypeDtoList.isEmpty()) {
            expenseTypeEntityList = new ArrayList<>(expenseTypeDtoList.size());
            for (ExpenseTypeDto expenseTypeDto : expenseTypeDtoList) {
                final ExpenseTypeEntity expenseTypeEntity = transform(expenseTypeDto);
                if (expenseTypeEntity != null) {
                    expenseTypeEntityList.add(expenseTypeEntity);
                }
            }
        } else {
            expenseTypeEntityList = new ArrayList<>();
        }
        return expenseTypeEntityList;
    }
    
}
