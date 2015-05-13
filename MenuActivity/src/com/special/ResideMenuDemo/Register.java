package com.special.ResideMenuDemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil.log;
import com.avos.avoscloud.RequestMobileCodeCallback;

/**
 * A login screen that offers login via email/password.
 */
@SuppressLint("NewApi")
public class Register extends Activity implements LoaderCallbacks<Cursor> {

	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"1@a.com:123", "pjt73651@gmail.com:joke" };
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mTelView;
	private EditText mMesgView;
	private View mProgressView;
	private View mLoginFormView;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registerin);
		mTelView = (AutoCompleteTextView) findViewById(R.id.tel);
		mMesgView=(EditText)findViewById(R.id.message);

		Button messageButton=(Button) findViewById(R.id.message_register_in_button);
		Button registerButton=(Button) findViewById(R.id.register_in_button);

		messageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				sendmessage();				
			}
		});
		
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptRegisterin();			
			}
		});
		//mLoginFormView = findViewById(R.id.login_form);
		//mProgressView = findViewById(R.id.login_progress);
	}
    Handler  hd = new Handler (){  
        public void handleMessage (Message msg)  
        {  
            super.handleMessage(msg);     
            myAction();                         
        }  
    };  
    Thread th = new Thread(){  
        public  void run ()  
        {  
            Message m= new Message();
            hd.sendMessage(m);  
            }  
    }; 
    public void myAction(){
    	AVQuery<AVObject> query=new AVQuery<AVObject>("UserList");
		final String tel=mTelView.getText().toString();
		query.whereEqualTo("ID", tel);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> user,AVException e){
				if(user.size()==0){
					AVOSCloud.requestSMSCodeInBackgroud(tel,"1km","×¢²á·þÎñ", 60, new RequestMobileCodeCallback(){
						 @Override
					      public void done(AVException e) {
							 
					      }
					});
				}
				else{
					showError("This user has already existed");
				}
			}
		});
    }
	public void sendmessage(){
		th.start();
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptRegisterin(){
		if(mAuthTask!=null){
			return ;
		}
		mTelView.setError(null);
		mMesgView.setError(null);
		
		String tel = mTelView.getText().toString();
		String password = mMesgView.getText().toString();
		if (!TextUtils.isEmpty(tel) && TextUtils.isEmpty(password))
		{
			showError("Please input message");
		}
		// Check for a valid email address.
		if (TextUtils.isEmpty(tel)) {
			showError("Please Input Telphone number");
		}
		if(!TextUtils.isEmpty(tel)&&!TextUtils.isEmpty(password)){
			checkuser(tel,password);
		}
	}
	
	private void checkuser(final String email,final String password){
		
		AVOSCloud.verifySMSCodeInBackground(password,email, new AVMobilePhoneVerifyCallback() {

		      @Override
		      public void done(AVException e) {
					TurnControl.user_ID=email;
					Intent intent=new Intent();
					intent.setClass(Register.this,RegisterPasswordActivity.class);
					startActivity(intent);
					Register.this.finish();
					//showProgress(true);
		      }
		    });				
	}
	private void showError(String errStr) {
		Toast errToast = Toast.makeText(this, errStr, Toast.LENGTH_SHORT);
		errToast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 50);
		errToast.show();
	}


	
	

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
		}

	}


	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(100);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Intent intent=new Intent();
				intent.setClass(Register.this, MenuActivity.class);
				startActivity(intent);
				Register.this.finish();
				showProgress(true);
			} else {
				showError("Password error");
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
		
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	      	 
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	
	        	Intent intent=new Intent(Intent.ACTION_MAIN);
	        	intent.addCategory(Intent.CATEGORY_HOME);
	        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	startActivity(intent);
	        	
	        	return true;
	         }
	         return false;
	     }
	    
	   
	}
}
