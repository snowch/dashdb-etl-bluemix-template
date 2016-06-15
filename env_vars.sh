#!/bin/bash

# dashDB environment variables:

export db_username=''
export db_password=''
export db_hostname=''

# non-ssl connection
export db_url="jdbc:db2://${db_hostname}:50000/BLUDB"

# ssl connection
#export db_url="jdbc:db2://${db_hostname}:50001/BLUDB:sslConnection=true;"

# the name of the table in dashDB createdb by the SDP
export source_tablename=''

# Cloud foundry environment variables:

export cf_username=''
export cf_password=''
export cf_app_name=''

