import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import au.anu.mymarketplace.Item;
import au.anu.mymarketplace.R;
import au.anu.mymarketplace.RedBlackTree;
import au.anu.mymarketplace.UI.ItemDetailsActivity;
import au.anu.mymarketplace.UI.RecyclerAdapter;
import au.anu.mymarketplace.parser.Parser;
import au.anu.mymarketplace.parser.Tokenizer;

/**
 * A simple {@link SearchFragment} subclass.
 *
 * @author WEIQIANG PU u7424738, Zeyu Zhang, Chunkun Ouyang
 */
public class SearchFragment extends Fragment {

    private EditText searchEditor;
    private ArrayList<Item> itemList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    Switch asc, des;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ImageView searchButton = v.findViewById(R.id.searchButton);
        searchEditor = v.findViewById(R.id.searchEditor);
        RecyclerView recyclerView = v.findViewById(R.id.searchList);
        recyclerAdapter = new RecyclerAdapter(itemList, v.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(recyclerAdapter);
        searchButton.setOnClickListener(view -> search());

        asc = v.findViewById(R.id.switch1);
        des = v.findViewById(R.id.switch2);
        asc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (itemList.size() == 0) {
                asc.setChecked(false);
                Toast.makeText(getContext(), "You should search for something first and then come here!", Toast.LENGTH_SHORT).show();
            } else {
                if (isChecked) {
                    des.setChecked(false);
                    itemList = sortAscent(itemList);
                    recyclerAdapter.notifyDataSetChanged();
                    Log.i("Search", "Ascending order");
                }
            }
        });
        des.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (itemList.size() == 0) {
                des.setChecked(false);
                Toast.makeText(getContext(), "You should search for something first and then come here!", Toast.LENGTH_SHORT).show();
            } else {
                if (isChecked) {
                    asc.setChecked(false);
                    itemList = sortDescent(itemList);
                    recyclerAdapter.notifyDataSetChanged();
                    Log.i("Search", "Descending order");
                }
            }
        });

        EditText decimal = v.findViewById(R.id.surprise);
        Button surprise = v.findViewById(R.id.button);
        surprise.setOnClickListener(v1 -> {
            if (decimal.getText().toString().equals("")) {
                Toast.makeText(getContext(), "You have to input a price!", Toast.LENGTH_SHORT).show();
            } else {
                if (itemList.size() == 0) {
                    Toast.makeText(getContext(), "You should search for something first and then come here!", Toast.LENGTH_SHORT).show();
                } else {
                    arrayToTree();
                    double num = Double.parseDouble(decimal.getText().toString());
                    Item surpriseItem = returnItem(num);
                    System.out.println(surpriseItem.getPrice());
                    Intent intent = new Intent(v1.getContext(), ItemDetailsActivity.class);
                    intent.putExtra("item", surpriseItem);
                    requireContext().startActivity(intent);
                }
            }
        });
        return v;
    }

    /**
     * Parser the string in the search box and search for the corresponding content
     *
     * @author Weiqiang Pu @u7424738
     */
    private void search() {
        asc.setChecked(false);
        des.setChecked(false);
        String searchContent = searchEditor.getText().toString();
        if (searchContent.isEmpty()) {
            Log.i("Search", "Missing search content");
            Toast.makeText(getContext(), "Why not try to search something", Toast.LENGTH_SHORT).show();
        } else {
            itemList.clear();
            Parser parser = new Parser(new Tokenizer(searchContent));
            parser.parseExp();

            DatabaseReference goods = FirebaseDatabase.getInstance().getReference().child("goods");
            goods.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        // iterate through the database to match the parser and get the eligible items
                        boolean isEqual = true;
                        if (parser.getName() != null)
                            isEqual = Objects.requireNonNull(s.child("name").getValue(String.class)).contains(parser.getName());
                        if (parser.getSeller() != null)
                            isEqual = Objects.equals(s.child("seller").getValue(String.class), parser.getSeller());
                        if (parser.getPrice() != null)
                            isEqual = Objects.equals(s.child("price").getValue(String.class), parser.getPrice());
                        if (parser.getDescription() != null)
                            isEqual = s.child("description").getValue(String.class).contains(parser.getDescription());
                        if (parser.getRating() > 0)
                            isEqual = Integer.parseInt(s.child("rating").getValue(String.class)) == parser.getRating();
                        if (parser.getTag() != null)
                            isEqual = Objects.equals(s.child("tag").getValue(String.class), parser.getTag().toString().toLowerCase());
                        if (isEqual)
                            itemList.add(new Item(Integer.parseInt(s.child("SN").getValue(String.class)), s.child("name").getValue(String.class), s.child("seller").getValue(String.class), s.child("price").getValue(String.class), Integer.parseInt(s.child("rating").getValue(String.class)), Integer.parseInt(s.child("sales").getValue(String.class)), getTag(s.child("tag").getValue(String.class)), "This is a description. balabalabala", s.child("pic").getValue(String.class), 396));
                    }
                    recyclerAdapter.notifyDataSetChanged();
                    Log.i("Search", "Search success");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    /**
     * helper method.
     * convert string to Tag type
     *
     * @param s input string
     * @return the transformed Tag type
     * @author Weiqiang Pu @u7424738
     */
    private Item.Tag getTag(String s) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "computer":
                return Item.Tag.COMPUTER;
            case "cosmetics":
                return Item.Tag.COSMETICS;
            case "food":
                return Item.Tag.FOOD;
            case "furniture":
                return Item.Tag.FURNITURE;
            case "maternal_infant":
                return Item.Tag.MATERNAL_INFANT;
            case "men":
                return Item.Tag.MEN;
            case "outdoor":
                return Item.Tag.OUTDOOR;
            case "pet":
                return Item.Tag.PET;
            case "stationary":
                return Item.Tag.STATIONARY;
            case "toy":
                return Item.Tag.TOY;
            case "women":
                return Item.Tag.WOMEN;
            default:
                return null;
        }
    }


    private final RedBlackTree<Double, Item> searchTree = new RedBlackTree<>();

    /**
     * Demonstrate the price-matched product in the Search Interface
     * This helper function will add itemList into a RBTree
     *
     * @author Zeyu Zhang u7394442, Chunkun Ouyang u7443132
     */
    private void arrayToTree() {
        for (int i = 0; i < itemList.size(); i++) {
            searchTree.insert(Double.valueOf(itemList.get(i).getPrice().replace("$", "").replace(",", "")), itemList.get(i));
        }
    }

    public Item returnItem(Double d) {
        return searchTree.returnItem2(searchTree.returnNode(d));
    }

    /**
     * This is the sort function will sort the product in ascent or descent order.
     *
     * @param list to be sorted
     * @return sorted list
     * @author Zeyu Zhang u7394442, Chunkun Ouyang u7443132
     */
    private ArrayList<Item> sortAscent(ArrayList<Item> list) {

        ArrayList<Item> arr = new ArrayList<>();
        ArrayList<Item> cloned = new ArrayList<>(list);

        int[] a = new int[cloned.size()];

        double[] b = new double[cloned.size()];

        for (int i = 0; i < a.length; i++) {
            a[i] = i;
            b[i] = Double.parseDouble(cloned.get(i).getPrice().replace("$", "").replace(",", ""));
        }

        for (int i = cloned.size(); i > 0; i--) {
            int maxIndex = 0;
            int j = 1;
            while (j < i) {
                maxIndex = b[maxIndex] > b[j] ? maxIndex : j;
                j++;
            }

            // swap b
            double bmaxIndex = b[maxIndex];
            double bj_1 = b[j - 1];
            b[maxIndex] = bj_1;
            b[j - 1] = bmaxIndex;

            // swap a
            int amaxIndex = a[maxIndex];
            int aj_1 = a[j - 1];
            a[maxIndex] = aj_1;
            a[j - 1] = amaxIndex;

        }
        for (int i = 0; i < b.length; i++) {
            arr.add(cloned.get(a[i]));
        }
        return arr;
    }

    private ArrayList<Item> sortDescent(ArrayList<Item> list) {

        ArrayList<Item> arr = new ArrayList<>();
        ArrayList<Item> cloned = new ArrayList<>(list);


        int[] a = new int[cloned.size()];

        double[] b = new double[cloned.size()];

        for (int i = 0; i < a.length; i++) {
            a[i] = i;
            b[i] = Double.parseDouble(cloned.get(i).getPrice().replace("$", "").replace(",", ""));
        }

        for (int i = cloned.size(); i > 0; i--) {
            int maxIndex = 0;
            int j = 1;
            while (j < i) {
                maxIndex = b[maxIndex] < b[j] ? maxIndex : j;
                j++;
            }

            // swap b
            double bmaxIndex = b[maxIndex];
            double bj_1 = b[j - 1];
            b[maxIndex] = bj_1;
            b[j - 1] = bmaxIndex;

            // swap a
            int amaxIndex = a[maxIndex];
            int aj_1 = a[j - 1];
            a[maxIndex] = aj_1;
            a[j - 1] = amaxIndex;

        }

        for (int i = 0; i < b.length; i++) {
            arr.add(cloned.get(a[i]));
        }

        return arr;

    }


}