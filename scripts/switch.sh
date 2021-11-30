#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy(){
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할포트 : $IDLE_PORT"
  echo "> PORT전환"
  echo "set \$service_url ec2-52-78-73-19.ap-northeast-2.compute.amazonaws.com:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
  echo "> nginx 리로드"
  sudo service nginx reload
}