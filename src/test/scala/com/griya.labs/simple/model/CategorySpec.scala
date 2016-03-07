package com.griya.labs.simple.model

import com.mongodb.casbah.commons.MongoDBObject
import org.specs2.Specification

/**
  * Created by mac on 3/5/16.
  */
class CategorySpec extends Specification {
  def is = {
    sequential ^
      "Category model should" ^
      p ^
      "create category" ! trees.createdCategory ^
    end
  }

  object trees {
    def createdCategory = {

      // create parrent
      val entry = CategoryDAO(name = "olahraga",
        displayName = "Olahraga",
        parent = ""
      )

      val id = CategoryDAO.insert(entry)

      val getResult = CategoryDAO.findOneById(id.get)

      // create child category
      val entryChild = CategoryDAO(name = "tenis_meja",
        displayName = "Tenis Meja",
        parent = entry.name
      )

      val idChild = CategoryDAO.insert(entryChild)

      val getResultChild = CategoryDAO.findOneById(idChild.get)

      val childs = CategoryDAO.find(ref = MongoDBObject("parent" -> entry.name))

      (getResult.isDefined must beTrue) and
        (getResultChild.isDefined must beTrue) and
        (childs.length mustEqual(1))

    }
  }
}
