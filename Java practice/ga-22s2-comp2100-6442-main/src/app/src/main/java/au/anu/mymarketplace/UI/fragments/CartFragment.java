import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import au.anu.mymarketplace.DAO.ItemDaoImp;
import au.anu.mymarketplace.Item;
import au.anu.mymarketplace.R;
import au.anu.mymarketplace.UI.CartAdapter;
import au.anu.mymarketplace.factory.DaoFactory;

/**
 * A simple {@link CartFragment} subclass.
 *
 * @author Songshen Xie, Weiqiang Pu
 */
public class CartFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private CartAdapter cartAdapter;
    private ArrayList<Item> items = acquireCartContents();
    private SwipeRefreshLayout swipeRefreshLayout;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartAdapter = new CartAdapter(items, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView rv = v.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setAdapter(cartAdapter);
        TextView textView = v.findViewById(R.id.cart_price);
        textView.setText("$" + cartAdapter.getTotalprice());
        return v;
    }

    /**
     * Acquire the cart data before showing in the list view
     *
     * @return the list of cart
     * @author Songshen Xie, Weiqiang Pu
     */
    private ArrayList<Item> acquireCartContents() {
        ItemDaoImp itemDAO = DaoFactory.getItemDao();
        ArrayList<Item> result = itemDAO.getCart();
        Log.i("Cart", "Successfully get cart information, not showing yet");
        return result;
    }

    /**
     * refresh the view, and updates the adapter
     *
     * @author Songshen Xie,
     */
    @Override
    public void onRefresh() {
        items = acquireCartContents();
        cartAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        Log.i("CartPageFresh", "Fresh page");
    }
}