//package com.example.drbed;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class DataListAdapter extends BaseAdapter {
//    private Context context;
//    private List<Data> dataList;
//    private Activity parentActivity;
//
//    public DataListAdapter(Context context, List<Data> foodList, Activity parentActivity) {
//        this.context = context;
//        this.foodList = foodList;
//        this.parentActivity = parentActivity;
//    }
//
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return dataList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(final int i, View view, ViewGroup viewGroup) {
//        View v = View.inflate(context, R.layout.food, null);
//        TextView ID = (TextView) v.findViewById(R.id.ID);
//        final TextView PWD = (TextView) v.findViewById(R.id.PWD);
//        TextView Price = (TextView) v.findViewById(R.id.Price);
//
//        ID.setText(foodList.get(i).getID());
//        PWD.setText(foodList.get(i).getPWD());
//        Price.setText(foodList.get(i).getPrice());
//
//        v.setTag(foodList.get(i).getPWD());
//
//
//        return v;
//    }
//
//}
