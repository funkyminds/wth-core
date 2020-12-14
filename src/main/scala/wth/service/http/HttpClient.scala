package wth.service.http

import wth.model.http.EntitySerDes
import zio._

object HttpClient {
  type HttpClient[SerDes[_] <: EntitySerDes[_]] = Has[Service[SerDes]]

  trait Service[SerDes[_] <: EntitySerDes[_]] {
    def get[T](uri: String,
               paths: Seq[String] = Nil,
               parameters: Map[String, String] = Map.empty,
               headers: Map[String, String] = Map.empty)(serDes: SerDes[T]): Task[T]

    def post[T](
      payload: T,
      uri: String,
      paths: Seq[String] = Nil,
      parameters: Map[String, String] = Map.empty,
      headers: Map[String, String] = Map.empty
    )(serDes: SerDes[T]): Task[Boolean]
  }
}
