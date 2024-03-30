$env:VAULT_ADDR = 'http://0.0.0.0:8200'
$env:VAULT_TOKEN = '<VAULT_DEV_ROOT_TOKEN_ID>' 
vault kv put secret/appointmed mongodb.username='<MONGO_APP_USERNAME>' mongodb.password='<MONGO_APP_PASSWORD>' \
							   google.oauth2ClientSecret='<Google Oauth2 Client Secret>' google.mapsApiKey='<Google Api Key>' \
							   keycloak.username='KEYCLOAK_ADMIN' keycloak.password='KEYCLOAK_PASSWORD' \
							   smtp.password='Smtp Password'
