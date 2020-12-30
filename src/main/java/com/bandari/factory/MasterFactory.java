package com.bandari.factory;

import cn.hutool.core.util.ObjectUtil;
import com.bandari.factory.service.OrderServiceFactory;
import java.io.Serializable;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 工厂服务,可抽离出一个接口(此处省略)
 */
@Component
public class MasterFactory implements Serializable {

  @Autowired
  private Map<String, OrderServiceFactory> orderServiceMap;

  /**
   * 获取商品对应的订单服务
   *
   * @param prodType 商品类型
   * @return 返回对应的订单服务
   */
  @SneakyThrows
  public OrderServiceFactory getOrderService(Integer prodType) {
    OrderServiceFactory orderServiceFactory = orderServiceMap.get("orderServiceFactory" + prodType);
    if (ObjectUtil.isNull(orderServiceFactory)) {
      throw new Exception("订单服务不存在");
    }
    return orderServiceFactory;
  }


}
