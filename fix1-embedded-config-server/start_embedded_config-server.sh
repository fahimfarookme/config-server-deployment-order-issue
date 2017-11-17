#!/bin/bash

CONFIG_REPO_URI=https://github.com/fahimfarookme/config-server-deployment-order-issue/
CONFIG_REPO_PATH=config-repo
DEBUG="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=6005,suspend=n"

function wait_till_started {
	until [ "`curl --silent --show-error --connect-timeout 1 http://localhost:$1/health | grep 'UP'`" != "" ];
	do
	  echo "sleeping for 10 seconds..."
	  sleep 10
	done
}

printf "\n\nPackaging...\n\n"
mvn clean package

printf "\n\nStarting the microservice...\n\n"
java $DEBUG -Dconfig.repo.uri=$CONFIG_REPO_URI -Dconfig.repo.path=$CONFIG_REPO_PATH -Dport=14001 -jar microservice/target/microservice-0.0.1-SNAPSHOT.jar &
wait_till_started   14001

printf "\n\nChecking whether the new date in config-repo is reflected in microservice...\n\n"
curl -s http://localhost:14001/config-prop/date

