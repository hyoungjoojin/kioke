#!/usr/bin/env bash

cd "$PROJECT_DIRECTORY"

info() {
  local MESSAGE="$1"

  echo "[INFO] $MESSAGE" >&1
}

warn() {
  local MESSAGE="$1"

  echo "[WARNING] $MESSAGE" >&1
}

error() {
  local MESSAGE="$1"

  echo "[ERROR] $MESSAGE" >&2
}

SERVICES=(service-discovery api-gateway auth-service user-service journal-service notification-service)

if [ "$STAGE" = "dev" ]; then
  docker-compose -f "docker-compose.dev.yml" up -d

  OUTPUT_DIRECTORY=$(mktemp -d)

  run_service() {
    SERVICE=$1

    cd "$PROJECT_DIRECTORY"
    ./mvnw \
      -f "$SERVICE/pom.xml" \
      spring-boot:run -Dspring-boot.run.profiles=dev >"$OUTPUT_DIRECTORY/$SERVICE" &
  }

  kill_service() {
    SERVICE=$1

    case $SERVICE in
    service-discovery)
      kill -9 $(sudo lsof -t -i :$SERVICE_DISCOVERY_PORT)
      ;;
    api-gateway)
      kill -9 $(sudo lsof -t -i :$API_GATEWAY_PORT)
      ;;
    auth-service)
      kill -9 $(sudo lsof -t -i :$AUTH_SERVICE_PORT)
      ;;
    user-service)
      kill -9 $(sudo lsof -t -i :$USER_SERVICE_PORT)
      ;;
    journal-service)
      kill -9 $(sudo lsof -t -i :$JOURNAL_SERVICE_PORT)
      ;;
    notification-service)
      kill -9 $(sudo lsof -t -i :$NOTIFICATION_SERVICE_PORT)
      ;;
    all)
      for service in "${SERVICES[@]}"; do
        kill_service "$service"
      done
      ;;
    esac
  }

  is_valid_service() {
    SERVICE=$1

    if [[ ! "${SERVICES[@]}" =~ "$SERVICE" ]]; then
      echo "Service $SERVICE is invalid. Choose one of ${SERVICES[@]}."
      return 0
    else
      return 1
    fi
  }

  for service in "${SERVICES[@]}"; do
    run_service "$service"
  done

  while true; do
    echo "Available actions:"
    echo "  q: Quit all services."
    echo "  v [SERVICE_NAME]: Show logs for the given service."
    echo "  r [SERVICE_NAME]: Restart the given service."
    read ACTION SERVICE_NAME

    if [ "$ACTION" = "q" ]; then
      kill_service all
      exit -1
    elif [ "$ACTION" = "v" ]; then
      is_valid_service $SERVICE_NAME
      if [[ $? -eq 1 ]]; then
        cat "$OUTPUT_DIRECTORY/$SERVICE_NAME"
        tail -f "$OUTPUT_DIRECTORY/$SERVICE_NAME" &
        LOG_PID=$!

        read TEMP
        kill -9 $LOG_PID
      fi

    elif [ "$ACTION" = "r" ]; then
      is_valid_service $SERVICE_NAME
      if [[ $? -eq 1 ]]; then
        kill_service $SERVICE_NAME
        rm "$OUTPUT_DIRECTORY/$SERVICE_NAME"
        run_service $SERVICE_NAME
      fi
    fi
  done
fi
