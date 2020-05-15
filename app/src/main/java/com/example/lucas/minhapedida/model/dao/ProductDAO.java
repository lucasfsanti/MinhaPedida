package com.example.lucas.minhapedida.model.dao;

import android.content.Context;

import com.example.lucas.minhapedida.model.helpers.DaoHelper;
import com.example.lucas.minhapedida.model.vo.Product;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductDAO extends DaoHelper<Product> {

    public ProductDAO(Context c) {
        super(c, Product.class);
    }

    public List<Product> listAllActive() throws SQLException {
        return getDao().queryForEq("isActive", true);
    }

}
