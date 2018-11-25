package fr.upem.course3.typeclasses

import fr.upem.course3.model.Bank.Account

object Print {

  // TODO Define typeclass Show
  trait Show[S] {
    def show(s: S): String
  }

  // TODO Implement instances of Show typeclass
  implicit val accountShow: Show[Account] = new Show[Account] {
    override def show(acc: Account): String =
      if (acc.balance > 0)
        s"Account number ${acc.number.value} has positive balance ${acc.balance}"
      else if (acc.balance == 0)
        s"Account number ${acc.number.value} is empty"
      else
        s"Account number ${acc.number.value} has negative balance ${acc.balance}"
  }

  implicit def optionShow[A](implicit showA: Show[A]): Show[Option[A]] = new Show[Option[A]] {
    override def show(s: Option[A]): String =
      s match {
        case None => "Nothing here"
        case Some(a) => s"Something inside : ${showA.show(a)}"
      }
  }

  // TODO Implement print function
  def print[A](a: A)(implicit ev: Show[A]): String = ev.show(a)

}
