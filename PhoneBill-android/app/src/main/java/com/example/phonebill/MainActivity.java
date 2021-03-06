package com.example.phonebill;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    PhoneBillList bills = new PhoneBillList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            bills = parseResults();
        } catch (IOException ioe) {
            Toast.makeText(this,"while reading file" + ioe.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                bills = bundle.getParcelable("bills");
                if (bills == null)
                    System.out.println("bills is null");
                else {
                    System.out.println("MainActivity getExtra" + bills);
                    try {
                        saveResults();
                    } catch (IOException ioe) {
                        Toast.makeText(this,"while writing file" + ioe.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        }

        String[] items = {"Create Phone Bill", "Pretty Print Phone Bill", "Searching", "Help"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    //String item = String.valueOf(parent.getItemAtPosition(i));
                    switch (i) {
                        case 0:
                            Intent createPBIntent = new Intent(getApplicationContext(), CreatePhoneBillActivity.class);
                            createPBIntent.putExtra("bills", bills);
                            startActivity(createPBIntent);
                            break;
                        case 1:
                            Intent prettyPrintPBIntent = new Intent(getApplicationContext(), PrettyPrintActivity.class);
                            prettyPrintPBIntent.putExtra("bills", bills);
                            startActivity(prettyPrintPBIntent);
                            break;
                        case 2:
                            Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                            searchIntent.putExtra("bills", bills);
                            startActivity(searchIntent);
                            break;
                        case 3:
                            Intent readmeIntent = new Intent(getApplicationContext(), ReadmeActivity.class);
                            readmeIntent.putExtra("bills", bills);
                            startActivity(readmeIntent);
                            break;
                        default:
                            break;
                    }
                    //Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                }
            }
        );
    }


    private PhoneBillList parseResults() throws IOException {
        PhoneBillList bills = new PhoneBillList();
        File dir = getDataDir();
        File file = new File(dir, "results.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String fileString = br.readLine();
            PhoneBill bill = null;
            while (fileString != null) {
                String[] fileStrArgs = fileString.split(" ");
                if (fileStrArgs.length == 1) {
                    bill = new PhoneBill(fileStrArgs[0]);
                    bills.addPhoneBill(bill);
                } else if (fileStrArgs.length == 8) {
                    bill.addPhoneCall(new PhoneCall(fileStrArgs[0], fileStrArgs[1],
                            fileStrArgs[2] + " " + fileStrArgs[3] + " " + fileStrArgs[4],
                            fileStrArgs[5] + " " + fileStrArgs[6]+ " " + fileStrArgs[7]));
                } else {
                    br.close();
                }
                fileString = br.readLine();
            }
            br.close();
            return bills;

        } finally {
            if (br!=null){
                br.close();
            }
        }

    }

    private void saveResults() throws  IOException{
        File dir = getDataDir();
        File file = new File(dir, "results.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file), true);
            for (Map.Entry bill : bills.getPhoneBills().entrySet()) {
                pw.println(bill.getKey());
                Collection<PhoneCall> calls = ((PhoneBill)bill.getValue()).getPhoneCalls();
                for (PhoneCall call: calls) {
                    pw.println(call.getCaller() + " " + call.getCallee() + " " +
                            call.getStartTimeString() + " " + call.getEndTimeString());
                }
            }
        } finally {
            if (pw!=null) {
                pw.close();
            }
        }

    }

}