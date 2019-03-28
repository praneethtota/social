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
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public  class CheckMessage extends Service {

	  private NotificationManager mNM;
	//  private int NOTIFICATION = R.string.local_service_started;

   String user1="sktest";
   String pass1="sktest";
   String TAG=null;
   String ns = Context.NOTIFICATION_SERVICE;   
   private static Settings MAIN_ACTIVITY;
   private static int NOTIFICATION = 1;
   private String FILENAME1="socialnet_file.txt";
   
   public static void setMainActivity(Settings activity) {
	   MAIN_ACTIVITY = activity;
	 }


   
   
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public void onCreate() {
		  super.onCreate();

		  // init the service here
		  try { 
	            
	     	   InputStream in=openFileInput(FILENAME1);
	     	     if(in!=null){
	     	    	 InputStreamReader tmp= new InputStreamReader(in);
	     	    	 BufferedReader reader=new BufferedReader(tmp);
	               
                    user1=reader.readLine().toString();
                    pass1=reader.readLine().toString();

	              //  pass=reader.readLine().toString();
	                in.close();
	                startService();
	      		  mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
 	    	 
	     	     }
	 	
	 	 } catch (FileNotFoundException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
		
	        // Display a notification about us starting.  We put an icon in the status bar.
	       //showNotification();


//		  if (MAIN_ACTIVITY != null) AppUtils.showToastShort(MAIN_ACTIVITY, "CheckMessage Service started");
		}
	
	
	@Override
	public void onDestroy() {
		  super.onDestroy();

	//	  stopService();
	//	  mNM.cancel(NOTIFICATION);


	//	  if (MAIN_ACTIVITY != null) AppUtils.showToastShort(MAIN_ACTIVITY, "Check Message Service stopped");
		}

	private Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 10000;


  public void startService() {

	  timer.scheduleAtFixedRate(
		      new TimerTask() {
		        public void run() {
		          getDataUpdate();
		        }
		      },
		      0,
		      UPDATE_INTERVAL);

	
  }
  
  public void getDataUpdate() {
	  
	  String[] Datastatus;
	  Boolean success=false;
	  
	  HttpClient client = new DefaultHttpClient();
      HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/checkmesgstatus");
      List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>(2);
      nvps.add(new BasicNameValuePair("usr_i",user1));
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
			//Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
     	      //    Toast.LENGTH_LONG).show();
		   }
      try {
      	HttpResponse res =client.execute(post);
      	 Log.v(TAG, res.getStatusLine().toString());
            HttpEntity responseEntity = res.getEntity();
            Log.v(TAG, "Set response to responseEntity");
            Datastatus=HttpHelper.request(res);					
	//		  String test = "PLEASE CHECK USERNAME AND"+ "\n" + "PASSWORD";
            if (Datastatus[0].contains("NEWDATA")){  success=true;
//            else success=true;
            
		       	 }
            else
            { // Toast.makeText(getApplicationContext(),"Encoding Error: Press Back button",
             	//          Toast.LENGTH_LONG).show();
            }
          	
            
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.v(TAG, e.toString());
			e.printStackTrace();
			//Toast.makeText(getApplicationContext(),"Connection Error:Press Back button",Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Log.v(TAG, e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Toast.makeText(getApplicationContext(),"Connection Exception: Press Back button",
  //       	          Toast.LENGTH_LONG).show();
		}
	  
	  if (success) {
		  showNotification();  
/*		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.setLatestEventInfo(this, getText(R.string.hello),"Messg",contentIntent);
		  mNM.notify(NOTIFICATION, notification); */
	  }
	  
  }
  
  private Notification notification;
  private  PendingIntent contentIntent;
  
  private void showNotification() {
      // In this sample, we'll use the same text for the ticker and the expanded notification
      CharSequence text = "Swan";//getText(R.string.hello);

      // Set the icon, scrolling text and timestamp
      notification = new Notification(R.drawable.swan, text,
              System.currentTimeMillis());

      // The PendingIntent to launch our activity if the user selects this notification
      contentIntent = PendingIntent.getActivity(this, 0,
              new Intent(this, CheckMessage.class), 0);
      notification.flags |= Notification.FLAG_AUTO_CANCEL;
      notification.defaults |= Notification.DEFAULT_SOUND;
      // Set the info for the views that show in the notification panel.
      notification.setLatestEventInfo(this, "Swan",//getText(R.string.hello),
                     "You have a new Message", contentIntent);

      // Send the notification.
      mNM.notify(NOTIFICATION, notification);
  }

  
}
