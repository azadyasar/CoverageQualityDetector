package com.example.azadyasar.coveragequalitydetector;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "InsideLoginActivity";
    public static final String FILE_NAME = "usernamePasswords.txt";
    /*
    IS_FIRST_TIME will be used with SharedPreferences to check whether it is the first time the user
    starts app. Otherwise getting nothing from file would cause a NullPointerException
     */
    private static final String IS_FIRST_TIME = "isFirstTime";
    private static final String FIRST_TIME_CHECKER_PREFERENCES = "first_time_check_pref";
    public static final String CLIENT_NAME = "client name";


    public static boolean isFirstTime;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button signUpButton;
    private SharedPreferences mSharedPreferences;
    private ArrayList<Client> mClientArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
            Widget initialization
         */



        mSharedPreferences = getSharedPreferences(FIRST_TIME_CHECKER_PREFERENCES, MODE_PRIVATE);
        isFirstTime = mSharedPreferences.getBoolean(IS_FIRST_TIME, true);
        Log.d(TAG, "Inside onCreate isFirstTime: " + isFirstTime);

        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        signInButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);


        /*
            Assigning listener to buttons
         */

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                /*
                In case user uses T9 or auto-correction
                 */
                userName = userName.trim();
                password = password.trim();

                /*
                    Checking whether the password or username fields are empty or not
                 */

                if( isFirstTime ){
                    Toast.makeText(LoginActivity.this, "No users found.\nPlease sign up", Toast.LENGTH_LONG).show();
                }else {

                    if (userName.compareToIgnoreCase("") == 0 || password.compareToIgnoreCase("") == 0)
                        Toast.makeText(LoginActivity.this, R.string.empty_information_warning, Toast.LENGTH_SHORT).show();
                    else {
                        /*
                        >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                            Check whether the username-password is correct
                         <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                         */

                        boolean userNameMatched = false;
                        boolean passwordNameMatched = false;

                        try
                        {
                            FileInputStream fileInputStream = openFileInput(FILE_NAME);
                            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                            mClientArrayList = (ArrayList<Client>) objectInputStream.readObject();

                            objectInputStream.close();
                            fileInputStream.close();
                        }catch (FileNotFoundException e){
                            Log.d(TAG, e.getMessage() );
                        }catch(IOException e){
                            Log.d(TAG, e.getMessage());
                        } catch (ClassNotFoundException e) {
                            Log.d(TAG, e.getMessage() );
                        }

                        String clientName = null;
                        for(Client client: mClientArrayList){
                            if (userName.compareToIgnoreCase(client.getUsername())  == 0) {
                                userNameMatched = true;
                                if(password.compareToIgnoreCase(client.getPassword()) == 0){
                                    passwordNameMatched = true;
                                    clientName = client.getName();
                                    break;
                                }
                            }

                        }

                        if(userNameMatched && passwordNameMatched ){
                            Intent rentActivityIntent = new Intent(LoginActivity.this, MenuActivity.class);
                            rentActivityIntent.putExtra(CLIENT_NAME, clientName.toUpperCase() );
                            startActivity(rentActivityIntent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Username or Password is wrong!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
        Log.d(TAG, "Inside onPause isFirstTime: " + isFirstTime);
        //Will be checked when the app is started again
    }


}

