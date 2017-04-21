package com.autonomyway;

import com.autonomyway.model.Transformation;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TransformationTests {
    @Test
    public void numberToCashRound() throws Exception {
        assertEquals(6621,Transformation.numberTocash("66.21"));
    }

    @Test
    public void cashToNumberRound() throws Exception {
        Locale.setDefault(Locale.US);
        assertEquals("66.21",Transformation.cashToNumber(6621));
    }

    @Test
    public void cashToCurrencyRound() throws Exception {
        Locale.setDefault(Locale.US);
        assertEquals("$66.21",Transformation.cashToCurrency(6621));
    }
}