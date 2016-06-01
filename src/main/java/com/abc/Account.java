package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}
	
	public void transferTo(Account other, double amount){
		if (other == null) {
			throw new IllegalArgumentException("must have another account");
		}
		else if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		}
		else {
			this.transactions.add(new Transaction(-amount));
			other.transactions.add(new Transaction(amount));
		}
	}

	public boolean hasRecentWithdrawals(){
		Date now = DateProvider.getInstance().now();
		for (int i=this.transactions.size()-1;i>=0;i--){
			Transaction tx = this.transactions.get(i);
			Date date = tx.getTransactionDate();
			long diff = now.getTime() - date.getTime();
			if (diff > 10*24*60*60*1000) return false;
			if (tx.isWithdrawal()){
				return true;
			}
		}
		
		return false;
	}
	
    public double interestEarned() {
        double amount = sumTransactions();
        double annualInterest = 0.0;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                	annualInterest = amount * 0.001;
                else
                	annualInterest = 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
                break;
            case MAXI_SAVINGS:
            
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
            	
            	// Change to new interest rules
            	if (this.hasRecentWithdrawals())
            		annualInterest = amount * 0.001;
            	else
            		annualInterest = amount * 0.05;
            	break;
            default:
            	annualInterest = amount * 0.001;
            	break;
        }
        return annualInterest / 365;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public void addTransaction(Transaction t) {
    	this.transactions.add(t);
    }

}
