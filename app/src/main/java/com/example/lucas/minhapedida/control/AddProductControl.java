package com.example.lucas.minhapedida.control;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucas.minhapedida.R;
import com.example.lucas.minhapedida.model.bo.CommandItemBO;
import com.example.lucas.minhapedida.model.dao.CommandItemDAO;
import com.example.lucas.minhapedida.model.dao.ProductDAO;
import com.example.lucas.minhapedida.model.vo.CommandItem;
import com.example.lucas.minhapedida.model.vo.Product;
import com.example.lucas.minhapedida.view.ActivityAddProduct;

import java.sql.SQLException;

public class AddProductControl extends AppCompatActivity {

    private ActivityAddProduct activity;
    private ProductDAO dao;
    private CommandItem commandItem;

    private ArrayAdapter<Product> produtos;

    public AddProductControl(ActivityAddProduct activity) {
        this.activity = activity;
        dao = new ProductDAO(this.activity);
        configSpinner();
    }

    private void configSpinner(){
        try {
            dao.getDao().createIfNotExists(new Product(1, "Refrigerante", 3.00, true));
            dao.getDao().createIfNotExists(new Product(2, "Cerveja", 5.00, true));
            dao.getDao().createIfNotExists(new Product(3, "Batata frita", 10.00, true));
            dao.getDao().createIfNotExists(new Product(4, "Água", 2.50, true));
            dao.getDao().createIfNotExists(new Product(5, "Pastel", 3.50, true));
            dao.getDao().createIfNotExists(new Product(6, "Petiscos", 6.00, true));

            produtos = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_item,
                    dao.listAllActive()
            );
            activity.getProductsSpinner().setAdapter(produtos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CommandItem getCommandItem() {
        Product product = (Product) activity.getProductsSpinner().getSelectedItem();
        int quantity = activity.getNpQuantity().getValue();

        CommandItem item = new CommandItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSubValue(CommandItemBO.calcSubValue(product, quantity));

        if (!CommandItemBO.isValidCommandItem(item)) {
            Toast.makeText(activity, activity.getString(R.string.warn_invalid_command_item), Toast.LENGTH_SHORT).show();
            return null;
        }

        return item;
    }

    public void sendItem(CommandItem item) {
        Intent intent=new Intent();
        intent.putExtra("CommandItem",item);
        setResult(RESULT_OK,intent);
        activity.finish();
    }
}