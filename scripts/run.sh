#!/usr/bin/env bash

set -e
set -u
set -o pipefail

ALL="all"
USER_SERVICE="user"
JOURNAL_SERVICE="journal"

TARGET=${1:-$ALL}

AVAILABLE_TARGETS=("$USER_SERVICE" "$JOURNAL_SERVICE" "$ALL")

if [[ ! "${AVAILABLE_TARGETS[*]}" =~ "$TARGET" ]]; then
    echo "Target $TARGET is invalid. Target must be one of (${AVAILABLE_TARGETS[*]})"
    exit -1
fi

if [ "$TARGET" = $ALL ]; then
    docker-compose down -v
    docker-compose up --build -d
    exit 0
fi

if [ "$TARGET" = $USER_SERVICE ]; then
    docker-compose down -v user-service user-service-database
    docker-compose up --build -d user-service user-service-database
    exit 0
fi

if [ "$TARGET" = $JOURNAL_SERVICE ]; then
    docker-compose down -v journal-service journal-service-database
    docker-compose up --build -d journal-service journal-service-database
    exit 0
fi

