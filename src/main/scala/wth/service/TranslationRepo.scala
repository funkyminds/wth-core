package wth.service

import wth.service.ResponseParser.Translation
import zio._
import zio.stream.Stream

object TranslationRepo {
  type TranslationRepo = Has[TranslationRepo.Service]

  def persist(translations: Set[Translation]): ZIO[TranslationRepo, Throwable, Boolean] =
    ZIO.accessM(_.get.persist(translations))

  def persist(translation: Translation): ZIO[TranslationRepo, Throwable, Boolean] =
    ZIO.accessM(_.get.persist(translation))

  trait Service {
    def persist(translations: Set[Translation]): Task[Boolean] =
      Stream.fromIterable(translations).foreach(persist) *> ZIO.succeed(true)

    def persist(translation: Translation): Task[Boolean]
  }
}
