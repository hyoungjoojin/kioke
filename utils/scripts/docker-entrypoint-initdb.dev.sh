#!/bin/bash

set -e
set -u

create_user_and_database() {
	local DATABASE=$1
	echo "  Creating user and database '$DATABASE'"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
		        CREATE USER $DATABASE;
		        CREATE DATABASE $DATABASE;
		        GRANT ALL PRIVILEGES ON DATABASE $DATABASE TO $DATABASE;
	EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for DB in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
		create_user_and_database $DB
	done

	echo "Done."
fi
