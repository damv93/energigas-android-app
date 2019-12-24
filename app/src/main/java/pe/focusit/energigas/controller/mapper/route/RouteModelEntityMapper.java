package pe.focusit.energigas.controller.mapper.route;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pe.focusit.energigas.controller.mapper.vehicle.VehicleModelEntityMapper;
import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.data.entity.ExpenseEntityDao;
import pe.focusit.energigas.data.entity.RouteEntity;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.controller.mapper.routeSegment.RouteSegmentModelEntityMapper;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.ParseUtil;

public class RouteModelEntityMapper {

    public static Route transform(RouteEntity routeEntity, @Nullable ExpenseEntityDao expenseEntityDao) {
        Route route = null;
        if (routeEntity != null) {
            route = new Route();
            ParseUtil.parseObject(routeEntity, route);
            if (routeEntity.getVehicle() != null) {
                route.setVehicle(VehicleModelEntityMapper.transform(routeEntity.getVehicle()));
            }
            routeEntity.resetRouteSegments();
            if (routeEntity.getRouteSegments() != null) {
                route.setRouteSegments(RouteSegmentModelEntityMapper.transform(routeEntity.getRouteSegments()));
                if (expenseEntityDao != null)
                    setRouteBudgetBalance(route, expenseEntityDao);
            }
        }
        return route;
    }

    private static void setRouteBudgetBalance(Route route, @NonNull ExpenseEntityDao expenseEntityDao) {
        if (route.getRouteSegments() == null)
            return;
        double expensesTotalAmount = 0;
        for (RouteSegment segment : route.getRouteSegments()) {
            List<ExpenseEntity> expenseEntityList = expenseEntityDao.queryBuilder()
                    .where(ExpenseEntityDao.Properties.RouteSegmentId.eq(segment.getId()))
                    .list();
            if (expenseEntityList == null)
                continue;
            for (ExpenseEntity expenseEntity : expenseEntityList) {
                if (expenseEntity.getAmount() != null && expenseEntity.getExpenseType() != null &&
                        expenseEntity.getExpenseType().getType() == Constants.FUND_TYPE)
                    expensesTotalAmount += expenseEntity.getAmount();
            }
        }
        route.setBudgetBalance(route.getAmountGiven() - expensesTotalAmount);
    }
    
}
