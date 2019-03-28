package com.example.socialnet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class IntList extends Activity implements OnClickListener {
	
	public View Frndview;
	private static final String TAG = null;
	private static final int INVISIBLE = 0;
	Button  Search, FriendsB, Interest, Map, Messagefrnd, SendfrndB, send_Button, cancel_Button ;
	TextView contact_name;
	EditText Messg;
    String user, pass;
    ListView lv;
    String Inttext;
    String[] text1;
    String FILENAME="socialnet_file.txt";
    public String mystatus;
    boolean StatusMessage=true;
    
	public InputMethodManager inputManager; View messView;  IBinder windToken;
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
     setContentView(R.layout.intfrnd);
    
     Intent currIntent =  getIntent();
		Inttext = currIntent.getStringExtra("intdata");		        

     
//     Frndview = this.getCurrentFocus();
    friendsname();    
    
    
     Map=(Button)findViewById(R.id.Map);
    Search=(Button)findViewById(R.id.Search);
    FriendsB=(Button)findViewById(R.id.Friends);
    Interest=(Button)findViewById(R.id.Interest);
    SendfrndB=(Button)findViewById(R.id.Sendfrnd);
/*    send_Button=(Button)findViewById(R.id.send_button);
    cancel_Button=(Button)findViewById(R.id.cancel_button);*/
//    Messg=(EditText)findViewById(R.id.messgfrnd);
    
    Search.setOnClickListener(this);
    FriendsB.setOnClickListener(this);
    Interest.setOnClickListener(this);
    Map.setOnClickListener(this);
    SendfrndB.setOnClickListener(this);
  	    	  
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
		   
		   case R.id.Interest:
			   i= new Intent(this, Interest.class);
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
			   
		case R.id.Sendfrnd:
			int visibility = INVISIBLE;
		     v.setVisibility(visibility);
			sendmessage();
			break;

		case R.id.send_button:
			
        		EditText Messg=(EditText)findViewById(R.id.send_message);
     		    String message = Messg.getText().toString(); 
     		//   Toast.makeText(Friends.this, "" +message,	Toast.LENGTH_SHORT).show();
//	    		inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
	         //   inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);					 
				if (StatusMessage){
     		     new SendDataMessage().execute("-1",user,message);}
				else
	            for (int k=0; k < ldata.size(); k++){
	             if	(ldata.elementAt(k).selected){
	            	 new SendDataMessage().execute("-2",user,ldata.elementAt(k).name,message);
	             }
	            }
	            

	            if (inputManager.isActive() && !(message.equals(""))){
					inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);	
			        
				}    	            
		      		        

	            setDisplay();
	   //   		i=new Intent(this, Friends.class);
		//	   startActivity(i);
              break;
		case R.id.cancel_button:
		
/*			if (inputManager.isActive()){
				inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);	
		        
			}*/
              setDisplay();
			//			i=new Intent(this, Friends.class);
	//	   startActivity(i);

		   }
	}

	public void setDisplay(){
		
        setContentView(R.layout.friends);		     
        IaddAdapter(text1);      
        Map=(Button)findViewById(R.id.Map);
        Search=(Button)findViewById(R.id.Search);
        FriendsB=(Button)findViewById(R.id.Friends);
        Interest=(Button)findViewById(R.id.Settings);
        SendfrndB=(Button)findViewById(R.id.Sendfrnd);
        
        Search.setOnClickListener(this);
        FriendsB.setOnClickListener(this);
        Interest.setOnClickListener(this);
        Map.setOnClickListener(this);
        SendfrndB.setOnClickListener(this);
		
	}
