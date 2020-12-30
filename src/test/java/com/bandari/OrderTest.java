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
    UnifiedConfigOrderBo configOrderBo = new UnifiedConfigOrderBo();
    configOrderBo.setNum(1);
    configOrderBo.setSpuId(1L);
    configOrderBo.setSkuId(2L);
    configOrderBo.setProdType(ProdTypeEnum.NEW_MEMBER.getCode());
    orderController.unifiedConfigOrder(configOrderBo);
  }

  @Test
  public void submitOrder() {
    orderController.unifiedSubmitOrder();
  }

  @AfterEach
  public void paySuccessOrder() {
    orderController
        .unifiedPaySuccessOrder(new UnifiedPaySuccessOrderBo());
  }


}
