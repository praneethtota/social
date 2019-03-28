package com.example.socialnet;

import java.io.IOException;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addfrnd extends Activity implements OnClickListener {

	Button SendFrnd, CancelFrnd;
	String user, pass;
	
	public InputMethodManager inputManager;
	public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfrienddialog);
        Intent currIntent =  getIntent();
		user = currIntent.getStringExtra("username");		        
		pass = currIntent.getStringExtra("passwd");		        
        SendFrnd = (Button) findViewById(R.id.addfrnd_button);
        CancelFrnd = (Button) findViewById(R.id.cancelfrnd_button);
//	messView = this.getCurrentFocus();

	inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
//	windToken = this.getCurrentFocus().getWindowToken();
	SendFrnd.setOnClickListener(this);
	CancelFrnd.setOnClickListener(this);
	
	}
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
	Intent i;	
	//String user= "sktest";
	//String pass="sktest";
	switch(v.getId())
	   {
	
	   case R.id.addfrnd_button:
		EditText Frndnameinput=(EditText)findViewById(R.id.send_frndname);
		    String frndname = Frndnameinput.getText().toString(); 
		//   Toast.makeText(Friends.this, "" +message,	Toast.LENGTH_SHORT).show();
//		inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
     //   inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);					 
		    new AddFrndName().execute(user,pass,frndname);
	    i=new Intent(this, Friends.class);
		startActivity(i);
	   break;
	   
	   case R.id.cancelfrnd_button:
		   i=new Intent(this, Friends.class);
			startActivity(i);
	   break;
		
	}
	}
	public class AddFrndName extends AsyncTask<String, Void, String[]> {

		private ProgressDialog dialog;
		
		@Override
			  protected void onPreExecute() {
			
			 dialog = ProgressDialog.show(addfrnd.this, "","Sending. Please wait...", true);
		} 
		
		
		@Override
		protected String[] doInBackground(String... mess) {
		String[] textfrnd=null;
		
	    	HttpClient client = new DefaultHttpClient();
	        HttpPost post1 = new HttpPost("http://mobileswan.com/index.php?/socialnet/addfrndand");
	        List<BasicNameValuePair> nvps1 = new ArrayList<BasicNameValuePair>(3);
	        nvps1.add(new BasicNameValuePair("usr_i", mess[0]));
	        nvps1.add(new BasicNameValuePair("pss", mess[1]));
	        nvps1.add(new BasicNameValuePair("friendname", mess[2]));
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
	             
	              textfrnd=HttpHelper.request(res);
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
			
			return textfrnd;
        }
        protected void onPostExecute(String[] result) {
			
//    		Toast.makeText(getApplicationContext(), result[0], Toast.LENGTH_LONG).show();
//       		 setContentView(R.layout.interestgrid);
//			 InterestAdapter();
			 dialog.dismiss();
	     }
		}

	
	
	
	
	
	
	
}


