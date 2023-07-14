import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashSet;

import au.anu.mymarketplace.Item;
import au.anu.mymarketplace.R;

/**
 * @author Weiqiang Pu
 * @date 2022/10/8  21:25
 */

public class ItemDetailsActivity extends AppCompatActivity {
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        initialize();
    }

    /**
     * initialize the view
     */
    private void initialize() {
        ImageView imageView = findViewById(R.id.itemPic);
        TextView name = findViewById(R.id.itemName);
        TextView seller = findViewById(R.id.seller);
        TextView rating = findViewById(R.id.rating);
        TextView sales = findViewById(R.id.sales);
        TextView stock = findViewById(R.id.stock);
        TextView description = findViewById(R.id.itemDescription);
        TextView price = findViewById(R.id.showPrice);
        item = (Item) getIntent().getExtras().getSerializable("item");
        name.setText(item.getName());
        seller.setText("Sold by: " + item.getSeller());
        rating.setText("Rating: " + item.getRating());
        sales.setText("Sales: " + item.getSales());
        stock.setText("Stock: " + (item.getStock()));
        description.setText(item.getDescription());
        price.setText(item.getPrice());
        StorageReference imageReference = FirebaseStorage.getInstance().getReference().child(item.getImage());
        imageReference.getBytes(1024 * 1024).addOnSuccessListener(bytes -> imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
        ImageView backward = findViewById(R.id.iv_finish);
        backward.setOnClickListener(view -> finish());
    }

    /**
     * check whether there are goods in the shopping cart. If yes, add item to the existing cart. If not, create a new shopping cart.
     *
     * @param v cart detail page
     */
    public void addCart(View v) {
        ArrayList<Item> existingCartList = new ArrayList<>();
        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart");
        // get the items in the current shopping cart
        cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        existingCartList.add(dataSnapshot.getValue(Item.class));
                        Log.i("Cart", "get current cartlist");
                    }
                }
                // if no item in the shopping cart, then create a shopping cart
                else {
                    cartReference.push();
                    Log.i("Wishlist", "create a wishlist");
                }
                existingCartList.add(item);
                cartReference.setValue(existingCartList);
                Toast.makeText(ItemDetailsActivity.this, "Successfully add to cart.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /**
     * check whether there are goods in the wishlist. If yes, add item to the existing list. If not, create a new wishlist.
     *
     * @param v cart detail page
     */
    public void addWishlist(View v) {
        HashSet<Item> existingCartList = new HashSet<>();
        DatabaseReference wishlistReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wishlist");
        // get the items in the current shopping cart
        wishlistReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        existingCartList.add(dataSnapshot.getValue(Item.class));
                        Log.i("Wishlist", "get current wishlist");
                    }
                }
                // if no item in the shopping cart, then create a shopping cart
                else {
                    wishlistReference.push();
                    Log.i("Wishlist", "create a wishlist");
                }
                existingCartList.add(item);
                wishlistReference.setValue(new ArrayList<>(existingCartList));
                Toast.makeText(ItemDetailsActivity.this, "Successfully add to wishlist.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}