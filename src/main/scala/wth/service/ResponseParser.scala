package wth.service

import zio._

object ResponseParser {
  type ResponseParser[R] = Has[ResponseParser.Service[R]]
  type Translation = (String, String)

  def parse[R: Tag](phrase: String, raw: R): ZIO[ResponseParser[R], Throwable, Set[Translation]] =
    ZIO.accessM(_.get.parse(phrase, raw))

  trait Service[R] {
    def parse(phrase: String, raw: R): Task[Set[Translation]]
  }
}
