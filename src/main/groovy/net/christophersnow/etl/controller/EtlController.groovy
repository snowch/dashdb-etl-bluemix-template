package net.christophersnow.etl.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.scheduling.annotation.Scheduled

import groovy.sql.Sql
import java.text.SimpleDateFormat

@Controller
public class EtlController {

    def env = System.getenv()

    def sql = Sql.newInstance(
                   env['db_url'], 
                   env['db_username'], 
                   env['db_password'], 
                   'com.ibm.db2.jcc.DB2Driver'
                )

    // we need the username other places in the script
    def DB_USERNAME = env['db_username']

    // the table that was created by the SDP process
    def SDP_TABLENAME = env['source_tablename']

    @RequestMapping('/')
    public String greeting(
            Model model
            ) {

        // the next few lines just return a web page with some dummy data

        def cmd = """
            SELECT COUNT(*) AS "count" FROM "${SDP_TABLENAME}"
        """.toString()

        def row_count = sql.rows(cmd)[0]['count']
        
        model.addAttribute('num_rows', row_count)

        return 'greeting'
    }

    @Scheduled(cron = "0 30 2 * * *")
    def void schedule() {
        /*
            Put ETL code in here that needs to run periodically, for example:
            
            def date = selectNextDateToASummarise() // 
        
            def cmd = """
            INSERT INTO "SUMMARY_TABLE" 
            ( 
                SELECT
                    SDP."DATE",
                    MAX(DECODE(SDP."Type", 'temperature',          SDP."Value")) AS temperature,
                    MAX(DECODE(SDP."Type", 'airhumidity',          SDP."Value")) AS airhumidity,
                    MAX(DECODE(SDP."Type", 'windspeed',            SDP."Value")) AS windspeed
                FROM
                    "SDP_POPULATED_TABLE" SDP
               WHERE
                    SDP."DATE" = '${date}' 
               GROUP BY
                    SDP."DATE"
            )
        """
        sql.execute cmd.toString()
        
        cmd = """
              DELETE FROM "SDP_POPULATED_TABLE" SDP WHERE SDP."DATE" = '${date}' 
              """
        sql.execute cmd.toString()
        */
    }

}
