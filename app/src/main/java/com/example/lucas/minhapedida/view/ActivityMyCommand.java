package com.example.lucas.minhapedida.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucas.minhapedida.R;
import com.example.lucas.minhapedida.control.MyCommandControl;
import com.example.lucas.minhapedida.model.vo.CommandItem;
import com.example.lucas.minhapedida.utils.Constants;

public class ActivityMyCommand extends AppCompatActivity {

    private TextView total;

    private ListView products;

    private Button addProduct;
    private Button clearList;
    private Button manageProducts;

    private MyCommandControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_command);
        initialize();
        control = new MyCommandControl(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.ADD_PRODUCT_REQUEST && resultCode == RESULT_OK) {
            CommandItem item = (CommandItem) data.getSerializableExtra("CommandItem");
            control.persistCommandItem(item);
            control.attView();
        }
    }

    private void initialize() {
        total = findViewById(R.id.tvTotal);
        products = findViewById(R.id.lvCommandItems);

        addProduct = findViewById(R.id.btnAddProducts);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.goToAddProduct();
            }
        });

        clearList = findViewById(R.id.btnClearList);
        clearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.clearList();
            }
        });

        manageProducts = findViewById(R.id.btnManageProducts);
        manageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.goToManageProducts();
            }
        });
    }

    public TextView getTotal() {
        return total;
    }

    public ListView getProducts() {
        return products;
    }

    public Button getAddProduct() {
        return addProduct;
    }

    public Button getClearList() {
        return clearList;
    }
}
