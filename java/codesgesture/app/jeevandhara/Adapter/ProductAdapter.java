package codesgesture.app.jeevandhara.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.PageAddress;
import codesgesture.app.jeevandhara.Product;
import codesgesture.app.jeevandhara.ProductDetails;
import codesgesture.app.jeevandhara.R;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<ProductModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    String s,sm;

    public ProductAdapter(Context context, ArrayList<ProductModel> arrayList, int layout) {
        this.arrayList = arrayList;
        s=context.getString(R.string.con);
        sm=context.getString(R.string.maincon);
        this.context = context;
        this.layout=layout;
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getExecutive_id();
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, final int i) {
        final ProductModel data = arrayList.get(i);

        holder.prdnm.setText(data.getProduct_full_name());
        holder.offmrp.setText("₹ "+data.getProduct_sell_price());
        holder.unit.setText(data.getProduct_unit_value()+" "+data.getProduct_unit());
        Glide.with(context).load(sm+data.getPhoto_path()).into(holder.prdimg);

        holder.prdimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("data",data);
                context.startActivity(intent);
            }
        });
        holder.btbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PageAddress.class);
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
        TextView prdnm,offmrp,unit;
        ImageView prdimg;
        Button btbuy;

        ViewHolder(View view) {
            super(view);
            prdnm = (TextView) view.findViewById(R.id.prdnm);
            prdimg= view.findViewById(R.id.prdimg);
            offmrp= view.findViewById(R.id.offmrp);
            unit= view.findViewById(R.id.unit);
            btbuy= view.findViewById(R.id.btbuy);

        }
    }

    public void updateList(ArrayList<ProductModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}