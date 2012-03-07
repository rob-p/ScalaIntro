 /* This is an example of a graph class with some basic algorithms
  * that we are using for a toolbox of scala utilities (ScalaA -- also
  * on GitHub)
  */
package scalaa.datastruct

import scala.collection.mutable.{ OpenHashMap => Map }
import scala.collection.mutable.Set
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/* Notice the 'case' word in front of the class?  This keyword
 * notifies the compiler to create a class with a variety of
 * handy Scala idioms.  First, any variables used to construct
 * the class (they would be in the parens for SlimGraph()), are
 * automatically exported so they are accessible to someone using
 * the class.  One handy thing about this is that the class is
 * automatically constructed with companion objects (no need for
 * using the 'new' keyword to automatically construct an object as well as
 * equality operations -- which is really nice, b/c
 * getting equality properties right in object oriented   
 * programming is actually not that straightforward */
case class SlimGraph() {
  /* Most of these definitions are just basic operations on getting
   * data out of a graph.  Here, for simplicity, the vertices are Ints,
   * but as Basics.scala shows, you can parameterize a class generically.
   */  
  val adjList = ArrayBuffer[Set[Int]]()
  var size = 0
  
  def insertEdge(u: Int, v: Int, directed: Boolean) {
  
    def insertAdjList(u: Int, v: Int) {
  
      while ( (u > adjList.size-1) || (v > adjList.size-1)) { 
        adjList += Set.empty[Int] 
      }
  
      adjList(u) += v
      size += 1
    }
  
    insertAdjList(u,v)
    if (!directed) { insertAdjList(v,u) }
  }
  
  def removeEdge(u: Int, v: Int, directed: Boolean) {
  
    def removeAdjList(u: Int, v: Int) {
      adjList(u) -= v
      size -= 1
    }
  
    removeAdjList(u,v)
    if (!directed) { removeAdjList(v,u) }
  }
  
  def neighbors(u: Int) = { 
    if( u < adjList.size ) { adjList(u) } 
    else { Set.empty[Int] } 
  }
  
  def order = adjList.size
  
  def edges = {
    val es = ArrayBuffer[(Int, Int)]()
  
    (0 until order).foreach { u=>
      es ++= adjList(u).map{ v => (u,v) }
    }
  
    es
  }
 
  /* This function defines a simple breadth-first traversal
   * of a graph with 'curried arguments'.  The first set of
   * arguments takes a start vertex and the second one takes
   * a closure that operates on the current vertex of the graph
   * and returns true if the traversal is to be continued.
   * An example of this is shown in Treversal.scala
   */
  def traverse(t: Int)(f: Int => Boolean) {
    var nextLevel = Set.empty[Int]
    val visited = Set.empty[Int]
    nextLevel += t
    
    while ( nextLevel.size > 0 ) {
      val thisLevel = nextLevel
      nextLevel = Set.empty[Int]
  
      thisLevel.foreach{ u =>
  
        if ( !visited(u) ) {
          visited.add( u )
          if(f(u)) {neighbors(u).foreach{ uval => nextLevel.add(uval)}}
        }
  
      }
  
    }   
  }
  
}
