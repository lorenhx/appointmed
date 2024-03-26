db = db.getSiblingDB('appointmed')

db.createUser({
    user: process.env.MONGO_APP_USERNAME,
    pwd: process.env.MONGO_APP_PASSWORD,
    roles: [
      {
        role: 'dbOwner',
      db: 'appointmed',
    },
  ],
});