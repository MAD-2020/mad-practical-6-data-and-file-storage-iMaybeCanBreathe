package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");
         */
        final EditText inputUsername = findViewById(R.id.inputUsername);
        final EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnCancel = findViewById(R.id.btnCancel);
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = inputUsername.getText().toString();
                final String password = inputPassword.getText().toString();
                UserData user = dbHandler.findUser(username);
                if (user != null){
                    Log.v(TAG, FILENAME + ": User already exist during new user creation!");
                    Toast.makeText(Main2Activity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<Integer> levelList = new ArrayList<Integer>();
                    ArrayList<Integer> scoreList = new ArrayList<Integer>();
                    for(int i = 1; i <= 10; i++){
                        levelList.add(i);
                        scoreList.add(0);
                    }
                    UserData newUser = new UserData(username, password, levelList, scoreList);
                    dbHandler.addUser(newUser);
                    Log.v(TAG, FILENAME + ": New user created successfully!");
                    Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Cancelled!");
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onStop() {
        super.onStop();
        finish();
    }
}
