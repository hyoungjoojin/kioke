#!/usr/bin/env bash

set -e
set -u
set -o pipefail

PRIVATE_KEY_FILE="private.pem"
PRIVATE_KEY_FILE_LOCATION="$PROJECT_DIRECTORY/auth-service/src/main/resources/certs"

PUBLIC_KEY_FILE="public.pem"
PUBLIC_KEY_FILE_LOCATIONS=(
  "$PROJECT_DIRECTORY/journal-service/src/main/resources/certs"
  "$PROJECT_DIRECTORY/user-service/src/main/resources/certs"
)

echo "Generating a new key pair with the ES256 algorithm."

echo "Writing private key to $PRIVATE_KEY_FILE_LOCATION."
mkdir -p "$PRIVATE_KEY_FILE_LOCATION"
TEMPORARY_FILE=$(mktemp)
openssl ecparam -name prime256v1 -genkey -noout -out "$TEMPORARY_FILE"
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in "$TEMPORARY_FILE" -out "$PRIVATE_KEY_FILE_LOCATION/$PRIVATE_KEY_FILE"
rm "$TEMPORARY_FILE"

TEMPORARY_FILE=$(mktemp)
openssl ec -in "$PRIVATE_KEY_FILE_LOCATION/$PRIVATE_KEY_FILE" -pubout >"$TEMPORARY_FILE"
for PUBLIC_KEY_FILE_LOCATION in "${PUBLIC_KEY_FILE_LOCATIONS[@]}"; do
  echo "$PUBLIC_KEY_FILE_LOCATION"
  echo "Writing public key to $PUBLIC_KEY_FILE_LOCATION."
  mkdir -p "$PUBLIC_KEY_FILE_LOCATION"

  cp "$TEMPORARY_FILE" "$PUBLIC_KEY_FILE_LOCATION/$PUBLIC_KEY_FILE"
done

rm "$TEMPORARY_FILE"

echo "Done."
