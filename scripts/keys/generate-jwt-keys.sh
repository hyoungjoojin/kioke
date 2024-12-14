#!/usr/bin/env bash

set -e
set -u
set -o pipefail

PRIVATE_KEY_FILE="private.pem"
PRIVATE_KEY_FILE_LOCATION="$ROOT_DIRECTORY/auth-service/src/main/resources/certs/$PRIVATE_KEY_FILE"

PUBLIC_KEY_FILE="public.pem"
PUBLIC_KEY_FILE_LOCATION="$ROOT_DIRECTORY/api-gateway/src/main/resources/certs/$PUBLIC_KEY_FILE"

check_file() {
    FILE=$1

    if [ -f "$FILE" ]; then
        INPUT="n"
        read -p "File $FILE already exists. Overwrite ? [y/n] " INPUT

        if [ "$INPUT" = "y" ]; then
            return 0
        else
            exit 0
        fi
    fi
}

check_file "$PRIVATE_KEY_FILE_LOCATION"
check_file "$PUBLIC_KEY_FILE_LOCATION"

echo "Generating a new key pair with the ES256 algorithm."

echo "Writing private key to $PRIVATE_KEY_FILE_LOCATION."
TEMPORARY_FILE=$(mktemp)
openssl ecparam -name prime256v1 -genkey -noout -out "$TEMPORARY_FILE"
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in "$TEMPORARY_FILE" -out "$PRIVATE_KEY_FILE_LOCATION"
rm "$TEMPORARY_FILE"

echo "Writing public key to $PUBLIC_KEY_FILE_LOCATION."
openssl ec -in "$PRIVATE_KEY_FILE_LOCATION" -pubout > "$PUBLIC_KEY_FILE_LOCATION"

echo "Done."
