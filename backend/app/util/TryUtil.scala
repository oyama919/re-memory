package util

import scala.util.Try

object TryUtil {

  //Seq[Try[T]]をTry[Seq[T]]へ変換するメソッド
  def sequence[T](xs: Seq[Try[T]]): Try[Seq[T]] = xs.foldLeft(Try(Seq.empty[T])) { (a, b) =>
    a flatMap (c => b map (d => c :+ d))
  }
}
