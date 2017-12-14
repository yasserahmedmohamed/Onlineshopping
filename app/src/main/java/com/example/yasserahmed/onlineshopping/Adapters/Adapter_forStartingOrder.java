package com.example.yasserahmed.onlineshopping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Orders;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Products;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;
import com.example.yasserahmed.onlineshopping.R;

import java.util.ArrayList;

/**
 * Created by yasser ahmed on 12/13/2017.
 */

public class Adapter_forStartingOrder extends BaseAdapter {
    Context context;
    ArrayList<Orders> arrayList = new ArrayList<>();

    public Adapter_forStartingOrder(Context context, ArrayList<Orders> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i).OrdID;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    ShoppingDataBase shoppingDataBase;

    @Override
    public View getView(int i, View ttt, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.startorder_item, null);
        shoppingDataBase = new ShoppingDataBase(context);
        final ArrayList<Products> product = shoppingDataBase.GetAnProducts(arrayList.get(i).ProductID);

        ImageView st_img = (ImageView) view.findViewById(R.id.st_img);
        TextView st_proname = (TextView) view.findViewById(R.id.st_proname);
        TextView st_proprice = (TextView) view.findViewById(R.id.st_proprice);
        TextView st_proquant = (TextView) view.findViewById(R.id.st_proquant);
        final EditText st_editquan = (EditText) view.findViewById(R.id.st_editquan);
        Button st_buttonsubmit = (Button) view.findViewById(R.id.st_buttonsubmit);

        TextView st_ID=(TextView)view.findViewById(R.id.st_ID);
        st_img.setImageResource(Integer.parseInt(product.get(0).prodImage));
        st_ID.setText(st_ID.getText().toString()+product.get(0).ProID);
        st_proname.setText(st_proname.getText().toString() + product.get(0).ProName);
        st_proprice.setText(st_proprice.getText().toString() + product.get(0).Price);
        st_proquant.setText(st_proquant.getText().toString() + product.get(0).Quantity);
        st_editquan.getText().toString();

        st_buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qditqun = Integer.valueOf(st_editquan.getText().toString());
                int avqun = Integer.valueOf(product.get(0).Quantity);
                if (qditqun > avqun) {
                    st_editquan.setError("your order is not available all quantity");
                    st_editquan.requestFocus();
                } else {
                    shoppingDataBase.updateQuantity(st_editquan.getText().toString());

                }
            }
        });
        return view;
    }
}
