package pe.focusit.energigas.controller.mapper.productDispatch;

import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.ProductDispatchEntity;
import pe.focusit.energigas.model.ProductDispatch;
import pe.focusit.energigas.util.ParseUtil;

public class ProductDispatchModelEntityMapper {

    public static ProductDispatchEntity transform(ProductDispatch productDispatch) {
        ProductDispatchEntity productDispatchEntity = null;
        if (productDispatch != null) {
            productDispatchEntity = new ProductDispatchEntity();
            ParseUtil.parseObject(productDispatch, productDispatchEntity);
        }
        return productDispatchEntity;
    }

    public static ProductDispatch transform(ProductDispatchEntity productDispatchEntity) {
        ProductDispatch productDispatch = null;
        if (productDispatchEntity != null) {
            productDispatch = new ProductDispatch();
            ParseUtil.parseObject(productDispatchEntity, productDispatch);
            /*if (productDispatchEntity.getProductDispatchType() != null) {
                productDispatch.setProductDispatchType(
                        ProductDispatchTypeModelEntityMapper.transform(productDispatchEntity.getProductDispatchType()));
            }*/
        }
        return productDispatch;
    }

    public static List<ProductDispatch> transform(List<ProductDispatchEntity> productDispatchEntityList) {
        final List<ProductDispatch> productDispatchList;
        if (productDispatchEntityList != null && !productDispatchEntityList.isEmpty()) {
            productDispatchList = new ArrayList<>(productDispatchEntityList.size());
            for (ProductDispatchEntity productDispatchEntity : productDispatchEntityList) {
                final ProductDispatch productDispatch = transform(productDispatchEntity);
                if (productDispatch != null) {
                    productDispatchList.add(productDispatch);
                }
            }
        } else {
            productDispatchList = new ArrayList<>();
        }
        return productDispatchList;
    }
    
}
