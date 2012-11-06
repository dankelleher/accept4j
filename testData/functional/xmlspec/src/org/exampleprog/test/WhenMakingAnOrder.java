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
@TestPack(group="Booking", name="When making an order")
public class WhenMakingAnOrder {

    @AcceptanceTest(id="1.1")
    @Test
    public void testTheClientShouldBeBilled() {
        System.out.println("hello world");
    }

    @AcceptanceTest(id="1.2")
    public void testTheStockShouldBeReduced() {

    }
}
