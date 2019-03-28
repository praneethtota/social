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
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.example.socialnet.Categories.CImageAdapter;
import com.example.socialnet.Categories.getBizzData;
import com.example.socialnet.Categories.CImageAdapter.ViewHolder;
import com.example.socialnet.Friends.SendDataMessage;
import com.example.socialnet.Friends.getFrndsData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Interest extends Activity  implements OnClickListener {
	String user_id,pass;
	String FILENAME = "socialnet_file.txt";
	String[] textInterest;
	boolean[] Interestselected;
	//= { false};;
	
	private ImageButton  Home, Friends, Add, Delete;

	Button  SendI,CancelI;

	public InputMethodManager inputManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.interestgrid);
		user_id="-1";
		
		try{
		InputStream in = openFileInput(FILENAME);					
		if(in!=null){
	    	    	 InputStreamReader tmp= new InputStreamReader(in);
	    	    	 BufferedReader reader=new BufferedReader(tmp);
	    	         user_id=reader.readLine().toString();
		             pass=reader.readLine().toString();
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
         if (user_id!="-1")
         {
        	 
        	 new	getInterestData().execute(user_id,pass); 
        	 
         }
 		Home = (ImageButton) findViewById(R.id.Search);
		Friends = (ImageButton) findViewById(R.id.Friends);
		Add = (ImageButton) findViewById(R.id.Settings);
		Delete = (ImageButton) findViewById(R.id.Map);
		
		Home.setOnClickListener(this);
		Friends.setOnClickListener(this);
		Add.setOnClickListener(this);
		Delete.setOnClickListener(this);
		
		
	}	
	 
	
	
	public class getInterestData extends AsyncTask<String, Void, String[]> {

		private ProgressDialog dialog;
		
		@Override
			  protected void onPreExecute() {
			
			 dialog = ProgressDialog.show(Interest.this, "","Loading. Please wait...", true);
		} 
		
		
		@Override
		protected String[] doInBackground(String... mess) {
		
	    String[] textfrnd=null;
		
	    	HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/signinand");
	        List nvps = new ArrayList(2);
	        nvps.add(new BasicNameValuePair("usr_i", mess[0]));
	        nvps.add(new BasicNameValuePair("pss", mess[1]));
	        try 
	        {
				post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) 
			    {
				// TODO Auto-generated catch block
				 StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
           	          Toast.LENGTH_LONG).show();
			   }
	        try {
	        	HttpResponse res =client.execute(post);
	        	
	              HttpEntity responseEntity = res.getEntity();
	            
	              textInterest=HttpHelper.request(res);
	               Interestselected= new boolean[textInterest.length];
	          	for(int i=0; i<textInterest.length;i++){
	        		
	          		Interestselected[i]=false;
	        		
	          	}
		//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
	            	
	              
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",
	           	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
	           	          Toast.LENGTH_LONG).show();
			}
         return textInterest;
        }
        protected void onPostExecute(String[] result) {
			

			 InterestAdapter();
			 dialog.dismiss();
	     }
		}

	private void InterestAdapter() {
		// TODO Auto-generated method stub
		         
				 GridView gridview = (GridView) findViewById(R.id.Intgridview);
				 gridview.setAdapter(new IntImageAdapter(this));


				 gridview.setOnItemClickListener(new OnItemClickListener() 
				 {
				     public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
				     {
			
	//Getting friends interest data
				        
						Intent i = new Intent();
					 	i.setClassName("com.example.socialnet", "com.example.socialnet.IntList");
					 	i.putExtra("intdata",textInterest[position]);
					 	startActivity(i);
//				    	 new	getFrndsIntData().execute(user_id,pass, textInterest[position]);

				 	}
				 });
			}

	class IntImageAdapter extends BaseAdapter  {
	    private Context mContext;
	    private LayoutInflater Lview;

	    public IntImageAdapter(Context c) {
	   	//Lview = LayoutInflater.from(c);
	    	mContext = c;
	    	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        
	    }

	    public int getCount() {
	        return textInterest.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    // create a new View for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	     
	    	View MyView = convertView;
	    //	final Boolean[] cInterest= Interestselected;
	    	final int position1=position;
	    	ViewHolder holder;
	    	TextView tv=null;
	   // 	ImageView iv=null;
	        if (convertView == null) { // if it's not recycled, initialize some
	                                    // attributes

	       
	         	
	       
		        MyView =  Lview.inflate(R.layout.interestitem, parent, false);
	    //    	MyView.setLayoutParams(new GridView.LayoutParams(90, 90));
		                	        
		        holder= new ViewHolder();
	            holder.tv = (TextView) MyView.findViewById(R.id.int_item_text);
	            
	//            holder.iv = (ImageView) MyView.findViewById(R.id.grid_item_image);
	//  	       final CheckBox checkbox = (CheckBox) MyView.findViewById(R.id.interestcheckbox);
		       holder.cb = (CheckBox) MyView.findViewById(R.id.interestcheckbox);
		       holder.cb.setId(position);
		       holder.cb.setOnClickListener(new OnClickListener() {
		           public void onClick(View v) {
		               // Perform action on clicks, depending on whether it's now checked
		        	   CheckBox cb1 = (CheckBox) v;
		        	   int pos = cb1.getId();
		        	   
		               if (((CheckBox) v).isChecked()) {
		            	//   
		            	  Interestselected[pos]=true;
		                  // Toast.makeText(Interest.this, "Selected "+pos, Toast.LENGTH_SHORT).show();
		                   for (int i=0;i<textInterest.length;i++){
		                	  if(Interestselected[i]) Toast.makeText(Interest.this, "Selected "+textInterest[i], Toast.LENGTH_SHORT).show();
		                   }
		                	   
		               } else {
		            	   Interestselected[pos]=false;
		                  // Toast.makeText(Interest.this, "Not selected", Toast.LENGTH_SHORT).show();
		                   //rdh1.selected=false;
		               }
		           }
		       });
	
	        	MyView.setTag(holder);
	          
	        } 
	        else {
	             holder= (ViewHolder) MyView.getTag();//convertView;
	        }
	        
	      // holder.iv.setAdjustViewBounds(true);
	      // holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);     
	      // holder.iv.setPadding(8, 8, 8, 8); 
	       //holder.tv.setText("Item "+ position); 
//	       holder.iv.setImageResource(InterestImage);
	       holder.id=position;
	       holder.cb.setId(position);
	       holder.tv.setText(textInterest[position]);
	       return MyView;
	    }

	   class ViewHolder {
	    	TextView tv;
	    	CheckBox cb;
	    	int id;
	    }
	    
	    // references to our images
	     
	
	}
	
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Friends:
			i = new Intent(this, Interest.class);
			startActivity(i);
			break;

		case R.id.Settings://add
           Addinterest();
			break;

		case R.id.Map://delete
			Deleteinterest();
			break;

		case R.id.Search:
			i = new Intent(this, Socialnet.class);
			startActivity(i);
			break;

		case R.id.addI_button:
			EditText Messg=(EditText)findViewById(R.id.send_interest);
 		    String message = Messg.getText().toString(); 
 		//   Toast.makeText(Friends.this, "" +message,	Toast.LENGTH_SHORT).show();
