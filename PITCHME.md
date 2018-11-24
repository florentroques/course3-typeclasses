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

# Typeclasses

- Implémenté pour la première fois dans Haskell
- Repose sur la preuve par les types
- Implémentation d'un polymorphisme ad-hoc

---

# Pourquoi les typeclasses
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

def show[T <: Showable](t: T): String = t.show

show(Car("Renault", 5))
show(Person("Mathieu", 31))
```

---

## Polymorphisme ad-hoc

- Doit être implémenté à la création du type
- Compliqué à implémenter pour les types existants
- Compliqué d'implémenter un autre comportement
- Complexifie la hiérarchie de types
- Mélange technique et métier

---