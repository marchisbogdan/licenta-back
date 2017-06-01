#!/bin/sh

echo "Starting Subscriber Api ..."

java -jar subscriber-api-1.0.jar server config.yml
