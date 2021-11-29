#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT에서 구동중인 app PID 체크"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PORT} ]
then
  echo "> 구동중인게 없음"
else
  echo "> kill -15 $IDLE_PID 실행"
  kill -15 ${IDLE_PID}
  sleep 5
fi
