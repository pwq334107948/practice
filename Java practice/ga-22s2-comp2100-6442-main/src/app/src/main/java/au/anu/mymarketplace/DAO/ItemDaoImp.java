import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import au.anu.mymarketplace.Item;

/**
 * @author Weiqiang Pu
 * @date 2022/10/12  10:02
 */

public class ItemDaoImp implements ItemDAO {
    private volatile static ItemDaoImp instance;

    private ItemDaoImp() {
    }

    /**
     * singleton pattern
     *
     * @return the instance of API
     */
    public static ItemDaoImp getInstance() {
        if (instance == null) {
            synchronized (ItemDaoImp.class) {
                if (instance == null) instance = new ItemDaoImp();
            }
        }
        return instance;
    }

    /**
     * retrieving user's shopping cart data from the realtime database.
     * when the data changes, the cart will be updated automatically.
     *
     * @author Weiqiang Pu u7424738
     */
    @Override
    public ArrayList<Item> getCart() {
        ArrayList<Item> items = new ArrayList<>();
        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart");
        cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        items.add(dataSnapshot.getValue(Item.class));
                    }
                } else cartReference.push();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return items;
    }
}
