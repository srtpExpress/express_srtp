package com.special.ResideMenuDemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager.Query;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogUtil.log;

/**
 * A login screen that offers login via email/password.
 */
@SuppressLint("NewApi")
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

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
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	private SharedPreferences sharedPreferences;
	public static boolean isConnect(Context context) { 
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理） 
    try { 
        ConnectivityManager connectivity = (ConnectivityManager) context 
                .getSystemService(Context.CONNECTIVITY_SERVICE); 
        if (connectivity != null) { 
            // 获取网络连接管理的对象 
            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
            if (info != null&& info.isConnected()) { 
                // 判断当前网络是否已经连接 
                if (info.getState() == NetworkInfo.State.CONNECTED) { 
                    return true; 
                } 
            } 
        } 
    } catch (Exception e) { 
// TODO: handle exception 
    Log.v("error",e.toString()); 
} 
        return false; 
    } 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		String temp_nameString;
		String temp_passwordString;
		sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
		temp_nameString=sharedPreferences.getString("name", "");
		temp_passwordString=sharedPreferences.getString("pwd", "");
		// Set up the login form.
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		mEmailView.setText(temp_nameString);
		populateAutoComplete();
		mPasswordView = (EditText)findViewById(R.id.password);
		mPasswordView.setText(temp_passwordString);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
							return true;
						}
						return false;
					}
				});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		Button mEmailRegisterButton=(Button) findViewById(R.id.email_register_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isConnect(getApplicationContext())==true)
					attemptLogin();	
				else {
					Toast.makeText(LoginActivity.this, "请检查互联网转态！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		mEmailRegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//attemptRegisterin();
				if(isConnect(getApplicationContext())==true){
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this, Register.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
				else {
					Toast.makeText(LoginActivity.this, "请检查互联网转态！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
		

	}

	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
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
		mEmailView.setError(null);
		mPasswordView.setError(null);
		
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();
		if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
		{
			showError("Please input password");
		}
		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			showError("Please Input Email");
		}
		if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
			checkuser(email,password);
		}
	}
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();
		sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();	
		editor.putString("name",  email);
		editor.putString("pwd", password);
		editor.commit();
		
		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
			checkPassword(email, password);
			//mAuthTask = new UserLoginTask(email, password);
			//mAuthTask.execute((Void) null);
		}
		
		if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
		{
			showError("Please input password");
		}
		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			showError("Please Input Email");
		}

	}

	//Connect Database and Check the password
	private void checkPassword(final String email, final String password) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("UserList");
		query.whereEqualTo("ID", email);
		query.whereEqualTo("Password", password);
		
		query.findInBackground(new FindCallback<AVObject>(){ 
			public void done(List<AVObject> user, AVException e) {
				if (user.size() != 0){
					TurnControl.userAvObject = user.get(0);
					TurnControl.background_ID = user.get(0).getString("backgroundID");
					TurnControl.user_ID=user.get(0).getString("ID");
			        AvosDatabase avosDatabase=new AvosDatabase();
			        avosDatabase.getDatabase(1);
			        avosDatabase.getDatabase(2);
					
			        
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this, MenuActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
					showProgress(true);
				}else{
					showError("Unregistered Email or Password error");
				}
			}
		});

	}
	private void checkuser(final String email,final String password){
		AVQuery<AVObject> query=new AVQuery<AVObject>("UserList");
		query.whereEqualTo("ID", email);
		
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> user,AVException e){
				if(user.size()==0){
					AVObject tmpUser = new AVObject("UserList");
					tmpUser.put("ID", email);
					tmpUser.put("Password", password);
					tmpUser.saveInBackground();
					
					Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
					
					sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
					Editor editor=sharedPreferences.edit();	
					editor.putString("name",  email);
					editor.putString("pwd", password);
					editor.commit();
					
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this,MenuActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
					showProgress(true);
				}else{
					showError("This user has already existed");
				}
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

		addEmailsToAutoComplete(emails);
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

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
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
				intent.setClass(LoginActivity.this, MenuActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
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
