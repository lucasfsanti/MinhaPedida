package com.example.lucas.minhapedida.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucas.minhapedida.R;
import com.example.lucas.minhapedida.control.AddProductControl;
import com.example.lucas.minhapedida.model.vo.CommandItem;

import java.util.ResourceBundle;

public class ActivityAddProduct extends AppCompatActivity {

    private Button btnSend;
    private Spinner productsSpinner;
    private NumberPicker npQuantity;

    AddProductControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initialize();
        control = new AddProductControl(this);
    }

    private void initialize() {
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandItem item = control.getCommandItem();
//                control.sendItem(item);
                Intent intent = new Intent();
                intent.putExtra("CommandItem",item);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        productsSpinner = findViewById(R.id.spinnerProducts);

        npQuantity = findViewById(R.id.npQuantity);
        npQuantity.setMinValue(0);
        npQuantity.setMaxValue(99);
    }

    public Button getBtnAddProduct() {
        return btnSend;
    }

    public Spinner getProductsSpinner() {
        return productsSpinner;
    }

    public NumberPicker getNpQuantity() {
        return npQuantity;
    }
}
