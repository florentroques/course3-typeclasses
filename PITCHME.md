# Scala


<img src="https://cdn.worldvectorlogo.com/logos/scala-4.svg" alt="Scala Logo" width="100px" style="border:none;background:none;" class="center"/>

Implicits et Typeclasses

---

# Objectifs

---

# Implicits
## Paramètre implicite
```scala
implicit val name = "my first implicit parameter"

def print(implicit s: String) = println(s)

print("Hello ESIPE") // prints "Hello ESIPE"
print // prints "my first implicit parameter"
```
- Résolution par type
- Résolution à la compilation

---

## Paramètre implicite
### Aucune résolution
```scala
implicit val age = 12

def print(implicit s: String) = println(s)

print("Hello ESIPE") // prints "Hello ESIPE"
print // does not compile
```
- Aucune valeur éligible

---

## Paramètre implicite
### Résolution ambigüe
```scala
implicit val first = "my first implicit parameter"
implicit val second = "my second implicit parameter"

def print(implicit s: String) = println(s)

print("Hello ESIPE") // prints "Hello ESIPE"
print // does not compile
```
- Plusieurs valeurs éligibles

---

# Implicits
## Extensions de méthodes
```scala
object Implicits {
  implicit class StringPlus(val s: String) extends AnyVal {
    def capitalizeEachWord: String = s.split(" ").map(w => w.capitalize).mkString(" ")
  }
}

import Implicits._
println("hello esipe".capitalizeEachWord) // prints Hello Esipe
```
- Ajoute des méthodes sur des types existants

---

## Extensions de méthodes
### Généricité
```scala
object Implicits {
  implicit class APlus[A](val a: A) extends AnyVal {
    def some: Option[A] = Some(a)
  }
}

import Implicits._
println("hello esipe".some) // prints Some("hello esipe")
println(3.some) // prints Some(3)
println(true.some) // prints Some(true)
```
- Ajoute des méthodes sur des types génériques

---

# Implicits
## Conversion implicite
```scala
implicit def IntToString(i: Int) = i.toString

def print(s: String) = println(s)

print(4) // prints "4"
```
- A utiliser avec précaution

---

# Implicits
## Preuve par les types
```scala
trait Show[A]

def show[A](a: A)(implicit ev: Show[A]) = println(a)

show(3) // does not compile
show(true) // does not compile
```
- show a besoin d'un implicit Show[A]

---

## Preuve par les types
```scala
trait Show[A]
implicit val IntShow = new Show[Int]{}

def show[A](a: A)(implicit ev: Show[A]) = println(a)

show(3) // compiles
show(true) // does not compile
```

---

## Preuve par les types
```scala
trait Show[A]
implicit val IntShow = new Show[Int]{}
implicit val BooleanShow = new Show[Boolean]{}

def show[A](a: A)(implicit ev: Show[A]) = println(a)

show(3) // compiles
show(true) // compiles
```

---

## Preuve par les types
### Sucre syntaxique
```scala
trait Show[A]
implicit val IntShow = new Show[Int]{}
implicit val BooleanShow = new Show[Boolean]{}

def show[A: Show](a: A) = println(a)

show(3) // compiles
show(true) // compiles
```

---

There are only two reasonable uses of implicits:

- Extension methods
- Type class instances

Every other use of implicits is an example of implicit abuse—including implicit conversions, implicit "configuration", implicit execution contexts (the cause of many thread pool bugs in Scala applications), and so forth. Implicit abuse makes working with large code bases extremely challenging, and scares larger companies away from using Scala.

**John De Goes**

---

# Typeclasses
- Implémenté pour la première fois dans Haskell
- Repose sur la preuve par les types
- Implémentation d'un polymorphisme ad-hoc

---

# Typeclasses
## Pourquoi ?
```scala
case class Car(name: String, wheelCount: Int)

case class Person(name: String, age: Int)
```

---

```scala
def show(c: Car): String = ???
```

---

```scala
def show(c: Car): String = s"${c.name} -> ${c.wheelCount}"
```

---

```scala
def show(p: Person): String = ???
```

---

```scala
def show(p: Person): String = s"${p.name} -> ${p.age}"
```

---

```scala
def show[T](t: T): String = ???
```

---

```scala
trait Showable {
  def show: String
}

case class Car(name: String, wheelCount: Int) extends Showable {
  def show = s"${this.name} -> ${this.wheelCount}"
}

case class Person(name: String, age: Int) extends Showable {
  def show = s"${this.name} -> ${this.age}"
}

def print[T <: Showable](t: T): String = t.show

print(Car("Renault", 5))
print(Person("Charles", 23))
```

---

## Polymorphisme ad-hoc avec la POO
- Doit être implémenté à la création du type
- Compliqué à implémenter pour les types existants
- Compliqué d'implémenter un autre comportement
- Complexifie la hiérarchie de types
- Mélange technique et métier

---

# Typeclasses
## Comment ?
```scala
trait Show[T] {
  def show(t: T): String
}

def print[T](t: T)(implicit ev: Show[T]) = ev.show(t) // def print[T: Show](t: T)

print("ESIPE") // does not compile
print(true) // does not compile
print(4) // does not compile
```

