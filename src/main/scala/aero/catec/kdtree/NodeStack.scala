package aero.catec.kdtree

import scala.collection.mutable.Stack
import aero.catec.kdtree.utils._

/** This class holds a sorted stack of Nodes, the first one would be the worst node
 * of the stack and the last would be the best. When we pop an element, we will be
 * always retrieving the worst node of the stack*/
class NodeStack[A <: Node](dimension:Int,x:Double,y:Double) extends Stack[Node]{
  var bestDistance:Double = 0
  var worstDistance:Double = 0
  
  /** This method receives a node and checks if the current size of the stack is
   * lesser than its dimension. If the stack has room for more nodes, we push the
   * new one without doing anything else. If the stack is full, we check if the 
   * new node is better than the worst.
   * The stack needs to be always sorted so we call sort every time we push a new
   * node.*/
  def push(n:A):NodeStack[A] = {
    val nodeDistance = euclideanDistance(x,n.x,y,n.y)
    if (super.length < dimension) {
     super.push(n)
    } else {
      if (nodeDistance < worstDistance) {
        super.pop
        super.push(n)
      } else {this}
    }
     sort
     worstDistance = euclideanDistance(x,super.head.x,y,super.head.y)
     return this
  }

  /* 
   * This method will be called right after a new node is put into the stack
   * so it's the only one that could be unsorted. We loop the stack replacing
   * the new node with a worse node until we reach a node that is better 
   */
  private def sort() {
    for(i <- 1 until super.size){
      val last = super.apply(i-1)
      val next = super.apply(i)
      val nextDistance = euclideanDistance(x,next.x,y,next.y)
      val lastDistance = euclideanDistance(x,last.x,y,last.y)
      if (nextDistance > lastDistance) {
        super.update(i,last)
        super.update(i-1,next)
      }      
    }
  }

  def printStack = super.foreach(println)  
}