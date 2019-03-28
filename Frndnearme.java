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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Frndnearme extends Activity implements OnClickListener {
	
	Button  Search, Friends, Settings, Map ;
	 ListView lv1;

    private String user_id, search1, search2;
    private String TAG = "Login";
    private String FILENAME1="socialnet_file.txt";
    
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
      setContentView(R.layout.friendsnearme);
       
      friendname();

       Map=(Button)findViewById(R.id.Map);
       Search=(Button)findViewById(R.id.Search);
       Friends=(Button)findViewById(R.id.Friends);
       Settings=(Button)findViewById(R.id.Settings);
       
       
       Search.setOnClickListener(this);
       Friends.setOnClickListener(this);
       Settings.setOnClickListener(this);
       Map.setOnClickListener(this);
     
       lv1.setOnItemClickListener(new OnItemClickListener() {
     	    public void onItemClick(AdapterView<?> parent, View view,
     	        int position, long id) {
     	      // When clicked, show a toast with the TextView text
     	      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
     	          Toast.LENGTH_SHORT).show();
     	    }
     	    });
        
	}
	
	private void friendname(){
		 search1 = "friends";
	        search2 = "location";
	        user_id="-1";
	   //     user_id=Filehelper.username()[0];
	        try { 
	            
	     	   InputStream in=openFileInput(FILENAME1);
	     	     if(in!=null){
	     	    	 InputStreamReader tmp= new InputStreamReader(in);
	     	    	 BufferedReader reader=new BufferedReader(tmp);
	                user_id=reader.readLine().toString();
	              //  pass=reader.readLine().toString();
	                in.close();
	     	    	 
	     	     }
	 	
	 	 } catch (FileNotFoundException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	     
	        if(user_id != "-1"){
	        
	        HttpClient client01 = new DefaultHttpClient();
	        HttpPost post01 = new HttpPost("http://mobileswan.com/index.php?/socialnet/searchand");
	        List nvps1 = new ArrayList(2);
	        nvps1.add(new BasicNameValuePair("user_id", user_id));
	         nvps1.add(new BasicNameValuePair("search1", search1));
	         nvps1.add(new BasicNameValuePair("search2", search2));
	         try {
	 			post01.setEntity(new UrlEncodedFormEntity(nvps1, HTTP.UTF_8));
	 		} catch (UnsupportedEncodingException e) {
	 			// TODO Auto-generated catch block
	 			 StringWriter sw = new StringWriter();
	 			    PrintWriter pw = new PrintWriter(sw);
	 			e.printStackTrace(pw);
	 		}
	         try {
	         	HttpResponse res =client01.execute(post01);
	         	 Log.v(TAG, res.getStatusLine().toString());
	               HttpEntity responseEntity = res.getEntity();
	               Log.v(TAG, "Set response to responseEntity");
	               String[] text2=HttpHelper.request(res);
	               addAdapter(text2);         
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
	       }
	        else{
	        	Toast.makeText(getApplicationContext(), "User name not available",
	       	          Toast.LENGTH_SHORT).show();
	        }
	     }
	
	private void addAdapter(String[] text2) {
		// TODO Auto-generated method stub
  	    //setListAdapter(new ArrayAdapter<String>(this,R.layout.itemlist, COUNTRIES));
           lv1 = (ListView) findViewById(R.id.list1);
         
          
           lv1.setAdapter(new ArrayAdapter<String>(this,R.layout.itemslist, text2 ));
    	//  lv = getListView();
    	//  lv.setTextFilterEnabled(true);
		
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
