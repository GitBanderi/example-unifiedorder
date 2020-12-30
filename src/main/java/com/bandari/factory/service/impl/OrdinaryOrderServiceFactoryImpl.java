package com.bandari.factory.service.impl;

import cn.hutool.json.JSONUtil;
import com.bandari.constant.Constant;
import com.bandari.factory.service.OrderServiceFactory;
import com.bandari.param.bo.UnifiedConfigOrderBo;
import com.bandari.param.bo.UnifiedPaySuccessOrderBo;
import com.bandari.param.bo.UnifiedRefundSuccessOrderBo;
import com.bandari.param.dto.ConfigOrderDTO;
import com.bandari.param.vo.UnifiedConfigOrderVo;
import com.bandari.param.vo.UnifiedPaySuccessOrderVo;
import com.bandari.param.vo.UnifiedRefundSuccessOrderVo;
import com.bandari.param.vo.UnifiedSubmitOrderVo;
import com.bandari.util.ThreadLocalCache;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * 普通订单服务
 *
 * @author bandari
 */
@Slf4j
@Service("orderServiceFactory1")
public class OrdinaryOrderServiceFactoryImpl implements OrderServiceFactory {

  @Override
  public UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo) {
    log.info("");
    log.info("普通商品进行确认订单操作:{}", JSONUtil.toJsonStr(configOrderBo));
    log.info("校验普通商品是否存在、库存是否充足等");

    //这里用ThreadLocal 代替Redis
    ConfigOrderDTO dto = new ConfigOrderDTO();
    BeanUtils.copyProperties(configOrderBo, dto);
    Map<String, ConfigOrderDTO> configOrderDTOMap = new HashMap<>();
    configOrderDTOMap.put(Constant.CONFIG_ORDER_KEY, dto);
    ThreadLocalCache.set(configOrderDTOMap);

    log.info("====================普通商品确认订单操作完成===============");
    return new UnifiedConfigOrderVo();
  }

  @Override
  public UnifiedSubmitOrderVo unifiedSubmitOrder(ConfigOrderDTO configOrderDTO) {
    log.info("");
    log.info("普通商品提交订单操作:{}", JSONUtil.toJsonStr(configOrderDTO));
    log.info("生成订单及清算信息");

    log.info("====================普通商品提交订单操作完成===============");
    return new UnifiedSubmitOrderVo();
  }

  @Override
  public UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(
      UnifiedPaySuccessOrderBo paySuccessOrderBo) {
    log.info("");
    log.info("普通商品支付成功后的操作:{}", JSONUtil.toJsonStr(paySuccessOrderBo));
    log.info("核销优惠券、积分、");
    log.info("====================普通商品支付成功后的操作完成===============");

    return new UnifiedPaySuccessOrderVo();
  }

  @Override
  public UnifiedRefundSuccessOrderVo unifiedRefundSuccessOrder(
      UnifiedRefundSuccessOrderBo refundSuccessOrderBo) {
    log.info("普通商品退款成功后的操作:{}", JSONUtil.toJsonStr(refundSuccessOrderBo));
    return new UnifiedRefundSuccessOrderVo();
  }
}
