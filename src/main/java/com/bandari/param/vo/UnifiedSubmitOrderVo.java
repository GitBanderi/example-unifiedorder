package com.bandari.param.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UnifiedSubmitOrderVo implements Serializable {

  @ApiModelProperty("订单号")
  private String orderNumber;
  
}
