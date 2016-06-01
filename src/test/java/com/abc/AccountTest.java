package com.abc;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {

	private static final double DOUBLE_DELTA = 1e-15;
	Account savingAccount;
	Date tendays;
	
	
	@Before
	public void setUp() throws Exception {
		savingAccount = new Account(Account.SAVINGS);
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, -10);
		tendays = Calendar.getInstance().getTime();
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTransferTo1() {
		savingAccount.deposit(1500.0);
		savingAccount.transferTo(null, 100.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTransferTo2() {
		savingAccount.deposit(1500.0);
		Account checkingAccount = new Account(Account.CHECKING);
		checkingAccount.deposit(1000.0);
		savingAccount.transferTo(checkingAccount, 0);
	}
	
	@Test
	public void testTransferTo3() {
		savingAccount.deposit(1500.0);
		Account checkingAccount = new Account(Account.CHECKING);
		checkingAccount.deposit(1000.0);
		savingAccount.transferTo(checkingAccount, 100.0);
		assertEquals(savingAccount.sumTransactions(), 1400.0, DOUBLE_DELTA);
		assertEquals(checkingAccount.sumTransactions(), 1100.0, DOUBLE_DELTA);
	}
	

	@Test
	public void testHasRecentWithdrawals() {
		Transaction transaction = new Transaction(200.0);
		transaction.setTransactionDate(tendays);
		savingAccount.addTransaction(transaction);
		assertEquals(false, savingAccount.hasRecentWithdrawals());
		savingAccount.deposit(1500.0);
		savingAccount.withdraw(100.0);
		assertEquals(true, savingAccount.hasRecentWithdrawals());
	}

}
