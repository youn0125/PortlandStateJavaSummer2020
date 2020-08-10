package com.example.phonebill;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

public class PhoneBillListAdaptor extends BaseAdapter {

    private Context context;
    private List<PhoneBill> phoneBillList;

    public PhoneBillListAdaptor(Context context, List<PhoneBill> phoneBillList) {
        this.context = context;
        this.phoneBillList = phoneBillList;
    }

    @Override
    public int getCount() {
        return phoneBillList.size();
    }

    @Override
    public Object getItem(int i) {
        return phoneBillList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.phonebill, null);
        TextView customerName = (TextView)v.findViewById(R.id.cNameText);
        TextView caller = (TextView)v.findViewById(R.id.callerText);
        TextView callee = (TextView)v.findViewById(R.id.calleeText);
        TextView start = (TextView)v.findViewById(R.id.startText);
        TextView end = (TextView)v.findViewById(R.id.endText);

        customerName.setText(phoneBillList.get(i).getCustomer());
//        Collection<PhoneCall> phoneCalls = phoneBillList.get(i).getPhoneCalls();
//        for ( PhoneCall call: phoneCalls) {
//
//        }
//        caller.setText()
        v.setTag(phoneBillList.get(i).getCustomer());
        return v;
    }
}
