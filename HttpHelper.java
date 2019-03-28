package com.example.socialnet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.json.JSONArray;

import android.util.Log;

public class HttpHelper {
	private static final String TAG = "Login";
	
	public static String cleanXML(String s){
		
		String ret=null;
		
		ret= s.replace("&amp;","&");
		ret=ret.replace("&lt;","<");
		ret=ret.replace("&quot;", "\"");
		ret=ret.replace("&gt;",">");
		ret=ret.replace("&apos","\'");		
		return ret;
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
	            int i=0;
	            while((line = reader.readLine()) != null){
	                if (i!=0){ str.append("\n");
	                }
	                i++;
	            	str.append(line);
	            }
	            in.close();
	            str1 = str.toString();
	            
	        }catch(Exception ex){
	            result = new String[] {"Error"};
	        }
	      
	        String strx=str1;
	            strx=str1.replace("][", ",");
	        	str1=strx.replace("[\"", "");
	        	strx=str1.replace("\"]", "");
	        if (strx != null || !strx.equalsIgnoreCase(""))
	        {
	        
	        	result = strx.split("\",\"");
	        }
	       for(int i=0;i<result.length;i++)
	    	   result[i]=cleanXML(result[i]);
	        
	       return result;  
	       
		 //old code end
	    }
	 
	 
	 
}
