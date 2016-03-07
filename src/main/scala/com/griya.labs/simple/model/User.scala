package com.griya.labs.simple.model

import com.mongodb.casbah.Imports.ObjectId

import com.mongodb.casbah.Imports._
import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._

/**
  * Created by mac on 3/5/16.
  */
case class User (_id: ObjectId = new ObjectId,
                 fullName: String,
                 userName: String,
                 password: String,
                 age: Int) {
}

class UserDAO extends SalatDAO[User, ObjectId] (collection= MongoConnection()("simple")("user"))
