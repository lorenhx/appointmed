db = db.getSiblingDB('appointmed')

db.createUser({
    user: process.env.MONGO_APPOINTMED_USERNAME,
    pwd: process.env.MONGO_APPOINTMED_PASSWORD,
    roles: [
      {
        role: 'dbOwner',
        db: process.env.MONGO_APPOINTMED_DATABASE,
    },
  ],
});