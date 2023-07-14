import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import au.anu.mymarketplace.R;


public class MessageActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private RecyclerView rvChat;
    private Button send;

    private String uid;

    List<MessageBean> chatBeanList = new ArrayList<>();
    private String firebaseKey;

    private MessageAdapter messageAdapter;
    private EditText postEt;
    private String userName;
    private String sellerName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageActivity that = (MessageActivity) o;
        return
                Objects.equals(reference, that.reference) && Objects.equals(rvChat, that.rvChat) && Objects.equals(send, that.send) &&
                Objects.equals(uid, that.uid) && Objects.equals(chatBeanList, that.chatBeanList) && Objects.equals(firebaseKey, that.firebaseKey) &&
                Objects.equals(messageAdapter, that.messageAdapter) && Objects.equals(postEt, that.postEt) && Objects.equals(userName, that.userName) &&
                Objects.equals(sellerName, that.sellerName);
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }

    public RecyclerView getRvChat() {
        return rvChat;
    }

    public void setRvChat(RecyclerView rvChat) {
        this.rvChat = rvChat;
    }

    public Button getSend() {
        return send;
    }

    public void setSend(Button send) {
        this.send = send;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, rvChat, send, uid, chatBeanList, firebaseKey, messageAdapter, postEt, userName, sellerName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the key value of the current chat room,
        // but there is a bug here, user 1 and user 2 chat, will generate a new key value.
        // At this time, you need to check, if the people in the chat room are the same,
        // then it will be judged as the same chat room.
        firebaseKey = getIntent().getStringExtra("key");
        setContentView(R.layout.activity_chat);
        initView();
        initData();
    }

    private void initData() {
        uid = getIntent().getStringExtra("uid");

        messageAdapter = new MessageAdapter(this);
        messageAdapter.setDataList(chatBeanList);
        messageAdapter.setUser(getIntent().getStringExtra("userName"));
        messageAdapter.setToId(getIntent().getStringExtra("sellerName"));



        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(messageAdapter);

        reference = FirebaseDatabase.getInstance().getReference();



        // Check whether two people chat, and history.
        // Firebase listener, using real-time database listening, chats data change, timely update.
        reference.child("chat").child(firebaseKey).child("chats").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                chatBeanList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot next = iterator.next();
                    if (next.getChildren().iterator().next().child("content").getValue() != null) {
                        MessageBean chat = next.getChildren().iterator().next().getValue(MessageBean.class);
                        chatBeanList.add(chat);
                    }
                }
                // Add to the database
                messageAdapter.addAll(chatBeanList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error",error.toString());
            }});

    }

    private void initView() {
        rvChat = findViewById(R.id.chat_list); send = findViewById(R.id.send); postEt = findViewById(R.id.et_chat);
       // Firebase listener, using real-time database listening, When you press the send button,
        // it changes in real time., timely update.
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String s=postEt.getText().toString();
                if ( s.length()==0 || firebaseKey == null || TextUtils.isEmpty(s) )  return;
                DatabaseReference c =
                        reference.child("chat").child(firebaseKey).child("chats").child(System.currentTimeMillis() + "").push();

                MessageBean chatBean = new MessageBean();
                chatBean.setContent(postEt.getText().toString());
                chatBean.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

                c.setValue(chatBean);
                postEt.setText("");
            }
        });

    }


}