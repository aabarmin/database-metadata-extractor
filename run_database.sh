#!/bin/bash

docker run \
  -p 51521:1521 \
  -p 9080:8080 \
  -p 55500:5500 \
  -e ORACLE_ALLOW_REMOTE=true \
  oracleinanutshell/oracle-xe-11g