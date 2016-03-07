package com.griya.labs.simple.api

import net.liftweb.json._
import org.specs2.Specification
import org.specs2.specification.Scope
import net.liftweb._
import util._
import Helpers._

/**
  * Created by mac on 3/6/16.
  */
class CategoryApiSpec extends Specification with RestapiTestHelper {
  def is = {
    sequential ^
      "Category api should" ^
      p ^
              "get product list" ! trees.getProductList ^
      end
  }

  object trees {
    def getProductList = {
      // @todo finish the category api test
      get("rest/api/product") { resp =>
        resp.getResponseBody mustEqual(true)
      }
    }
  }
}
