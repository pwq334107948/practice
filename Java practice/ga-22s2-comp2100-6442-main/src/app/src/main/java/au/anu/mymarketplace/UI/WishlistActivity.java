import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import au.anu.mymarketplace.Item;
import au.anu.mymarketplace.R;

/**
 * @author Weiqiang Pu
 * @date 2022/10/13  10:25
 */

public class WishlistActivity extends AppCompatActivity {
    private RecyclerAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        initialize();
    }

    /**
     * initialize the view
     */
    private void initialize() {
        ImageView backward = findViewById(R.id.iv_finish);
        backward.setOnClickListener(view -> finish());
        RecyclerView recyclerView = findViewById(R.id.wishlist_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistAdapter = new RecyclerAdapter(getWishlist(), this);
        recyclerView.setAdapter(wishlistAdapter);
    }

    /**
     * retrieving user's shopping cart data from the realtime database.
     * when the data changes, the cart will be updated automatically.
     *
     * @author Weiqiang Pu u7424738
     */
    public ArrayList<Item> getWishlist() {
        ArrayList<Item> wishlist = new ArrayList<>();
        DatabaseReference wishlistReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wishlist");
        wishlistReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        wishlist.add(dataSnapshot.getValue(Item.class));
                    }
                } else wishlistReference.push();
                wishlistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return wishlist;
    }
}