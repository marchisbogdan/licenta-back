DB_OPTS="-Ddb.host=$DB_HOST -Ddb.port=$DB_PORT -Ddb.name=$DB_NAME -Ddb.username=$DB_USERNAME -Ddb.password=$DB_PASSWORD"

PORT_OPS="-Ddw.server.applicationConnectors[0].port=$PORT -Ddw.server.adminConnectors[0].port=$(($PORT+1))"

echo "java $PORT_OPS $DB_OPTS -jar api-*.jar server config.yml"

cd api/

java $PORT_OPS $DB_OPTS -jar api-*.jar server config.yml

cd ..
