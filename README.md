# appointmed

To run it locally.

1. Add 127.0.0.1 keycloak to /etc/hosts or C:\Windows\System32\drivers\etc\hosts
2. Run docker compose build && docker compose up -d
3. Set vault secrets in ./initVault.sh
4. Run ./initVault.sh or ./initVault.ps1 (Require you to have vault installed, for now..)
5. Wait that all containers boot up and connect to http://localhost