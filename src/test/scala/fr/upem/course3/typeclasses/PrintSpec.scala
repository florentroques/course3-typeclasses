package fr.upem.course3.typeclasses

import fr.upem.course3.model.Bank
import fr.upem.course3.model.Bank.Account
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class PrintSpec extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  "print" should "print a positive bank account" in {
    forAll { n: String =>
      val balance = 100
      val account = Bank.Account(Account.Number(n), balance)

      Print.print(account) should be(s"Account number $n has positive balance $balance")
    }
  }

  "print" should "print a negative bank account" in {
    forAll { n: String =>
      val balance = -100
      val account = Bank.Account(Account.Number(n), balance)

      Print.print(account) should be(s"Account number $n has negative balance $balance")
    }
  }

  "print" should "print a zero bank account" in {
    forAll { n: String =>
      val balance = 0
      val account = Bank.Account(Account.Number(n), balance)

      Print.print(account) should be(s"Account number $n is empty")
    }
  }

  "print" should "print none account" in {
    Print.print(Option.empty[Account]) should be(s"Nothing here")
  }

  "print" should "print a positive bank account" in {
    forAll { n: String =>
      val balance = 100
      val account = Option(Bank.Account(Account.Number(n), balance))

      Print.print(account) should be(s"Something inside : Account number $n has positive balance $balance")
    }
  }

}
