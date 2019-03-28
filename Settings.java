

package com.example.socialnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

import com.example.socialnet.Categories.CImageAdapter.ViewHolder;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

	


 public class Settings extends Activity implements OnClickListener {
	
	Button  Submit, ChangeU, Interest;
	ImageButton Search, Friends, Settings; 
	EditText Username, Password;
	public String user, pass;
	String FILENAME="socialnet_file.txt"; /* stores user name and password */
	String TAG = null;
	TextView contact_name;
	boolean loggedIn = false;
	public String[] textInterest;
    public	String[] locationString = {"My Location On","Accuracy Level: Coarse (Network)" };
    public int ItemChecked=1;
	
	@Override
   
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user="-1";
		try{
			InputStream in = openFileInput(FILENAME);
			if(in!=null){
   	    	 InputStreamReader tmp= new InputStreamReader(in);
   	    	 BufferedReader reader=new BufferedReader(tmp);
   	         user=reader.readLine().toString();
   	         pass=reader.readLine().toString();
   	         locationString[0]=reader.readLine().toString();
    	     locationString[1]=reader.readLine().toString();
    	
//	                user_data[1]=reader.readLine().toString();
   	         in.close();	     
			}	    	     

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    //  	 user=Socialnet.username()[0];
     if (user!="-1") loggedIn=true;
/*        InputStream in;
		try {
			in = openFileInput(FILENAME);
			if (in != null){loggedIn = true; in.close();}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  */      
     if (!loggedIn)   {
        
        setContentView(R.layout.settings);
        
        Search=(ImageButton)findViewById(R.id.Search);
        Friends=(ImageButton)findViewById(R.id.Friends);
        Settings=(ImageButton)findViewById(R.id.Settings);
        Interest=(Button)findViewById(R.id.Interest);
        Submit=(Button)findViewById(R.id.Submit02);
        
        Submit.setOnClickListener(this);
             
        Search.setOnClickListener(this);
       Friends.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Interest.setOnClickListener(this);
        }
       
     else
        {
        	
        	startloggedIn(user, pass);
        	
        } 	
	}

	
        public void onClick(View v) {
    		
    		Intent i;
    		
    		// TODO Auto-generated method stub
    		   switch(v.getId())
    		   {
    		   case R.id.Friendslin:
    		   case R.id.Friends: 
    			   i=new Intent(this, Friends.class);
    			   startActivity(i);
    			   break;
    		   
    		   case R.id.Settings:
    		   case R.id.Settingslin:
    			   i= new Intent(this, Settings.class);
    			   startActivity(i);
    			   break;
    			   
    		   case R.id.Interest:
    		   case R.id.Intlin:
    			   i= new Intent(this, Interest.class);
    			   startActivity(i);
    			   break;
    			   
    		   case R.id.Search:
    		   case R.id.Searchlin:
    			   i= new Intent(this, Socialnet.class);
    			   startActivity(i);
    			   break;
    			   
    		   case R.id.Changeusr:
    			   deleteFile(FILENAME);
/*    			   File filen= new File(FILENAME);
    			   if (file.delete()){
    				   int j=10;
    			   }*/
    			   Intent svc= new Intent(this, CheckMessage.class);
    	        	stopService(svc);
    			   i= new Intent(this, Settings.class);
    			   startActivity(i);
    			   break;
    		   
    		  case R.id.Submit02:      
    	            Username=(EditText)findViewById(R.id.Username);
    		        Password=(EditText)findViewById(R.id.Password);
    		        user = Username.getText().toString(); 
    		        pass = Password.getText().toString();
    		        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE); 
    		        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    	    	    if (startloggedIn(user,pass)){
    	    	    writeToUserName();
    	    	    }
					//startloggedIn();
					break;
    		   }
        }

        public void writeToUserName(){
        	
	        try {
		        String buf=user+"\n"+pass+"\n"+locationString[0]+"\n"+locationString[1];

	        	OutputStreamWriter fos = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
				fos.write(buf);
                 fos.close();
                 CharSequence text = "user data saved!";
                 Toast.makeText(getApplicationContext(),text,
             	          Toast.LENGTH_SHORT).show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Press Back button",
	           	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Press Back button",
	           	          Toast.LENGTH_LONG).show();
			}

        }
    		        				
  public boolean startloggedIn(String user1, String pass1) {
		    
	  boolean success=false;
	  
	     		
    		
	        HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/signinand");
	        List nvps = new ArrayList(2);
	        nvps.add(new BasicNameValuePair("usr_i", user1));
	        nvps.add(new BasicNameValuePair("pss", pass1));
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
	        	 Log.v(TAG, res.getStatusLine().toString());
	              HttpEntity responseEntity = res.getEntity();
	              Log.v(TAG, "Set response to responseEntity");
	              textInterest=HttpHelper.request(res);					
		//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
	              if (!textInterest[0].contains("PLEASE CHECK USERNAME")){  success=true;
//	              else success=true;
	              
			       	 }
	              else
	              {  Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
	               	          Toast.LENGTH_LONG).show();
	              }
	            	
	              
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.v(TAG, e.toString());
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",
	           	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Log.v(TAG, e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
	           	          Toast.LENGTH_LONG).show();
			}
        if (!success){	
        	Toast.makeText(getApplicationContext(),"Press Back Button",Toast.LENGTH_LONG).show();
        }
        else{
        	setDisplaySettings();

        }
		return success;		
  }
  
  public void setDisplaySettings() {
	  
	setContentView(R.layout.settingsloggedin);
  	
  	TextView textuser= (TextView) findViewById(R.id.TextViewlogin);
  	textuser.setText("Hi,"+user);
  	ChangeU=(Button) findViewById(R.id.Changeusr);
  	  Search=(ImageButton)findViewById(R.id.Searchlin);
      Friends=(ImageButton)findViewById(R.id.Friendslin);
      Settings=(ImageButton)findViewById(R.id.Settingslin);
      Interest=(Button)findViewById(R.id.Intlin);
  	
      Search.setOnClickListener(this);
      Friends.setOnClickListener(this);
      Settings.setOnClickListener(this);
      Interest.setOnClickListener(this);
      ChangeU.setOnClickListener(this);					
      //starttheservice;
        try{
        	//CheckMessage.setMainActivity(this);
        	Intent svc= new Intent(this, CheckMessage.class);
        	startService(svc);
        }
        catch (Exception e){
        }
        
      
	     PrefAdapter(textInterest);
  }
        
 private  Vector<RowD> ldata;
 private RowD rd;
 private class RowD {
 String title="";
 String detail="";
RowD(String S1, String[] S2){
	title= S1;
	detail=S2[0];
	for (int i =1; i < S2.length;i++){
		detail = detail + ", " + S2[i];
	}
}
 }
 
  public void PrefAdapter(String[] text1){

	  
	  ldata= new Vector<RowD>();
	  rd = new RowD("Interest List", text1);
	  ldata.add(rd);
	  rd = new RowD("Location Settings", locationString);
	  ldata.add(rd);
	  	  
	  ListView listsetview = (ListView) findViewById(R.id.settinglist);
	  listsetview.setAdapter(new SettingsArrayAdapter(this,R.layout.twoitemlist, ldata ));
//	  listsetview.setAdapter(new ArrayAdapter<String>(this,R.layout.itemlist, data ));
	  
//	  listsetview.setAdapter(new LImageAdapter(this));


	  listsetview.setOnItemClickListener(new OnItemClickListener() 
	  {
	      public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
	      {
	  	//Toast.makeText(Socialnet.this, "" + position,	Toast.LENGTH_SHORT).show();
	  	switch(position){
	  	case 0:
	  		Toast.makeText(Settings.this, "interest list change under construction",	Toast.LENGTH_SHORT).show();
	  		break;
	  	case 1:
	  		//Toast.makeText(Settings.this, "location settings under construction",	Toast.LENGTH_SHORT).show();
	  		locationsettings();
	  	}
	  
	      }
      });
  }
  
  public class SettingsArrayAdapter extends ArrayAdapter<RowD> {
	    private Context mContext;
	    private LayoutInflater Lview;
	public SettingsArrayAdapter(Context context, int textViewResourceId,
			List<RowD> objects) {
		super(context, textViewResourceId, objects);
		mContext=context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	     
    	View MyView = convertView;
    	ViewHolder holder;
    	TextView tv=null;
    	ImageView iv=null;
    	RowD rdh = getItem(position);
        if (convertView == null) { // if it's not recycled, initialize some
                                    // attributes

       
         	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       
	        MyView =  Lview.inflate(R.layout.twoitemlist, parent, false);
    //    	MyView.setLayoutParams(new GridView.LayoutParams(90, 90));
	                	        
	        holder= new ViewHolder();
            holder.tv1 = (TextView) MyView.findViewById(R.id.title);
            holder.tv2 = (TextView) MyView.findViewById(R.id.detail);
          
        	MyView.setTag(holder);
          
        } 
        else {
             holder= (ViewHolder) MyView.getTag();//convertView;
        }
        
//       holder.iv.setAdjustViewBounds(true);
  //     holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);     
    //   holder.iv.setPadding(8, 8, 8, 8); 
       //holder.tv.setText("Item "+ position); 
       holder.tv1.setText(rdh.title);      
       holder.tv2.setText(rdh.detail);
       return MyView;
    }

    class ViewHolder {
    	TextView tv1;
    	TextView  tv2;
    }
    

}
  
  public void locationsettings(){
  final CharSequence[] items = {"Location off", "Location coarse(Network)", "Location fine(GPS)"};
  
  AlertDialog.Builder builder = new AlertDialog.Builder(this);
  if (locationString[0].equals("My Location Off")) {ItemChecked =0;}
  else {
	  if (locationString[1].equals("Accuracy Level: Coarse (Network)"))
		  {ItemChecked=1;}
	  else ItemChecked=2;
  }
  
  builder.setTitle("My Location Settings");
  
  builder.setSingleChoiceItems(items, ItemChecked, new DialogInterface.OnClickListener(){
  
      public void onClick(DialogInterface dialog, int item) {
          Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
          switch(item){
          case 0:
        	  locationString[0]= "My Location Off";
        	  ItemChecked=0;
        	  break;
          case 1:
        	  locationString[0]="My Location On";
        	  locationString[1]= "Accuracy Level: Coarse (Network)";
        	  ItemChecked=1;
        	  break;
          case 2:
        	  locationString[0]="My Location On";
        	  locationString[1]= "Accuracy Level: Fine (GPS)";
        	  ItemChecked=2;
        	  break;
          }
          writeToUserName();
          dialog.dismiss();
          setDisplaySettings();
      }
  });
  final AlertDialog alert =  builder.create();
  alert.show();
  }
 }