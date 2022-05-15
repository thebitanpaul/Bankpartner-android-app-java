package com.example.bankpartner;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.widget.TextViewOnReceiveContentListener;

        import android.annotation.SuppressLint;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText CreditScore, Age, Tenure, Balance, NumberOfProducts, HasCrCard, IsActiveMember, EstimatedSalary;
    Button predict;
    TextView result;
    String url = "https://bankpartner.herokuapp.com/predict";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CreditScore = findViewById(R.id.CreditScore);
        Age = findViewById(R.id.Age);
        Tenure = findViewById(R.id.Tenure);
        Balance = findViewById(R.id.Balance);
        NumberOfProducts = findViewById(R.id.NumberOfProducts);
        HasCrCard = findViewById(R.id.HasCrCard);
        IsActiveMember = findViewById(R.id.IsActiveMember);
        EstimatedSalary = findViewById(R.id.EstimatedSalary);
        result = findViewById(R.id.result);

        predict = (Button) findViewById(R.id.predict);
        predict.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Hit api using Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("RestrictedApi")
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("stay");
//
//                                    result.setText(data);
                                    if (data.equals("1")){
                                        result.setText("Good News! Customer Will Stay");
                                    }else {
                                        result.setText("Customer Will Leave The Bank. Do some efforts!");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {


                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("CreditScore", CreditScore.getText().toString());
                        params.put("Age", Age.getText().toString());
                        params.put("Tenure", Tenure.getText().toString());
                        params.put("Balance", Balance.getText().toString());
                        params.put("NumberOfProducts", NumberOfProducts.getText().toString());
                        params.put("HasCrCard", HasCrCard.getText().toString());
                        params.put("IsActiveMember", IsActiveMember.getText().toString());
                        params.put("EstimatedSalary", EstimatedSalary.getText().toString());

                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}