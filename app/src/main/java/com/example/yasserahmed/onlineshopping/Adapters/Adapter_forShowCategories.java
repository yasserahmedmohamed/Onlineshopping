package com.example.yasserahmed.onlineshopping.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Categories;
import com.example.yasserahmed.onlineshopping.R;

import java.util.ArrayList;

/**
 * Created by yasser ahmed on 11/30/2017.
 */

public class Adapter_forShowCategories extends BaseAdapter {

    ArrayList<Categories>arrayList=new ArrayList<>();
    Context context;

    public Adapter_forShowCategories(ArrayList<Categories> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).Image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.categorisitems,null);


        ImageView imageView=(ImageView)view.findViewById(R.id.catg_img_id);
        TextView catgText=(TextView)view.findViewById(R.id.catg_name_id);
        TextView catgnumText=(TextView)view.findViewById(R.id.catg_num_id);

        imageView.setImageResource(arrayList.get(position).Image);
        catgText.setText(arrayList.get(position).category);
        catgnumText.setText("Offers Num: "+arrayList.get(position).numberOfCateg);
        return view;
    }
}
