package com.example.phonebill;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle bundle = getIntent().getExtras();
        final PhoneBillList bills = bundle.getParcelable("bills");
        System.out.println("SearchActivity getExtra:" + bills);

        final EditText cNameText = (EditText) findViewById(R.id.searchCNameText);
        final EditText startText = (EditText) findViewById(R.id.searchStartText);
        final EditText startTimeText = (EditText) findViewById(R.id.searchStartTimeText);
        final EditText startAMPMText = (EditText) findViewById(R.id.searchStartAMPMText);
        final EditText endText = (EditText) findViewById(R.id.searchEndText);
        final EditText endTimeText = (EditText) findViewById(R.id.searchEndTimeText);
        final EditText endAMPMText = (EditText) findViewById(R.id.searchEndAMPMText);

        Button searchButton = (Button) findViewById(R.id.searchSearchButton);


        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String customerName = cNameText.getText().toString();
                String startDate = startText.getText().toString();
                String startTime = startTimeText.getText().toString();
                String startAMPM = startAMPMText.getText().toString();
                String endDate = endText.getText().toString();
                String endTime = endTimeText.getText().toString();
                String endAMPM = endAMPMText.getText().toString();

                if ( customerName == null || startDate == null || startTime == null || startAMPM == null ||
                        endDate == null || endTime == null || endAMPM == null) {
                    //missing parameters
                } else {
                    Date searchSDate = parseDate(startDate + " " + startTime + " " + startAMPM);
                    Date searchEDate = parseDate(endDate + " " + endTime + " " + endAMPM);

                    PhoneBill bill = bills.getPhoneBill(customerName);
                    StringBuilder sb = new StringBuilder();
                    if (bill == null) {
                        sb.append("There is no phone bill for this customer");
                    } else {
                        Collection<PhoneCall> calls =  bill.getPhoneCalls();
                        for (PhoneCall call: calls) {
                            if ( searchSDate.getTime() < call.getStartTime().getTime() && call.getStartTime().getTime() < searchEDate.getTime()) {
                                sb.append(call + "\n");
                            }
                        }
                    }
                    TextView phonecallTextView = (TextView) findViewById(R.id.phoneCallsTextView);
                    phonecallTextView.setText(sb.toString());
                }
            }

            /**
             * @return a <code>Date</code> of MM/dd/yy h:mm a format of date and time
             */
            private Date parseDate(String date){
                try {
                    return new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(date);
                } catch (ParseException ex) {
//                    System.err.println("Bad date format");
//                    System.exit(1);
                    return null;
                }
            }
        });

        Button menuButton = (Button) findViewById(R.id.searchGoToMainButton);
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
