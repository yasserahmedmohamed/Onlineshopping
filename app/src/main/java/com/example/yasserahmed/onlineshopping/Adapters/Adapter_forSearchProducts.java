package com.example.yasserahmed.onlineshopping.Adapters;

import android.content.Context;
import android.text.Html;
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
 * Created by yasser ahmed on 12/7/2017.
 */

public class Adapter_forSearchProducts extends BaseAdapter {
    ArrayList<Products>arrayList=new ArrayList<>();
    Context context;

    public Adapter_forSearchProducts( Context context,ArrayList<Products> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i).ProID;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View ttt, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.search_item,null);

        ImageView imageView=(ImageView)view.findViewById(R.id.search_image);
        TextView search_name=(TextView)view.findViewById(R.id.search_name);
        TextView search_price=(TextView)view.findViewById(R.id.search_price);
        imageView.setImageResource(Integer.parseInt(arrayList.get(i).prodImage));
        search_name.setText(Html.fromHtml(arrayList.get(i).ProName));
        search_price.setText(arrayList.get(i).Price);
        return view;
    }
}
