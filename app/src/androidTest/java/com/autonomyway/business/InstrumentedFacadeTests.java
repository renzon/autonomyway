package com.autonomyway.business;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.autonomyway.AutonomyWayFacade;
import com.autonomyway.R;
import com.autonomyway.model.Income;

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
        Income income=facade.createIncome("Salary",1,2, Income.Type.WORK);
        assertEquals("Salary", income.getName());
        assertEquals(1, income.getRecurrentTime());
        assertEquals(2, income.getRecurrentCash());
        assertEquals(Income.Type.WORK, income.getType());
        assertTrue(income.getId()>0);
    }
    @Test
    public void testGetIncomeList() throws Exception {
        List<Income> incomeList=facade.getIncomeList();
        assertEquals(0, incomeList.size());

        facade.createIncome("Stocks",1,2,Income.Type.WORK);
        incomeList=facade.getIncomeList();
        assertEquals(1, incomeList.size());
        assertEquals("Stocks", incomeList.get(0).getName());

        facade.createIncome("Salary",1,2, Income.Type.BUSINESS);
        incomeList=facade.getIncomeList();
        assertEquals(2, incomeList.size());
        // Incomes are ordered by name
        assertEquals("Salary", incomeList.get(0).getName());
        assertEquals("Stocks", incomeList.get(1).getName());

    }

    @Test
    public void testDefaultInit() throws Exception {
        List<Income> incomeList=facade.getIncomeList();
        assertEquals(0, incomeList.size());
        facade.createInitialData();
        incomeList=facade.getIncomeList();
        assertEquals(2, incomeList.size());
        String[] init_income_names={
                ctx.getString(R.string.income_init_salary),
                ctx.getString(R.string.income_init_stocks)
        };
        for (int i =0; i<2; ++i){
            assertEquals(init_income_names[i], incomeList.get(i).getName());
        }
        facade.createInitialData();
        incomeList=facade.getIncomeList();
        assertEquals("Nothing must happen after first call",2, incomeList.size());

    }

    @After
    @Before
    public void cleanDB(){
        ((AutonomyWay)facade).session.getIncomeDao().deleteAll();
    }
}
