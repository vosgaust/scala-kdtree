package aero.catec.test

import aero.catec.kdtree.NodeStack
import aero.catec.kdtree.Node

object StackTest {
  def main(args:Array[String]){
	  val s = new NodeStack[Node](5,5.0,4.0)
	  val n = new Node("first",1,5,null,null,null,true)
	  val n2 = new Node("second",2,5,null,null,null,true)
	  val n3 = new Node("third",4,5,null,null,null,true)
	  val n4 = new Node("forth",3,5,null,null,null,true)
	  val n5 = new Node("fifth",1.1,5,null,null,null,true)
	  val x = 5.0
	  val y = 4.0
	  s.push(n)
	  s.push(n2)
	  s.push(n3)
	  s.push(n4)
	  
	  println("Not sorted")
	  s.printStack
	  println("Sorted")
	  s.printStack
	  
	  
	  s.push(n5)
	  println("desordenado")
	  s.printStack
	  println("Should be sorted new")
	  s.printStack
	  s.pop
	  s.push(n4)
  
	  }

}