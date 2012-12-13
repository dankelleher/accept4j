package org.exampleprog.test;

import org.accept4j.annotation.AcceptanceTest;
import org.accept4j.annotation.TestPack;

/**
 * User: Daniel Date: 23.09.12 Time: 17:18
 */

@TestPack(
        group="Invoicing",
        name="When generating invoices"
)
public class WhenGeneratingInvoices {

    @AcceptanceTest(id="3.1")
    public void testAllClientsWithOutstandingBalancesShouldReceiveInvoices() {

    }
}
