/*
ChuKun OuYang
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import au.anu.mymarketplace.R;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatViewHolder> {

    private Context ctx;
    private String user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageAdapter that = (MessageAdapter) o;
        return Objects.equals(ctx, that.ctx) && Objects.equals(user, that.user) && Objects.equals(toId, that.toId) && Objects.equals(dataList, that.dataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctx, user, toId, dataList);
    }

    private String toId;
    private List<MessageBean> dataList;

    public MessageAdapter() {
    }

    public MessageAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public MessageAdapter(Context ctx, List<MessageBean> chatList, String uid, String toId) {
        this.ctx = ctx;
        this.dataList = chatList;
        this.user = uid;
        this.toId = toId;
    }

    public void addAll(List<MessageBean> mDatas){
        this.dataList = mDatas;
        notifyDataSetChanged();
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public List<MessageBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<MessageBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return
                new ChatViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_chat,parent,false));
    }

    /*
    First retrieve the user currently logged in, because it is a peer-to-peer chat,
    so the chat list is equal to the current user, put on the right, otherwise put on the left.
     */
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder cvh, int position) {
        // Get the current user
        String cur_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Compare current chat list users.
        String to_id=dataList.get(position).getUid();
        String select="";
        boolean b=cur_id.equals(to_id);
        if(b) select="right"; else  select="left";
        switch (select)
        {
            case "right":
                cvh.llL.setVisibility(View.GONE);
                cvh.llR.setVisibility(View.VISIBLE);
                cvh.tvR.setVisibility(View.VISIBLE);
                cvh.tvR.setText(dataList.get(position).getContent());
                cvh.tvRName.setText(user);
                break;
            case "left":
                cvh.llR.setVisibility(View.GONE);
                cvh.llL.setVisibility(View.VISIBLE);
                cvh.tvL.setVisibility(View.VISIBLE);
                cvh.tvL.setText(dataList.get(position).getContent());
                cvh.tvName.setText(toId);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvL;
        TextView tvR;
        TextView tvName;
        TextView tvRName;
        LinearLayout llL;
        LinearLayout llR;

        // Layout reuse
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRName = itemView.findViewById(R.id.tvName2);
            llL = itemView.findViewById(R.id.llL);
            llR = itemView.findViewById(R.id.llR);
            tvL = itemView.findViewById(R.id.text1);
            tvR = itemView.findViewById(R.id.text3);
        }
    }
}