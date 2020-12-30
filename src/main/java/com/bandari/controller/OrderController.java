package com.bandari.controller;


import com.bandari.param.bo.UnifiedConfigOrderBo;
import com.bandari.param.bo.UnifiedPaySuccessOrderBo;
import com.bandari.param.vo.UnifiedConfigOrderVo;
import com.bandari.param.vo.UnifiedPaySuccessOrderVo;
import com.bandari.param.vo.UnifiedSubmitOrderVo;
import com.bandari.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单控制器
 * </p>
 *
 * @author bandari
 * @since 2020-12-30
 */
@Api(tags = "订单服务")
@RestController
@RequestMapping("/order/")
public class OrderController {

  @Autowired
  private OrderService orderService;

  /**
   * 统一确认订单
   *
   * @param configOrderBo 统一确认订单入参
   * @return 统一确认订单出参
   */
  @PostMapping("/v1/unifiedConfigOrder")
  public UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo) {
    return orderService.unifiedConfigOrder(configOrderBo);
  }

  /**
   * 统一提交订单
   *
   * @return 统一提交订单出参
   */
  @PostMapping("/v1/unifiedSubmitOrder")
  public UnifiedSubmitOrderVo unifiedSubmitOrder() {
    return orderService.unifiedSubmitOrder();
  }


  /**
   * 微信/支付宝通知订单支付成功
   *
   * @return 微信/支付宝通知订单支付成功
   */
  @PostMapping("/v1/unifiedPaySuccessOrder")
  public UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(
      UnifiedPaySuccessOrderBo paySuccessOrderBo) {
    return orderService
        .unifiedPaySuccessOrder(paySuccessOrderBo);
  }


}

