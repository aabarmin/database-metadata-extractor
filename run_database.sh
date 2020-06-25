#!/bin/bash

docker run \
  -d \
  -p 51521:1521 \
  -p 9080:8080 \
  -p 55500:5500 \
  -e ORACLE_ALLOW_REMOTE=true \
  --cpus=0.5 \
  --memory=1G \
  oracleinanutshell/oracle-xe-11g