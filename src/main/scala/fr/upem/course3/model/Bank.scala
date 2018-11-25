package fr.upem.course3.model

import java.util.UUID.randomUUID

object Bank {

  final case class Safe[A](number: Safe.Number, stuff: A)

  object Safe {

    final case class Number(value: String) extends AnyVal

    object Number {
      def apply() = new Number(randomUUID().toString)
    }

  }

  final case class Account(number: Account.Number, balance: Int)

  object Account {

    final case class Number(value: String) extends AnyVal

    object Number {
      def apply() = new Number(randomUUID().toString)
    }

  }

}