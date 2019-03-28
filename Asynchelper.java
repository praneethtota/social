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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class Asynchelper<Context> {

	
	
	public class  AddInterestData extends AsyncTask<String, Void, String[]> {

		private ProgressDialog dialog;
		
		@Override
			  protected void onPreExecute() {
			
//			 dialog = ProgressDialog.show(this, "","Sending. Please wait...", true);
			
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
		//		Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
          // 	          Toast.LENGTH_LONG).show();
			   }
	        try {
	        	HttpResponse res =client.execute(post1);
	        	
	              HttpEntity responseEntity = res.getEntity();
	             
	            //  textInterest=HttpHelper.request(res);
/*	              Interestselected= new boolean[textInterest.length];	               
	               for( int i=0; i<textInterest.length;i++){
	        		
	          		Interestselected[i]=false;
	        		
	          	}*/
		//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
	            	              
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
		//		Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",
	      //     	          Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			//	Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
	          // 	          Toast.LENGTH_LONG).show();
			}
			
			return  null;
        }
        protected void onPostExecute(String[] result) {
			
//    		Toast.makeText(getApplicationContext(), result[0], Toast.LENGTH_LONG).show();
//       		 setContentView(R.layout.interestgrid);
//			 InterestAdapter();
			 dialog.dismiss();
	     }
		}

	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

