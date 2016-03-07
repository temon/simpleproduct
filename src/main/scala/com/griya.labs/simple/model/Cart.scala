package com.griya.labs.simple.model


import com.mongodb.casbah.Imports.ObjectId

import com.mongodb.casbah.Imports._
import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._

/**
  * Created by mac on 3/5/16.
  */
case class CartDAO (_id: ObjectId = new ObjectId,
                 name: String,
                 color: String,
                 producer: String) {
}

object CartDAO extends SalatDAO[CartDAO, ObjectId] (collection= MongoConnection()("simple")("cart"))
