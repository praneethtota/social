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

import com.example.socialnet.Categories.BizzArrayAdapter;
import com.example.socialnet.Categories.BizzArrayAdapter.ViewHolder;
import com.example.socialnet.Friends.SendDataMessage;
import com.example.socialnet.Settings.SettingsArrayAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class BizPage extends Activity implements OnClickListener{

	String bizid; String bizname="Cafe57"; String usr_id="-1";
	String[] text1= new String[22]; String pass="sktest";
	
	Button Home, Order, Map;
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bizpage);
		Intent currIntent =  getIntent();
		bizid = currIntent.getStringExtra("bizid");
		bizname = currIntent.getStringExtra("bizname");
		usr_id = currIntent.getStringExtra("usrid");
		pass=currIntent.getStringExtra("pswd");
		
		if(usr_id.contentEquals("-1")) Toast.makeText(BizPage.this, "You will need to sign in to order",	Toast.LENGTH_LONG).show();		
		new	getBizzPageData().execute(bizid);
	
	     Map=(Button)findViewById(R.id.Map);
	     Home=(Button)findViewById(R.id.Home);
	     Order=(Button)findViewById(R.id.Order);
	   //  Settings=(Button)findViewById(R.id.Settings);
	    // Delete=(Button)findViewById(R.id.Delete);
	    // SendfrndB=(Button)findViewById(R.id.Sendfrnd);
	 /*    send_Button=(Button)findViewById(R.id.send_button);
	     cancel_Button=(Button)findViewById(R.id.cancel_button);*/
//	     Messg=(EditText)findViewById(R.id.messgfrnd);
	     
	     Home.setOnClickListener(this);
	     Order.setOnClickListener(this);
	     
	     Map.setOnClickListener(this);
	     
		
		text1[0]= "Coffee/Teas";
		text1[1]= "Coffee with vanilla";
		text1[2]= "CafeAmericano";
		text1[3]= "3";
		text1[4]="8oz";
		text1[5]="$2.99";
		text1[6]="10oz";
		text1[7]="$3.99";
		text1[8]="15oz";
		text1[9]="$4.99";
        text1[10]="1";
        
		text1[11]= "Coffee/Teas";
		text1[12]= "Coffee with vanilla";
		text1[13]= "CafeAmericano";
		text1[14]= "3";
		text1[15]="8oz";
		text1[16]="$2.99";
		text1[17]="10oz";
		text1[18]="$3.99";
		text1[19]="15oz";
		text1[20]="$4.99";
		text1[21]="2";
				
		