//    		inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
         //   inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);					 
 		    new AddInterestData().execute(user_id,pass,message);
		    i=new Intent(this, Interest.class);
			startActivity(i);			
			break;
		
		case R.id.cancelI_button:
			i = new Intent(this, Interest.class);
			startActivity(i);
			break;

		}
	}
	
	public void Addinterest(){
		
			//final Intent i;
			setContentView(R.layout.addinterestdialog);
			SendI = (Button) findViewById(R.id.addI_button);
			CancelI = (Button) findViewById(R.id.cancelI_button);
//			messView = this.getCurrentFocus();

			inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		//	windToken = this.getCurrentFocus().getWindowToken();
			SendI.setOnClickListener(this);
			CancelI.setOnClickListener(this);			 
			 
		}
		
	public class AddInterestData extends AsyncTask<String, Void, String[]> {

		private ProgressDialog dialog;
		
		@Override
			  protected void onPreExecute() {
			
			 dialog = ProgressDialog.show(Interest.this, "","Sending. Please wait...", true);
		} 
		
		
		@Override
		protected String[] doInBackground(String... mess) {
		int cnt=0;
		
	    	HttpClient client = new DefaultHttpClient();
	        HttpPost post1 = new HttpPost("http://mobileswan.com/index.php?/socialnet/addinterestand");
	        List<BasicNameValuePair> nvps1 = new ArrayList<BasicNameValuePair>(3);
	        nvps1.add(new BasicNameValuePair("usr_i", mess[0]));
	        nvps1.add(new BasicNameValuePair("pss", mess[1]));
	        nvps1.add(new BasicNameValuePair("interest", mess[2]));
	        try 
	        {
				post1.setEntity(new UrlEncodedFormEntity(nvps1, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) 
			    {
				// TODO Auto-generated catch block
				 StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
           	          Toast.LENGTH_LONG).show();
			   }
	        try {
	        	HttpResponse res =client.execute(post1);
	        	
	              HttpEntity responseEntity = res.getEntity();
	             
	              textInterest=HttpHelper.request(res);
/*	              Interestselected= new boolean[textInterest.length];	               
	               for( int i=0; i<textInterest.length;i++){
	        		
	          		Interestselected[i]=false;
	        		
	          	}*/
		//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
	            	              
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",
	           	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
	           	          Toast.LENGTH_LONG).show();
			}
			
			return textInterest;
        }
        protected void onPostExecute(String[] result) {
			
//    		Toast.makeText(getApplicationContext(), result[0], Toast.LENGTH_LONG).show();
//       		 setContentView(R.layout.interestgrid);
//			 InterestAdapter();
			 dialog.dismiss();
	     }
		}
		
	
	
	public void Deleteinterest(){

		 if (user_id!="-1")
         {
        	 
        	 new	delInterestData().execute(user_id,pass); 

		
		
	}
	
}

	public class delInterestData extends AsyncTask<String, Void, String[]> {

		private ProgressDialog dialog;
		
		@Override
			  protected void onPreExecute() {
			
			 dialog = ProgressDialog.show(Interest.this, "","Deleting. Please wait...", true);
		} 
		
		
		@Override
		protected String[] doInBackground(String... mess) {
		int cnt=0;
		String[] delInterest = null;
	    String[] textfrnd=null;
	    for(int i=0; i<textInterest.length;i++){
		    if(Interestselected[i] == true)
		    	{
			//	Toast.makeText(getApplicationContext(),"deleting interest "+i,Toast.LENGTH_LONG).show();
		    //	delInterest[cnt]=textInterest[i];
		    	cnt++;
		    	}
		    }
	    
	    	HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/delinterestand");
	        List<BasicNameValuePair> nvps = new ArrayList(3+cnt);
	       
	        nvps.add(new BasicNameValuePair("usr_i", mess[0]));
	        nvps.add(new BasicNameValuePair("pss", mess[1]));
	        cnt=0;
		    for(int i=0; i<textInterest.length;i++){
			    if(Interestselected[i] == true)
			    	{
		        	nvps.add(new BasicNameValuePair("interest"+cnt,textInterest[i]));
		//			Toast.makeText(getApplicationContext(),"deleting  "+textInterest[i],Toast.LENGTH_LONG).show();
			    //	delInterest[cnt]=textInterest[i];
			    	cnt++;
			    	}
			    }
	        
           
	        nvps.add(new BasicNameValuePair("countI",Integer.toString(cnt)));
	        try 
	        {
				post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) 
			    {
				// TODO Auto-generated catch block
				 StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
           	          Toast.LENGTH_LONG).show();
			   }
	        try {
	        	HttpResponse res =client.execute(post);
	        	
	              HttpEntity responseEntity = res.getEntity();
	            
	            //  textInterest=HttpHelper.request(res);

	              
	              
//	              Interestselected= new boolean[textInterest.length];	               
/*	               for(int i=0; i<textInterest.length;i++){
	   				Toast.makeText(getApplicationContext(),textInterest[i],
		           	          Toast.LENGTH_LONG).show();
//	          		Interestselected[i]=false;
	        		
	          	} */
		//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
	            	              
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",
	           	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
	           	          Toast.LENGTH_LONG).show();
			}
	    	
	    
  
			HttpClient client1 = new DefaultHttpClient();
	        HttpPost post1 = new HttpPost("http://mobileswan.com/index.php?/socialnet/signinand");
	        List nvps1 = new ArrayList(2);
	        nvps1.add(new BasicNameValuePair("usr_i", mess[0]));
	        nvps1.add(new BasicNameValuePair("pss", mess[1]));
//	        nvps1.add(new BasicNameValuePair("interest", textInterest[i]));
	        try 
	        {
				post1.setEntity(new UrlEncodedFormEntity(nvps1, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) 
			    {
				// TODO Auto-generated catch block
				 StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
           	          Toast.LENGTH_LONG).show();
			   }
	        try {
	        	HttpResponse res =client1.execute(post1);
	        	
	              HttpEntity responseEntity = res.getEntity();
	            
	              textInterest=HttpHelper.request(res);
	              Interestselected= new boolean[textInterest.length];	               
	               for( int i=0; i<textInterest.length;i++){
	        		
	          		Interestselected[i]=false;
	        		
	          	}
		//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
	            	              
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",
	           	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
	           	          Toast.LENGTH_LONG).show();
			}
			
			return textInterest;
        }
        protected void onPostExecute(String[] result) {
			
			 InterestAdapter();
			 dialog.dismiss();
	     }
		}
}

