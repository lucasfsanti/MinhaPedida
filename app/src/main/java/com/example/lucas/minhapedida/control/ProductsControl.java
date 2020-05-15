package com.example.lucas.minhapedida.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucas.minhapedida.R;
import com.example.lucas.minhapedida.model.bo.ProductBO;
import com.example.lucas.minhapedida.model.dao.ProductDAO;
import com.example.lucas.minhapedida.model.vo.Product;
import com.example.lucas.minhapedida.view.ActivityProducts;

import java.sql.SQLException;
import java.util.List;

public class ProductsControl extends AppCompatActivity {

    private ActivityProducts activity;

    private ProductDAO dao;

    private List<Product> products;
    private ArrayAdapter<Product> productsAdapter;

    private Product product;

    public ProductsControl(ActivityProducts activity) {
        this.activity = activity;
        dao = new ProductDAO(this.activity);
        configListView();
    }

    public void configListView() {
        try {
            products = dao.listAllActive();
            productsAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, products);
            activity.getProducts().setAdapter(productsAdapter);
        } catch (SQLException e) {
            Toast.makeText(activity, activity.getString(R.string.error_listing_all_active_products), Toast.LENGTH_SHORT).show();
        }

        setOnShortClickListener();
        setOnLongClickListener();
    }

    private void setOnShortClickListener() {
        activity.getProducts().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product = productsAdapter.getItem(position);

                activity.getProductName().setText(product.getName());
                activity.getPrice().setText(product.getPrice().toString());
            }
        });
    }

    private void setOnLongClickListener() {
        activity.getProducts().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                product = productsAdapter.getItem(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setTitle(activity.getString(R.string.confirm_delete_product));
                alert.setMessage(activity.getString(R.string.you_wish_to_delete) + product.getName() + "?");
                alert.setIcon(android.R.drawable.ic_menu_delete);
                alert.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
                alert.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProdoct(product);
                        productsAdapter.remove(product);
                    }
                });
                alert.show();
                return true;
            }
        });
    }

    private void deleteProdoct(Product product) {
        product.setActive(false);
        try {
            dao.getDao().update(product);
        } catch (SQLException e) {
            Toast.makeText(activity, activity.getString(R.string.error_deleting_product), Toast.LENGTH_SHORT).show();
        }
    }


    public int saveProduct() {
        if (!validateForm()) {
            return 0;
        }

        String name = activity.getProductName().getText().toString();
        Double price = Double.parseDouble(activity.getPrice().getText().toString());

        if (product == null) {
            product = new Product();
        }
        product.setName(name);
        product.setPrice(price);
        product.setActive(true);

        try {
            int result = dao.getDao().createOrUpdate(product).getNumLinesChanged();
            return result;
        } catch (SQLException e) {
            Toast.makeText(activity, activity.getString(R.string.error_saving_product), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public boolean validateForm() {
        boolean isValid = true;
        String name = activity.getProductName().getText().toString();
        String priceText = activity.getPrice().getText().toString();
        if (priceText != null && !priceText.trim().isEmpty()) {
            Double price = new Double(priceText);
            if (!ProductBO.isValidPrice(price)) {
                activity.getPrice().setError(activity.getString(R.string.invalid_product_price));
                isValid = false;
            }
        } else {
            activity.getPrice().setError(activity.getString(R.string.invalid_product_price));
            isValid = false;
        }
        if (!ProductBO.isValidName(name)) {
            activity.getProductName().setError(activity.getString(R.string.invalid_product_name));
            isValid = false;
        }

        return isValid;
    }

    public void clear() {
        product = null;
        activity.getProductName().setText("");
        activity.getPrice().setText("");
    }

}
