package com.autonomyway.business;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Expense;
import com.autonomyway.model.Income;
import com.autonomyway.model.Transfer;
import com.autonomyway.model.Wealth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedFacadeTests {
    Context ctx = InstrumentationRegistry.getTargetContext();
    AutonomyWayFacade facade = AutonomyWay.getInstance(ctx);


    @Test
    public void testCreateIncome() throws Exception {
        Income income = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        assertEquals("Salary", income.getName());
        assertEquals(1, income.getRecurrentDuration());
        assertEquals(2, income.getRecurrentCash());
        assertEquals(Income.Type.WORK, income.getType());
        assertTrue(income.getId() > 0);
    }

    @Test
    public void testGetIncome() throws Exception {
        Income income = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        Income dbIncome = facade.getIncome(income.getId());
        assertEquals(income, dbIncome);
    }

    @Test
    public void testEditIncome() throws Exception {
        Income income = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        income.setName("Stocks");
        income.setRecurrentDuration(2);
        income.setRecurrentCash(3);
        income.setType(Income.Type.BUSINESS);
        facade.editIncome(income);
        Income dbIncome = facade.getIncome(income.getId());
        assertEquals(income, dbIncome);
    }


    @Test
    public void testGetIncomeList() throws Exception {
        List<Income> incomeList = facade.getIncomeList();
        assertEquals(0, incomeList.size());

        facade.createIncome("Stocks", 1, 2, Income.Type.WORK);
        incomeList = facade.getIncomeList();
        assertEquals(1, incomeList.size());
        assertEquals("Stocks", incomeList.get(0).getName());

        facade.createIncome("Salary", 1, 2, Income.Type.BUSINESS);
        incomeList = facade.getIncomeList();
        assertEquals(2, incomeList.size());
        // Incomes are ordered by name
        assertEquals("Salary", incomeList.get(0).getName());
        assertEquals("Stocks", incomeList.get(1).getName());

    }


    @Test
    public void testCreateWealth() throws Exception {
        Wealth wealth = facade.createWealth("Bank Account", 2);
        assertEquals("Bank Account", wealth.getName());
        assertEquals(2, wealth.getInitialBalance());
        assertEquals(2, wealth.getBalance());
        assertTrue(wealth.getId() > 0);
    }

    @Test
    public void testGetWealth() throws Exception {
        Wealth wealth = facade.createWealth("Bank Account", 1);
        Wealth dbWealth = facade.getWealth(wealth.getId());
        assertEquals(wealth, dbWealth);
    }

    @Test
    public void testEditWealth() throws Exception {
        Wealth wealth = facade.createWealth("Bank Account", 1);
        wealth.setName("House");
        wealth.setInitialBalance(2);
        facade.editWealth(wealth);
        Wealth dbWealth = facade.getWealth(wealth.getId());
        assertEquals(wealth, dbWealth);
        assertEquals("Wealth initial balance must reflect on current balance", 2, wealth.getBalance());
    }


    @Test
    public void testGetWealthList() throws Exception {
        List<Wealth> wealthList = facade.getWealthList();
        assertEquals(0, wealthList.size());

        facade.createWealth("House", 1);
        wealthList = facade.getWealthList();
        assertEquals(1, wealthList.size());
        assertEquals("House", wealthList.get(0).getName());

        facade.createWealth("Bank Account", 2);
        wealthList = facade.getWealthList();
        assertEquals(2, wealthList.size());
        // Wealth are ordered by name
        assertEquals("Bank Account", wealthList.get(0).getName());
        assertEquals("House", wealthList.get(1).getName());

    }

    @Test
    public void testCreateExpense() throws Exception {
        Expense expense = facade.createExpense("Taxes", 2);
        assertEquals("Taxes", expense.getName());
        assertEquals(2, expense.getRecurrentCash());
        assertTrue(expense.getId() > 0);
    }

    @Test
    public void testGetExpense() throws Exception {
        Expense expense = facade.createExpense("Taxes", 1);
        Expense dbExpense = facade.getExpense(expense.getId());
        assertEquals(expense, dbExpense);
    }

    @Test
    public void testEditExpense() throws Exception {
        Expense expense = facade.createExpense("Taxes", 1);
        expense.setName("House Expenses");
        expense.setRecurrentCash(2);
        facade.editExpense(expense);
        Expense dbExpense = facade.getExpense(expense.getId());
        assertEquals(expense, dbExpense);
    }


    @Test
    public void testGetExpenseList() throws Exception {
        List<Expense> expenseList = facade.getExpenseList();
        assertEquals(0, expenseList.size());

        facade.createExpense("House Expenses", 1);
        expenseList = facade.getExpenseList();
        assertEquals(1, expenseList.size());
        assertEquals("House Expenses", expenseList.get(0).getName());

        facade.createExpense("Taxes", 2);
        expenseList = facade.getExpenseList();
        assertEquals(2, expenseList.size());
        // Expenses are ordered by name
        assertEquals("House Expenses", expenseList.get(0).getName());
        assertEquals("Taxes", expenseList.get(1).getName());

    }

    @Test
    public void testTransferFromIncomeToWealth() {
        Income salary = facade.createIncome("Salary", 0, 0, Income.Type.WORK);
        Wealth account = facade.createWealth("Bank Account", 1);
        Date dt = new Date();
        Transfer firstSalary = facade.createTransfer(salary, account, dt, 6, 3, "First Salary");
        assertNotNull(firstSalary.getId());
        assertEquals(6, firstSalary.getCash());
        assertEquals(3, firstSalary.getDuration());
        assertEquals(dt, firstSalary.getDate());
        assertEquals("First Salary", firstSalary.getDetail());
        assertEquals(Transfer.NodeClassHolder.INCOME, firstSalary.getOriginClassHolder());
        assertEquals(Transfer.NodeClassHolder.WEALTH, firstSalary.getDestinationClassHolder());
        assertEquals(salary.getId(), (Long) firstSalary.getOriginId());
        assertEquals(account.getId(), (Long) firstSalary.getDestinationId());
        account = facade.getWealth(account.getId());
        assertEquals("Balance must be increased by transfer cash", 1 + 6, account.getBalance());

    }


    @Test
    public void testTransferFromWealthToExpense() {
        Expense taxes = facade.createExpense("Taxes", 0);
        Wealth account = facade.createWealth("Bank Account", 1);
        Date dt = new Date();
        Transfer irs = facade.createTransfer(account, taxes, dt, 6, 3, "IRS");
        assertEquals(Transfer.NodeClassHolder.WEALTH, irs.getOriginClassHolder());
        assertEquals(Transfer.NodeClassHolder.EXPENSE, irs.getDestinationClassHolder());
        account = facade.getWealth(account.getId());
        assertEquals("Balance must be decreased by transfer cash", 1 - 6, account.getBalance());
    }

    @Test
    public void testTransferFromWealthToWealth() {
        Wealth account = facade.createWealth("Bank Account", 20);
        Wealth house = facade.createWealth("House", 1);
        Date dt = new Date();
        facade.createTransfer(account, house, dt, 6, 3, "Buying Home");
        account = facade.getWealth(account.getId());
        house = facade.getWealth(house.getId());
        assertEquals("Account balance must be decreased by transfer cash",
                20 - 6, account.getBalance());
        assertEquals("Home balance must be increased by transfer cash",
                1 + 6, house.getBalance());
    }


    @Test
    public void testGetTransferList() {
        Wealth account = facade.createWealth("Bank Account", 20);
        Income salary = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        Date dt = new Date();
        Transfer firstSalary = facade.createTransfer(salary, account, dt, 6, 3, "First salary");
        List<Transfer> transferList = facade.getTransferList();
        assertEquals(1, transferList.size());
        assertEquals(account, transferList.get(0).getDestination());
        assertEquals(salary, transferList.get(0).getOrigin());
        Transfer secondSalary = facade.createTransfer(
                salary, account, new Date(), 12, 3, "Second salary");
        transferList = facade.getTransferList();
        // Ordered by date Desc
        assertEquals(secondSalary,transferList.get(0));
        assertEquals(firstSalary,transferList.get(1));

    }

    @Test
    public void testEditTransferCashIncomeToWealth() {
        int initial = 20;
        Wealth account = facade.createWealth("Bank Account", initial);
        Income salary = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        Date dt = new Date();
        int firstSalaryAmount = 6;
        facade.createTransfer(salary, account, dt, firstSalaryAmount, 3, "First salary");
        int secondSalaryAmount = 8;
        Transfer secondSalary = facade.createTransfer(salary, account, dt, secondSalaryAmount, 3, "First salary");
        int expectedBalance = initial + firstSalaryAmount + secondSalaryAmount;
        assertEquals(expectedBalance, account.getBalance());
        int editedSecondSalaryAmount = 4;
        secondSalary.setCash(editedSecondSalaryAmount);
        facade.editTransfer(secondSalary);
        expectedBalance = initial + firstSalaryAmount + editedSecondSalaryAmount;
        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    public void testEditTransferCashWealthToExpense() {
        int initial = 20;
        Wealth account = facade.createWealth("Bank Account", initial);
        Expense rent = facade.createExpense("Rent", 1);
        Date dt = new Date();
        int firstRentAmount = 6;
        facade.createTransfer(account, rent, dt, firstRentAmount, 3, "First Rent");
        int secondRentAmount = 8;
        Transfer secondRent = facade.createTransfer(account, rent, dt, secondRentAmount, 3,
                "Second Rent ");
        int expectedBalance = initial - firstRentAmount - secondRentAmount;
        assertEquals(expectedBalance, account.getBalance());
        int editedSecondRentAmount = 4;
        secondRent.setCash(editedSecondRentAmount);
        facade.editTransfer(secondRent);
        expectedBalance = initial - firstRentAmount - editedSecondRentAmount;
        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    public void testEditTransferCashWealthToWealth() {
        int initialBankAmount = 20;
        Wealth account = facade.createWealth("Bank Account", initialBankAmount);
        int initialHouseAmount = 1;
        Wealth house = facade.createWealth("House", initialHouseAmount);
        Date dt = new Date();
        int firstHouseAmount = 6;
        facade.createTransfer(account, house, dt, firstHouseAmount, 3, "First Mortgage");
        int secondHouseAmount = 8;
        Transfer secondPayment = facade.createTransfer(account, house, dt, secondHouseAmount, 3,
                "Second Mortgage");
        int expectedBankBalance = initialBankAmount - firstHouseAmount - secondHouseAmount;
        assertEquals(expectedBankBalance, account.getBalance());
        int expectedHouseBalance = initialHouseAmount + firstHouseAmount + secondHouseAmount;
        assertEquals(expectedHouseBalance, house.getBalance());
        int editedSecondHouseAmount = 4;
        secondPayment.setCash(editedSecondHouseAmount);
        facade.editTransfer(secondPayment);
        expectedBankBalance = initialBankAmount - firstHouseAmount - editedSecondHouseAmount;
        assertEquals(expectedBankBalance, account.getBalance());
        expectedHouseBalance = initialHouseAmount + firstHouseAmount + editedSecondHouseAmount;
        assertEquals(expectedHouseBalance, house.getBalance());
    }

    @Test
    public void testEditTransferCashAndNode() {
        Wealth account = facade.createWealth("Bank Account", 0);
        Wealth house = facade.createWealth("House", 0);
        Date dt = new Date();
        Expense rent = facade.createExpense("Rent", 0);
        Income salary = facade.createIncome("Salary", 0, 0, Income.Type.WORK);
        final int TWENTY = 20;
        Transfer transfer=facade.createTransfer(salary,account,dt, TWENTY,10, "");
        assertEquals(transfer.getCash(), account.getBalance());
        // Edit destination to other wealth
        transfer.setDestination(house);
        facade.editTransfer(transfer);
        account=facade.getWealth(account.getId());
        assertEquals(TWENTY, house.getBalance());
        assertEquals(0, account.getBalance());
        // Destination become origin
        transfer.setOrigin(house);
        transfer.setDestination(account);
        facade.editTransfer(transfer);
        account=facade.getWealth(account.getId());
        house=facade.getWealth(house.getId());
        assertEquals(TWENTY, account.getBalance());
        assertEquals(-TWENTY, house.getBalance());
        // destination become origin and vice versa
        transfer.setOrigin(account);
        transfer.setDestination(house);
        facade.editTransfer(transfer);
        account=facade.getWealth(account.getId());
        house=facade.getWealth(house.getId());
        assertEquals(-TWENTY, account.getBalance());
        assertEquals(TWENTY, house.getBalance());
        // destination become origin and vice versa, cash changed by half
        transfer.setOrigin(house);
        transfer.setDestination(account);
        final int TWENTY_FIVE = TWENTY + 5;
        transfer.setCash(TWENTY_FIVE);
        facade.editTransfer(transfer);
        account=facade.getWealth(account.getId());
        house=facade.getWealth(house.getId());
        assertEquals(TWENTY_FIVE, account.getBalance());
        assertEquals(-TWENTY_FIVE, house.getBalance());

    }

    @Test
    public void testDefaultInit() throws Exception {
        // Nothing on beginning
        List<Income> incomeList = facade.getIncomeList();
        List<Wealth> wealthList = facade.getWealthList();
        List<Expense> expenseList = facade.getExpenseList();

        assertEquals(0, incomeList.size());
        assertEquals(0, wealthList.size());
        assertEquals(0, expenseList.size());

        facade.createInitialData();

        //Income
        incomeList = facade.getIncomeList();
        assertEquals(2, incomeList.size());
        String[] init_income_names = {
                ctx.getString(R.string.income_init_salary),
                ctx.getString(R.string.income_init_stocks)
        };
        for (int i = 0; i < init_income_names.length; ++i) {
            assertEquals(init_income_names[i], incomeList.get(i).getName());
        }

        //Wealth

        wealthList = facade.getWealthList();
        assertEquals(6, wealthList.size());
        String[] init_wealth_names = {
                ctx.getString(R.string.wealth_init_bank_account),
                ctx.getString(R.string.wealth_init_car),
                ctx.getString(R.string.wealth_init_credit_card),
                ctx.getString(R.string.wealth_init_house),
                ctx.getString(R.string.wealth_init_stocks),
                ctx.getString(R.string.wealth_init_wallet),
        };
        for (int i = 0; i < init_wealth_names.length; ++i) {
            assertEquals(init_wealth_names[i], wealthList.get(i).getName());
        }

        //Expense
        expenseList = facade.getExpenseList();
        assertEquals(7, expenseList.size());
        String[] init_expense_names = {
                ctx.getString(R.string.expense_init_car_expenses),
                ctx.getString(R.string.expense_init_children),
                ctx.getString(R.string.expense_init_depreciation),
                ctx.getString(R.string.expense_init_health),
                ctx.getString(R.string.expense_init_house_expenses),
                ctx.getString(R.string.expense_init_leisure),
                ctx.getString(R.string.expense_init_taxes)

        };
        for (int i = 0; i < init_expense_names.length; ++i) {
            assertEquals(init_expense_names[i], expenseList.get(i).getName());
        }

        facade.createInitialData();
        incomeList = facade.getIncomeList();
        assertEquals("Nothing must happen after first call", init_income_names.length, incomeList.size());

        wealthList = facade.getWealthList();
        assertEquals("Nothing must happen after first call", init_wealth_names.length, wealthList.size());

        expenseList = facade.getExpenseList();
        assertEquals("Nothing must happen after first call", init_expense_names.length, expenseList.size());

    }


    @After
    @Before
    public void cleanDB() {
        AutonomyWay facade = (AutonomyWay) this.facade;
        facade.cleanAllCache();
        DaoSession session = facade.session;
        session.getIncomeDao().deleteAll();
        session.getWealthDao().deleteAll();
        session.getExpenseDao().deleteAll();
        session.getTransferDao().deleteAll();
    }
}
