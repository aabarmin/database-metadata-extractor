#!/bin/bash

docker run -d \
   -v confluence_data:/var/atlassian/application-data/confluence \
   -p 8090:8090 \
   -p 8091:8091 \
   atlassian/confluence-server