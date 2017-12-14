package com.example.yasserahmed.onlineshopping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Products;
import com.example.yasserahmed.onlineshopping.R;

import java.util.ArrayList;

/**
 * Created by yasser ahmed on 12/4/2017.
 */

public class Adapter_forShowProducts extends BaseAdapter {
    Context context ;
    ArrayList<Products>arrayList;
    public Adapter_forShowProducts(Context context, ArrayList<Products> arrayList){

        this.arrayList=arrayList;
        this.context=context;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i).CatID;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View tt, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.productitem,null);

        ImageView imageView=(ImageView)view.findViewById(R.id.Pro_imag);
        TextView Pro_name=(TextView)view.findViewById(R.id.Pro_name);
        TextView Pro_price=(TextView)view.findViewById(R.id.Prod_price) ;
        imageView.setImageResource(Integer.parseInt(arrayList.get(i).prodImage));
        Pro_name.setText(""+arrayList.get(i).ProName);
        Pro_price.setText(arrayList.get(i).Price);
        return view;
    }
}
