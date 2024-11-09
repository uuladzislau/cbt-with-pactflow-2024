#!/bin/bash

# Import variables from the .env file. The following variables are expected:
# - PACT_BROKER_BASE_URL
# - PACT_BROKER_TOKEN
source .env

docker run --rm \
  --env PACT_BROKER_BASE_URL \
  --env PACT_BROKER_TOKEN \
  pactfoundation/pact-cli:1.3.0.10 \
  pact-broker record-deployment \
  --pacticipant $1 \
  --version $2\
  --environment production
