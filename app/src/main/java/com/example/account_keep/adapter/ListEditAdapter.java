package com.example.account_keep.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account_keep.DBOpenHelper;
import com.example.account_keep.R;
import com.example.account_keep.activity.EditActivity;
import com.example.account_keep.entity.Account;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ListEditAdapter extends RecyclerView.Adapter{

    private static List<Account> accList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageButton delete;
        private final TextView description;
        private final TextView amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.deleteButton);
            description = itemView.findViewById(R.id.description);
            amount = itemView.findViewById(R.id.amount);

            itemView.setOnClickListener(view -> {
                int position = getLayoutPosition();
                Context context = view.getContext();
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("acc", ListEditAdapter.getItem(position));
                context.startActivity(intent);
            });

            itemView.setOnLongClickListener(view -> {
                ImageButton delete = view.findViewById(R.id.deleteButton);

                if(delete.getVisibility() == View.INVISIBLE){
                    delete.setVisibility(View.VISIBLE);
                }
                else{
                    delete.setVisibility(View.INVISIBLE);
                }

                return true;
            });
        }

        public TextView getDescription(){
            return description;
        }

        public TextView getAmount(){
            return amount;
        }

        public ImageButton getDelete(){
            return delete;
        }

    }

    public ListEditAdapter(List<Account> accList){
        ListEditAdapter.accList = accList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).getDescription().setText(accList.get(position).getDescription());
        ((ViewHolder)holder).getAmount().setText(String.valueOf(accList.get(position).getAmount()));
        ((ViewHolder)holder).getDelete().setOnClickListener(view -> {
            DBOpenHelper dbOpenHelper = new DBOpenHelper(view.getContext(), 1);
            deleteItem(holder.getBindingAdapterPosition(), dbOpenHelper);
            /*
            //hide the button after item deleted
            ImageButton delete = view.findViewById(R.id.deleteButton);
            delete.setVisibility(View.INVISIBLE);*/
        });
        //((ViewHolder)holder).getDelete().setTag(position);
    }

    @Override
    public int getItemCount() {
        if(accList==null){
            return -1;
        }
        return accList.size();
    }

    /**
     * delete item
     */
    public void deleteItem(int position, DBOpenHelper dbOpenHelper){
        if(accList == null || accList.isEmpty()){
            return;
        }
        accList.remove(position);
        dbOpenHelper.accountDelete(getItem(position));
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    /**
     * get account from acclist
     * @return account
     */
    public static Account getItem(int position){
        return ListEditAdapter.accList.get(position);
    }


    public void addItem(int position, Account account){
        accList.add(account);
        notifyItemInserted(position);
    }

    public void setAccList(List<Account> accList){
        ListEditAdapter.accList.clear();
        ListEditAdapter.accList.addAll(accList);
        notifyDataSetChanged();
    }



}
