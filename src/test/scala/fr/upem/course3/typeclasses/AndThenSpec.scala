package fr.upem.course3.typeclasses

import fr.upem.course3.model.University.Address.Marseille
import fr.upem.course3.model.University.{Address, School, Student}
import fr.upem.course3.typeclasses.AndThen.{FlatMap, andThen}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class AndThenSpec extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  // TODO You have to write a Containing instance here to check inside Option !
  implicit val optionFlatMap: FlatMap[Option] = new FlatMap[Option] {
    override def flatMap[A, B](f: A => Option[B])(fa: Option[A]): Option[B] =
      fa match {
        case None => None
        case Some(a) => f(a)
      }
  }


  "andThen" should "check if student is in Marseille" in {

    val maybeParis = andThen(Student.getStudentByName("Elisa")) { student =>
      andThen(School.getSchoolFor(student)) { school =>
        andThen(Address.getAddressFor(school)) { address =>
          if (address.city == Marseille) Some(true) else Some(false)
        }
      }
    }

    maybeParis should contain(true)
  }

}