package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are
    // executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;
    private double amount = 0;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", 0);
        bankAccount = new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(bankAccount.getAccountHolder(), mRossi);
        assertEquals(bankAccount.getBalance(), 0);
        assertEquals(bankAccount.getTransactionsCount(), 0);
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the
     * balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), 100);
        amount += 100;
        assertEquals(1, bankAccount.getTransactionsCount());
        assertEquals(amount, bankAccount.getBalance());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        amount = amount - StrictBankAccount.TRANSACTION_FEE - StrictBankAccount.MANAGEMENT_FEE;
        assertEquals(amount, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -100);
            Assertions.fail("withdrawing a negative ammount was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(amount, bankAccount.getBalance());
            assertNotNull(e);
            assertFalse(e.getMessage().isBlank());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), bankAccount.getBalance() * 2);
            Assertions.fail("withdrawing more money than it is in the account is allowed, but should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(amount, bankAccount.getBalance());
            assertNotNull(e);
            assertFalse(e.getMessage().isBlank());
        }
    }
}
