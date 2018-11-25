package fr.upem.course3.typeclasses

object Sort {

  // TODO Implement Ordering for Ints
  implicit val IntOrdering = ???

  // TODO Implement instances of Ordering according to the unit tests. Maybe you could use IntOrdering

  // TODO Implement sort function
  def sort[A](as: List[A]): List[A] = ???

}
