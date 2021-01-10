package com.wonsang.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.common.api.Api;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.model.Contact;

public class EditContactActivity extends AppCompatActivity implements ApiCallable, View.OnClickListener {
    private EditText editName;
    private EditText editPhNumber;
    private ApiProvider apiProvider;
    private int id;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        TextView tx1 = (TextView) findViewById(R.id.wordText);
        TextView tx2 = (TextView) findViewById(R.id.meaningText);
        EditText editName = (EditText) findViewById(R.id.editText1);
        EditText editPhNumber = (EditText) findViewById(R.id.editText2);
        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");

        position = intent.getExtras().getInt("position");
        String name = intent.getExtras().getString("name");
        String phNumbers = intent.getExtras().getString("phNumbers");
        apiProvider = new ApiProvider(this);

        tx1.setText(name);
        tx2.setText(phNumbers);

        Log.i("EditActivity", name);
        Log.i("EditActivity", phNumbers);

        Button sub = (Button) findViewById((R.id.save_button));
        sub.setOnClickListener(this);
    }
        @Override
        public void onClick(View v) {
            EditText editName = (EditText) findViewById(R.id.editText1);
            EditText editPhNumber = (EditText) findViewById(R.id.editText2);
            String newname = editName.getText().toString();
            String newphNumbers = editPhNumber.getText().toString();

            apiProvider = new ApiProvider(this);
            String token = LoginManagement.getInstance().getToken(this);
            System.out.println(id);
            apiProvider.EditContact(token, id, position, newname, newphNumbers, this);
        }


    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        if (type == ApiProvider.RequestType.EDIT_CONTACTS){
            try {
                String name = response.getResponse().getJSONObject(0).getString("name");
                String phoneNumber = response.getResponse().getJSONObject(0).getString("phone_number");
                int id = response.getResponse().getJSONObject(0).getInt("id");

                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("position", position);
                setResult(101, intent);
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getError(VolleyError error) {

    }
}
