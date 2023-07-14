import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import au.anu.mymarketplace.R;

/**
 * @author Weiqiang Pu
 * @date 2022/10/13  20:32
 */

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPassword, newPassword, checkPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initialize();
    }

    /**
     * initialize the view
     */
    private void initialize() {
        ImageView backward = findViewById(R.id.iv_finish);
        backward.setOnClickListener(view -> finish());
        oldPassword = findViewById(R.id.editor_Password);
        newPassword = findViewById(R.id.editor_new_password);
        checkPassword = findViewById(R.id.editor_check_password);
    }

    /**
     * First verify the identity of the current user.
     * Then verify that the two new passwords are the same, and change the password if they are.
     *
     * @param view current view
     * @author Weiqiang Pu u7424738
     */
    public void confirm(View view) {
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword.getText().toString());
        FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (newPassword.getText().toString().equals(checkPassword.getText().toString())) {
                    FirebaseAuth.getInstance().getCurrentUser().updatePassword(checkPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update Password", "Password updated");
                                Toast.makeText(ChangePasswordActivity.this, "Successfully change password.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.w("Update Password", "Error password not updated", task.getException());
                                Toast.makeText(ChangePasswordActivity.this, "Password change failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Log.d("Update Password", "Check password error");
                    Toast.makeText(ChangePasswordActivity.this, "Check password failed.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.w("auth", "Error auth failed", task.getException());
                Toast.makeText(ChangePasswordActivity.this, "Error auth failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}