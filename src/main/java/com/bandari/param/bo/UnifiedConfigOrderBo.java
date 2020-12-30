package com.bandari.param.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnifiedConfigOrderBo implements Serializable {

  @ApiModelProperty("skuId")
  @NotNull(message = "请选择sku")
  private Long skuId;

  @ApiModelProperty("spuId")
  @NotNull(message = "请选择spu")
  private Long spuId;

  @ApiModelProperty("购买数量")
  @NotNull(message = "请选择购买数量")
  @Min(value = 1, message = "购买数量必须大于0")
  private Integer num;

  @ApiModelProperty("商品类型")
  @NotNull(message = "商品类型不能为空")
  private Integer prodType;
}
