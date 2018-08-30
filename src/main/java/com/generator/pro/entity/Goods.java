package com.generator.pro.entity;

import java.math.BigDecimal;
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
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类id
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 商品副标题
     */
    @TableField("goods_sub_name")
    private String goodsSubName;

    /**
     * 商品图片
     */
    @TableField("goods_image")
    private String goodsImage;

    /**
     * 价钱
     */
    private BigDecimal price;

    @TableField("line_price")
    private BigDecimal linePrice;

    @TableField("goods_detail")
    private String goodsDetail;

    /**
     * 上下架：1 上架  0 下架
     */
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSubName() {
        return goodsSubName;
    }

    public void setGoodsSubName(String goodsSubName) {
        this.goodsSubName = goodsSubName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(BigDecimal linePrice) {
        this.linePrice = linePrice;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
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
        return "Goods{" +
        ", id=" + id +
        ", categoryId=" + categoryId +
        ", goodsName=" + goodsName +
        ", goodsSubName=" + goodsSubName +
        ", goodsImage=" + goodsImage +
        ", price=" + price +
        ", linePrice=" + linePrice +
        ", goodsDetail=" + goodsDetail +
        ", upDown=" + upDown +
        ", del=" + del +
        "}";
    }
}
