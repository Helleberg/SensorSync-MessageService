#!/bin/bash

set -e

host="$1"
port="$2"
max_retries=10
retries=0

echo "Checking if device-service is up..."

until curl -sSf "http://${host}:${port}/actuator/health" &> /dev/null; do
    if [ $retries -eq $max_retries ]; then
        echo "Reached maximum retries. Exiting..."
        exit 1
    fi

    echo "Device-service is not yet available. Retrying... (Attempt $((retries + 1)) of $max_retries)"
    sleep 2
    retries=$((retries + 1))
done

echo "Device-service is up! Starting message-service..."

# Start your message-service here
# For example, you might use exec to replace the current shell process with the message-service process
exec "$@"

