package fr.upem.course3.typeclasses

import fr.upem.course3.model.Bank
import fr.upem.course3.model.Bank.Safe
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class TransformSpec extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  "transform" should "add money in a safe" in {
    forAll { (n: String, balance: Int) =>
      val safe = Bank.Safe(Safe.Number(n), balance)

      Transform.transform(safe)(x => x + 1000) should be(Safe(Safe.Number(n), balance + 1000))
    }
  }

  "bitransform" should "transform left value" in {
    forAll { a: String =>
      val either = Left(a).asInstanceOf[Either[String, Int]]

      Transform.bitransform(either)(_.length)(_ + 1000) should be(Left(a.length))
    }
  }

  "bitransform" should "transform right value" in {
    forAll { i: Int =>
      val either = Right(i).asInstanceOf[Either[String, Int]]

      Transform.bitransform(either)(_.length)(_ + 1000) should be(Right(i + 1000))
    }
  }

}