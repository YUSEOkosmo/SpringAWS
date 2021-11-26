#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=SpringAWS
echo ">빌드파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/
echo ">구동중인 어플 PID확인"
CURRENT_PID=$(pgrep -fl SpringAWS | grep java | awk '{print $1}')
echo "> PID: $CURRENT_PID"
echo "> 이름: $PROJECT_NAME"
if [ -z "$CURRENT_PID" ]; then
  echo "> 구동중인게없음"
else
  echo "> KILL -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 어플 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR NAME: $JAR_NAME"
echo "> $JAR_NAME 에 권한추가"
chmod +x $JAR_NAME
echo "> $JAR_NAME 실행"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &