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
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONObject;

public class EditContactActivity extends AppCompatActivity implements ApiCallable<JSONObject>, View.OnClickListener {

    private ApiProvider apiProvider;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        TextView tx1 = findViewById(R.id.wordText);
        TextView tx2 = findViewById(R.id.meaningText);
        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");

        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("email");
        apiProvider = new ApiProvider(this);

        tx1.setText(name);
        tx2.setText(email);

        Log.i("EditActivity", name);
        Log.i("EditActivity", email);

        Button sub = findViewById((R.id.save_button));
        sub.setOnClickListener(this);
    }
        @Override
        public void onClick(View v) {
            EditText editName =  findViewById(R.id.editText1);
            EditText editEmail =  findViewById(R.id.editText2);

            String newName = editName.getText().toString();
            String newEmail = editEmail.getText().toString();

            apiProvider = new ApiProvider(this);
            String token = LoginManagement.getInstance().getToken(this);

            apiProvider.editContact(token, new Contact(newName, newEmail, id), this);
        }


    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        if (type == ApiProvider.RequestType.EDIT_CONTACTS){
            try {
                String name = response.getString("name");
                String email = response.getString("email");
                int id = response.getInt("id");

                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("email", email);

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
        System.out.println(error);
    }
}
