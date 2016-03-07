package com.griya.labs.simple.api

import com.griya.labs.simple.model.CategoryDAO
import com.mongodb.WriteConcern
import com.mongodb.casbah.commons.MongoDBObject
import net.liftweb.http.S
import net.liftweb.http.rest.RestHelper
import net.liftweb.json._
import JsonDSL._
import org.bson.types.ObjectId

/**
  * Created by mac on 3/5/16.
  */
object CategoryRestApi extends RestHelper {

  serve {
    case "v1" :: "rest" :: "api" :: "category" :: Nil JsonPost ((jsonData, req)) => {
      try {
        case class CategoryData (name:String, displayName: String, parent: String="")

        val data = jsonData.extract[CategoryData]

        val id = CategoryDAO.create(data.name, data.displayName, data.parent)

        UtilsApi.success(id.toString)
      } catch {
        case e:Exception =>
          UtilsApi.failedT(e.toString)
      }
    }
    case "v1" :: "rest" :: "api" :: "category" :: Nil Get _ => {
      try {

        val limit = S.param("limit").getOrElse{
          throw new Exception("param limit is required")
        }.toInt

        val offset = S.param("offset").getOrElse{
          throw new Exception("param limit is required")
        }.toInt

        val _search = S.param("query").getOrElse("")

        val _parent = S.param("parent").getOrElse("")

        val getResult = CategoryDAO.getListAll(_search, _parent, offset, limit)

        ("count" -> getResult._2) ~
          ("entries" -> getResult._1.map{ x =>
            ("_id" -> x._id.toString) ~
              ("name" -> x.displayName) ~
              ("parent" -> x.parent):JValue
          }):JValue
      } catch {
        case e:Exception =>
          ("count" -> 0) ~
            ("entries" -> List()):JValue
      }
    }
    case "v1" :: "rest" :: "api" :: "category" :: "detail" :: Nil Get _ => {
      try {
        val id = S.param("id").getOrElse{
          throw new Exception("param id is required")
        }

        val result = CategoryDAO.findOneById(new ObjectId(id))
        result.map{ x =>

          ("id" -> x._id.toString) ~
            ("name" -> x.name) ~
            ("parent" -> x.parent):JValue

        }.getOrElse(throw new Exception)
      } catch {
        case e:Exception =>
          UtilsApi.failedT("Category not found")
      }
    }
    case "v1" :: "rest" :: "api" :: "category" :: Nil JsonPut ((jsonData, req)) => {

      try {
        case class CategoryData (_id:String, name:String, displayName: String, parent: String="")

        val data = jsonData.extract[CategoryData]

        val entry = CategoryDAO(new ObjectId(data._id), data.name, data.displayName, data.parent)

        val result = CategoryDAO.update(MongoDBObject("_id" -> new ObjectId(data._id)), entry, false, false, new WriteConcern)

        UtilsApi.success(result.toString)
      } catch {
        case e:Exception =>
          UtilsApi.failedT(e.toString)
      }

    }
    case "v1" :: "rest" :: "api" :: "category" :: Nil Delete _ => {
      try {

        val id = S.param("id").getOrElse{
          throw new Exception("param id is required")
        }

        val status = CategoryDAO.removeById(new ObjectId(id))

        UtilsApi.successN
      } catch {
        case e:Exception =>
          UtilsApi.failedT(e.toString)
      }
    }
  }
}
