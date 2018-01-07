#!/usr/bin/env bash
./wait-for-it.sh -t 60 ngapp:3000
psql -h postgres -U postgres -d postgres -p 5432 -f db_insert_date.sql
