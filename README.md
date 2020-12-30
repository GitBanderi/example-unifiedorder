# 电商项目-统一下单
在介绍这篇文章之前我们先了解一下

##### Q1.什么是统一下单
大家如果做过微信、支付宝支付的话，那一定用过统一下单这个接口。
这里我们以**微信支付**为例

微信支付文档：https://pay.weixin.qq.com/wiki/doc/api/index.html
![image](8C1A92AFC8CF4643A6F30D0B093D6DA7)
在
trade_type参数中我们有很多中选择，不同的参数代表我们不同的场景，不同的场景校验的参数以及链路是不同的，比如在小程序、微信内置支付需要传递OpenId

##### Q2.统一下单解决了什么

提供了一个清晰对外的接口,根据传入不同的参数来决定不同的链路方向以及返回不同的结果

---

## 电商应用场景
在电商项目中，我们也会有统一下单的场景，比如我们有普通商品、新用户专享活动商品、会员用户专享活动商品、而活动商品是基于普通商品转换过来的（可能这里说的比较抽象，给大家上一幅简易的图来解释）
![image](48808E2F95BA44E0AB9C2AEA2B553A29)


t_spu、t_sku表是所有商品的基础信息以及价格（简称为普通商品)


t_activity_prod表是活动商品信息，引入基础表的spu_id、sku_id
。**但活动商品的价格(price)、库存(stock)则需要独立添加，因为在电商项目中，活动商品的价格比普通商品更低，并且库存还有限制**

---
## 不同订单不同走向
普通商品和活动商品订单流程也是不同的，这里以支付回调后的清算为例子（**参考下图**)<br/>
普通商品清算后需要核销使用的优惠券和积分<br/>
活动商品清算则需要核销新人或新会员的专享机会(假设只能购买一次)
![image](E036E237BAA342509C3F21A847FAFDBB)

---


## 代码中如何实现
在电商项目中一个支付成功订单的生成分为三步
【确认订单】、【提交订单并支付】、【订单支付回调】<br/>
废话不多说，我们直接开搞！**前方高能！！！！！前方高能！！！！！前方高能！！！！！**<br/>

```
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
   * @return 统一提交订单出参
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
```
普通订单、新人专享订单、新会员订单重写这个接口<br/>

```
/**
 * 普通订单服务
 * 注意这里的orderServiceFactory1
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
```

```
/**
 * 新人专享订单服务
 * 注意这里的orderServiceFactory2
 * @author bandari
 */
@Slf4j
@Service("orderServiceFactory2")
public class NewPeopleOrderServiceFactoryImpl implements OrderServiceFactory {

  @Override
  public UnifiedConfigOrderVo unifiedConfigOrder(UnifiedConfigOrderBo configOrderBo) {
    log.info("");
    log.info("新人专享商品进行确认订单操作:{}", JSONUtil.toJsonStr(configOrderBo));
    log.info("校验新人专享商品是否存在、库存、新人专享机会等条件是否充足等");

    //这里用ThreadLocal 代替Redis
    ConfigOrderDTO dto = new ConfigOrderDTO();
    BeanUtils.copyProperties(configOrderBo, dto);
    Map<String, ConfigOrderDTO> configOrderDTOMap = new HashMap<>();
    configOrderDTOMap.put(Constant.CONFIG_ORDER_KEY, dto);
    ThreadLocalCache.set(configOrderDTOMap);

    log.info("====================新人专享商品确认订单操作完成===============");
    return new UnifiedConfigOrderVo();
  }

  @Override
  public UnifiedSubmitOrderVo unifiedSubmitOrder(ConfigOrderDTO configOrderDTO) {
    log.info("");
    log.info("新人专享商品提交订单操作:{}", JSONUtil.toJsonStr(configOrderDTO));
    log.info("生成订单及清算信息");

    log.info("====================新人专享商品提交订单操作完成===============");
    return new UnifiedSubmitOrderVo();
  }

  @Override
  public UnifiedPaySuccessOrderVo unifiedPaySuccessOrder(
      UnifiedPaySuccessOrderBo paySuccessOrderBo) {
    log.info("");
    log.info("新人专享商品订单支付成功后的操作");
    log.info("核销优惠券、积分、");
    log.info("核销新人专享机会");
    log.info("====================新人专享商品支付成功操作完成===============");

    return new UnifiedPaySuccessOrderVo();
  }

  @Override
  public UnifiedRefundSuccessOrderVo unifiedRefundSuccessOrder(
      UnifiedRefundSuccessOrderBo refundSuccessOrderBo) {
    log.info("新人专享商品退款成功后的操作:{}", JSONUtil.toJsonStr(refundSuccessOrderBo));
    return new UnifiedRefundSuccessOrderVo();
  }
}
```

