package com.example.socialnet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.example.socialnet.Categories.CImageAdapter;
import com.example.socialnet.Categories.CImageAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Favorites  extends Activity  implements OnClickListener  {
	int gridno;  
	Button  Search, Friends, Settings, Map ;
	ListView lv1;
	

	String FILENAMEV = "grid_favs.txt";
	int[] cat = {-1, -1, -1, -1, -1, -1, -1, -1 ,-1,-1};
	int[] subcat = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int catlength;

	@Override	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.category);

		    try { 
	            InputStream in=openFileInput(FILENAMEV);
		    	     if (in!=null){
		    	    	   InputStreamReader tmp= new InputStreamReader(in);
		    	    	   BufferedReader reader=new BufferedReader(tmp);
		    	    	   
		    	    	   int i=0; catlength= cat.length;
		    	    	   while (i < catlength)  
		    	    		   {
		    	    		   subcat[i]=reader.read(); 
		    	    		   cat[i] = reader.read();	
		    	    		   if (subcat[i] > 100) catlength=i;
		    	    		   i++;
		    	     }
		    	    	
		    	    	   in.close();	
	           
		    	     }
		 } catch (FileNotFoundException e) {
							e.printStackTrace();
						}

				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	
	    	
	    	
	

			
//			Intent currIntent =  getIntent();
//			gridno = currIntent.getIntExtra("gridposition", 1);		
			addcatadapter();
			
		       Map=(Button)findViewById(R.id.Map);
		       Search=(Button)findViewById(R.id.Search);
		       Friends=(Button)findViewById(R.id.Friends);
		       Settings=(Button)findViewById(R.id.Settings);
		       
		       
		       Search.setOnClickListener(this);
		       Friends.setOnClickListener(this);
		       Settings.setOnClickListener(this);
		       Map.setOnClickListener(this);
			
			
		}
			
			private void addcatadapter() {
		// TODO Auto-generated method stub
				 GridView gridview = (GridView) findViewById(R.id.Cgridview);
				 gridview.setAdapter(new CImageAdapter(this));


				 gridview.setOnItemClickListener(new OnItemClickListener() 
				 {
				     public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
				     {
				 	Toast.makeText(Favorites.this, "" + position,
				 			Toast.LENGTH_SHORT).show();
				 	
				 	/*Intent CatIntent = new Intent();
				 	CatIntent.setClassName("com.example.socialnet", "com.example.socialnet.Categories");
				 	CatIntent.putExtra("gridposition", position);
				 	startActivity(CatIntent);*/
				 /*	switch (position) {
				 	case 0:
				 		  setContentView(R.layout.result);
				 		  tv1=(TextView)findViewById(R.id.DTextView01);
				 		  tv2=(TextView)findViewById(R.id.DTextView02); 
				 		  tv1.setText("Bus");
				 		  tv2.setText("Wait Times"); 
				 		String[] text= {"Bus Wait Time is 5 mins","Bus 3 Wait time is 10 mins"};//{"Bus No. 1 is 10 min away", "Bus 2 is 5 min away"};
				 		addAdapter(text);
				 		break;

				 	case 2 :
				 		setContentView(R.layout.result);
				 		  tv1=(TextView)findViewById(R.id.DTextView01);
				 		  tv2=(TextView)findViewById(R.id.DTextView02); 
				 		  tv1.setText("Bus");
				 		  tv2.setText("Wait Times"); 
				 		String[] text1= {"Table 1 Wait Time is 5 mins","Table 2 Wait time is 10 mins"};//{"Bus No. 1 is 10 min away", "Bus 2 is 5 min away"};
				 		addAdapter(text1);
				 		
				 		break;

				 	case 3:
				 	
				 		break;

				 	case 4:
				 		
				 		break;
				 	}*/
				 	}
				 });
			}


		class CImageAdapter extends BaseAdapter  {
			    private Context mContext;
			    private LayoutInflater Lview;

			    public CImageAdapter(Context c) {
			   	//Lview = LayoutInflater.from(c);
			    	mContext = c;
			        
			        
			    }

			    public int getCount() {
                  if (catlength < cat.length){ return catlength; } //ImageIds[gridno].length;
                  else {return cat.length; }
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

			       
			         	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			       
				        MyView =  Lview.inflate(R.layout.griditem, parent, false);
			    //    	MyView.setLayoutParams(new GridView.LayoutParams(90, 90));
				                	        
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
			       holder.iv.setImageResource(ImageIds[cat[position]][subcat[position]]);      
			       holder.tv.setText(TextIds[cat[position]][subcat[position]]);
			       return MyView;
			    }

		    class ViewHolder {
			    	TextView tv;
			    	ImageView iv;
			    }
			    
			    // references to our images
			    private Integer[][] ImageIds = 
			    { {R.drawable.bus, R.drawable.pinblue, R.drawable.casino, R.drawable.knapsack} ,
			     {R.drawable.property, R.drawable.globe, R.drawable.insurance, R.drawable.airtickets},
			     { R.drawable.chara, R.drawable.travelm, R.drawable.shield}}; 
			    
			    private String[][] TextIds = 
			    { 	{"bus", "pinblue", "casino", "shops"}, 
			    	{"property","globe", "insurance","agent"},
			    	{"airport", "travelm",  "shield"}}; 
			     
			
			}
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Intent i;
			// TODO Auto-generated method stub
			   switch(v.getId())
			   {
			   case R.id.Friends: 
				   i=new Intent(this, Friends.class);
				   startActivity(i);
				   break;
			   
			   case R.id.Settings:
				   i= new Intent(this, Settings.class);
				   startActivity(i);
				   break;
				   
			   case R.id.Map:
				   i= new Intent(this, Maps.class);
				   startActivity(i);
				   break;
				   
			   case R.id.Search:
				   i= new Intent(this, Socialnet.class);
				   startActivity(i);
				   break;		   		
		}					
		}
}
