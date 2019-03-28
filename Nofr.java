package com.example.socialnet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Nofr extends Activity implements OnClickListener {
	
	
	Button  Submit, Search, Friends, Settings, Map ;
	EditText Username, Password;
	String user, pass;
	String FILENAME="socialnet_file";
	
	TextView contact_name;
	
	@Override
   
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.nofr);
        
       
        
        Search=(Button)findViewById(R.id.Search);
        Friends=(Button)findViewById(R.id.Friends);
        Settings=(Button)findViewById(R.id.Settings);
        Map=(Button)findViewById(R.id.Map);
       Submit=(Button)findViewById(R.id.Submit02);
        
        Submit.setOnClickListener(this);
        
        
      
        
        
     
      /* user="ptota";
        pass="tota"; 
        
        */
      
      
        
        
        Search.setOnClickListener(this);
       Friends.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Map.setOnClickListener(this);
	}
	
	
        public void onClick(View v) {
    		
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
    		   
    		  case R.id.Submit02:
    	
    			  Username=(EditText)findViewById(R.id.Username);
    		        Password=(EditText)findViewById(R.id.Password);
    		        user = Username.getText().toString(); 
    		        pass = Password.getText().toString();
    		        
				try {
					FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fos.write(user.getBytes());
					fos.write(pass.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
    		        
    		        /*   Log.v(TAG, Username.getText().toString());
    		        Log.v(TAG, Password.getText().toString());
    		        contact_name=(TextView)findViewById(R.id.contact_name);
    		        
    		        HttpClient client = new DefaultHttpClient();
    		        HttpPost post = new HttpPost("http://64.131.105.191/codeigniter/index.php/socialnet/signinand");
    		        List nvps = new ArrayList(2);
    		         nvps.add(new BasicNameValuePair("usr_i", user));
    		         nvps.add(new BasicNameValuePair("pss", pass));
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
    	              //    String[] text1=HttpHelper.request(res);
    	                  
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
    				*/
    				
    		        break;
    			   
    		   }
}

}

	
