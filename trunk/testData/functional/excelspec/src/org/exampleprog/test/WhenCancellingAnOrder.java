package org.exampleprog.test;

import org.accept4j.annotation.AcceptanceTest;
import org.accept4j.annotation.TestPack;

/**
 * User: Daniel Date: 23.09.12 Time: 17:18
 */

@TestPack(
        group="Booking",
        name="When cancelling an order"
)
public class WhenCancellingAnOrder {

    @AcceptanceTest(id="2.1")
    public void testTheClientShouldBeRefundedIfTheOrderIsNotYetFilled() {
        // test code here
    }

    @AcceptanceTest(id="2.2")
    public void testTheCancellationShouldBeDeclinedIfTheOrderIsFilled() {
        // test code here
    }
}
