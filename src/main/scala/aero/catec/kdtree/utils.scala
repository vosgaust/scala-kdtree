package aero.catec.kdtree

object utils {
	/* Given two 2D points, computes the euclidean distance between them */
	def euclideanDistance (x1:Double,x2:Double,y1:Double,y2:Double):Double = {
		val dx = x1-x2 
		val dy = y1-y2
		return math.sqrt((dx*dx)+(dy*dy))
	}
}