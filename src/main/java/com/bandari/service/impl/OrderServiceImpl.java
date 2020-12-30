package com.bandari.service.impl;

import com.bandari.constant.Constant;
import com.bandari.factory.MasterFactory;
import com.bandari.param.bo.UnifiedConfigOrderBo;
import com.bandari.param.bo.UnifiedPaySuccessOrderBo;
import com.bandari.param.dto.ConfigOrderDTO;
import com.bandari.param.vo.UnifiedConfigOrderVo;
import com.bandari.param.vo.UnifiedPaySuccessOrderVo;
import com.bandari.param.vo.UnifiedSubmitOrderVo;
import com.bandari.service.OrderService;
import com.bandari.util.CacheObject;
import com.bandari.util.ThreadLocalCache;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品订单实现服务
 * </p>
 *
 * @author bandari
 * @since 2020-12-28
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private MasterFactory masterFactory;

  @Override
  public UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo) {
    return masterFactory
        .getOrderService(configOrderBo.getProdType()).unifiedConfigOrder(configOrderBo);
  }

  @Override
  public UnifiedSubmitOrderVo unifiedSubmitOrder() {

    Map<String, ? extends CacheObject> stringMap = ThreadLocalCache.get();
    ConfigOrderDTO dto = (ConfigOrderDTO) stringMap.get(Constant.CONFIG_ORDER_KEY);
    return masterFactory
        .getOrderService(dto.getProdType()).unifiedSubmitOrder(dto);
  }

  @Override
  public UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(
      UnifiedPaySuccessOrderBo paySuccessOrderBo) {
    //根据微信/支付宝返回的payNo找到系统内的订单号及商品类型prodType
    //这里只做演示(所以还是用ConfigOrderDTO中的缓存信息)
    Map<String, ? extends CacheObject> stringMap = ThreadLocalCache.get();
    ConfigOrderDTO dto = (ConfigOrderDTO) stringMap.get(Constant.CONFIG_ORDER_KEY);
    masterFactory.getOrderService(dto.getProdType())
        .unifiedPaySuccessOrder(new UnifiedPaySuccessOrderBo());
    return null;
  }


}
