package com.example.user.easyfindern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_information );
    }

    public void onClickSearchButton(View view) {



        EditText addressField = (EditText) findViewById(R.id.edit_information_id);
        String hospitalname = addressField.getText().toString();
        TextView t1 =(TextView) findViewById( R.id.informationtextid );
        MyJson json = new MyJson(t1,hospitalname);

        //t1.setText( "What is happing " );
        json.execute(  );




    }
}
