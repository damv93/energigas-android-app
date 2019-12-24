package pe.focusit.energigas.controller.mapper.expense;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.controller.mapper.expenseType.ExpenseTypeModelEntityMapper;
import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.model.Expense;
import pe.focusit.energigas.util.ParseUtil;

public class ExpenseModelEntityMapper {

    public static ExpenseEntity transform(Expense expense) {
        ExpenseEntity expenseEntity = null;
        if (expense != null) {
            expenseEntity = new ExpenseEntity();
            ParseUtil.parseObject(expense, expenseEntity);
        }
        return expenseEntity;
    }

    public static Expense transform(ExpenseEntity expenseEntity) {
        Expense expense = null;
        if (expenseEntity != null) {
            expense = new Expense();
            ParseUtil.parseObject(expenseEntity, expense);
            if (expenseEntity.getExpenseType() != null) {
                expense.setExpenseType(
                        ExpenseTypeModelEntityMapper.transform(expenseEntity.getExpenseType()));
            }
            if (expenseEntity.getExpenseSubType() != null) {
                expense.setExpenseSubType(
                        ExpenseTypeModelEntityMapper.transform(expenseEntity.getExpenseSubType()));
            }
        }
        return expense;
    }

    public static List<Expense> transform(List<ExpenseEntity> expenseEntityList) {
        final List<Expense> expenseList;
        if (expenseEntityList != null && !expenseEntityList.isEmpty()) {
            expenseList = new ArrayList<>(expenseEntityList.size());
            for (ExpenseEntity expenseEntity : expenseEntityList) {
                final Expense expense = transform(expenseEntity);
                if (expense != null) {
                    expenseList.add(expense);
                }
            }
        } else {
            expenseList = new ArrayList<>();
        }
        return expenseList;
    }
    
}
