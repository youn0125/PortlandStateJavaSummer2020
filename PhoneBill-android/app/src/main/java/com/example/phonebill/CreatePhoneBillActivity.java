package com.example.phonebill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePhoneBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phone_bill);

        Bundle bundle = getIntent().getExtras();
        final PhoneBillList bills = bundle.getParcelable("bills");

        final EditText cNameText = (EditText) findViewById(R.id.cNameText);
        final EditText callerText = (EditText) findViewById(R.id.callerText);
        final EditText calleeText = (EditText) findViewById(R.id.calleeText);
        final EditText startText = (EditText) findViewById(R.id.startText);
        final EditText startTimeText = (EditText) findViewById(R.id.startTimeText);
        final EditText startAMPMText = (EditText) findViewById(R.id.startAMPMText);
        final EditText endText = (EditText) findViewById(R.id.endText);
        final EditText endTimeText = (EditText) findViewById(R.id.endTimeText);
        final EditText endAMPMText = (EditText) findViewById(R.id.endAMPMText);

        cNameText.clearComposingText();

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

                if ( customerName.equals("") || caller.equals("") || callee.equals("") || startDate.equals("") ||
                        startTime.equals("") || startAMPM.equals("") || endDate.equals("") || endTime.equals("") || endAMPM.equals("")) {
                    popAlertDialog("Check your missing inputs");
                } else if(!checkPNumberPatten(caller) || !checkPNumberPatten(callee)) {
                    popAlertDialog("Check your caller or callee number");
                } else if (!checkDatePattern(startDate) || !checkTimePattern(startTime + " " + startAMPM)) {
                    popAlertDialog("Check your start date and time");
                } else if (!checkDatePattern(endDate) || !checkTimePattern(endTime + " " + endAMPM)) {
                    popAlertDialog("Check your end date and time");
                } else if (!checkStartEndTime(startDate + " " + startTime + " " + startAMPM,
                        endDate + " " + endTime + " " + endAMPM)) {
                    popAlertDialog("End time is before its starts time");
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

                    popAlertDialog("Create Phone bill with Phone call");

                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mainIntent.putExtra("bills", bills);
                    startActivity(mainIntent);
                }

            }


            /**
             * @return a <code>boolean</code> of validity of phone number.
             */
            private boolean checkPNumberPatten(String pNumber) {
                String pattern = "(?:\\d{3}-){2}\\d{4}";
                if (pNumber.matches(pattern)) {
                    return true;
                } else {
                    return false;
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

            private void popAlertDialog(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePhoneBillActivity.this);
                builder.setMessage(message)
                        .setPositiveButton("confirm", null)
                        .create()
                        .show();
            }
        });

        Button menuButton = (Button) findViewById(R.id.createPBMenuButton);
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