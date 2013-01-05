package org.exampleprog.test;

import org.accept4j.annotation.AcceptanceTest;
import org.accept4j.annotation.TestPack;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: Daniel Date: 23.09.12 Time: 17:18
 */

@TestPack(
        group="Booking",
        name="When cancelling an order"
)
public class WhenCancellingAnOrder {

    @AcceptanceTest(id="2.1")
    @Test
    public void testTheClientShouldBeRefundedIfTheOrderIsNotYetFilled() {
        assertTrue(true);
    }

    @AcceptanceTest(id="2.2")
    @Test
    public void testTheCancellationShouldBeDeclinedIfTheOrderIsFilled() {
        assertTrue(true);
    }
}
