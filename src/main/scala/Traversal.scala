import scalaa.datastruct.SlimGraph
import scala.collection.mutable.{ OpenHashMap => Map }
import scala.collection.mutable.Set
import scala.collection.mutable.ArrayBuffer
  
/* This is a stripped-down version of a class used in one
 * of our projects.  We use it to illustrate graph traversals
 * with a closure.
 */
class Pebble36(val graph: SlimGraph) {
  
  val pebbleGraph = SlimGraph()

  //pebbleGraph.addNVertices(graph.getOrder())
  val numPebs = Array.fill(graph.order)(3)

  def findPebble2(r: Int, forbid: Set[Int]) = {
  
    val root = r
    val pathTree = Map[Int,Option[Int]]( r -> None )
 
    /* Here, the traverse function is being called from
     * some root node r and a pathTree is built as a
     * part of the traversal. Since this is a proper
     * closure we can modify pathTree within the closure */     
    pebbleGraph.traverse(r){ v=>
  
      if ( numPebs(v) > 0 && !forbid.contains(v) ) {
        pathTree
      }
  
      pebbleGraph.neighbors(v).foreach{ uval =>
        if ( !pathTree.contains(uval) ) { pathTree += uval -> Some(v) }
      }
  
      true
    }
   
  }
  
  
}
