package com.example.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class PrettyPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prettyprint);
        Bundle bundle = getIntent().getExtras();
        final PhoneBillList bills = bundle.getParcelable("bills");
        System.out.println("PrettyPrintActivity getExtra:" + bills);

        final EditText cNameText = (EditText) findViewById(R.id.pPrintCNameText);
        Button searchButton = (Button) findViewById(R.id.pPrintButton);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String customerName = cNameText.getText().toString();

                PhoneBill bill = bills.getPhoneBill(customerName);
                String pPrinterStr;
                if ( bill == null) {
                    pPrinterStr = "There is no phone bill for this customer";
                } else {
                    pPrinterStr = bill.prettyPrinter();
                }


                TextView phoneBillListTextView = (TextView) findViewById(R.id.phonebillListTextView);
                phoneBillListTextView.setText(pPrinterStr);
            }
        });
        Button menuButton = (Button) findViewById(R.id.pPrintGoToMainButton);
        menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("bills", bills);
                startActivity(mainIntent);
            }
        });

    }

}
