#!/usr/bin/env bash

function find_idle_profile()
{
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" ec2-52-78-73-19.ap-northeast-2.compute.amazonaws.com/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s ec2-52-78-73-19.ap-northeast-2.compute.amazonaws.com/profile)
    fi

    if [ "${CURRENT_PROFILE}" == "real1" ]
    then
        IDLE_PROFILE=real2
    else
        IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}

function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}