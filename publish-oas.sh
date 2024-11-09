#!/bin/bash

# Import variables from the .env file. The following variables are expected:
# - PACT_BROKER_BASE_URL
# - PACT_BROKER_TOKEN
source .env

docker run --rm \
  --volume /${PWD}:/${PWD} \
  --workdir ${PWD} \
      --env PACT_BROKER_BASE_URL \
      --env PACT_BROKER_TOKEN \
      pactfoundation/pact-cli:1.3.0.10 \
      pactflow publish-provider-contract provider/api.yaml\
      --provider "products-service" \
      --provider-app-version $(git rev-parse --short HEAD) \
      --branch $(git rev-parse --abbrev-ref HEAD) \
      --content-type application/yaml \
