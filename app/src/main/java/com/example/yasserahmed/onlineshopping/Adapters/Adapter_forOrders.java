package com.example.yasserahmed.onlineshopping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Orders;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Products;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;
import com.example.yasserahmed.onlineshopping.R;

import java.util.ArrayList;

/**
 * Created by yasser ahmed on 12/9/2017.
 */

public class Adapter_forOrders extends BaseAdapter {
    Context context;
    ArrayList<Orders>arrayList=new ArrayList<>();
    int kind;

    public Adapter_forOrders(Context context, ArrayList<Orders> arrayList, int kind) {
        this.context = context;
        this.arrayList = arrayList;
        this.kind = kind;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View ttt, ViewGroup viewGroup) {
        ShoppingDataBase shoppingDataBase=new ShoppingDataBase(context);



        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.order_item,null);

        ImageView ord_image=(ImageView)view.findViewById(R.id.ord_image);
        TextView ord_name=(TextView)view.findViewById(R.id.ord_name);
        TextView ord_price=(TextView)view.findViewById(R.id.ord_price);


            ArrayList<Products> product=shoppingDataBase.GetAnProducts(arrayList.get(i).ProductID);

            ord_image.setImageResource(Integer.parseInt(product.get(0).prodImage));
        ord_name.setText(product.get(0).ProName);
        ord_price.setText("price : "+product.get(0).Price);

        return view;
    }
}
