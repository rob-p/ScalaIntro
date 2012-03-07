package scalaa.scientific
import scala.collection.IterableLike
import scala.util.Random

import scala.collection._
import generic.CanBuildFrom
import mutable.Builder
import scala.collection.Set

object RichImplicits {
  /* If we have a class of IndexedSeq[A] and we try to call a method
   * that is not a member of that class, the instance of the iterable S[A] will be
   * implicitly wrapped with a RichIterable class where we have more
   * methods that we just defined.  This is a statically-typed way to achieve
   * a similar functionality as dynamic class extensions (e.g. Ruby uses these)
   */
  
  // Any class that can be viewed as an Iterable class is wrapped appropriately
  implicit def wrapIterable[A, S[A] <: Iterable[A]](seq: S[A]) =
    new RichIterable(seq)
  
   
  /* Here, any objects that are iterable can now be 'extended'
   * with the sample and choice methods.  
   */
  class RichIterable[A, S[A] <: Iterable[A]](val seq: S[A]) {

    val b = seq.genericBuilder[A]
    val random = scala.util.Random

    def sample(k: Int) = {
      val n = seq.size
      // TODO: If you want more than half the elements, ...
      assert (k <= n, { println("k = "+k+" > n = "+n) } )
      var i = 0
      var numerator = k
      val it = seq.iterator
      while (numerator > 0) {
        val denominator = n - i
        val prob = numerator.toDouble/denominator
        val x = it.next
        if (random.nextDouble < prob) {
          b += x
          numerator -= 1
        }
        i += 1
      }
      b.result
    }

    def choice() = { sample(1).head }

  }

}
