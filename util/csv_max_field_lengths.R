#!/usr/bin/env Rscript

# R script for calculating length of each field in a csv file
# useful when declaring CHAR field lengths in SQL DDL statements
# run with `Rscript csv_max_field_lengths.R`

print('Calculating length of date_dimension fields')
df <- read.csv('./date_dimension.csv')
df.fields <- sapply(df, function(x) max(nchar(as.character(x))))
print(df.fields)
