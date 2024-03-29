#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s ec2-52-78-73-19.ap-northeast-2.compute.amazonaws.com:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${REPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1
    echo "> HEALTH체크 성공"
    switch_proxy
    break
  else
    echo "> HEALTH 체크의 응답을 알수없거나 실행상태가 아님"
    echo "> HEALTH CHECK: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> HEALTH체크 실패"
    echo "> nginx에 연결하지않고 배포종료"
    exit 1
  fi

  echo "> HEALTH체크 연결실패, 재시도"
  sleep 10
done