package com.example.lucas.minhapedida.model.bo;

import com.example.lucas.minhapedida.model.vo.Product;

public class ProductBO {

    public static boolean isValidProduct(Product p) {
        if (!isValidName(p.getName())) {
            return false;
        }

        if (!isValidPrice(p.getPrice())) {
            return false;
        }
        return true;
    }

    public static boolean isValidPrice(Double price) {
        return price == null ? false : price < 0 || price > 9999 ? false : true;
    }

    public static boolean isValidName(String name) {
        return name == null ? false : name.trim().isEmpty() ? false : true;
    }

}
