FROM node:20.11.1-alpine3.18 as builder

ARG VITE_API_URL
ARG VITE_KEYCLOAK_URL
ARG VITE_GOOGLE_API_KEY 
ARG VITE_KEYCLOAK_REALM
ARG VITE_KEYCLOAK_CLIENT_ID

ENV VITE_API_URL=${VITE_API_URL}
ENV VITE_KEYCLOAK_URL=${VITE_KEYCLOAK_URL}
ENV VITE_GOOGLE_API_KEY=${VITE_GOOGLE_API_KEY}
ENV VITE_KEYCLOAK_REALM=${VITE_KEYCLOAK_REALM}
ENV VITE_KEYCLOAK_CLIENT_ID=${VITE_KEYCLOAK_CLIENT_ID}

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:alpine3.18

COPY --from=builder /usr/src/app/dist /usr/share/nginx/appointmed

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80 

CMD nginx -g "daemon off;"


