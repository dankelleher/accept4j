package org.exampleprog.test;

import org.accept4j.annotation.*;
import org.junit.Test;

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
        System.out.println("hello world");
    }

    @AcceptanceTest(id="TST-48731")
    public void testTheStockShouldBeReduced() {

    }
}