---

```scala
trait Show[T] {
  def show(t: T): String
}

object Show {
  implicit val StringShow = new Show[String] {
    def show(s: String) = s
  }
  
  implicit val BooleanShow = new Show[Boolean] {
    def show(b: Boolean) = if (b) "yes" else "no"
  }
  
  implicit val IntShow = new Show[Int] {
    def show(i: Int) = i.toString
  }
}

def print[T](t: T)(implicit ev: Show[T]) = ev.show(t)

import Show.IntShow
print("ESIPE") // prints "ESIPE"

import Show.BooleanShow
print(true) // prints "yes"

import Show.IntShow
print(4) // prints "4"
```

---

```scala
trait Show[T] {
  def show(t: T): String
}

case class Car(name: String, wheelCount: Int)
object Car {
  implicit val Show = new Show[Car] {
    def show(c: Car) = s"${c.name} -> ${c.wheelCount}"
  }
}

case class Person(name: String, age: Int)
object Person {
  implicit val Show = new Show[Person] {
    def show(p: Person) = s"${p.name} -> ${p.age}"
  }
}

def print[T](t: T)(implicit ev: Show[T]) = ev.show(t)

print(Car("Renault", 5))
print(Person("Charles", 23))
```

---

## Typeclasses
- ~~Doit être implémenté à la création du type~~
- Peut être implémenté à tout moment
- ~~Compliqué à implémenter pour les types existants~~
- Facile à implémenter pour les types existants
- ~~Compliqué d'implémenter un autre comportement~~
- Facile d'implémenter de multiples instances
- ~~Complexifie la hiérarchie de types~~
- Ne touche pas la hiérarchie de types
- ~~Mélange technique et métier~~
- Séparation nette entre technique et métier

---

## Sucre syntaxique
```scala
trait Show[T] {
  def show(t: T): String
}

def print[T: Show](t: T) = implicitly[Show[T]].show(t)

// def implicitly[T](implicit e: T): T
```

---

## Summoner pattern
```scala
trait Show[T] {
  def show(t: T): String
}

object Show {
  def apply[T](implicit ev: Show[T]) = ev
}

Show[Int].show(1)
```

---

## Dérivation de typeclasses
Est-il possible, à partir d'une instance de **Show[T]**, de déduire une instance de **Show[Option[T]]** ?

---

## Dérivation de typeclasses
```scala
trait Show[T] {
  def show(t: T): String
}

object Show {
  def OptionShow[T](implicit ev: Show[T]): Show[Option[T]] = new Show[Option[T]] {
    def show(o: Option[T]) = o match {
      case Some(t) => s"Value is ${ev.show(t)}"
      case None => "Nothing here"
    }
  }
}
```

---

## Dérivation de typeclasses
Est-il possible, à partir d'une instance de **Show[T]**, de déduire une instance de **Show[(T, T)]** ?

---

## Dérivation de typeclasses
```scala
trait Show[T] {
  def show(t: T): String
}

object Show {
  def TupleShow[T](implicit ev: Show[T]): Show[(T, T)] = new Show[(T, T)] {
    def show(t: (T, T)) = s"(${ev.show(t._1)}, ${ev.show(t._2)})"
  }
}
```

---

# Typeclasses couramment utilisés
## Eq
```scala
trait Eq[T] {
  def ===(a: T, b: T): Boolean
}

def eq[V: Eq](a: V, b: V): Boolean = implicitly[Eq[V]].===(a, b)

object Eq {
  implicit val IntEq = new Eq[Int] {
    def ===(a: Int, b: Int) = a == b
  }
}
```

---

## Eq
### Typeclasses et extension de méthodes
```scala
trait Eq[T] {
  def ===(a: T, b: T): Boolean
}

object Eq {
  implicit val IntEq = new Eq[Int] {
    def ===(a: Int, b: Int) = a == b
  }
  implicit class EqPlus[V: Eq](val a: V) {
    def ===(b: V): Boolean = implicitly[Eq[V]].===(a, b)
  }
}

import Eq.EqPlus
import Eq.IntEq
println(4 === 6) // prints "false"
println(10 === 10) // prints "true"
```

---

# Typeclasses et type constructor
## Functor
A partir d'une fonction **A => B**, et d'un Type Constructor **F[_]**, est-il possible d'en déduire une fonction **F[A] => F[B]** ?

---

# Typeclasses et type constructor
## Functor
```scala
trait Functor[F[_]] {
  def map[A, B](f: A => B)(fa: F[A]): F[B]
}
```

---

## Functor
```scala
trait Functor[F[_]] {
  def map[A, B](f: A => B)(fa: F[A]): F[B]
}
object Functor {
  implicit val OptionFunctor = new Functor[Option] {
    def map[A, B](f: A => B)(fa: Option[A]) = fa match {
      case Some(x) => Some(f(x))
      case None => None
    }
  }
}

def transform[F[_]: Functor, A, B](fa: F[A])(f: A => B) = implicitly[Functor[F]].map(f)(fa)

import Functor.OptionFunctor
println(transform(Option(5))(x => x + 10)) // prints "Some(15)"
```

---

# Typeclasses couramment utilisés
## Monad