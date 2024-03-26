#!/bin/bash

# The variables are mapped 1-1 with the environment variables defined in the .env file.

export VAULT_ADDR='http://0.0.0.0:8200'

export VAULT_TOKEN='<VAULT_DEV_ROOT_TOKEN_ID>' 
vault kv put secret/appointmed mongodb.username='<MONGO_APP_USERNAME>' mongodb.password='<MONGO_APP_PASSWORD>' \
							   google.oauth2ClientSecret='<Google Oauth2 Client Secret>' google.mapsApiKey='<Google Api Key>' \
							   keycloak.username='KEYCLOAK_ADMIN' keycloak.password='KEYCLOAK_PASSWORD' \
							   smtp.password='Smtp Password'

# Google Api Key with services enabled:
    # Maps JavaScript API
    # Maps Static API
    # Maps Embed API
    # Places API
    # Geocoding API					

