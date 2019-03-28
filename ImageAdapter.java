package com.example.socialnet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater Lview;

    public ImageAdapter(Context c) {
   	//Lview = LayoutInflater.from(c);
    	mContext = c;
    	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        
    }

    public int getCount() {
        return ImageIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
     
    	View MyView = convertView;
    	ViewHolder holder;
    	TextView tv=null;
    	ImageView iv=null;
        if (convertView == null) { // if it's not recycled, initialize some
                                    // attributes

       
         	
       
	        MyView =  Lview.inflate(R.layout.griditem, parent, false);
        //	MyView.setLayoutParams(new GridView.LayoutParams(95, 95));
	                	        
	        holder= new ViewHolder();
            holder.tv = (TextView) MyView.findViewById(R.id.grid_item_text);
            holder.iv = (ImageView) MyView.findViewById(R.id.grid_item_image);
          
        	MyView.setTag(holder);
          
        } 
        else {
             holder= (ViewHolder) MyView.getTag();//convertView;
        }
        
       holder.iv.setAdjustViewBounds(true);
       holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);     
       holder.iv.setPadding(8, 8, 8, 8); 
       //holder.tv.setText("Item "+ position); 
       holder.iv.setImageResource(ImageIds[position]);      
       holder.tv.setText(TextIds[position]);
       return MyView;
    }

    class ViewHolder {
    	TextView tv;
    	ImageView iv;
    }
    
    // references to our images
    public static Integer[] ImageIds = 
          { 
    		R.drawable.food, R.drawable.nightlife, R.drawable.shopping, 
    		R.drawable.theatre, R.drawable.active_life, 
    		R.drawable.health, R.drawable.travel, R.drawable.spa,
    		R.drawable.finance}; 
//    		R.drawable.travelm,  	R.drawable.shield}; 
    
    public static String[] TextIds = 
    { 
		"restaurants", "nightlife", "shopping",
		"entertainment","active life", 
		"health","travel","beauty", "finance"};
   // , "travelm",  "shield"}; 

}



