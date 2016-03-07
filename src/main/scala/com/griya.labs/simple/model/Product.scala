package com.griya.labs.simple.model

import com.mongodb.casbah.Imports.ObjectId

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._

/**
  * Created by mac on 3/5/16.
  */

case class ProductDAO (_id: ObjectId = new ObjectId,
                    name: String,
                    category: ObjectId,
                    color: String,
                    price: Int,
                    currency: String) {
}

object ProductDAO extends SalatDAO[ProductDAO, ObjectId] (collection= MongoConnection()("simple")("product")) {

  def create (name: String,
              category: ObjectId,
              color: String,
              price: Int,
              currency: String) = {
      val entry = ProductDAO(name = name,
          category=category,
          color=color,
          price=price,
          currency=currency
      )

      val id = ProductDAO.insert(entry)
      id
  }

  def getListAll(keyword: String, offset:Int=0, limit:Int=10) = {
    var filter:List[(String, String)] = List()

    if (!keyword.isEmpty) {
      filter = filter ++ List(("name" -> keyword))
    }

    val productCount = ProductDAO.count(MongoDBObject(filter))

    (ProductDAO.find(ref = MongoDBObject(filter))
      .skip(offset)
      .limit(limit)
      .toList, productCount)
  }

}