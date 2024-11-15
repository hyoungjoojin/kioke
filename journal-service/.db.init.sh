#!/usr/bin/env bash

echo "Initializing the MongoDB database for journal service."

if [ ! -n $JOURNAL_SERVICE_DATABASE_USER_USERNAME ]; then
    echo "The environment variable JOURNAL_SERVICE_DATABASE_USER_USERNAME could not be found."
    exit -1
fi

if [ ! -n $JOURNAL_SERVICE_DATABASE_USER_PASSWORD ]; then
    echo "The environment variable JOURNAL_SERVICE_DATABASE_USER_PASSWORD could not be found."
    exit -1
fi

mongosh -u $JOURNAL_SERVICE_DATABASE_ADMIN_USERNAME -p $JOURNAL_SERVICE_DATABASE_ADMIN_PASSWORD << EOF
use journal-service-database

db.createCollection("journals")
db.createCollection("pages")

db.createUser({ \
  "user": "$JOURNAL_SERVICE_DATABASE_USER_USERNAME", \
  "pwd": "$JOURNAL_SERVICE_DATABASE_USER_PASSWORD", \
  "roles": [ \
    { \
      "role": "readWrite", \
      "db": "$JOURNAL_SERVICE_DATABASE_NAME", \
    }, \
  ], \
})

EOF

