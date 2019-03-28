package com.example.socialnet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.example.socialnet.Friends.getFrndsData;
import com.example.socialnet.Friends.FriendsArrayAdapter.ViewHolder;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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

public class Categories extends Activity  implements OnClickListener {
	
int gridno;  
Button  Search, Friends, Settings, Map, MapOver ;
ListView lv1;
private static final String TAG = null;
String user_id,pass;
String FILENAME = "socialnet_file.txt";
String[] btext1;

@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.category);
		Intent currIntent =  getIntent();
		gridno = currIntent.getIntExtra("gridposition", 1);		
		
		user_id="-1";
		try{
		InputStream in = openFileInput(FILENAME);					
		if(in!=null){
	    	    	 InputStreamReader tmp= new InputStreamReader(in);
	    	    	 BufferedReader reader=new BufferedReader(tmp);
	    	         user_id=reader.readLine().toString();
	    	         pass = reader.readLine().toString();
//		                user_data[1]=reader.readLine().toString();
	    	         in.close();	     
				}	    	     
	     	   }	
       	  catch (FileNotFoundException e) {
	 		 
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}

		addcatadapter();
		
	  
	       Search=(Button)findViewById(R.id.Search);
	       Friends=(Button)findViewById(R.id.Friends);
	       Settings=(Button)findViewById(R.id.Settings);
	       Map=(Button)findViewById(R.id.Map);
	       
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
		
