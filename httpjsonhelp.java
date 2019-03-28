/*package com.example.socialnet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class httpjsonhelp {
	
		private static final String TAG = "Login";
		static int size=0;
		
		public int insize(){
			return size;
				
		}
		 public static String[] request(HttpResponse res) throws JSONException{
			 Log.v(TAG, "entered httphelper class");
		     String str1 = null;  
			 String[] result = null;
			
			//old code start
		        
		      try{
		            InputStream in = res.getEntity().getContent();
		            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		            StringBuilder str = new StringBuilder();
		            String line = null;
		            while((line = reader.readLine()) != null){
		                str.append(line + "\n");
		            }
		            in.close();
		            str1 = str.toString();
		            
		        }catch(Exception ex){
		            result = new String[] {"Error"};
		        }
		        
		       
		        JSONArray entries = new JSONArray(str1);
		        int i;
		        for  (i=0;i<entries.length();i++){
		        	JSONObject post = entries.getJSONObject(i);
		        	result[i]= post.getString(null);
		        }
		        
		        size = entries.length();
		        return result;
				
		        
		        /*
		        //old code
		            String strx=str1;
		        	strx=str1.replace("[\"", "");
		        	str1=strx.replace("\"]", "");
		        if (str1 != null || !str1.equalsIgnoreCase(""))
		        {
		        
		        	result = str1.split("\",\"");
		        }
		       
		        
		       return result;  
		       
			 //old code end
	
		        
		    }






}
*/
package com.example.socialnet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.json.JSONArray;

import android.util.Log;



public class httpjsonhelp {
	private static final String TAG = "Login";
	
	static int size=0;

	public int insize(){
		return size;
			
	}
	
	 public static String[] request(HttpResponse res){
		 Log.v(TAG, "entered httphelper class");
	     String str1 = null;  
		 String[] result = null;
		
		//old code start
	        
	      try{
	            InputStream in = res.getEntity().getContent();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            StringBuilder str = new StringBuilder();
	            String line = null;
	            while((line = reader.readLine()) != null){
	                str.append(line + "\n");
	            }
	            in.close();
	            str1 = str.toString();
	            
	        }catch(Exception ex){
	            result = new String[] {"Error"};
	        }
	        String strx=str1;
	        	strx=str1.replace("[\"", "");
	        	str1=strx.replace("\"]", "");
	        	String str2=str1.concat("\",\"EOVAL");
	        if (str2 != null || !str2.equalsIgnoreCase(""))
	        {
	        
	        	result = str2.split("\",\"");
	        }
	       
	        
	       return result;  
	       
		 //old code end
	    }
	 
	 
	 
}
