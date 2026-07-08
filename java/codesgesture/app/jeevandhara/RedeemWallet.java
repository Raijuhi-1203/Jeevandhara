package codesgesture.app.jeevandhara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJsonWithoutProgress;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class RedeemWallet extends AppCompatActivity {
    TextView txpoint;
    ExecutiveModel executiveModel;
    Button btntransaction,btnaccount;
    String s;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_wallet);
        executiveModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        s=getString(R.string.con);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Redeem Wallet");

        txpoint=findViewById(R.id.txpoint);
        btnaccount=findViewById(R.id.btnaccount);
        btntransaction=findViewById(R.id.btntransaction);
        GetPointValue();

        btntransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RedeemWallet.this,TransactionHistory.class));
            }
        });
        btnaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RedeemWallet.this,Profile.class));
            }
        });

    }

    private void GetPointValue() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(RedeemWallet.this);
        param.add(new NetParam("executive_id",executiveModel.getExecutive_id()));
        jc.SendRequest(s,"get_redeem_point", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    float a = Float.parseFloat(obj.getString("transaction_point"));
                    int aa = (int) a;
                    String ss = String.valueOf(aa);
                    txpoint.setText(" Point : "+ ss +" ");
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }

}
