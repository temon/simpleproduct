package com.griya.labs.simple.api

import net.liftweb.json.JsonDSL._
import net.liftweb.json._

/**
  * Created by mac on 3/5/16.
  */
object UtilsApi {
  def success(data:JValue) = ("result", "success") ~ ("data", data)
  def successN = ("result", "success"):JValue
  def failedT(message:String) = ("result", "failed") ~ ("message", message)
}