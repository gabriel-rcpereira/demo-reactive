package com.grcp.reactive.order.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVo {

    private List<ProductOrderVo> products;
}
