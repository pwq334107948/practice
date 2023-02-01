import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import au.anu.mymarketplace.Item;
import au.anu.mymarketplace.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewHolder> {

    private final ArrayList<Item> arrayList;
    private final double totalprice;
    private final Context context;

    public CartAdapter(ArrayList<Item> arr, Context context) {
        arrayList = arr;
        totalprice = sumAll();
        this.context = context;
    }

    private double sumAll() {
        double total = 0.0;
        for (Item item : arrayList) {
            total += Double.parseDouble(item.getPrice().replace("$", "").replace(",", ""));
        }
        return total;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Item item = arrayList.get(position);
        holder.name.setText(item.getName());
        StorageReference imageReference = FirebaseStorage.getInstance().getReference().child(item.getImage());
        imageReference.getBytes(1024 * 1024).addOnSuccessListener(bytes -> holder.image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
        holder.price.setText(item.getPrice());
        holder.quantity.setText("1");

        holder.viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ItemDetailsActivity.class);
            intent.putExtra("item", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public double getTotalprice() {
        return Math.round(totalprice * 100.0) / 100.0;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final ImageView image;
        private final TextView quantity;
        private final View viewButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.pic);
            quantity = itemView.findViewById(R.id.quantity);
            viewButton = itemView.findViewById(R.id.view);
        }
    }
}
