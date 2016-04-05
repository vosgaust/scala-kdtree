package aero.catec.kdtree

import scala.collection.mutable.Stack
import aero.catec.kdtree.utils._

class KDTree {
	private var root:Node = null		//the first node of the tree	
	private var k:Int = 0 
	private var ns:NodeStack[Node] = null
	private var (pointX,pointY):(Double,Double) = (0,0)
	
	/** Defines the root of the tree*/
	def setRoot (x:Double, y:Double, data:String) {
	  root = new Node(data,x,y,null,null,null,true)
	}

	/** Adds a new node to the tree, given its coordinates and data */
	def insert (x:Double, y:Double, data:String) {
	  /* Look for the node where to link the new node */
	  val (rover,dist) = searchAproximate(x,y) //We 
	  /* We check first at which variable (x or y) we have to look and
	   * then we check if we have to put our node under the lesser or the greater link */
	  if (rover.switch) {
	    if (x >= rover.x) rover.greater = new Node(data,x,y,null,null,rover,!rover.switch)
	    else rover.lesser = new Node(data,x,y,null,null,rover,!rover.switch)
	  } else {
	    if (y >= rover.y) rover.greater = new Node(data,x,y,null,null,rover,!rover.switch)
	    else rover.lesser = new Node(data,x,y,null,null,rover,!rover.switch)
	  } 
	}
	
	def searchKNeighbors (x:Double,y:Double,k:Int):NodeStack[Node] = {
	  pointX = x
	  pointY = y
	  this.k = k
	  ns = new NodeStack[Node](k,x,y)
	  searchNP()
	  ns
	}
	
	/* Given a point, this method returns the best aproximate node and 
	 * the distance between them */
	private def searchAproximate (pointX:Double,pointY:Double):(Node,Double) = {
		var rover = root
		var aprox:Node = null
		
		/* We loop the tree until we reach a leaf*/
		while (rover != null) {
		  aprox = rover
		  if (rover.switch) {		    
		    rover = if (pointX >= rover.x) {rover.greater} else {rover.lesser}
		  } else {
		    rover = if (pointY >= rover.y) {rover.greater} else {rover.lesser}
		  }
		}
		
		/* When we exit the loop, we have null at "rover" and the aproximate
		 * node at "aprox". Then we compute the euclidean distance between the points
		 * and returns a tuple containing both the node and the distance */
		(aprox,euclideanDistance(pointX,aprox.x,pointY,aprox.y))
	}
	
	/*  */
	private def searchNP (n:Node = root) {
	  /* If Switch is true, we have to check the X variable */
	  n.switch match {
	  	case true => checkCoordinate(n,(pointX,n.x))
	  	case false => checkCoordinate(n,(pointY,n.y))
	  }
	}
	
	/* This method goes down the tree given a node. It checks wether the best distance
	 * so far intersect with the hyperplane that the node defines. Every time we find a
	 * new node, we try to put it into the stack until it is full */
	private def checkOtherBranch (n:Node,bestDistance:Double) {
	  /* we push the father node */
	  ns.push(n)
	  /* checking at which variable we have to look */
	  if (n.switch) {
	    /* If the best distance so far intersect the hyperplane, we have to check
	     * both branches (greater and lesser) */
	    if (ns.worstDistance  > math.abs(pointX-n.x)) {
	      if (n.hasGreater) checkOtherBranch(n.greater,ns.worstDistance)
	      if (n.hasLesser)  checkOtherBranch(n.lesser,ns.worstDistance)
	    }
	    /* If it doesn't intersect, we still have to check the lesser branch */
	    else {
	      if (n.hasLesser) checkOtherBranch(n.lesser,ns.worstDistance)
	    }
	  }
	  /* The same if we have to look at the Y coordinate instead */
	  else {
	    if (ns.worstDistance > math.abs(pointY-n.y)) {
	      if (n.hasGreater) checkOtherBranch(n.greater,ns.worstDistance)
	      if (n.hasLesser)  checkOtherBranch(n.lesser,ns.worstDistance)	      
	    } else {
	      if (n.hasLesser)  checkOtherBranch(n.lesser,ns.worstDistance)
	    }	    
	  }
	}
	
	
	/* This method is a blind servant, it receives a node and a tuple with two variables
	 * ,no matter which coordinate it is(x,y,z,...), one for the point and one for the 
	 * node and then checks if our node's coordinate is greater or lesser than the point's
	 * coordinate.*/
	private def checkCoordinate (n:Node,toCheck:(Double,Double)) {
	  if (toCheck._1 >= toCheck._2) { 
	    if (n.hasGreater) {
	      searchNP(n.greater)
	      /* Once we have reached the best aproximation recursively, we try to put more
	       * nodes into the stack, until it is full*/
	      ns.push(n)
	      /* We check if our current hypersphere intersect the plane
	      * if it does, we go down the tree recursively until we find a leaf */
	      if(n.hasLesser && (ns.worstDistance  > math.abs(toCheck._1-toCheck._2) || toCheck._1-toCheck._2 == 0)){
	        checkOtherBranch(n.lesser, ns.worstDistance)
	      }
	    }
	    else {
   	      /* If I have no leafs, I'm the best aproximate neighbor */
	      ns.push(n)
 	      /* I have to look to my other branch in case I have it*/
 	      if(n.hasLesser) {checkOtherBranch(n.lesser,ns.worstDistance)}
	    }
	  }
	  /* if our point's coordinate is lesser than the node's*/
	  else {
	    if (n.hasLesser) {
	      searchNP(n.lesser)
	      ns.push(n)
	      if (n.hasGreater && (ns.worstDistance  > math.abs(toCheck._1-toCheck._2) || toCheck._1-toCheck._2 == 0)) {
	        checkOtherBranch(n.greater,ns.worstDistance)
	      }
	    }
	    /* If the node doesn't have childs, this node is the best aproximate */
	    else {
	      ns.push(n)
	      if (n.hasGreater) {checkOtherBranch(n.greater,ns.worstDistance)}
	    }
	  }
	}
	
	
	def printTree (n:Node = root) {
	  if (n.hasGreater) {printTree(n.greater)}
	  if (n.hasLesser) {printTree(n.lesser)}
	  println(n)
	}
	
}