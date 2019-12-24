package pe.focusit.energigas.controller.mapper.productDispatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pe.focusit.energigas.data.entity.ProductDispatchEntity;
import pe.focusit.energigas.data.net.dto.ProductDispatchDto;
import pe.focusit.energigas.util.Base64Util;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.util.ParseUtil;

public class ProductDispatchDtoEntityMapper {

    private static String TAG = ProductDispatchDtoEntityMapper.class.getSimpleName();

    public static ProductDispatchDto transform(ProductDispatchEntity productDispatchEntity) {
        ProductDispatchDto productDispatchDto = null;
        if (productDispatchEntity != null) {
            productDispatchDto = new ProductDispatchDto();
            ParseUtil.parseObject(productDispatchEntity, productDispatchDto);
            if (productDispatchEntity.getPhotoFilePath() != null) {
                File photoFile = new File(productDispatchEntity.getPhotoFilePath());
                productDispatchDto.setPhotoFileName(photoFile.getName());
                try {
                    productDispatchDto.setPhotoFile(Base64Util.encodeImageToBase64(productDispatchEntity.getPhotoFilePath()));
                } catch (IOException e) {
                    LogUtil.e(TAG, e.getMessage());
                }
            }
        }
        return productDispatchDto;
    }

    public static List<ProductDispatchDto> transform(List<ProductDispatchEntity> productDispatchEntityList) {
        final List<ProductDispatchDto> productDispatchDtoList;
        if (productDispatchEntityList != null && !productDispatchEntityList.isEmpty()) {
            productDispatchDtoList = new ArrayList<>(productDispatchEntityList.size());
            for (ProductDispatchEntity productDispatchEntity : productDispatchEntityList) {
                final ProductDispatchDto productDispatchDto = transform(productDispatchEntity);
                if (productDispatchDto != null) {
                    productDispatchDtoList.add(productDispatchDto);
                }
            }
        } else {
            productDispatchDtoList = new ArrayList<>();
        }
        return productDispatchDtoList;
    }
    
}
