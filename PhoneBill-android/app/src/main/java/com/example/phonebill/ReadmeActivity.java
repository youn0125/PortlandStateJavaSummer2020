package com.example.phonebill;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReadmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        Bundle bundle = getIntent().getExtras();
        final PhoneBillList bills = bundle.getParcelable("bills");
        System.out.println("ReadmeActivity getExtra:" + bills);

        Button createButton = (Button) findViewById(R.id.readmeMenuButton);


        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("bills", bills);
                startActivity(mainIntent);
            }
        });

    }
}