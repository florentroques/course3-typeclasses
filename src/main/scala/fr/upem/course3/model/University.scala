package fr.upem.course3.model

import fr.upem.course3.model.University.Address.{Bordeaux, City, Marseille, Paris}

object University {

  final case class Address(street: String, city: City)

  object Address {

    sealed trait City

    case object Paris extends City

    case object Marseille extends City

    case object Bordeaux extends City

    def getAddressFor(school: School) = school.address

  }

  final case class School(name: String, address: Option[Address])

  object School {

    def getSchoolFor(student: Student) = student.school

  }

  final case class Student(name: String, school: Option[School])

  object Student {
    val students = List(
      Student("Charles", Some(School("ESIPE", Some(Address("Rue des archives", Paris))))),
      Student("Elisa", Some(School("ESIPE", Some(Address("Rue de picpus", Marseille))))),
      Student("Emilie", Some(School("ESIPE", Some(Address("Rue de picpus", Bordeaux))))),
      Student("Jonathan", Some(School("ESIPE", None))),
      Student("Joachim", None),
    )

    def getStudentByName(name: String) = students.find(s => s.name == name)
  }

}