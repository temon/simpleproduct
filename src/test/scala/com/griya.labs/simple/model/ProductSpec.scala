package com.griya.labs.simple.model

import com.mongodb.casbah.commons.MongoDBObject
import org.specs2.Specification

/**
  * Created by mac on 3/5/16.
  */
class ProductSpec extends Specification {
  def is = {
    sequential ^
      "Product model should" ^
      p ^
      "create product" ! trees.createProduct ^
      end
  }

  object trees {
    def createProduct = {

      val childs = CategoryDAO.find(ref = MongoDBObject("parent" -> "olahraga"))
          .limit(1)
          .toList
          .head

      // Data for test:
      val entry = ProductDAO(name = "Chocolate santa",
        category = childs._id,
        color = "cacao, mushrooms, milk",
        price = 20000,
        currency = "IDR")

      // Create:
      val id = ProductDAO.insert(entry)

      val getResult = ProductDAO.findOneById(id.get)

      (getResult.isDefined must beTrue)

    }
  }

}
