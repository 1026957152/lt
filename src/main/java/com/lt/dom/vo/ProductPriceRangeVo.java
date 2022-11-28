package com.lt.dom.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductPriceRangeVo {

    private ProductPriceVo from;
    private ProductPriceVo to;

    public ProductPriceVo getFrom() {
        return from;
    }

    public void setFrom(ProductPriceVo from) {
        this.from = from;
    }

    public ProductPriceVo getTo() {
        return to;
    }

    public void setTo(ProductPriceVo to) {
        this.to = to;
    }

    public static class ProductPriceVo {

        private Integer original; //The original price for this product which will be the same or higher than the sale amount. Use this to show a discount has been applied e.g. $10 $8.50
        private Integer retail; //The sale price you should charge your customers.


        public Integer getOriginal() {
            return original;
        }

        public void setOriginal(Integer original) {
            this.original = original;
        }

        public Integer getRetail() {
            return retail;
        }

        public void setRetail(Integer retail) {
            this.retail = retail;
        }
    }


}