```
/**
 * 新会员专享订单服务
 * 注意这里的orderServiceFactory3
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
```
不同订单不同走向，所以我们需要一个ProdType参数来控制方向

```
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
```

## 项目测试案例

```

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
    configOrderBo.setProdType(ProdTypeEnum.NEW_MEMBER.getCode());
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
```
当ProdType为NEW_MEMBER(新会员专享)，订单日志流程如下

```
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 新会员专享商品进行确认订单操作:{"num":1,"prodType":3,"spuId":1,"skuId":2}
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 校验新会员专享商品是否存在、库存、新会员专享机会、是否为会员等条件是否充足等
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : ====================新会员专享商品确认订单操作完成===============
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 新会员专享商品提交订单操作:{"num":1,"prodType":3,"spuId":1,"skuId":2}
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 生成订单及清算信息
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : ====================新会员专享商品提交订单操作完成===============
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 新会员专享商品订单支付成功后的操作
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 核销新会员专享机会
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : 新增会员成长值
 [           main] b.f.s.i.NewMemberOrderServiceFactoryImpl : ====================新会员专享商品支付成功操作完成===============
```
当ProdType为NEW_PEOPLE(新人专享)，订单日志流程如下

```
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 新人专享商品进行确认订单操作:{"num":1,"prodType":2,"spuId":1,"skuId":2}
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 校验新人专享商品是否存在、库存、新人专享机会等条件是否充足等
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : ====================新人专享商品确认订单操作完成===============
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 新人专享商品提交订单操作:{"num":1,"prodType":2,"spuId":1,"skuId":2}
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 生成订单及清算信息
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : ====================新人专享商品提交订单操作完成===============
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 新人专享商品订单支付成功后的操作
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : 核销新人专享机会
[           main] b.f.s.i.NewPeopleOrderServiceFactoryImpl : ====================新人专享商品支付成功操作完成===============
```
当ProdType为ORDINARY(普通商品)，订单日志流程如下

```
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 普通商品进行确认订单操作:{"num":1,"prodType":1,"spuId":1,"skuId":2}
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 校验普通商品是否存在、库存是否充足等
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : ====================普通商品确认订单操作完成===============
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 普通商品提交订单操作:{"num":1,"prodType":1,"spuId":1,"skuId":2}
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 生成订单及清算信息
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : ====================普通商品提交订单操作完成===============
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 普通商品支付成功后的操作:{}
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : 核销优惠券、积分、
[           main] .b.f.s.i.OrdinaryOrderServiceFactoryImpl : ====================普通商品支付成功后的操作完成===============
```


好了，上述就是作者在电商项目中写的【统一下单】流程，并已满足现存业务场景；关于这个流程实现的方法有很多。每个人都有不同的想法和见解。希望这篇文章能给你一些新的启发。<br/>

## 最后
上述代码已经上传至GitHub。有需要的同学去上面看看吧。<br/>
项目案例地址：https://github.com/gitbandari/example-unifiedorder<br/>
后续作者将会更新一期电商优惠券的设计，敬请期待。<br/>
**欢迎各位同学的点赞、收藏、评论**   
