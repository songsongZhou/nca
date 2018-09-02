package com.tobi.nca.utils;

import com.generator.pro.entity.Goods;
import com.generator.pro.entity.GoodsSku;

import java.util.List;

public class GoodsVo {

    private GoodsVoBean goodsVo;

    public GoodsVoBean getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVoBean goodsVo) {
        this.goodsVo = goodsVo;
    }

    public static class GoodsVoBean {
        private Goods goods;

        private List<GoodsSku> goodsSkus;

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public List<GoodsSku> getGoodsSkus() {
            return goodsSkus;
        }

        public void setGoodsSkus(List<GoodsSku> goodsSkus) {
            this.goodsSkus = goodsSkus;
        }
    }

}