/*	public void sendmessage1(){
		 
		    String message = Messg.getText().toString(); 
 		inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
         inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);					 
			new SendDataMessage().execute(message);
		
	
	}*/
	public void sendmessage(){
		//final Intent i;
		setContentView(R.layout.messagedialog);
		send_Button = (Button) findViewById(R.id.send_button);
		cancel_Button = (Button) findViewById(R.id.cancel_button);
//		messView = this.getCurrentFocus();

		inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
	//	windToken = this.getCurrentFocus().getWindowToken();
		send_Button.setOnClickListener(this);
		cancel_Button.setOnClickListener(this);
		 		 
	}

	public class SendDataMessage extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... mess) {
			// TODO Auto-generated method stub
			  HttpClient clientm1 = new DefaultHttpClient(); 
			  HttpPost postm1 = null;
			  //= new HttpPost("http://mobileswan.com/index.php?/socialnet/statusand");
			 List<BasicNameValuePair> nvpsm1 = new ArrayList<BasicNameValuePair>(2);
			 if (mess[0].equals("-1")){
			 nvpsm1.add(new BasicNameValuePair("usr_i", mess[1])); 
			 nvpsm1.add(new BasicNameValuePair("message", mess[2]));
			 postm1 = new HttpPost("http://mobileswan.com/index.php?/socialnet/statusand");
			 }
			 else{
				 postm1 = new HttpPost("http://mobileswan.com/index.php?/socialnet/messageand");
				 nvpsm1.add(new BasicNameValuePair("usr_id", mess[1])); 
				 nvpsm1.add(new BasicNameValuePair("frnd_id", mess[2])); 
				 nvpsm1.add(new BasicNameValuePair("message", mess[3]));
			 }
				 
			 try {
			 postm1.setEntity(new UrlEncodedFormEntity(nvpsm1, HTTP.UTF_8)); }
			 catch (UnsupportedEncodingException e) { 
				 // TODO Auto-generated catch block 
				 StringWriter sw = new StringWriter(); 
				 PrintWriter pw = new PrintWriter(sw); 
				 e.printStackTrace(pw); 
				 } 
			 try { 
				 HttpResponse res =clientm1.execute(postm1); 
				 Log.v(TAG,res.getStatusLine().toString()); 
				 HttpEntity responseEntity = res.getEntity(); 
				 Log.v(TAG, "Set response to responseEntity");
			     String[] textn=HttpHelper.request(res);
				//   Toast.makeText(Friends.this, "" +textn[0],	Toast.LENGTH_SHORT).show();
			 
			  // contact_name.setText(text1[0]); 
			   }
			   catch (ClientProtocolException e) { 
				   // TODO Auto-generated catch block 
				   Log.v(TAG, e.toString());
			  e.printStackTrace(); 
			  } catch (IOException e) { 
				  Log.v(TAG, e.toString()); 
				  // TODO Auto-generated catch block
			  e.printStackTrace(); 
			  }
			return null;
		}		
		}

		
	public void friendsname(){
	//  String user, pass;
	  boolean filetest=false;
	     user="ptots";
	     pass="tots";	    	     
	    if(filetest) {
	    	String[] text2= readFrndStringArray();
	    IaddAdapter(text2);
	    }
	    else{
	     try { 
	    	   InputStream in=openFileInput(FILENAME);
	    	     if(in!=null){
	    	    	 InputStreamReader tmp= new InputStreamReader(in);
	    	    	 BufferedReader reader=new BufferedReader(tmp);
                     user=reader.readLine().toString();
                     pass=reader.readLine().toString();
                     in.close();
	    	     }	    	     
	    	 
				/*RandomAccessFile fos = new RandomAccessFile(FILENAME,"r");
				user=fos.readUTF();
				pass=fos.readUTF();
			    fos.close();*/
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	/*	Log.v(TAG, Username.getText().toString());
        Log.v(TAG, Password.getText().toString());
        contact_name=(TextView)findViewBufferedReaderById(R.id.contact_name);
       */ 
	   //Call asynch task to get data
		new	getFrndsData().execute(user,pass,Inttext);
	    
	    }
	}

	public class getFrndsData extends AsyncTask<String, Void, String[]> {

		private ProgressDialog dialog;
		
		@Override
			  protected void onPreExecute() {
			
			 dialog = ProgressDialog.show(IntList.this, "","Loading. Please wait...", true);
		} 
		
		
		@Override
		protected String[] doInBackground(String... mess) {
		
	    String[] textfrnd=null;
		
	    HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/intfrndsand");
        List nvps = new ArrayList(3);
         nvps.add(new BasicNameValuePair("usr_i", mess[0]));
         nvps.add(new BasicNameValuePair("pss", mess[1]));
         nvps.add(new BasicNameValuePair("interest2", mess[2]));

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
              textfrnd=HttpHelper.request(res);
              // Use this to store frnds data   storeStringArray(user,pass,text1);
        // Call this when not doing asynch      IaddAdapter(text1);         
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
		text1=textfrnd;
		return textfrnd;
	    }
		
		 protected void onPostExecute(String[] result) {
			
			 IaddAdapter(result);
			 dialog.dismiss();
	     }

	
	}
	
	public void storeStringArray(String user, String pass, String[] text1){
		
		String buf = user + "\n" + pass + "\n";
		buf = buf + Integer.toString(text1.length)+ "\n" ;
		for (int i=0; i < text1.length; i++){
			buf = buf+"||" + text1[i];
		}
		
		OutputStreamWriter fos;
		try {
			fos = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
			fos.write(buf);
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] readFrndStringArray(){
		
		String[] frndlist={};
		
		 try { 
	    	   InputStream in=openFileInput(FILENAME);
	    	     if(in!=null){
	    	    	 InputStreamReader tmp= new InputStreamReader(in);
	    	    	 BufferedReader reader=new BufferedReader(tmp);
                   user=reader.readLine().toString();
                   pass=reader.readLine().toString();
                   int lngth=  Integer.parseInt(reader.readLine().toString());
                    String tmp1 = reader.readLine().toString();
                    frndlist = tmp1.split("||");
                   /*for(int i =0; i < lngth;i++){
                	   tmp1 =   reader.readLine().toString();
                	   frndlist[i]=tmp1; 
                   }*/
                   in.close();
	    	     }	    	     
	    	 

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return frndlist;
	}
  

	 private  Vector<RowD> ldata;
	 private RowD rd;
	 private class RowD {
	 String name="";
	 String status="";
	 String statustime="";

	 String mesg="";
	 String mesgtime="";
	 String email="";
	 String image="";
	 boolean selected;
	 
	RowD(String S0, String S1, String S2, String S3, String S4, String S5, String Img ){
		mesg=S0;
		mesgtime=S1;
		name= S2;
		status=S3;
		statustime=S4;
		email =S5;
		image = Img;
		selected=false;

	}
	 }
	 
	  public void IaddAdapter(String[] text1){

         mystatus=text1[0];
         TextView statusView=  (TextView) findViewById(R.id.statustxt);
         statusView.setText("Me:"+" \""+mystatus+ "\""+"["+text1[1]+"]");
		  ldata= new Vector<RowD>();
		  int lng = (text1.length-2)/7;
		  int l= 2+lng+lng+lng;
		  lng=1+2;
		  
		  while (l   < text1.length ) {
			  
//		      rd = new RowD(text1[i], "Hi, I am at Sbaucks", "kapoor@iit.edu",1);
		      rd = new RowD(text1[lng].toString(),text1[lng+1].toString(),text1[l].toString(),text1[l+1].toString(),text1[l+2].toString(),text1[l+3].toString(),text1[l].toString());
		      l=l+4;lng= lng+3;
		  ldata.add(rd);
		  }
		  	  
		  ListView listsetview = (ListView) findViewById(R.id.list);
		  listsetview.setAdapter(new FriendsArrayAdapter(this,R.layout.picbitemlist, ldata ));
//		  listsetview.setAdapter(new ArrayAdapter<String>(this,R.layout.itemlist, data ));
		  
	/*	  listsetview.setAdapter(new LImageAdapter(this));

*/
		  listsetview.setOnItemClickListener(new OnItemClickListener() 
		  {
		      public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
		      {
//		  	Toast.makeText(Friends.this, "" + position,	Toast.LENGTH_SHORT).show();
		  	
//		  	Intent CatIntent = new Intent();
	//	  	CatIntent.setClassName("com.example.socialnet", "com.example.socialnet.Categories");
		//  	CatIntent.putExtra("gridposition", position);
		      }
	      });
	  }
	  
	  class FriendsArrayAdapter extends ArrayAdapter<RowD> {
		    private Context mContext;
		    private LayoutInflater Lview;
				
		   public FriendsArrayAdapter(Context context, int textViewResourceId,
				Vector<RowD> objects) {
			super(context, textViewResourceId, objects);
			mContext=context;
       
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

	    	View MyView = convertView;
	//    	final View Mview1;

	    	ViewHolder holder=null;
	    	TextView tv=null;
	    	ImageView[] iv1=null;
	        RowD  rdh = getItem(position);
	        final RowD rdh1 = rdh;
	
           
            
 	        if (convertView !=null) holder= (ViewHolder) MyView.getTag();
	        if ((convertView == null)||(holder==null)) { // if it's not recycled, initialize some
	                                    // attributes	       

	         	Lview = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
		        MyView =  Lview.inflate(R.layout.picbitemlist, parent, false);
	    //    	MyView.setLayoutParams(new GridView.LayoutParams(90, 90))
		        holder = new ViewHolder();
	            holder.tv1 = (TextView) MyView.findViewById(R.id.text13it);
	            holder.tv2 = (TextView) MyView.findViewById(R.id.text23it);
	            holder.tv3 = (TextView) MyView.findViewById(R.id.text33it);
	            holder.iv1 = (ImageView) MyView.findViewById(R.id.img3item);
	           // holder.button= (Button) MyView.findViewById(R.id.button3it);
	            //MyView.findViewById(R.id.button3it).setTag(rdh.email);
	           // MyView.findViewById(R.id.button3it).setTag(rdh.email);
	 	       final CheckBox checkbox = (CheckBox) MyView.findViewById(R.id.frndcheckbox);
		       checkbox.setOnClickListener(new OnClickListener() {
		           public void onClick(View v) {
		               // Perform action on clicks, depending on whether it's now checked
		               if (((CheckBox) v).isChecked()) {
		            	  // e-maillist.add(position);
		                   Toast.makeText(IntList.this, "Selected"+rdh1.name, Toast.LENGTH_SHORT).show();
		                   rdh1.selected=true;
		                   StatusMessage = false;
		               } else {
		                   Toast.makeText(IntList.this, "Not selected", Toast.LENGTH_SHORT).show();
		                   rdh1.selected=false;
		               }
		           }
		       });		    	
	        } 
	        else {
	             holder= (ViewHolder) MyView.getTag();//convertView;
	        }
	        
//	       holder.iv.setAdjustViewBounds(true);
	  //     holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);     
	    //   holder.iv.setPadding(8, 8, 8, 8); 
	       //holder.tv.setText("Item "+ position); 
	       holder.tv1.setText(rdh.name);      
	       holder.tv2.setText("\""+rdh.status+"\""+"\n ["+rdh.statustime+"]");
	       holder.tv3.setText("\""+rdh.mesg+"\""+"\n ["+rdh.mesgtime+"]");
	       
	 //      holder.iv1.setImageResource(R.drawable.pinblue);
	       Drawable image = ImageOperations(this.getContext(),
	    		   "http://mobileswan.com/system/application/views/images/"+rdh.name+".gif");
	       
	       holder.iv1.setImageDrawable(image);
	       

//	       Mview1 = MyView;
	       
	     
	       return MyView;
	    }
		
		private Drawable ImageOperations(Context ctx, String url) {
			try {
				InputStream is = (InputStream) this.fetch(url);
				Drawable d = Drawable.createFromStream(is, "src");
				return d;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		
		
		public Object fetch(String address) throws MalformedURLException,IOException {
			URL url = new URL(address);
			Object content = url.getContent();
			return content;
		}
		    	
      class ViewHolder {
	        	    	ImageView iv1;
	        	    	TextView tv1;
	        	    	TextView  tv2;
	        	    	TextView  tv3;
	        	    	Button button;
	        	 
	                        }
	  }
	  //class ImagesId[]
	  

}



