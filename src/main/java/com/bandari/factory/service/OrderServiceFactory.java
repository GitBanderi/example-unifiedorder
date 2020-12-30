package com.bandari.factory.service;

import com.bandari.param.bo.UnifiedConfigOrderBo;
import com.bandari.param.bo.UnifiedPaySuccessOrderBo;
import com.bandari.param.bo.UnifiedRefundSuccessOrderBo;
import com.bandari.param.dto.ConfigOrderDTO;
import com.bandari.param.vo.UnifiedConfigOrderVo;
import com.bandari.param.vo.UnifiedPaySuccessOrderVo;
import com.bandari.param.vo.UnifiedRefundSuccessOrderVo;
import com.bandari.param.vo.UnifiedSubmitOrderVo;

/**
 * 统一订单支付服务 2020年12月30日 13点49分
 *
 * @author bandari
 */
public interface OrderServiceFactory {

  /**
   * 统一确认订单
   *
   * @param configOrderBo 统一确认订单入参
   * @return 统一确认订单出参
   */
  UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo);

  /**
   * 统一提交订单
   *
   * @param submitOrderBo 统一提交订单入参
   * @return 统一提交订单出餐
   */
  UnifiedSubmitOrderVo unifiedSubmitOrder(ConfigOrderDTO configOrderDTO);

  /**
   * 统一订单支付成功后的事件处理
   *
   * @param paySuccessOrderBo 支付成功订单入参
   * @return 支付成功订单出参
   */
  UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(UnifiedPaySuccessOrderBo paySuccessOrderBo);

  /**
   * 统一订单退款成功后的事件处理
   *
   * @param refundSuccessOrderBo 退款成功订单入参
   * @return 退款成功订单出参
   */
  UnifiedRefundSuccessOrderVo unifiedRefundSuccessOrder(
      UnifiedRefundSuccessOrderBo refundSuccessOrderBo);

}
