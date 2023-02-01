import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import au.anu.mymarketplace.R;

/**
 * @author Weiqiang Pu
 * @date 2022/9/20  21:22
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private EditText e1, e2;
    private FirebaseAuth mAuth;
    private FirebaseFirestore dbInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    /**
     * initialize login interface
     *
     * @author Weiqiang Pu @u7424738
     */
    private void initialize() {
        e1 = findViewById(R.id.editor_Password);
        e2 = findViewById(R.id.edit_Password);
        ImageView i1 = findViewById(R.id.deleteTrigger1);
        ImageView i2 = findViewById(R.id.deleteTrigger2);
        clearListener(e1, i1);
        clearListener(e2, i2);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * clear the content in the EditText when click its delete trigger
     *
     * @param e the EditText that needs to listen
     * @param i the ImageView that needs to listen
     * @author Weiqiang Pu @u7424738
     */
    public static void clearListener(final EditText e, final ImageView i) {
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) i.setVisibility(View.VISIBLE);
                else i.setVisibility(View.INVISIBLE);
            }
        });
        i.setOnClickListener(v -> e.setText(""));
    }

    /**
     * check whether the user information is valid. if yes, jump to next activity; if not, post a toast message
     *
     * @param v the login view
     * @author Weiqiang Pu @u7424738
     */
    public void checkLogin(View v) {
        String username = e1.getText().toString();
        String password = e2.getText().toString();
        if (e1 == null || e1.length() == 0)
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_LONG).show();
        else if (e2 == null || e2.length() == 0)
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show();
        else {
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i(TAG, "signInWithEmail:success");
                    updateUI();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    e1.setText("");
                    e2.setText("");
                }
            });
        }
    }

    /**
     * check whether the input username exists. if yes, post an error message; if not, register a new user and login
     *
     * @param v the login view
     * @author Weiqiang Pu @7424738
     */
    public void register(View v) {
        String username = e1.getText().toString();
        String password = e2.getText().toString();
        if (e1 == null || e1.length() == 0)
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_LONG).show();
        else if (e2 == null || e2.length() == 0)
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show();
        else {
            mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    // chukun add code
                    //--------------------------------------------
                    FirebaseUser user = mAuth.getCurrentUser();
                    dbInstance = FirebaseFirestore.getInstance();
                    dbInstance.collection("Users")
                            .document(user.getUid())
                            .set(new AccountInfo(user.getEmail(), user.getEmail(), user.getUid()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    Toast.makeText(LoginActivity.this, "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                    Toast.makeText(LoginActivity.this, "Error writing document!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    // ------------------------------------------


                    updateUI();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    e1.setText("");
                    e2.setText("");
                }
            });
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
    }

    /**
     * jump to next activity when user successfully logs in
     *
     * @author Weiqiang Pu @7424738
     */
    private void updateUI() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}