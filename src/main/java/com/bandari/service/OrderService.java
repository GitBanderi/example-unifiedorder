package com.bandari.service;

import com.bandari.param.bo.UnifiedConfigOrderBo;
import com.bandari.param.bo.UnifiedPaySuccessOrderBo;
import com.bandari.param.vo.UnifiedConfigOrderVo;
import com.bandari.param.vo.UnifiedPaySuccessOrderVo;
import com.bandari.param.vo.UnifiedSubmitOrderVo;

/**
 * <p>
 * 平台推送到仓库的商品明细临时表 服务类
 * </p>
 *
 * @author bandari
 * @since 2020-12-28
 */
public interface OrderService {

  /**
   * 统一确认订单(以用户为单位缓存到Redis中,这里以ThreadLocal代替)
   *
   * @param configOrderBo 统一确认订单入参
   * @return 统一确认订单出参
   */
  UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo);

  /**
   * 统一提交订单(生成订单信息及清算信息)
   *
   * @return 统一提交订单出参
   */
  UnifiedSubmitOrderVo unifiedSubmitOrder();

  /**
   * 微信/支付宝通知订单支付成功
   *
   * @param paySuccessOrderBo 通知入参
   */
  UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(UnifiedPaySuccessOrderBo paySuccessOrderBo);
}
