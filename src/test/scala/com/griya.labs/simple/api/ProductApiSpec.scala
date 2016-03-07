package com.griya.labs.simple.api

import dispatch.classic./
import org.specs2.Specification
import net.liftweb._
import util._
import Helpers._

/**
  * Created by mac on 3/5/16.
  */
class ProductApiSpec extends Specification {
  def is = {
    sequential ^
      "Product api should" ^
      p ^
        "get product list" ! trees.getProductList ^
      end
  }

  object trees {
    // @todo finish the product api test
    def getProductList = {

    }
  }

}
