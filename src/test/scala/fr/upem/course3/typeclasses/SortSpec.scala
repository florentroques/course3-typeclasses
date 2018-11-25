package fr.upem.course3.typeclasses

import fr.upem.course3.model.Bank
import fr.upem.course3.model.Bank.Account
import org.scalacheck.Arbitrary
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class SortSpec extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  "sort" should "sort bank account list" in {
    forAll { balances: List[Int] =>
      val accounts = balances.map(b => Bank.Account(Account.Number(), b))

      AndThen.sort(accounts).map(_.balance) should contain theSameElementsInOrderAs balances.sorted
    }
  }

  // TODO Implement random generator to inject accounts into the test below. You don't have to modify the test !
  implicit lazy val accountArb: Arbitrary[Account] = ???

  "sort" should "sort bank account list with account generation" in {
    forAll { accounts: List[Account] =>
      AndThen.sort(accounts).map(_.balance) should contain theSameElementsInOrderAs accounts.map(_.balance).sorted
    }
  }

}
