package aero.catec.test

import aero.catec.kdtree.KDTree
import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class KDTreeTest {
  var kdtree:KDTree = null
  @Before def makeTree{
    kdtree = new KDTree
    kdtree.setRoot(1,1,"test1")
    kdtree.insert(3,3,"test2")
    kdtree.insert(3,-3,"test3")
    kdtree.insert(-2,2,"test4")
    kdtree.insert(-1,2,"test2Left")
    kdtree.insert(1,-2,"test4Left")
    kdtree.insert(2,-2,"test5")    
  }
  
  
  @Test def pointNearRoot{
    //Point near root
    assertEquals("(test1,1.0,1.0)",kdtree.searchKNeighbors(2,1,1).last.toString)
  }

  @Test def pointInYAxis{
    assertEquals("Wrong point in Y axis", "(test2Left,-1.0,2.0)",kdtree.searchKNeighbors(0,3,1).last.toString)
  }
  
  @Test def pointInXplane{
	assertEquals("Wrong second point", "(test2,3.0,3.0)",kdtree.searchKNeighbors(1,4,1).last.toString)
  }
  
  @Test def pointFarFromCloud{
	assertEquals("Wrong forth point left", "(test4Left,1.0,-2.0)",kdtree.searchKNeighbors(-3,-2,1).last.toString)
  }
  @Test def point5{
	assertEquals("wrong forth point", "(cuarto,-2.0,2.0)",kdtree.searchKNeighbors(-3,-1,1).last.toString)
  }

  @Test def point6{
	assertEquals("Wrong", "(tercero IZq,1.0,-2.0)",kdtree.searchKNeighbors(-3,-2,1).last.toString)
  }
  
  @Test def point7{
	assertEquals("Wrong", "(cuarto,-2.0,2.0)",kdtree.searchKNeighbors(-2,3,1).last.toString)
  }  
}