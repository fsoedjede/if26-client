package fr.utt.if26.resto;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.utt.if26.resto.AsyncTasks.UserRegisterTask;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends Activity{

    // UI references.
    private EditText mEmailView, mPasswordView, mFirstnameView, mLastnameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the register form.
        mLastnameView = (EditText) findViewById(R.id.register_lastname);
        mFirstnameView = (EditText) findViewById(R.id.register_firstname);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);

        mLastnameView.setText("EXAMPLE");
        mFirstnameView.setText("Foo");
        mEmailView.setText("foo@example.com");
        mPasswordView.setText("hello");


        Button mEmailSignInButton = (Button) findViewById(R.id.email_register_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    /**
     * Attempts to register the account specified by the register form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual register attempt is made.
     */
    public void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mFirstnameView.setError(null);
        mLastnameView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String firstname = mFirstnameView.getText().toString();
        String lastname = mLastnameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid last name, if the user entered one.
        if (TextUtils.isEmpty(lastname)) {
            mLastnameView.setError(getString(R.string.error_field_required));
            focusView = mLastnameView;
            cancel = true;
        } else if(!isTextFieldValid(lastname)){
            mLastnameView.setError(getString(R.string.error_invalid_lastname));
            focusView = mLastnameView;
            cancel = true;
        }

        // Check for a valid first name, if the user entered one.
        if (TextUtils.isEmpty(firstname)) {
            mFirstnameView.setError(getString(R.string.error_field_required));
            focusView = ((focusView != null) ? focusView : mFirstnameView);
            cancel = true;
        } else if (!isTextFieldValid(firstname)) {
            mFirstnameView.setError(getString(R.string.error_invalid_firstname));
            focusView = ((focusView != null) ? focusView : mFirstnameView);
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = ((focusView != null) ? focusView : mEmailView);
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = ((focusView != null) ? focusView : mEmailView);
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = ((focusView != null) ? focusView : mPasswordView);
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = ((focusView != null) ? focusView : mPasswordView);
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            if(Resto.isInternetconnected()) {
                new UserRegisterTask(RegisterActivity.this).execute(mEmailView.getText().toString(), mPasswordView.getText().toString());
            } else {
                Toast.makeText(this, R.string.network_no_access, Toast.LENGTH_LONG).show();
            }

        }
    }

    private boolean isEmailValid(String email) {
        //return email.contains("@");
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isTextFieldValid(String text){
        //TODO: Replace the logic we will choose
        return text.length() > 4;
    }

}



