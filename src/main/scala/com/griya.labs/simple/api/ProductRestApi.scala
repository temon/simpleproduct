package com.griya.labs.simple.api

import com.griya.labs.simple.model.ProductDAO
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
object ProductRestApi extends RestHelper {
  serve {
    case "v1" :: "rest" :: "api" :: "product" :: Nil JsonPost ((jsonData, req)) => {
      try {
        case class ProductData (name:String, category:String, color:String, price:Int, currency:String)

        val data = jsonData.extract[ProductData]

        val id = ProductDAO.create(data.name, new ObjectId(data.category), data.color, data.price, data.currency)

        UtilsApi.success(id.get.toString)
      } catch {
        case e:Exception =>
          UtilsApi.failedT(e.toString)
      }
    }
    case "v1" :: "rest" :: "api" :: "product" :: Nil Get _ => {
      try {

        val limit = S.param("limit").getOrElse{
          throw new Exception("param limit is required")
        }.toInt

        val offset = S.param("offset").getOrElse{
          throw new Exception("param limit is required")
        }.toInt

        val _search = S.param("query").getOrElse("")

        val getResult = ProductDAO.getListAll(_search, offset, limit)

        ("count" -> getResult._2) ~
          ("entries" -> getResult._1.map{ x =>
            ("_id" -> x._id.toString) ~
              ("name" -> x.name) ~
              ("category" -> x.category.toString) ~
              ("color" -> x.color) ~
              ("price" -> x.price) ~
              ("currency" -> x.currency):JValue
          }):JValue
      } catch {
        case e:Exception =>
          ("count" -> 0) ~
            ("entries" -> List()):JValue
      }
    }
    case "v1" :: "rest" :: "api" :: "product" :: "detail" :: Nil Get _ => {
      try {
        val id = S.param("id").getOrElse{
          throw new Exception("param id is required")
        }

        val result = ProductDAO.findOneById(new ObjectId(id))
        result.map{ x =>

          ("id" -> x._id.toString) ~
            ("name" -> x.name) ~
            ("category" -> x.category.toString) ~
            ("color" -> x.color) ~
            ("price" -> x.price) ~
            ("currency" -> x.currency):JValue

        }.getOrElse(throw new Exception)
      } catch {
        case e:Exception =>
          UtilsApi.failedT("Category not found")
      }
    }
    case "v1" :: "rest" :: "api" :: "product" :: Nil JsonPut ((jsonData, req)) => {
      try {
        case class ProductData (_id:String, name:String, category: String, color: String="", price: Int=0, currency: String="")

        val data = jsonData.extract[ProductData]

        val entry = ProductDAO(new ObjectId(data._id), data.name, (new ObjectId(data.category)), data.color, data.price, data.currency)

        val result = ProductDAO.update(MongoDBObject("_id" -> new ObjectId(data._id)), entry, false, false, new WriteConcern)

        UtilsApi.success(result.toString)
      } catch {
        case e:Exception =>
          UtilsApi.failedT(e.toString)
      }
    }
    case "v1" :: "rest" :: "api" :: "product" :: Nil Delete _ => {
      try {

        val id = S.param("id").getOrElse{
          throw new Exception("param id is required")
        }

        val status = ProductDAO.removeById(new ObjectId(id))

        UtilsApi.successN
      } catch {
        case e:Exception =>
          UtilsApi.failedT(e.toString)
      }
    }
  }
}
