package com.griya.labs.simple.model

import com.mongodb.casbah.Imports.ObjectId

import com.mongodb.casbah.Imports._
import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._
import org.joda.time.DateTime

/**
  * Created by mac on 3/5/16.
  */
case class DiscountDAO (_id: ObjectId = new ObjectId,
                     code: String,
                     discount_type: String, // percent or price
                     value: Int,
                     start_date: DateTime,
                     end_date: DateTime){
}

object DiscountDAO extends SalatDAO[DiscountDAO, ObjectId] (collection= MongoConnection()("simple")("discount"))
