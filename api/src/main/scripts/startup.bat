@ECHO OFF

title Wahoo API - started at %TIME%

echo "Starting Wahoo Api ..."

java -jar api-1.0.jar server config.yml

:End
