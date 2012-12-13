package org.accept4j.testpack

/**
 * Copyright: Daniel Kelleher Date: 09.12.12 Time: 16:07
 */
class AcceptanceTestUnitUtils {
    static AcceptanceTestPack makePackWithTests(String... ids) {
        AcceptanceTestPack pack = new AcceptanceTestPack()
        ids.each { pack << new AcceptanceTestItem(id: it)}
        return pack
    }
}
