package codesgesture.app.jeevandhara.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import codesgesture.app.jeevandhara.Models.CategoryModel;
import codesgesture.app.jeevandhara.Product;
import codesgesture.app.jeevandhara.R;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Services.UserUtil;
import codesgesture.app.jeevandhara.Utils.SessionManage;
import codesgesture.app.jeevandhara.interfaces.ExtraCallBack;
import codesgesture.app.jeevandhara.interfaces.Notify;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    String s,sm;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> arrayList, int layout) {
        this.arrayList = arrayList;
        s=context.getString(R.string.con);
        sm=context.getString(R.string.maincon);
        this.context = context;
        this.layout=layout;
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getExecutive_id();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final CategoryModel data = arrayList.get(i);

        holder.catnm.setText(data.getCategory_name());
        Glide.with(context).load(sm+data.getCategory_photo()).into(holder.catimg);

        holder.click_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Product.class);
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
        TextView catnm;
        ImageView catimg;
        LinearLayout click_layout;
        ViewHolder(View view) {
            super(view);
            catnm = (TextView) view.findViewById(R.id.catnm);
            catimg= view.findViewById(R.id.catimg);
            click_layout= view.findViewById(R.id.click_layout);
        }
    }

    public void updateList(ArrayList<CategoryModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}