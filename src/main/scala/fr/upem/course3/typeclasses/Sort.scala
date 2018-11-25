package fr.upem.course3.typeclasses

import fr.upem.course3.model.Bank.Account

object Sort {

  // TODO Implement Ordering for Ints
  implicit val IntOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)

  // TODO Implement instances of Ordering according to the unit tests. Maybe you could use IntOrdering
  implicit val AccountOrdering: Ordering[Account] = new Ordering[Account] {
    override def compare(x: Account, y: Account): Int = IntOrdering.compare(x.balance, y.balance)
  }

  // TODO Implement sort function
  def sort[A: Ordering](as: List[A]): List[A] = as.sorted

}
