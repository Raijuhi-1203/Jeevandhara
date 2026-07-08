package codesgesture.app.jeevandhara.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.AddressModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.R;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Services.UserUtil;
import codesgesture.app.jeevandhara.UpdateAddress;
import codesgesture.app.jeevandhara.Utils.SessionManage;
import codesgesture.app.jeevandhara.interfaces.ExtraCallBack;
import codesgesture.app.jeevandhara.interfaces.Notify;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private ArrayList<AddressModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    Notify notify;
    ExtraCallBack ecb;
    ProductModel productModels;
    String s,sm;

    public AddressAdapter(Context context, ArrayList<AddressModel> arrayList, int layout,Notify notify1,ProductModel productModels) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        this.notify=notify1;
        this.productModels=productModels;
        s=context.getString(R.string.con);
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
        final AddressModel data = arrayList.get(i);

        holder.txnm.setText(data.getAddress_customer_name());
        holder.txmail.setText(data.getAddress_customer_email());
        holder.txmob.setText(data.getAddress_customer_mobileno());
        holder.txaddress.setText(data.getAddress_line_1()+", "+data.getAddress_line_2()+", "+data.getAddress_city_name()+", "+data.getAddress_state_name()+"-"+data.getAddress_pincode());
        holder.txmail.setText(data.getAddress_customer_email());

        holder.btdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAddress(data.getId(),data.getCustomer_id());
            }
        });

        holder.btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, UpdateAddress.class);
                intent.putExtra("data",data);
                intent.putExtra("data1",productModels);
                context.startActivity(intent);
            }
        });
        holder.btdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MakeDefault(data.getId(),data.getCustomer_id(),"Yes");
               holder.linear_layout.setBackgroundResource(R.drawable.box_round);
            }
        });
    }

    private void MakeDefault(String id, String customer_id, String status) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("customer_id",customer_id));
        param.add(new NetParam("id",id));
        param.add(new NetParam("status",status));
        jc.SendRequest(s,"makedefault_address", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Address Make Default !!",context);
                if (ecb != null){
                    ecb.OnCompleted("removed");
                }
                notify.onAdd(null);
            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }

    private void DeleteAddress(String id, String customer_id) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("customer_id",customer_id));
        param.add(new NetParam("id",id));
        jc.SendRequest(s,"delete_address", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Address Deleted !!",context);
                if (ecb != null){
                    ecb.OnCompleted("removed");
                }
                notify.onAdd(null);
            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txnm,txaddress,txmob,txmail;
        Button btedit,btdefault;
        ImageView btdel;
        LinearLayout linear_layout;
        ViewHolder(View view) {
            super(view);
            txnm = (TextView) view.findViewById(R.id.txnm);
            txmob= view.findViewById(R.id.txmob);
            txaddress= view.findViewById(R.id.txaddress);
            txmail= view.findViewById(R.id.txmail);
            btedit= view.findViewById(R.id.btedit);
            btdel= view.findViewById(R.id.btdel);
            btdefault= view.findViewById(R.id.btdefault);
            linear_layout= view.findViewById(R.id.linear_layout);
        }
    }

    public void updateList(ArrayList<AddressModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}