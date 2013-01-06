package org.accept4j.ext.specification.generator.text

import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.specification.SpecParseException
import org.accept4j.testpack.AcceptanceTestItem

/**
 * Copyright: Daniel Kelleher Date: 09.12.12 Time: 19:35
 */
class TextSpecBuilder {
    static class SpecData {
        String suite, group, pack, id, name, description
        boolean inDescription
        
        boolean isValid() {
            id != null 
        }
        
        SpecData cloneStructure() {
            return new SpecData(suite: suite, group: group, pack: pack)
        }
    }
    
    def fieldNames = ["suite", "group", "pack", "id", "name", "description"]
    SpecData data = new SpecData()
    AcceptanceTestSuite suite
    
    void reset() {
        data = data.cloneStructure()
    }

    void acceptLine(String line) {
        if (recognisedField(line)) {
            addField(line)
        } else if (data.inDescription) {
            data.description += "\n" + line
        } else throw new SpecParseException("Unrecognised field " + line)
    }

    void addField(String line) {
        if (isOptionalTestField(line)) {
            if (data.id == null) throw new SpecParseException("Need test ID before line " + line)
        }

        def name = getFieldName(line)
        
        if (data.isValid() && name in ['suite', 'group', 'pack', 'id']) {
            //starting new test
            complete()
        }

        data.inDescription = (name == "description")

        data[name] = getFieldValue(line)        
    }

    boolean isOptionalTestField(String line) {
        getFieldName(line) in ['name', 'description']
    }

    boolean recognisedField(String line) {
        getFieldName(line) in fieldNames
    }

    void complete() {
        if (!data.isValid()) return
        
        def test = new AcceptanceTestItem(id:data.id, name:data.name, description:data.description)
        if (data.suite == null) data.suite = ""
        if (data.group == null) data.group = ""
        if (data.pack == null) data.pack = ""

        suite.name = data.suite
        suite.findOrCreate(data.group).findOrCreate(data.pack).add(test)
        
        reset()
    }
    
    String getFieldName(String line) {
        def matcher = line =~ /([^:]*):.*/
        if (matcher.matches()) {
            return matcher[0][1]
        }
        return null
    }

    String getFieldValue(String line) {
        def matcher = line =~ /[^:]*:(.*)/
        if (matcher.matches()) {
            return matcher[0][1]
        }
        return null
    }
}
