package wth.service

import zio._

object QueryService {
  type QueryService[T] = Has[QueryService.Service[T]]

  def query[T: Tag](toQuery: String): ZIO[QueryService[T], Throwable, T] =
    ZIO.accessM(_.get.query(toQuery))

  trait Service[T] {
    def query(toQuery: String): Task[T]
  }
}
