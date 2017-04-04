package com.autonomyway.business;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.DaoSession;
import com.autonomyway.model.Income;
import com.autonomyway.model.Wealth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedFacadeTests {
    static Context ctx = InstrumentationRegistry.getTargetContext();
    static AutonomyWayFacade facade = AutonomyWay.getInstance(ctx);


    @Test
    public void testCreateIncome() throws Exception {
        Income income = facade.createIncome("Salary", 1, 2, Income.Type.WORK);
        assertEquals("Salary", income.getName());
        assertEquals(1, income.getRecurrentTime());
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
        income.setRecurrentTime(2);
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
    public void testDefaultInit() throws Exception {
        // Nothing on beginning
        List<Income> incomeList = facade.getIncomeList();
        List<Wealth> wealthList = facade.getWealthList();
        
        assertEquals(0, incomeList.size());
        assertEquals(0, wealthList.size());
        
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
        
        facade.createInitialData();
        incomeList = facade.getIncomeList();
        assertEquals("Nothing must happen after first call", init_income_names.length, incomeList.size());
        
        wealthList = facade.getWealthList();
        assertEquals("Nothing must happen after first call", init_wealth_names.length, wealthList.size());

    }
    
    



    @After
    @Before
    public void cleanDB() {
        DaoSession session = ((AutonomyWay) facade).session;
        session.getIncomeDao().deleteAll();
        session.getWealthDao().deleteAll();
    }
}
