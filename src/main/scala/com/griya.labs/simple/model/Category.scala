package com.griya.labs.simple.model

import com.mongodb.casbah.Imports.ObjectId

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao.SalatDAO
import com.novus.salat.global._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._
import JsonDSL._

/**
  * Created by mac on 3/5/16.
  */
case class CategoryDAO (_id: ObjectId = new ObjectId,
                     name: String,
                     displayName: String,
                     parent: String) {
}

object CategoryDAO extends SalatDAO[CategoryDAO, ObjectId] (collection= MongoConnection()("simple")("category")) {

  CategoryDAO.collection.ensureIndex(DBObject("name" -> 1), "category_name", true)

  def create(name: String,
             displayName: String,
             parent: String
            ) = {
    val entry = CategoryDAO(name = name,
      displayName = displayName,
      parent = parent
    )

    val id = CategoryDAO.insert(entry)
    id
  }

  def getListAll(keyword: String, parent:String, offset:Int=0, limit:Int=10) = {
    var filter:List[(String, String)] = List()
    if (!parent.isEmpty) {
      var filter = List(("parent" -> parent))
    }

    if (!keyword.isEmpty) {
      filter = filter ++ List(("displayName" -> keyword))
    }

    val categoryCount = CategoryDAO.count(MongoDBObject(filter))

    (CategoryDAO.find(ref = MongoDBObject(filter))
        .skip(offset)
        .limit(limit)
        .toList, categoryCount)
  }

}