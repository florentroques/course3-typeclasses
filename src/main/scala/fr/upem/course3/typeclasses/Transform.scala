package fr.upem.course3.typeclasses

object Transform {

  // TODO Define typeclass Functor
  trait Functor[F[_]]

  // TODO Implement instances according to the unit tests

  // TODO Implement transform function
  def transform[F[_], A, B](fa: F[A])(f: A => B): F[B] = ???

  // TODO Define typeclass BiFunctor according to the unit tests
  trait BiFunctor[F[_, _]]

  // TODO Implement instances according to the unit tests

  // TODO Implement bitransform function
  def bitransform[F[_, _], A, B, C, D](fab: F[A, B])(ac: A => C)(cb: B => D): F[C, D] = ???

}