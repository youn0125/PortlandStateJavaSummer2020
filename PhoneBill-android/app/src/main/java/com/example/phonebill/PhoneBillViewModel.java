package com.example.phonebill;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class PhoneBillViewModel extends ViewModel {
    private final Map<String, PhoneBill> phoneBills;

    PhoneBillViewModel() {
        phoneBills = new HashMap<>();
    }

    public Map<String, PhoneBill> getPhoneBills() {
        return phoneBills;
    }
    /**
     * @return a <code>PhoneBill</code>
     */
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }
    /**
     * Adds a <code>PhoneBill</code>
     */
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }

}