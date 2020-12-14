package wth.service

import zio._

object PhrasesProvider {
  type PhrasesProvider = Has[PhrasesProvider.Service]

  def provide(): ZIO[PhrasesProvider, Throwable, Seq[String]] =
    ZIO.accessM(_.get.provide())

  trait Service {
    def provide(): Task[Seq[String]]
  }
}
