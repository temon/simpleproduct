package com.griya.labs.simple.api

import com.ning.http.client.Response
import net.liftweb.http._
import net.liftweb.http.provider.servlet.{ServletFilterProvider, HTTPRequestServlet}
import net.liftweb._
import org.specs2.Specification
import util._
import Helpers._
import json._
import JsonDSL._

/**
  * Created by mac on 3/6/16.
  */
trait RestapiTestHelper {

  import scala.concurrent.ExecutionContext.Implicits.global
  import dispatch._
  import net.liftweb.json._

  val http = new Http

  var version:String = "v1"

  protected def toAbsoluteUrl(endpoint:String) = {
    "http://localhost:8080/" + version + "/" + endpoint
  }


  class ResponseJsonable(resp:Response){
    def toJSON:JValue = {
      parse(resp.getResponseBody)
    }
  }

  implicit def implicitResponseJsonable(resp:Response) = new ResponseJsonable(resp)

  def get(endpoint:String)( respHandler: Response => Unit ){
    respHandler {
      http(url(toAbsoluteUrl(endpoint))).apply()
    }
  }

  def get(endpoint:String, headers:Map[String, String])( respHandler: Response => Unit ){
    respHandler {
      val req = url(toAbsoluteUrl(endpoint))
      for ((hk, hv) <- headers)
        req.addHeader(hk, hv)
      http(req).apply()
    }
  }

  def post(endpoint:String, data:Map[String,String])(respHandler: Response => Unit){
    respHandler {
      val req = url(toAbsoluteUrl(endpoint)) << data
      http(req).apply()
    }
  }

  def put(endpoint:String, data:Map[String,String])(respHandler: Response => Unit){
    respHandler {
      val req = url(toAbsoluteUrl(endpoint)).PUT << data
      http(req).apply()
    }
  }

  def del(endpoint:String, data:Map[String,String]=Map.empty[String, String])(respHandler: Response => Unit){
    respHandler {
      val req = url(toAbsoluteUrl(endpoint)).DELETE << data
      http(req).apply()
    }
  }

}