package com.example.lucas.minhapedida.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucas.minhapedida.R;
import com.example.lucas.minhapedida.control.ProductsControl;


public class ActivityProducts extends AppCompatActivity {

    private EditText productName;
    private EditText price;

    private ListView products;

    private Button cancel;
    private Button save;

    private ProductsControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        initialize();
        control = new ProductsControl(this);
    }

    private void initialize() {
        productName = findViewById(R.id.editProductName);
        price = findViewById(R.id.editPrice);

        products = findViewById(R.id.lvProducts);

        cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save = findViewById(R.id.btnSaveProduct);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = control.saveProduct();
                if (result > 0) {
                    control.clear();
                    control.configListView();
                }
            }
        });
    }

    public EditText getProductName() {
        return productName;
    }

    public EditText getPrice() {
        return price;
    }

    public ListView getProducts() {
        return products;
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getSave() {
        return save;
    }
}