//Getting business data
			    
			    	 new	getBizzData().execute(user_id,
			 				ImageAdapter.TextIds[gridno],CTextIds[gridno][position]);

			 	}
			 });
		}
		
		
		public class getBizzData extends AsyncTask<String, Void, String[]> {

			private ProgressDialog dialog;
			
			@Override
				  protected void onPreExecute() {
				
				 dialog = ProgressDialog.show(Categories.this, "","Loading. Please wait...", true);
			} 
			
			
			@Override
			protected String[] doInBackground(String... mess) {
			
		    String[] bizztext=null;

	 		HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/biztaband");
	        List nvps = new ArrayList<String>(3);
	//        user_id="ptota";
	         nvps.add(new BasicNameValuePair("usr_id", mess[0]));
	         nvps.add(new BasicNameValuePair("catg",mess[1] ));
	         nvps.add(new BasicNameValuePair("subcatg",mess[2]));
	        try {
				post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				 StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
			}
	        try {
	        	HttpResponse res =client.execute(post);
	        	 Log.v(TAG, res.getStatusLine().toString());
	              HttpEntity responseEntity = res.getEntity();
	              Log.v(TAG, "Set response to responseEntity");
	             bizztext=HttpHelper.request(res);
/*	             setContentView(R.layout.bizlist);
	             CatAdapter(btext1, gridno, position);
*/
	    //    contact_name.setText(text1[0]);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.v(TAG, e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.v(TAG, e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			btext1=bizztext;
			return bizztext;
		 	}

		    
			
			 protected void onPostExecute(String[] result) {
				 setContentView(R.layout.bizlist);
				 CatAdapter(result);
	             //gridno);
				 dialog.dismiss();
//				 setContentView(R.layout.bizlist);

		     }

		
		}
		
		
		
		
		

	    private Integer[][] CImageIds = 
	    { {R.drawable.cafe,R.drawable.pizza,R.drawable.fastfood , R.drawable.pasta, R.drawable.chinesefood1},
	     {R.drawable.finedining, R.drawable.drink, R.drawable.dancing},
	     { R.drawable.groceries, R.drawable.clothes, R.drawable.auto, R.drawable.homeimprove,
	    	 R.drawable.consumer_electronics,R.drawable.toys, R.drawable.shoes, R.drawable.book},
	     {},{},{},{R.drawable.hospital,R.drawable.clinic,R.drawable.agency},{},{},{R.drawable.banks, R.drawable.xchng}}; 
	    
	    private String[][] CTextIds = 
	    { 	{"cafe","pizza","fastfood", "Italian",  "Chinese"}, 
	    	{"fine dining","bars", "clubs"},
	    	{"groceries", "clothes",  "automobile", "home improvement",
	    		"electronics","toys","shoes", "books" },
	    		{},{},{},
	    		{"hospital", "clinic", "agency"},{},{},{"banks", "exchange"}
	    		}; 


	class CImageAdapter extends BaseAdapter  {
		    private Context mContext;
		    private LayoutInflater Lview;

		    public CImageAdapter(Context c) {
		   	//Lview = LayoutInflater.from(c);
		    	mContext = c;
		    	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        
		    }

		    public int getCount() {
		        return CImageIds[gridno].length;
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
		       holder.iv.setImageResource(CImageIds[gridno][position]);      
		       holder.tv.setText(CTextIds[gridno][position]);
		       return MyView;
		    }

		   class ViewHolder {
		    	TextView tv;
		    	ImageView iv;
		    }
		    
		    // references to our images
		     
		
		}
	
	class BRowD {
		 String bid= " ";
		 String bname= " ";
		 String baddress="";
		 String city="";
		 String state="";
		 String zip="";
		 String latitude ="";
		 String longitute ="";
		 String description ="";
		 String bemail = " ";
		 
		 String specials ="";
		 String coupon = " ";
		 String coupondesc = " ";
		 String waitime = " ";
		 String timeentry= " "; 
	     String image="";
	     
	    	 
	     
		BRowD(String S0, String S1, String S2, String S3,String S4, String S5, String S6,
				String S7, String S8,  String R0, String R1, String R2, String R3, String R4, 
				String R5){
	     	
			bid=S0;
			 bname= S1;
			 baddress= S2;
			 city= S3;
			 state=S4;
			 zip=S5;
			 latitude = S6;
			 longitute = S7;
			 description =S8;
			 
			 
			 image=R0;
			 specials = R1;
			 coupon =  R2;
			 coupondesc = R3;
			 waitime = R4;
			 timeentry= R5; 
		     
			

	     	}
		 }
	
	 Vector<BRowD> bdata;
	 
	public void CatAdapter(String[] text1){
		// int gridno, int position){
	
		 BRowD brd;
   		bdata= new Vector<BRowD>();
			  int l = 0; //counter for first data block
			  int records_num=text1.length/15;
			  int l2= records_num*9; //l2 is the start point of the second data block
			  while (l2 < text1.length ) {				  
//			      rd = new RowD(text1[i], "Hi, I am at Sbaucks", "kapoor@iit.edu",1);
			      brd = new BRowD(text1[l],text1[l+1],text1[l+2],text1[l+3],
			    		  text1[l+4],text1[l+5],text1[l+6],text1[l+7],text1[l+8],
			    		  text1[l2],text1[l2+1],text1[l2+2],text1[l2+3],
			    		  text1[l2+4],text1[l2+5]);
			      l=l+9;l2=l2+6;
			  bdata.add(brd);
			  }
			  
			  	  
			  ListView listsetview = (ListView) findViewById(R.id.bzlist);
			  listsetview.setAdapter(new BizzArrayAdapter(this,R.layout.bizitemlist, bdata ));
	             MapOver=(Button)findViewById(R.id.Mapover);
		  	       MapOver.setOnClickListener(this);
		  	       Search=(Button)findViewById(R.id.Search);
			       Friends=(Button)findViewById(R.id.Friends);
			       Settings=(Button)findViewById(R.id.Settings);
			       
			       
			       Search.setOnClickListener(this);
			       Friends.setOnClickListener(this);
			       Settings.setOnClickListener(this);

//			  listsetview.setAdapter(new ArrayAdapter<String>(this,R.layout.itemlist, data ));
			  
		/*	  listsetview.setAdapter(new LImageAdapter(this));*/


			  listsetview.setOnItemClickListener(new OnItemClickListener() 
			  {
			      public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
			      {
			    	Intent i;
			    	i = new Intent();
			    	  i.setClassName("com.example.socialnet", "com.example.socialnet.BizPage");
					 	i.putExtra("bizid",bdata.get(position).bid);
					 	i.putExtra("bizname",bdata.get(position).bname);
					 	i.putExtra("usrid",user_id);
					 	i.putExtra("pswd",pass);
					 
//			    	  Intent i= new Intent(Categories.this,BizPage.class);
					   startActivity(i);
			    	  
			  	//Toast.makeText(Socialnet.this, "" + position,	Toast.LENGTH_SHORT).show();
			  	

			      }
		      });
		  }
		  
		
	
	
public	 class BizzArrayAdapter extends ArrayAdapter<BRowD> {
	    private Context mContext;
	    private LayoutInflater Lview;
			
	   public BizzArrayAdapter(Context context, int textViewResourceId,
			Vector<BRowD> objects) {
		super(context, textViewResourceId, objects);
		mContext=context;
    
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

    	View MyView = convertView;
    	final View Mview1;

    	ViewHolder holder=null;
    	TextView tv=null;
    	ImageView[] iv1=null;
        BRowD  rdh = getItem(position);
        


        if (convertView !=null) holder= (ViewHolder) MyView.getTag();
        if ((convertView == null)||(holder==null)) { // if it's not recycled, initialize some
        
        //if (convertView == null) { // if it's not recycled, initialize some
                                    // attributes	       

         	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
	        MyView =  Lview.inflate(R.layout.bizitemlist, parent, false);
    //    	MyView.setLayoutParams(new GridView.LayoutParams(90, 90))
	        holder = new ViewHolder();
            holder.tv1 = (TextView) MyView.findViewById(R.id.textb1it);
            holder.tv2 = (TextView) MyView.findViewById(R.id.textb2it);
            holder.tv3 = (TextView) MyView.findViewById(R.id.textb3it);
            holder.tv4 = (TextView) MyView.findViewById(R.id.textb4it);
            holder.iv1 = (ImageView) MyView.findViewById(R.id.imgbitem);
            holder.iv2 = (ImageView) MyView.findViewById(R.id.img2bitem);
//            holder.button= (Button) MyView.findViewById(R.id.button3it);
            //MyView.findViewById(R.id.button3it).setTag(rdh.email);
           // MyView.findViewById(R.id.button3it).setTag(rdh.email);
	    	
        } 
        else {
             holder= (ViewHolder) MyView.getTag();//convertView;
        }
        
//       holder.iv.setAdjustViewBounds(true);
  //     holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);     
    //   holder.iv.setPadding(8, 8, 8, 8); 
       //holder.tv.setText("Item "+ position); 
		         
       holder.tv1.setText(rdh.bname);      
       holder.tv2.setText(rdh.baddress+","+ rdh.city+","+rdh.state);
       holder.tv3.setText(rdh.timeentry+"::Waittime:"+rdh.waitime);
       holder.tv4.setText("Specials:"+rdh.specials+";Coupon:"+rdh.coupondesc);
      
       Drawable image = GetPicURL.ImageOperations(this.getContext(),
    		   "http://mobileswan.com/system/application/views/images/"+rdh.bid+".gif");
       
       
       holder.iv1.setImageDrawable(image);
       holder.iv1.setMinimumHeight(60)   ;
       holder.iv2.setImageResource(R.drawable.fivestar);
       Mview1 = MyView;
      
       return MyView;
    }
	    	
   class ViewHolder {
        	    	ImageView iv1;
        	    	ImageView iv2;
        	    	TextView tv1;
        	    	TextView  tv2;
        	    	TextView tv3;
        	    	TextView  tv4;
       	 
                        }
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
			   
		   case R.id.Mapover:
			  
			 	i = new Intent();
			 	i.setClassName("com.example.socialnet", "com.example.socialnet.Mapover");
			 	i.putExtra("bizdata",btext1);
			 	startActivity(i);
//			   i= new Intent(this, Maps.class);
	//		   startActivity(i);
			   break;
			   
		   case R.id.Search:
			   i= new Intent(this, Socialnet.class);
			   startActivity(i);
			   break;		   		
	}					
	}
}

