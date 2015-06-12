ETL Service
===============

The app contains one controller that interacts with DashDB that queries dashDB.  There is also a method where you can put code to run on a cron schedule.

### Setup

Copy your db2 jdbc jar `db2jcc4.jar` to the `./libs` folder

### Building

Execute the instruction below to execute:

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

