package org.accept4j.specification.generator

import groovy.io.FileType
import org.accept4j.specification.SpecParseException
import org.accept4j.testpack.AcceptanceTestSuite
import org.accept4j.testpack.AcceptanceTestPackGroup
import org.accept4j.testpack.AcceptanceTestPack
import org.accept4j.testpack.AcceptanceTestItem

/**
 * Copyright: Daniel Kelleher Date: 12.10.12 Time: 18:24
 */
class DirectorySpecGenerator implements SpecGenerator {
    public static final String PATH = "spec"

    @Override
    void generate() {
        def structure = [:]
        
        try {
            loadDirectoryStructure(new File(PATH), structure)
        } catch (FileNotFoundException e) {
            throw new SpecParseException(e)
        }

        AcceptanceTestSuite suite = convertDirectoryStructureToSpec(structure)

        def xmlFile = new File("spec.xml")
        xmlFile.withWriter() { it << suite.toXML() }
    }

    AcceptanceTestSuite convertDirectoryStructureToSpec(def structure) {
        AcceptanceTestSuite suite = new AcceptanceTestSuite()

        eachNonDocs(structure) { suiteName, suiteContents ->
            suite.name = suiteName
            eachNonDocs(suiteContents) { groupName, groupContents ->
                AcceptanceTestPackGroup group = suite.findOrCreate(groupName)
                eachNonDocs(groupContents) { packName, packContents ->
                    AcceptanceTestPack pack = group.findOrCreate(packName)
                    packContents.docs.each {
                        String id = extractIDFromFilename(it)
                        String name = extractNameFromFilename(it)
                        AcceptanceTestItem test = new AcceptanceTestItem(id: id, name: name)
                        pack.add(test)
                    }
                }
            }
        }

        return suite
    }

    def eachNonDocs(def map, Closure c) {
        map.findAll({k,v -> k != "docs"}).each(c)
    }

    def getFieldFromFilename = {fieldIndex, name ->
        def trimmedName = name.replaceAll(/(.*_.*)\.[^\.]*$/, '$1')        // remove file extension if it exists
        def matcher = trimmedName =~ /([^_]*)_(.*)/
        if (matcher.matches()) {
            return matcher[0][fieldIndex]
        }
        throw new SpecParseException("Unable to parse file name $name")
    }
    def extractIDFromFilename = getFieldFromFilename.curry(1)
    def extractNameFromFilename = getFieldFromFilename.curry(2)

    @Override
    void postProcess() {
        // do nothing here - we might change this later using a config file to indicate whether we want to recreate the spec on every compile
        // default is not to do so, as it may be slow
    }
    
    void loadDirectoryStructure(File directory, def structure) {
        def docs = []
        structure.docs = docs
        
        directory.eachFileMatch(FileType.FILES, ~/.*_.*/) {      // anything with an underscore
            docs << it.name
        }

        directory.eachDir {
            def subMap = [:]
            structure[it.name] = subMap
            loadDirectoryStructure(it, subMap)
        }
    } 
}
