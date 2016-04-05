package aero.catec.kdtree


object test {
  val kdtree = new KDTree
  
  def main(args:Array[String])={
    val k = 1
      
    kdtree.setRoot(1,1,"test1")
    kdtree.insert(3,3,"test2")
    kdtree.insert(3,-3,"test3")
    kdtree.insert(-2,2,"test4")
    kdtree.insert(-1,2,"testLeft")
    kdtree.insert(1,-2,"testRight")
    kdtree.insert(2,-2,"test5")
    val stack = kdtree.searchKNeighbors(-2,3,k)
    println("The %d nearest neighbors are: ".format(k))
    stack.foreach(println)
    println("The best one is :" + stack.last)
  }
}
