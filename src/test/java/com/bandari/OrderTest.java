package com.bandari;

import com.bandari.controller.OrderController;
import com.bandari.enums.ProdTypeEnum;
import com.bandari.param.bo.UnifiedConfigOrderBo;
import com.bandari.param.bo.UnifiedPaySuccessOrderBo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest
@Slf4j
@WebAppConfiguration
class OrderTest {

  @Autowired
  private OrderController orderController;

  @BeforeEach
  public void configOrder() {
    //模拟确认订单
    UnifiedConfigOrderBo configOrderBo = new UnifiedConfigOrderBo();
    configOrderBo.setNum(1);
    configOrderBo.setSpuId(1L);
    configOrderBo.setSkuId(2L);
    //NEW_MEMBER:新会员专享商品 NEW_PEOPLE:新人专享商品 ORDINARY:普通商品
    configOrderBo.setProdType(ProdTypeEnum.ORDINARY.getCode());
    orderController.unifiedConfigOrder(configOrderBo);
  }

  @Test
  public void submitOrder() {
    //模拟提交订单
    orderController.unifiedSubmitOrder();
  }

  @AfterEach
  public void paySuccessOrder() {
    //模拟订单支付成功
    orderController
        .unifiedPaySuccessOrder(new UnifiedPaySuccessOrderBo());
  }


}
