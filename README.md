# appointmed

To run it locally.

1. Add 127.0.0.1 keycloak to /etc/hosts
2. Run docker compose up -d
3. Set vault secrets in ./initVault.sh
4. Run ./initVault.sh
5. Wait that all containers boot up and connect to http://localhost