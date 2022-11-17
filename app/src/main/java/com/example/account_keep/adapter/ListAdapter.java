package com.example.account_keep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.account_keep.DBOpenHelper;
import com.example.account_keep.R;
import com.example.account_keep.entity.Account;

import java.util.List;

public class ListAdapter extends BaseAdapter{

    private List<Account> accList;
    private Context context;
    private LayoutInflater inflater;
    public final class ListItemView{
        public ImageButton imageButton;
        public TextView description;
        public TextView amount;
    }

    public ListAdapter(Context context, List<Account> accList){
        this.accList = accList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accList.size();
    }

    @Override
    public Object getItem(int position) {
        return accList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return accList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListItemView listItemView;

        if(convertView == null){
            listItemView = new ListItemView();
            convertView = inflater.inflate(R.layout.list_item, null);
            listItemView.imageButton = convertView.findViewById(R.id.deleteButton);
            convertView.setTag(listItemView);

            listItemView.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBOpenHelper dbOpenHelper = new DBOpenHelper(context, 1);
                    dbOpenHelper.accountDelete(accList.get(position));
                    listItemView.imageButton.setVisibility(View.INVISIBLE);
                    accList.remove(position);
                    ListAdapter.this.notifyDataSetChanged();
                    //deleteItem(view, position);
                }
            });

            listItemView.description = convertView.findViewById(R.id.description);
            listItemView.amount = convertView.findViewById(R.id.amount);
        }
        else{
            listItemView = (ListItemView)convertView.getTag();
        }

        //set text
        listItemView.description.setText(accList.get(position).getDescription());
        listItemView.amount.setText(String.valueOf(accList.get(position).getAmount()));

        return convertView;
    }

    /*private void deleteItem(final View view, final int position){
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        };

        collapse(view, animationListener);
    }

    /**
     * animation effect of delete of items in ListView
     * @param view
     * @param animationListener
     */
    /*
    private  void collapse(final View view, Animation.AnimationListener animationListener){
        final int initialHeight = view.getHeight();

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    view.setVisibility(View.GONE);
                }
                else{
                    view.getLayoutParams().height = initialHeight - (int)(initialHeight*interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds(){
                return true;
            }
        };

        if(animationListener != null){
            anim.setAnimationListener(animationListener);
        }
        anim.setDuration(2);
        view.startAnimation(anim);
    }*/

}
