/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.fede.wizard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;
import com.fede.R;
import com.fede.Utilities.GeneralUtils;
import com.fede.Utilities.PrefUtils;

public class MailWizard extends SherlockActivity {
	private Button 				mNextButton;
	private Button 				mBackButton;
	private EditText			mMailPwd;
	private EditText			mMailUser;
	private EditText			mMailTarget;
	private SharedPreferences.Editor mEditor;
	private Context				mContext;

	private SharedPreferences	mPrefs;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_mail);
        
        mMailUser = (EditText) findViewById(R.id.Wizard_gmail_user);
        mMailPwd = (EditText) findViewById(R.id.Wizard_gmail_pwd);
        mMailTarget = (EditText) findViewById(R.id.Wizard_mail_target);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mEditor = mPrefs.edit();
		mContext = this;

        setupButtons();
        fillValues();

    }
    
    

    private void fillValues(){
    	String mailUser = PrefUtils.getStringPreference(mPrefs, R.string.gmail_user_key, this);
    	String mailPwd = PrefUtils.getStringPreference(mPrefs, R.string.gmail_pwd_key, this);
    	String targetMail = PrefUtils.getStringPreference(mPrefs, R.string.mail_to_forward_key, this);
    	
    	mMailUser.setText(mailUser);
    	mMailPwd.setText(mailPwd);
    	mMailTarget.setText(targetMail);
    }
    
    private void storeValues(boolean enabled){
    	PrefUtils.setStringPreference(mEditor, R.string.gmail_user_key, mMailUser.getText().toString().trim(), this);
    	PrefUtils.setStringPreference(mEditor, R.string.gmail_pwd_key, mMailPwd.getText().toString().trim(), this);
    	PrefUtils.setStringPreference(mEditor, R.string.mail_to_forward_key, mMailTarget.getText().toString().trim(), this);
    	PrefUtils.setBoolPreference(mEditor, R.string.forward_to_mail_key, enabled, this);
    	mEditor.commit();
    }
    private boolean checkEmptyValues()
    {
    	if(mMailUser.getText().toString().equals(""))
    		return true;
    	if(mMailPwd.getText().toString().equals(""))
    		return true;
    	if(mMailTarget.getText().toString().equals(""))
    		return true;
    	return false;
    }
    
    
    private void setupButtons()
    {

		// BUTTONS
    	mNextButton = (Button) findViewById(R.id.MailNextButton);
    	mBackButton = (Button) findViewById(R.id.MailBackButton);
		
		mNextButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				if(checkEmptyValues()){
					GeneralUtils.showConfirmDialog(view.getContext().getString(R.string.empty_mail_values), view.getContext(), new OnClickListener(){
						public void onClick(DialogInterface intf, int a){
							storeValues(false);
							Intent i = new Intent(mContext, SmsWizard.class);
					        startActivity(i);
					        finish();
						}
					});
				}else{
					storeValues(true);
			        finish();
				}
			}
		});
	
		
		mBackButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent i = new Intent(view.getContext(), StartWizard.class);
		        startActivity(i);
		        finish();
			}
		});
    }
}