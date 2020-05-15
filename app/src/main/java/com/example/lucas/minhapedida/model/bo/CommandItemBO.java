package com.example.lucas.minhapedida.model.bo;

import com.example.lucas.minhapedida.model.vo.CommandItem;
import com.example.lucas.minhapedida.model.vo.Product;

public class CommandItemBO {

    public static boolean isValidCommandItem(CommandItem ci) {
        if (!isValidQuantity(ci.getQuantity())) {
            return false;
        }

        if (!isValidSubValue(ci.getSubValue())) {
            return false;
        }

        if (!isValidProduct(ci.getProduct())) {
            return false;
        }

        return true;
    }

    public static boolean isValidQuantity(Integer q) {

        return q == null ? false : q < 1 || q > 99 ? false : true;
    }

    public static boolean isValidSubValue(Double v) {
        return v == null ? false : v < 0 ? false : true;
    }

    public static boolean isValidProduct(Product p) {
        return p != null;
    }

    public static Double calcSubValue(Product p, Integer q) {
        return p.getPrice() * q;
    }

}
