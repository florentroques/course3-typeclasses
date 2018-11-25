package fr.upem.course3.typeclasses

import fr.upem.course3.model.Bank.Safe

object Transform {

  // TODO Define typeclass Functor
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  // TODO Implement instances according to the unit tests
  implicit val bankSafeFunctor: Functor[Safe] = new Functor[Safe] {
    override def map[A, B](fa: Safe[A])(f: A => B): Safe[B] = fa.copy(stuff = f(fa.stuff))
  }

  // TODO Implement transform function
  def transform[F[_], A, B](fa: F[A])(f: A => B)(implicit ev: Functor[F]): F[B] = ev.map(fa)(f)

  // TODO Define typeclass BiFunctor according to the unit tests
  trait BiFunctor[F[_, _]] {
    def bimap[A, B, C, D](fab: F[A, B])(f: A => C)(g: B => D): F[C, D]
  }

  // TODO Implement instances according to the unit tests
  implicit val eitherBifunctor: BiFunctor[Either] = new BiFunctor[Either] {
    override def bimap[A, B, C, D](fab: Either[A, B])(f: A => C)(g: B => D): Either[C, D] =
      fab match {
        case Right(a) => Right(g(a))
        case Left(b) => Left(f(b))
      }
  }

  // TODO Implement bitransform function
  def bitransform[F[_, _], A, B, C, D](fab: F[A, B])(ac: A => C)(cb: B => D)(implicit ev: BiFunctor[F]): F[C, D] = ev.bimap(fab)(ac)(cb)

}