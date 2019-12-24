package pe.focusit.energigas.controller.mapper.expense;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.data.net.dto.ExpenseDto;
import pe.focusit.energigas.util.Base64Util;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.util.ParseUtil;

public class ExpenseDtoEntityMapper {

    private static String TAG = ExpenseDtoEntityMapper.class.getSimpleName();

    public static ExpenseDto transform(ExpenseEntity expenseEntity) {
        ExpenseDto expenseDto = null;
        if (expenseEntity != null) {
            expenseDto = new ExpenseDto();
            ParseUtil.parseObject(expenseEntity, expenseDto);
            if (expenseEntity.getExpenseType() != null) {
                expenseDto.setType(expenseEntity.getExpenseType().getType());
            }
            if (expenseEntity.getPhotoFilePath() != null) {
                File photoFile = new File(expenseEntity.getPhotoFilePath());
                expenseDto.setPhotoFileName(photoFile.getName());
                try {
                    expenseDto.setPhotoFile(Base64Util.encodeImageToBase64(expenseEntity.getPhotoFilePath()));
                } catch (IOException e) {
                    LogUtil.e(TAG, e.getMessage());
                }
            }
        }
        return expenseDto;
    }

    public static List<ExpenseDto> transform(List<ExpenseEntity> expenseEntityList) {
        final List<ExpenseDto> expenseDtoList;
        if (expenseEntityList != null && !expenseEntityList.isEmpty()) {
            expenseDtoList = new ArrayList<>(expenseEntityList.size());
            for (ExpenseEntity expenseEntity : expenseEntityList) {
                final ExpenseDto expenseDto = transform(expenseEntity);
                if (expenseDto != null) {
                    expenseDtoList.add(expenseDto);
                }
            }
        } else {
            expenseDtoList = new ArrayList<>();
        }
        return expenseDtoList;
    }

}
