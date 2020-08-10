package com.example.phonebill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePhoneBillActivity extends AppCompatActivity {
//    private PhoneBillViewModel pbViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phone_bill);
//        pbViewModel = ViewModelProviders.of(this).get(PhoneBillViewModel.class);


        Bundle bundle = getIntent().getExtras();
        final PhoneBillList bills = bundle.getParcelable("bills");
        System.out.println("CreatePhoneBillActivity getExtra:" + bills);

        final EditText cNameText = (EditText) findViewById(R.id.cNameText);
        final EditText callerText = (EditText) findViewById(R.id.callerText);
        final EditText calleeText = (EditText) findViewById(R.id.calleeText);
        final EditText startText = (EditText) findViewById(R.id.startText);
        final EditText startTimeText = (EditText) findViewById(R.id.startTimeText);
        final EditText startAMPMText = (EditText) findViewById(R.id.startAMPMText);
        final EditText endText = (EditText) findViewById(R.id.endText);
        final EditText endTimeText = (EditText) findViewById(R.id.endTimeText);
        final EditText endAMPMText = (EditText) findViewById(R.id.endAMPMText);

        Button createButton = (Button) findViewById(R.id.createButton);


        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String customerName = cNameText.getText().toString();
                String caller = callerText.getText().toString();
                String callee = calleeText.getText().toString();
                String startDate = startText.getText().toString();
                String startTime = startTimeText.getText().toString();
                String startAMPM = startAMPMText.getText().toString();
                String endDate = endText.getText().toString();
                String endTime = endTimeText.getText().toString();
                String endAMPM = endAMPMText.getText().toString();

                if ( customerName == null || caller == null || callee == null || startDate == null || startTime == null || startAMPM == null ||
                 endDate == null || endTime == null || endAMPMText == null) {
                    //missing parameters
                } else {
                    PhoneBill bill;
                    if(bills.getPhoneBill(customerName) == null) {
                        bill = new PhoneBill(customerName);

                    } else {
                        bill = bills.getPhoneBill(customerName);
                    }

                    bill.addPhoneCall(new PhoneCall(caller, callee, startDate + " " + startTime + " " + startAMPM,
                            endDate + " " + endTime + " " + endAMPM));
                    bills.addPhoneBill(bill);

                    System.out.println("CreatePhoneBillActivity addPhoneBill:" + bills);
//                    pbViewModel.addPhoneBill(bill);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreatePhoneBillActivity.this);
                    builder.setMessage("Create Phone bill with Phone call")
                            .setPositiveButton("confirm", null)
                            .create()
                            .show();
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mainIntent.putExtra("bills", bills);
                    startActivity(mainIntent);
                }

            }
        });
    }
}