import java.time.format.DateTimeFormatter

import RandomUtil._
import scala.util.chaining._

object Usage extends App {

  val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
  val rc
    : RandomCounter[Int]                 = LazyList.from(0).iterator // If Scala 2.12 use Stream
  implicit val otCnt: RandomCounter[Int] = LazyList.from(0).iterator

  val list = for (i <- 1 to 20) yield {
    (i,
     ascii(8),
     double(10, 20),
     rc.next(),
     rc.next(),
     offsetTime("2019-11-01T10:00:00.001+09:00", 10)(otCnt),
     offsetTime("2019-11-01T10:00:00.002+09:00", 10)(otCnt),
    )
  }

  list.foreach(_.productIterator.mkString("\t").pipe(println))
}