//		BizpgAdapter(text1);
		  
		
		
	}
		


		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i;
			   switch(v.getId())
			   {
			   case R.id.Home: 
				   i= new Intent(this, Socialnet.class);
				   startActivity(i);
				   break;

				  
			   
				   			
			   case R.id.Map:
						   
				   i= new Intent(this, Maps.class);
				   startActivity(i);
				   break;
				   
			   case R.id.Order:
				   showOrder();
				   break;
				   
			   case R.id.PlaceOrder:
					//new	PlaceOrderData().execute(bizname);
				    confirmOrder(bizid);   
				   break;
				   
			   case R.id.Change:
					setContentView(R.layout.bizpage);
					  ListView listsetview = (ListView) findViewById(R.id.bzpglist);
					  listsetview.setAdapter(new BizzPgAdapter(this,R.layout.bizpageitem, bdata ));
					
				   
				   break;

			   }

			
		}
		
		public class getBizzPageData extends AsyncTask<String, Void, String[]> {

			private ProgressDialog dialog;
			
			@Override
				  protected void onPreExecute() {
				
				 dialog = ProgressDialog.show(BizPage.this, "","Loading. Please wait...", true);
			} 
			
			
			@Override
			protected String[] doInBackground(String... mess) {
			
		    String[] bizpgtext=null;

	 		HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/bizpageand");
	        List nvps = new ArrayList<String>(1);

	         nvps.add(new BasicNameValuePair("bizname", mess[0]));

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

	              HttpEntity responseEntity = res.getEntity();

	             bizpgtext=HttpHelper.request(res);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			return bizpgtext;
		 	}

		
			 protected void onPostExecute(String[] result) {
				
				 BizpgAdapter(result);
	    
				 dialog.dismiss();
//				 setContentView(R.layout.bizlist);

		     }
		}
		
		
		class BzpgRowD {
			
			 String ItemCategory= " ";
			 String ItemDescript= " ";
			 String ItemName="";
			 //String ItemPrice="";
			 String ItemOptionCount="";
			 String[] ItemOption1={"",""};
			 String[] ItemOption2={"",""};
			 String[] ItemOption3 ={"",""};
			 String ItemId; 
			 String ItemQuantity;;	
			 String option;
			 int optionpos;
		     
			BzpgRowD(String S0, String S1, String S2, String S3,String S4, String S5, String S6,
					String S7, String S8, String S9, String S10){
		//, String S8,  String R0, String R1, String R2, String R3, String R4,String R5){

				 ItemCategory= S0;
				 ItemDescript= S1;
				 ItemName=S2;
				 //String ItemPrice="";
				 ItemOptionCount=S3;
				 ItemOption1[0]=S4;
				 ItemOption1[1]=S5;
				 ItemOption2[0]=S6;
				 ItemOption2[1]=S7;
				 ItemOption3[0] =S8;
				 ItemOption3[1] =S9;
				 ItemId=S10;
				 				
		     	}
			 }
		
		 Vector<BzpgRowD> bdata;
		 
		public void BizpgAdapter(String[] text1){
			BzpgRowD brd;
	   		bdata= new Vector<BzpgRowD>();
				  int l = 0; //counter for first data block
				  int records_num=text1.length/10;
//				  int l2= records_num*9; //l2 is the start point of the second data block
				  while (l < text1.length ) {				  
//				      rd = new RowD(text1[i], "Hi, I am at Sbaucks", "kapoor@iit.edu",1);
				      brd = new BzpgRowD(text1[l],text1[l+1],text1[l+2],text1[l+3],
				    		  text1[l+4],text1[l+5],
				    		  text1[l+6],text1[l+7],text1[l+8],text1[l+9], text1[l+10]);
				      //,text1[l+8],
				    	//	  text1[l2],text1[l2+1],text1[l2+2],text1[l2+3],
				    		//  text1[l2+4],text1[l2+5]);
				      l=l+11;//l2=l2+6;
				  bdata.add(brd);
				  }
				  
				  	  
				  ListView listsetview = (ListView) findViewById(R.id.bzpglist);
				  listsetview.setAdapter(new BizzPgAdapter(this,R.layout.bizpageitem, bdata ));
/*		             MapOver=(Button)findViewById(R.id.Mapover);
			  	       MapOver.setOnClickListener(this);
			  	       Search=(Button)findViewById(R.id.Search);
				       Friends=(Button)findViewById(R.id.Friends);
				       Settings=(Button)findViewById(R.id.Settings);
				       
				       
				       Search.setOnClickListener(this);
				       Friends.setOnClickListener(this);
				       Settings.setOnClickListener(this);

//				  listsetview.setAdapter(new ArrayAdapter<String>(this,R.layout.itemlist, data ));
				  
			/*	  listsetview.setAdapter(new LImageAdapter(this));*/


				  listsetview.setOnItemClickListener(new OnItemClickListener() 
				  {
				      public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
				      {
				  	//Toast.makeText(Socialnet.this, "" + position,	Toast.LENGTH_SHORT).show();
				  	

				      }
			      });
			  }
			  

		public	 class BizzPgAdapter extends ArrayAdapter<BzpgRowD> {
		    private Context mContext;
		    private LayoutInflater Lview;
				
		   public BizzPgAdapter(Context context, int textViewResourceId,
				Vector<BzpgRowD> objects) {
			super(context, textViewResourceId, objects);
			mContext=context;
	    
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

	    	View MyView = convertView;
	    	final View Mview1;

	    	ViewHolder bholder=null;
	    	TextView tv=null;
	    	ImageView[] iv1=null;
	        final BzpgRowD  rdh = getItem(position);
	        


	        if (convertView !=null) bholder= (ViewHolder) MyView.getTag();
	        if ((convertView == null)||(bholder==null)) { // if it's not recycled, initialize some
	        
	        //if (convertView == null) { // if it's not recycled, initialize some
	                                    // attributes	       

	         	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
		        MyView =  Lview.inflate(R.layout.bizpageitem, parent, false);
	    
		        bholder = new ViewHolder();
		        
	            bholder.tv1 = (TextView) MyView.findViewById(R.id.textbpg1it);
	            bholder.tv2 = (TextView) MyView.findViewById(R.id.textbpg2it);
	            bholder.tv3 = (TextView) MyView.findViewById(R.id.textbpgid);
	            
	        } 
	        else {
	             bholder= (ViewHolder) MyView.getTag();//convertView;
	        }
	        
			         
	       bholder.tv1.setText(rdh.ItemName);      
	       bholder.tv2.setText(rdh.ItemDescript);
	       bholder.tv3.setText(rdh.ItemId);
	       
	       CharSequence[] spinit = new CharSequence[3];
	        spinit[0]= rdh.ItemOption1[0]+"\n"+rdh.ItemOption1[1];
	        spinit[1]= rdh.ItemOption2[0]+"\n"+rdh.ItemOption2[1];
	        spinit[2]= rdh.ItemOption3[0]+"\n"+rdh.ItemOption3[1];
	        bholder.SpinnerItems = spinit;
            Spinner spinner = (Spinner) MyView.findViewById(R.id.spinner1bpg);	           
            ArrayAdapter<CharSequence> adapter = 
            	 new ArrayAdapter<CharSequence>(BizPage.this, R.layout.spinnerform,spinit);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter); 	
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent,
                    View view, int pos, long id) {
             //     Toast.makeText(parent.getContext(), "The planet is " +
                 //parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
                	rdh.option=parent.getItemAtPosition(pos).toString();
                	rdh.optionpos=pos;
  //              	rdh.option.replace("\n"," ");
                }

                public void onNothingSelected(AdapterView parent) {
                  // Do nothing.
                }
            });
           
            Spinner spinner2 = (Spinner) MyView.findViewById(R.id.spinner2bpg);	           
            ArrayAdapter<String> adapter2 = 
            	 new ArrayAdapter<String>(BizPage.this, R.layout.spinnerform,bholder.Spinner2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2); 	
            spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent,
                    View view, int pos, long id) {
             //     Toast.makeText(parent.getContext(), "The planet is " +
                 //parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
                	rdh.ItemQuantity=parent.getItemAtPosition(pos).toString();
                }

                public void onNothingSelected(AdapterView parent) {
                  // Do nothing.
                }
            });
	       
	       
	       Mview1 = MyView;
	      
	       return MyView;
	    }
		    	
	   class ViewHolder {
//	        	    	ImageView iv1;
	        	    	CharSequence[] SpinnerItems={"","",""};
	        	    	String[] Spinner2 = {"0","1","2","3","4","5","6","7","8","9"};
	        	    	TextView tv1;
	        	    	TextView  tv2;
	        	    	TextView  tv3;
	        	    	//TextView tv3;
	        	    	//TextView  tv4;
	       	 
	                        }
	  }
