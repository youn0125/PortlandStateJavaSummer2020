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

                if ( !customerName.equals("") && (startDate.equals("") && startTime.equals("") && startAMPM.equals("") &&
                        endDate.equals("") && endTime.equals("") && endAMPM.equals(""))) {
                    PhoneBill bill = bills.getPhoneBill(customerName);
                    StringBuilder sb = new StringBuilder();
                    if (bill == null) {
                        sb.append("There is no phone bill for this customer");
                    } else {
                        Collection<PhoneCall> calls =  bill.getPhoneCalls();
                        int cnt = 0;
                        for (PhoneCall call: calls) {
                            sb.append(call + "\n\n");
                            cnt++;
                        }
                        if(cnt == 0) {
                            sb.append("There is no phone calls for this condition\n");
                        }
                    }
                    TextView phonecallTextView = (TextView) findViewById(R.id.phoneCallsTextView);
                    phonecallTextView.setText(sb.toString());
                } else if ( customerName.equals("") || startDate.equals("") || startTime.equals("") || startAMPM.equals("") ||
                        endDate.equals("") || endTime.equals("") || endAMPM.equals("")) {
                    popAlertDialog("Check your missing inputs");
                } else if (!checkDatePattern(startDate) || !checkTimePattern(startTime + " " + startAMPM)) {
                    popAlertDialog("Check your start date and time");
                } else if (!checkDatePattern(endDate) || !checkTimePattern(endTime + " " + endAMPM)) {
                    popAlertDialog("Check your end date and time");
                } else if (!checkStartEndTime(startDate + " " + startTime + " " + startAMPM,
                        endDate + " " + endTime + " " + endAMPM)) {
                    popAlertDialog("End time is before its starts time");
                } else {
                    Date searchSDate = parseDate(startDate + " " + startTime + " " + startAMPM);
                    Date searchEDate = parseDate(endDate + " " + endTime + " " + endAMPM);

                    PhoneBill bill = bills.getPhoneBill(customerName);
                    StringBuilder sb = new StringBuilder();
                    if (bill == null) {
                        sb.append("There is no phone bill for this customer");
                    } else {
                        Collection<PhoneCall> calls =  bill.getPhoneCalls();
                        int cnt = 0;
                        for (PhoneCall call: calls) {
                            if ( searchSDate.getTime() < call.getStartTime().getTime() && call.getStartTime().getTime() < searchEDate.getTime()) {
                                sb.append(call + "\n\n");
                                cnt++;
                            }
                        }
                        if(cnt == 0) {
                            sb.append("There is no phone calls for this condition\n");
                        }
                    }
                    TextView phonecallTextView = (TextView) findViewById(R.id.phoneCallsTextView);
                    phonecallTextView.setText(sb.toString());
                }
            }
            /**
             * @return a <code>boolean</code> of validity of date.
             */
            private boolean checkDatePattern(String date) {
                String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}";
                if (date.matches(pattern)) {
                    return true;
                } else {
                    return false;
                }
            }
            /**
             * @return a <code>boolean</code> of validity of time.
             */
            private boolean checkTimePattern(String date) {

                String pattern = "(1[012]|0?[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
                if (date.matches(pattern)) {
                    return true;
                } else {
                    return false;
                }
            }
            /**
             * @return a <code>boolean</code> of validity of start and end time(endTime-startTime >= 0).
             */
            private boolean checkStartEndTime(String sTime, String eTime) {
                Date sDate = parseDate(sTime);
                Date eDate = parseDate(eTime);
                if (eDate.getTime() - sDate.getTime() < 0)
                    return false;
                return true;
            }


            private void popAlertDialog(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setMessage(message)
                        .setPositiveButton("confirm", null)
                        .create()
                        .show();
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
