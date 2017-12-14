package com.example.yasserahmed.onlineshopping.Adapters_Classes;

import android.graphics.drawable.Drawable;

/**
 * Created by yasser ahmed on 11/30/2017.
 */

public class Categories  {

    public int id;
    public int Image;
    public String category;
    public String numberOfCateg;




    public Categories(int CatID, String CatName,int ImageURl, String NumofOffers) {
        this.id=CatID;
        Image = ImageURl;
        this.category = CatName;
        this.numberOfCateg = NumofOffers;
    }
}
