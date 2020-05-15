package com.example.lucas.minhapedida.model.vo;

import com.example.lucas.minhapedida.utils.Constants;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class CommandItem implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private Integer quantity;

    @DatabaseField(canBeNull = false)
    private Double subValue;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = Constants.PRODUCT_ID_FIELD_NAME)
    private Product product;

    public CommandItem() {
    }

    public CommandItem(Integer id, Integer quantity, Double subValue, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.subValue = subValue;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSubValue() {
        return subValue;
    }

    public void setSubValue(Double subValue) {
        this.subValue = subValue;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return this.product.getName() + " - R$ " + this.product.getPrice() + " - x" + this.getQuantity() + " -  R$ " + String.format("%.2f", this.getSubValue());
    }
}
