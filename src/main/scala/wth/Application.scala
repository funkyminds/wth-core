package wth

import wth.service.PhrasesProvider.PhrasesProvider
import wth.service.QueryService.QueryService
import wth.service.ResponseParser.ResponseParser
import wth.service.TranslationRepo.TranslationRepo
import wth.service._
import zio._
import zio.console._
import zio.stream.Stream

/**
 * Defines a full application ready for concrete services implementations.
 *
 * To implement:
 * - failures <- there could be words that failed in fetching definition. Log them or something.
 * - maybe return a collect/seq of tuples (successful, failures)?
 */
object Application {
  type Application[T] =
    ZIO[Console with PhrasesProvider with QueryService[T] with ResponseParser[T] with TranslationRepo, Throwable, Unit]

  def program[T: Tag]: Application[T] =
    //@formatter:off
    for {
      phrases <- PhrasesProvider.provide()
      _ <- Stream
        .fromIterable(phrases)
        .foreach(
          phrase =>
            for {
              _ <- putStrLn(s"Starting: $phrase")
              result <- QueryService.query[T](phrase)
              translations <- ResponseParser.parse[T](phrase, result)
              _ <- TranslationRepo.persist(translations)
              _ <- putStrLn(s"Done: $phrase\n")
            } yield ()
        )
    } yield ()
  //@formatter:on
}
