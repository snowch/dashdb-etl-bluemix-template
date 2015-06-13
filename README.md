ETL Service
===============

The app contains one controller that interacts with DashDB that queries dashDB.  There is also a method where you can put code to run on a cron schedule.

### Development

Edit the class [EtlController](./src/main/groovy/net/christophersnow/etl/controller/EtlController.groovy).  You can see there are currently two methods:

- greeting(): this method is initiated when the application is running and you access the root url (e.g. http://localhost:8080/).  The example code in this method connects to dashDB and returns a count of the rows in the table populated by the SDP.  The row count is stored in the variable `row_count`.  At the end of the method a view template called 'greeting' is rendered.  The view is passed the `row_count` value.  The view code is [here](./src/main/resources/templates/greeting.html)
- schedule(): this method will get fired by the cron timer.  Put code in here that you want to be run every time the timer fires.

Reference:
- The [Groovy SQL documentation](http://docs.groovy-lang.org/latest/html/api/groovy/sql/Sql.html) provides many more sql code examples.
- The [Thymeleaf documentation](http://www.thymeleaf.org/documentation.html) provides more information on creating view templates.

### Setup

Copy your db2 jdbc jar `db2jcc4.jar` to the `./libs` folder

### Building

Execute the instruction below to build:

```
./gradlew build
```

### Running

Change variables in env_vars.sh to suit your environment and set them using:

```
source env_vars.sh
```

#### Running locally

To run locally: 
```
./gradlew run
```
or:
```
./gradlew build
java -jar build/libs/ETL_Service-0.1.0.jar
``` 

Hit the url http://localhost:8080 to execute the controller method

#### Running on bluemix

To push to bluemix:
```
cf logic -u cf_username -p cf_password

cf set-env $cf_app_name db_url $db_url 
cf set-env $cf_app_name db_username $db_username
cf set-env $cf_app_name db_password $db_password
cf push    $cf_app_name -p build/libs/ETL_Service-0.1.0.jar
```

### Utility scripts

There are some utility scripts in the `./util` folder. 

```
cd util

# example script that loads a date dimension table from a csv file
# (needs groovy installed and the dashDB env vars set)

source ../env_vars.sh
./load_date_dimension.groovy

# R script to dump the length (num chars) of each CSV field
# (needs R installed)

./csv_max_field_lengths.R
```

