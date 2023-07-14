import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.anu.mymarketplace.UI.MessageActivity;
import au.anu.mymarketplace.databinding.FragmentMessageBinding;



/**
 * A simple {@link MessageFragment} subclass.
 *
 * @author Chunkun Ouyang
 */
public class MessageFragment extends Fragment {


    private FirebaseAuth instance;
    private DatabaseReference reference;
    private au.anu.mymarketplace.databinding.FragmentMessageBinding inflate;

    public MessageFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        inflate = FragmentMessageBinding.inflate(getLayoutInflater(), container, false);

        inflate.chat.setAlpha(0f);
        inflate.chat.animate().alpha(1f).setDuration(2000);


        inflate.chat.setOnClickListener(view -> toChat());
        //inflate.blacklist.setOnClickListener(view -> blacklist());
        return inflate.getRoot();
    }

    public void toChat() {
        EditText editText =  new EditText(getActivity());
        new AlertDialog.Builder(getActivity())
                .setView(editText)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = editText.getText().toString();
                        chat(id);
                    }
                }).setNegativeButton("cancel",null).create().show();



    }
    private void chat(String email){
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users");
        Query query = collectionReference.whereEqualTo("email", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getString("uid");
                        reference.child("blacklist").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                while (iterator.hasNext()){
                                    DataSnapshot next = iterator.next();

                                    List<String> stringList = (ArrayList) next.getChildren().iterator().next().getValue();
                                    assert stringList != null;
                                    if (stringList.size()>0){
                                        for (int i = 0; i < stringList.size(); i++) {
                                            if (stringList.contains(id)){
                                                Toast.makeText(getActivity(), "Unable to chat with blocked user !", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }}
                                }




                                //Find and create chat rooms.
                                reference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                        String key = "" ;
                                        while (iterator.hasNext()){
                                            DataSnapshot point = iterator.next();
                                            // It is easy to cause misunderstanding.
                                            // This is because we said in the previous meeting that we would make two separate login interfaces for merchants and users,
                                            // so each chat room is composed of merchants and users. But then it was all users.
                                            String user = point.child("user").getValue().toString();
                                            String other = point.child("seller").getValue().toString();
                                            /*
                                            The problem here is that if user 1 and user 2 chat a room with a key value,
                                            then user 2 and user 1 chat a new room.
                                            To circumvent this bug, previous chat rooms will be checked to see if these two people are present,
                                            so that they will not be re-created.
                                             */
                                            if ((user.equals(instance.getUid())&&other.equals(id))|| user.equals(id)&&other.equals(instance.getUid()))
                                            {
                                                key = point.getKey();
                                            }
                                        }
                                        // If you haven't already created a chat room, create a new one.
                                        if (TextUtils.isEmpty(key)){

                                            DatabaseReference message = reference.child("chat").push();
                                            message.child("user").setValue(instance.getCurrentUser().getUid());
                                            message.child("seller").setValue(id);
                                            key = message.getKey();
                                        }


                                        /*
                                        Pass user information, chat room information, and seller information to MessageActivity.
                                        However, the seller and user information are invalid at this time. In MessageAciticy,
                                        obtain the current logged-in user id to determine who is on the right and left side of the dialog box.
                                         */



                                        Intent intent = new Intent(getActivity(), MessageActivity.class);
                                        intent.putExtra("userName",instance.getCurrentUser().getEmail());
                                        intent.putExtra("sellerName",":)"+id);
                                        intent.putExtra("key",key);
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("error",error.toString());
                                    }
                                });

                            }

                            // Add the log
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("error",error.toString());
                            }
                        });
                    }
                }
            }
        });


    }

}

