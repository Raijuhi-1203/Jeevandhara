package codesgesture.app.jeevandhara.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.Order;
import codesgesture.app.jeevandhara.OrderProductDetails;
import codesgesture.app.jeevandhara.R;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Order> arrayList;
    private Context context;
    String Userid="",sm;
    private int layout;

    public OrderAdapter(Context context, ArrayList<Order> arrayList, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        sm=context.getString(R.string.maincon);
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getExecutive_id();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final Order data = arrayList.get(i);

        holder.txorderid.setText("Order Id : "+data.getOrder_id());
        holder.txdate.setText(" Placed on : "+data.getOrder_delivery_date());
        holder.txstatus.setText("Status : "+data.getOrder_status());

        String url = sm+data.getPhoto_path();
        Glide.with(context).load(url).into(holder.prdimg);

        holder.txcount.setText(data.getProduct_name());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderProductDetails.class);
                intent.putExtra("data",data);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txorderid,txdate,txstatus,txcount;
        LinearLayout click;
        ImageView prdimg;


        ViewHolder(View view) {
            super(view);
            txorderid = view.findViewById(R.id.txorderid);
            txdate= view.findViewById(R.id.txdate);
            txcount= view.findViewById(R.id.txcount);
            txstatus= view.findViewById(R.id.txstatus);
            click= view.findViewById(R.id.click);
            prdimg= view.findViewById(R.id.prdimg);
        }
    }

    public void updateList(ArrayList<Order> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}