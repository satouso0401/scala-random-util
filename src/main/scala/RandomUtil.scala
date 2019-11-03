import java.time.{Instant, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.util.UUID

import scala.util.Random

object RandomUtil {
  def uuid()                  = UUID.randomUUID.toString
  def alphanumeric(len: Int)  = Random.alphanumeric.take(len).mkString
  def ascii(len: Int)         = (1 to len).map(_ => Random.nextPrintableChar).mkString
  def unicodeString(len: Int) = Random.nextString(len)

  def emo(code: Int) = Character.toChars(code).map(c => c.toString).mkString
  //  (0x1F300 to 0x1F5FF).foreach(i => print( /*" " + i + ":" + */ emo(i)))

  def int(min: Int, max: Int)          = min + Random.nextInt(max - min + 1)
  def long(min: Long, max: Long)       = min + Random.nextLong() % (max - min)
  def double(min: Double, max: Double) = min + Random.nextDouble() * (max - min)

  def dateTime(
      min: String,
      max: String,
      formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME) = {
    val timeMin = ZonedDateTime.parse(min, formatter)
    val timeMax = ZonedDateTime.parse(max, formatter)
    val unixTime =
      long(timeMin.toInstant.toEpochMilli, timeMax.toInstant.toEpochMilli)
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixTime), timeMin.getOffset)
  }

  type RandomCounter[Int] = Iterator[Int]
  def offsetTime(
      base: String,
      amp: Double = 0.001,
      formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME)(
      implicit cnt: RandomCounter[Int]) = {
    val baseTime = ZonedDateTime.parse(base, formatter)
    val unixTime =
      (baseTime.toInstant.toEpochMilli + cnt.next() * 1000 * amp).toLong
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixTime), baseTime.getOffset)
  }
}
