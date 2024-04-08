# appointmed

To run it locally.

1. Add "127.0.0.1 appointmed.site", "127.0.0.1 api.appointmed.site", "127.0.0.1 auth.appointmed.site" to /etc/hosts or C:\Windows\System32\drivers\etc\hosts
2. Add .env following configuration in env.example.env 
3. Run docker compose build && docker compose up -d
4. Set vault secrets in ./initVault.sh
5. Wait that vault service starts and run ./initVault.sh or ./initVault.ps1 (Require you to have vault installed, for now..)
6. When services are up, connect to http://appointmed.site