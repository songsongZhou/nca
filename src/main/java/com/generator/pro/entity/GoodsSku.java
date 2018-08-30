package com.generator.pro.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tobi
 * @since 2018-08-27
 */
@TableName("goods_sku")
public class GoodsSku extends Model<GoodsSku> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("goods_id")
    private Integer goodsId;

    @TableField("sku_name")
    private String skuName;

    @TableField("sku_price")
    private BigDecimal skuPrice;

    @TableField("sku_line_price")
    private BigDecimal skuLinePrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品 sku 编码
     */
    @TableField("sku_code")
    private String skuCode;

    @TableField("sku_image")
    private String skuImage;

    @TableField("up_down")
    private Integer upDown;

    /**
     * 逻辑删除：1 正常 0删除
     */
    private Integer del;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public BigDecimal getSkuLinePrice() {
        return skuLinePrice;
    }

    public void setSkuLinePrice(BigDecimal skuLinePrice) {
        this.skuLinePrice = skuLinePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public Integer getUpDown() {
        return upDown;
    }

    public void setUpDown(Integer upDown) {
        this.upDown = upDown;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsSku{" +
        ", id=" + id +
        ", goodsId=" + goodsId +
        ", skuName=" + skuName +
        ", skuPrice=" + skuPrice +
        ", skuLinePrice=" + skuLinePrice +
        ", stock=" + stock +
        ", skuCode=" + skuCode +
        ", skuImage=" + skuImage +
        ", upDown=" + upDown +
        ", del=" + del +
        "}";
    }
}
