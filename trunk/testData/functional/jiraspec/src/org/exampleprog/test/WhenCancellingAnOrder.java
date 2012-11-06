package org.exampleprog.test;

import org.accept4j.annotation.AcceptanceTest;
import org.accept4j.annotation.TestPack;

/**
 * User: Daniel Date: 23.09.12 Time: 17:18
 */

@TestPack(
        group="",
        name=""
)
public class WhenCancellingAnOrder {

    @AcceptanceTest(id="TST-48701")
    public void testTheClientShouldBeRefundedIfTheOrderIsNotYetFilled() {
        // test code here
    }

    @AcceptanceTest(id="TST-48702")
    public void testTheCancellationShouldBeDeclinedIfTheOrderIsFilled() {
        // test code here
    }
}