public Button PlaceOrder,Change;
		
List<String> OrderedItems= new ArrayList<String>();
		public void showOrder(){
		
			setContentView(R.layout.orderpage);
		     Home=(Button)findViewById(R.id.Home);
		     PlaceOrder=(Button)findViewById(R.id.PlaceOrder);
		     Change=(Button)findViewById(R.id.Change);
		     Home.setOnClickListener(this);
		     PlaceOrder.setOnClickListener(this);		     
		     Change.setOnClickListener(this);

//			createOrderlist();
			
			
			int j=0;
			BzpgRowD tempitem;
			for(int i =0; i < bdata.size();i++ ){
			if (bdata.elementAt(i).ItemQuantity != "0")
			{ 
				tempitem=bdata.elementAt(i);
				String tmpstring=tempitem.ItemName + " "+tempitem.option+ "      "+tempitem.ItemQuantity;
				tmpstring=tmpstring.replace("\n"," ");
				OrderedItems.add(tmpstring);
				
				j++;
			}
			}
			
//Add to adapter			
			  ListView listsetview = (ListView) findViewById(R.id.orderlist);
			  listsetview.setAdapter(new ArrayAdapter<String>(this,R.layout.itemslist,OrderedItems ));
			  


			  listsetview.setOnItemClickListener(new OnItemClickListener() 
			  {
			      public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
			      {
			  	//Toast.makeText(Socialnet.this, "" + position,	Toast.LENGTH_SHORT).show();
			  
			      }
		      });		
		}
		
		public void confirmOrder(String bizid){
						
			if(usr_id.contentEquals("-1")){
				Toast.makeText(BizPage.this, "You need to sign in to order",	Toast.LENGTH_SHORT).show();
			}
			else{
				new	placeconfirmOrder().execute(bizid,usr_id,pass);
				
			}
			
		}
		public class placeconfirmOrder extends AsyncTask<String, Void, String[]> {

			private ProgressDialog dialog;
			
			@Override
				  protected void onPreExecute() {
				
				 dialog = ProgressDialog.show(BizPage.this, "","Your order is being Placed...", true);
			} 
			
			
			@Override
			protected String[] doInBackground(String... mess) {
			
		    String[] textorder=null;
		    	HttpClient client = new DefaultHttpClient();
		        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/placeOrderand");
		        List<BasicNameValuePair> nvps = new ArrayList(4+OrderedItems.size());
		       
		        nvps.add(new BasicNameValuePair("bizid", mess[0]));
		        nvps.add(new BasicNameValuePair("usr_id", mess[1]));
		        nvps.add(new BasicNameValuePair("pass", mess[2]));
		        nvps.add(new BasicNameValuePair("countorder",Integer.toString(OrderedItems.size())));
		        
			    for(int i=0; i<OrderedItems.size();i++){
			        	nvps.add(new BasicNameValuePair("order"+i,OrderedItems.get(i)));
			//			Toast.makeText(getApplicationContext(),"deleting  "+textInterest[i],Toast.LENGTH_LONG).show();
				    //	delInterest[cnt]=textInterest[i];

				    }
		        
	        
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
		            
		              textorder=HttpHelper.request(res);

		              
		              
//		              Interestselected= new boolean[textInterest.length];	               
	/*	               for(int i=0; i<textInterest.length;i++){
		   				Toast.makeText(getApplicationContext(),textInterest[i],
			           	          Toast.LENGTH_LONG).show();
//		          		Interestselected[i]=false;
		        		
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
		    	
		    		
				return textorder;
	        }
	        protected void onPostExecute(String[] result) {
	
	        	Toast.makeText(getApplicationContext(),"Your Order has been placed",
	           	          Toast.LENGTH_LONG).show();
//				 InterestAdapter();
				 dialog.dismiss();
		     }
			}

		
		
}
			
			
