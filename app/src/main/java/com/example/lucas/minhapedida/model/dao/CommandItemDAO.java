package com.example.lucas.minhapedida.model.dao;

import android.content.Context;

import com.example.lucas.minhapedida.model.helpers.DaoHelper;
import com.example.lucas.minhapedida.model.vo.CommandItem;
import com.j256.ormlite.stmt.QueryBuilder;

public class CommandItemDAO extends DaoHelper {

    public CommandItemDAO(Context c) {
        super(c, CommandItem.class);
    }

}
