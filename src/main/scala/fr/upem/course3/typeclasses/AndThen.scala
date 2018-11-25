package fr.upem.course3.typeclasses

object AndThen {

  // TODO Define FlatMap typeclass
  trait FlatMap[T]

  // TODO Implement andThen function
  def andThen[F[_], A, B](fa: F[A])(f: A => F[B]): F[B] = ???
}
