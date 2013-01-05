package org.exampleprog.test;

import org.accept4j.annotation.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel
 * Date: 15.09.12
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
@TestPack(group="", name="")
public class WhenMakingAnOrder {

    @AcceptanceTest(id="TST-48730")
    @Test
    public void testTheClientShouldBeBilled() {
        assertTrue(true);
    }

    @AcceptanceTest(id="TST-48731")
    @Test
    public void testTheStockShouldBeReduced() {
        assertTrue(true);
    }
}
