package com.example.azadyasar.coveragequalitydetector;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.io.StreamCorruptedException;
        import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "InsideSignUpActivity";

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button submitButton;

    private ArrayList<Client> mClientArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        /*
            Widget initialization
         */

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        surnameEditText = (EditText) findViewById(R.id.surname_edit_text);

        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        submitButton = (Button) findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameField = nameEditText.getText().toString();
                String surnameField = surnameEditText.getText().toString();
                String usernameField = usernameEditText.getText().toString().trim();
                String passwordField = passwordEditText.getText().toString().trim();

                /*
                    Checking whether there are unfilled fields or not
                 */
                if( (nameField.compareToIgnoreCase("") == 0) ||
                        (surnameField.compareToIgnoreCase("") == 0) ||
                        (usernameField.compareToIgnoreCase("") == 0) ||
                        (passwordField.compareToIgnoreCase("") == 0)){
                    Toast.makeText(SignUpActivity.this, R.string.empty_information_warning, Toast.LENGTH_SHORT).show();
                } else{

                    Client client = new Client(nameField+" "+surnameField, usernameField,
                            passwordField);

                    if( LoginActivity.isFirstTime ){
                        Log.d(TAG, "It's first time list is being created and written to file");
                        mClientArrayList = new ArrayList<Client>();
                        LoginActivity.isFirstTime = false;

                    }else{

                        try{
                            Log.d(TAG, "It's not the first time user is being added to file");
                            FileInputStream fileInputStream = openFileInput(LoginActivity.FILE_NAME);
                            ObjectInputStream objectInputStream =
                                    new ObjectInputStream(fileInputStream);
                            mClientArrayList = (ArrayList<Client>) objectInputStream.readObject();

                            objectInputStream.close();
                            fileInputStream.close();

                        } catch (FileNotFoundException e) {
                            Log.d(TAG, e.getMessage());
                        } catch (StreamCorruptedException e) {
                            Log.d(TAG, e.getMessage());
                        } catch (IOException e) {
                            Log.d(TAG, e.getMessage());
                        } catch (ClassNotFoundException e) {
                            Log.d(TAG, e.getMessage());
                        }
                    }


                    /*
                        userNameExist will hold whether the username already in use or not
                     */
                    boolean userNameExist = false;

                    /*
                    Comparing all usernames with the new one. I wish I've used a HashMap instead of a List
                     */
                    for(Client tmp: mClientArrayList){
                        if( tmp.getUsername().compareToIgnoreCase(client.getUsername()) == 0)
                            userNameExist = true;
                    }


                    if(userNameExist ){
                        Toast.makeText(SignUpActivity.this, "Username already in use.", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            Log.d(TAG, "Newly created list is being written to the file");
                            FileOutputStream fileOutputStream = openFileOutput(
                                    LoginActivity.FILE_NAME, MODE_PRIVATE);
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(fileOutputStream);
                            mClientArrayList.add(client);
                            objectOutputStream.writeObject(mClientArrayList);
                            LoginActivity.isFirstTime = false;
                            objectOutputStream.close();
                            fileOutputStream.close();
                        } catch (FileNotFoundException e) {
                            Log.d(TAG, e.getMessage());
                        } catch (IOException e) {
                            Log.d(TAG, e.getMessage());
                        }

                        Log.d(TAG, "isFirstTime is now: " + LoginActivity.isFirstTime);
                        Toast.makeText(SignUpActivity.this, "Succesfully created", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                    }
                }

            }
        });

    }

}

