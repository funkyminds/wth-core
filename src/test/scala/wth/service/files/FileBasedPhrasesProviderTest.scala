package wth.service.files

import java.io.File

import wth.service.PhrasesProvider
import wth.service.PhrasesProvider.PhrasesProvider
import wth.service.files.FileBasedPhrasesProvider._
import zio._
import zio.test.Assertion._
import zio.test._

object FileBasedPhrasesProviderTest extends DefaultRunnableSpec {
  private val dep: ULayer[PhrasesProvider] = (ZIO.effect {
    val loader = getClass().getClassLoader()
    val file = new File(loader.getResource("file_based_input_test.txt").getFile)
    file.getAbsolutePath
  }.toLayer >>> service).orDie

  override def spec =
    suite("FileBasedPhrasesProvider") {
      testM("should read file from test resources") {
        for {
          lines <- PhrasesProvider.provide
        } yield assert(lines)(equalTo(Seq("line1", "line 2", "line  3")))
      }.provideCustomLayer(dep)
    }
}
