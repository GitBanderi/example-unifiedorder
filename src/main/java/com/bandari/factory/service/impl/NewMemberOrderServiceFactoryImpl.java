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
 * 新会员专享订单服务
 *
 * @author bandari
 */
@Slf4j
@Service("orderServiceFactory3")
public class NewMemberOrderServiceFactoryImpl implements OrderServiceFactory {

  @Override
  public UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo) {
    log.info("");
    log.info("新会员专享商品进行确认订单操作:{}", JSONUtil.toJsonStr(configOrderBo));
    log.info("校验新会员专享商品是否存在、库存、新会员专享机会、是否为会员等条件是否充足等");

    //这里用ThreadLocal 代替Redis
    ConfigOrderDTO dto = new ConfigOrderDTO();
    BeanUtils.copyProperties(configOrderBo, dto);
    Map<String, ConfigOrderDTO> configOrderDTOMap = new HashMap<>();
    configOrderDTOMap.put(Constant.CONFIG_ORDER_KEY, dto);
    ThreadLocalCache.set(configOrderDTOMap);

    log.info("====================新会员专享商品确认订单操作完成===============");
    return new UnifiedConfigOrderVo();
  }

  @Override
  public UnifiedSubmitOrderVo unifiedSubmitOrder(ConfigOrderDTO configOrderDTO) {
    log.info("");
    log.info("新会员专享商品提交订单操作:{}", JSONUtil.toJsonStr(configOrderDTO));
    log.info("生成订单及清算信息");

    log.info("====================新会员专享商品提交订单操作完成===============");
    return new UnifiedSubmitOrderVo();
  }

  @Override
  public UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(
      UnifiedPaySuccessOrderBo paySuccessOrderBo) {
    log.info("");
    log.info("新会员专享商品订单支付成功后的操作");
    log.info("核销优惠券、积分、");
    log.info("核销新会员专享机会");
    log.info("新增会员成长值");
    log.info("====================新会员专享商品支付成功操作完成===============");

    return new UnifiedPaySuccessOrderVo();
  }

  @Override
  public UnifiedRefundSuccessOrderVo unifiedRefundSuccessOrder(
      UnifiedRefundSuccessOrderBo refundSuccessOrderBo) {
    log.info("新会员专享商品退款成功后的操作:{}", JSONUtil.toJsonStr(refundSuccessOrderBo));
    return new UnifiedRefundSuccessOrderVo();
  }
}
