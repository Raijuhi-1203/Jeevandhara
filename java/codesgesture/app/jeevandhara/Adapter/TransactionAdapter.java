package codesgesture.app.jeevandhara.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.Order;
import codesgesture.app.jeevandhara.OrderProductDetails;
import codesgesture.app.jeevandhara.R;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private ArrayList<Order> arrayList;
    private Context context;
    String Userid="",sm;
    private int layout;

    public TransactionAdapter(Context context, ArrayList<Order> arrayList, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        sm=context.getString(R.string.maincon);
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getExecutive_id();
    }

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TransactionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TransactionAdapter.ViewHolder holder, final int i) {
        final Order data = arrayList.get(i);

        holder.txorderid.setText("Order Id : "+data.getOrder_id());
        holder.txdate.setText(" Date : "+data.getOrder_delivery_date());
        holder.txamount.setText("Amount (₹) : "+data.getTotal_order_amount());
        holder.txpoint.setText("Point : "+data.getPoint_value());
        holder.txcnm.setText("Customer name : "+data.getCustomer_name());
        holder.txtype.setText("Type : "+data.getTransaction_type());

        if (data.getTransaction_type().equals("Credit")){
            holder.txtype.setTextColor(context.getResources().getColor(R.color.green));
        }
        else if (data.getTransaction_type().equals("Debit")){
            holder.txtype.setTextColor(context.getResources().getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txorderid,txdate,txamount,txpoint,txcnm,txtype;
        ViewHolder(View view) {
            super(view);
            txorderid = view.findViewById(R.id.txorderid);
            txdate= view.findViewById(R.id.txdate);
            txpoint= view.findViewById(R.id.txpoint);
            txcnm= view.findViewById(R.id.txcnm);
            txamount= view.findViewById(R.id.txamount);
            txtype= view.findViewById(R.id.txtype);

        }
    }

    public void updateList(ArrayList<Order> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}