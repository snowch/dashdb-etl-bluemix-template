#!/usr/bin/env groovy

/*
 * This script creates a simple date dimension from a CSV file.
 * 
 */

import groovy.sql.Sql
import java.text.SimpleDateFormat

this.getClass().classLoader.rootLoader.addURL(new File("../libs/db2jcc4.jar").toURL())

def env = System.getenv()

def sql = Sql.newInstance(
               env['db_url'], 
               env['db_username'], 
               env['db_password'], 
               'com.ibm.db2.jcc.DB2Driver'
            )

try {
    println 'Dropping DATE_DIMENSION table'
    sql.execute """DROP TABLE DATE_DIMENSION"""
} catch (all) {
    // ignore
}

println 'Creating DATE_DIMENSION table'
sql.execute """
    CREATE TABLE DATE_DIMENSION (
        YEAR_ID         INTEGER,
        YEAR_LABEL      CHAR(4),
        QUARTER_ID      INTEGER,
        QUARTER_LABEL   CHAR(6),
        MONTH_ID        INTEGER,
        MONTH_LABEL     CHAR(7),
        DAY_ID          TIMESTAMP,
        DAY_LABEL       CHAR(10),
        HOUR_ID         TIMESTAMP,
        HOUR_LABEL      CHAR(26),      
        MINUTE_ID       TIMESTAMP,
        MINUTE_LABEL    CHAR(16)
    )
"""

def cmd = """INSERT INTO DATE_DIMENSION VALUES ( 
                         ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                     )
             """

def csv = getClass().getResourceAsStream('/date_dimension.csv').text

def batchSize = 5000

sql.withBatch(batchSize, cmd) { ps ->   

    println 'Reading csv file: '

    csv.eachLine { line, count ->

        print '.'

        def params = line.split(',')

        // skip the header line
        if (count != 0) 
        {
            ps.addBatch( params )
        }
    }
}
println '\nFinished loading date dimension'

