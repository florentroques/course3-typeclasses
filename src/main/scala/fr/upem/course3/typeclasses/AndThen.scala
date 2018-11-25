package fr.upem.course3.typeclasses

object AndThen {

  // TODO Define FlatMap typeclass
  trait FlatMap[F[_]] {
    def flatMap[A, B](f: A => F[B])(fa: F[A]): F[B]
  }

  // TODO Implement andThen function
  def andThen[F[_], A, B](fa: F[A])(f: A => F[B])(implicit ev: FlatMap[F]): F[B] =
    ev.flatMap(f)(fa)

}
