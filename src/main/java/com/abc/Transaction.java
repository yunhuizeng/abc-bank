package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.setTransactionDate(DateProvider.getInstance().now());
    }

	public Date getTransactionDate() {
		return transactionDate;
	}

	//should be private, but change to public for testing purpose.
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public boolean isWithdrawal() {
		return this.amount<0;
	}

}
