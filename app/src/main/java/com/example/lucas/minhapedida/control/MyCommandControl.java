package com.example.lucas.minhapedida.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lucas.minhapedida.R;
import com.example.lucas.minhapedida.model.bo.CommandItemBO;
import com.example.lucas.minhapedida.model.dao.CommandItemDAO;
import com.example.lucas.minhapedida.model.vo.CommandItem;
import com.example.lucas.minhapedida.utils.Constants;
import com.example.lucas.minhapedida.view.ActivityAddProduct;
import com.example.lucas.minhapedida.view.ActivityMyCommand;
import com.example.lucas.minhapedida.view.ActivityProducts;

import java.sql.SQLException;
import java.util.List;

public class MyCommandControl extends AppCompatActivity {

    private ActivityMyCommand activity;

    private CommandItemDAO dao;
    private List<CommandItem> items;
    private ArrayAdapter<CommandItem> itemsAdapter;

    public MyCommandControl(ActivityMyCommand activity) {
        this.activity = activity;
        dao = new CommandItemDAO(activity);
        attView();
    }

    public void attView() {
        configureListView();
        calcTotalValue();
    }

    public void calcTotalValue() {
        double total = 0;
        for (CommandItem item : items) {
            total += item.getSubValue();
        }
        activity.getTotal().setText(String.format("%.2f", total));
    }

    public void goToAddProduct() {
        Intent it = new Intent(activity, ActivityAddProduct.class);
        activity.startActivityForResult(it, Constants.ADD_PRODUCT_REQUEST);
    }

    public void configureListView() {
        try {
            items = dao.getDao().queryForAll();
            itemsAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, items);
            activity.getProducts().setAdapter(itemsAdapter);
        } catch (SQLException e) {
            Toast.makeText(activity, activity.getString(R.string.error_listing_all_commanditems), Toast.LENGTH_SHORT).show();
        }

        setOnShortClickListener();
        setOnLongClickListener();
    }

    private void setOnShortClickListener() {
        activity.getProducts().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommandItem item = itemsAdapter.getItem(position);
                showProductDialog(item);
            }
        });
    }

    private void setOnLongClickListener() {
        activity.getProducts().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommandItem item = itemsAdapter.getItem(position);
                showDeleteItemDialog(item);
                return true;
            }
        });
    }

    public void showDeleteItemDialog(final CommandItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.confirm_delete_commanditem));
        alert.setMessage(activity.getString(R.string.you_wish_to_delete) + item.getProduct().getName() + "?");
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
                    deleteItem(item);
            }
        });
        alert.show();
    }

    public void deleteItem(final CommandItem item) {
        try {
            dao.getDao().delete(item);
            itemsAdapter.remove(item);
        } catch (SQLException ex) {
            Toast.makeText(activity, activity.getString(R.string.error_deleting_commanditem), Toast.LENGTH_SHORT).show();
        }
    }

    public void showProductDialog(final CommandItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(item.getProduct().getName());
        alert.setMessage(item.getProduct().toString());
        alert.setNegativeButton("-1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                int newQuantity = item.getQuantity() - 1;
                if (CommandItemBO.isValidQuantity(newQuantity)) {
                    item.setQuantity(newQuantity);
                    double newSubValue = CommandItemBO.calcSubValue(item.getProduct(), newQuantity);
                    if (CommandItemBO.isValidSubValue(newSubValue)) {
                        item.setSubValue(newSubValue);
                        persistCommandItem(item);
                        attView();
                    }
                } else if (newQuantity == 0) {
                    deleteItem(item);
                    attView();
                }
            }
        });
        alert.setPositiveButton("+1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                int newQuantity = item.getQuantity() + 1;
                if (CommandItemBO.isValidQuantity(newQuantity)) {
                    item.setQuantity(newQuantity);
                    double newSubValue = CommandItemBO.calcSubValue(item.getProduct(), newQuantity);
                    if (CommandItemBO.isValidSubValue(newSubValue)) {
                        item.setSubValue(newSubValue);
                        persistCommandItem(item);
                        attView();
                    }
                }
            }
        });
        alert.setNeutralButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        alert.show();
    }

    public void clearList() {
            while (itemsAdapter.getCount() > 0) {
                deleteItem(itemsAdapter.getItem(0));
            }
    }

    public void persistCommandItem(CommandItem item) {
        try {
            dao.getDao().createOrUpdate(item);
        } catch (SQLException e) {
            Toast.makeText(activity, activity.getString(R.string.error_persisting_command_item), Toast.LENGTH_SHORT).show();
        }
    }

    public void goToManageProducts() {
        Intent it = new Intent(activity, ActivityProducts.class);
        activity.startActivity(it);
    }
}
