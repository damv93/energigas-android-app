package pe.focusit.energigas.controller.mapper.deposit;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.DepositEntity;
import pe.focusit.energigas.model.Deposit;
import pe.focusit.energigas.util.ParseUtil;

public class DepositModelEntityMapper {

    public static Deposit transform(DepositEntity depositEntity) {
        Deposit deposit = null;
        if (depositEntity != null) {
            deposit = new Deposit();
            ParseUtil.parseObject(depositEntity, deposit);
        }
        return deposit;
    }

    public static List<Deposit> transform(List<DepositEntity> depositEntityList) {
        final List<Deposit> depositList;
        if (depositEntityList != null && !depositEntityList.isEmpty()) {
            depositList = new ArrayList<>(depositEntityList.size());
            for (DepositEntity depositEntity : depositEntityList) {
                final Deposit deposit = transform(depositEntity);
                if (deposit != null) {
                    depositList.add(deposit);
                }
            }
        } else {
            depositList = new ArrayList<>();
        }
        return depositList;
    }
    
}
