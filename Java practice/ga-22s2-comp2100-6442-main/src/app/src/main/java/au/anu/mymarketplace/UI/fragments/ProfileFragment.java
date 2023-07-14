import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import au.anu.mymarketplace.R;
import au.anu.mymarketplace.UI.ChangePasswordActivity;
import au.anu.mymarketplace.UI.WishlistActivity;

/**
 * A simple {@link ProfileFragment} subclass.
 *
 * @author WEIQIANG PU u7424738
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView name = view.findViewById(R.id.hello_account);
        name.setText("hello, " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        View changePassword = view.findViewById(R.id.view6);
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        View wishlistButton = view.findViewById(R.id.wishlist_jump_button);
        wishlistButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), WishlistActivity.class);
            startActivity(intent);
        });
        Button signOut = view.findViewById(R.id.signOutButton);
        signOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            requireActivity().finish();
            Log.i("Signout", "Successfully signout");
        });
        return view;
    }


}