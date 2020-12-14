package wth.service.files

import java.io.FileInputStream

import wth.service.PhrasesProvider._
import zio._

import scala.io.Source

object FileBasedPhrasesProvider {
  val service: RLayer[Has[String], PhrasesProvider] = ZLayer.fromFunction(
    path =>
      () =>
        Task
          .effect(new FileInputStream(path.get))
          .bracket(is => UIO.effectTotal(is.close)) { is =>
            Task.effect(Source.fromInputStream(is).getLines().toSeq)
      }
  )
}
