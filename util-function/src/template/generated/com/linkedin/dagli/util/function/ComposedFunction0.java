// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/ComposedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;
import com.linkedin.dagli.util.named.Named;


/**
 * A function class implementing {@link Function0.Serializable} that composes
 * {@link Function0} with a {@link Function1}.  The function is only <strong>actually</strong> serializable
 * if its constituent composed functions are serializable, of course.
 */
class ComposedFunction0<R, Q> implements Function0.Serializable<R>, Named {
  private static final long serialVersionUID = 1;

  private final Function0<Q> _first;
  private final Function1<? super Q, ? extends R> _andThen;

  /**
   * Creates a new instance that composes two functions, i.e. {@code andThen(first(inputs))}.
   *
   * @param first the first function to be called in the composition
   * @param andThen the second function to be called in the composition, accepting the {@code first} functions result
   *                as input
   */
  ComposedFunction0(Function0<Q> first, Function1<? super Q, ? extends R> andThen) {
    _first = first;
    _andThen = andThen;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ComposedFunction0<R, Q> safelySerializable() {
    return new ComposedFunction0<>(((Function0.Serializable<Q>) _first).safelySerializable(),
        ((Function1.Serializable<? super Q, ? extends R>) _andThen).safelySerializable());
  }

  @Override
  public R apply() {
    return _andThen.apply(_first.apply());
  }

  @Override
  public int hashCode() {
    return Objects.hash(ComposedFunction0.class, _first, _andThen);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ComposedFunction0) {
      return this._first.equals(((ComposedFunction0) obj)._first)
          && this._andThen.equals(((ComposedFunction0) obj)._andThen);
    }
    return false;
  }

  @Override
  public String toString() {
    return Named.getShortName(_andThen) + "(" + Named.getShortName(_first) + ")";
  }

  @Override
  public String getShortName() {
    return Named.getShortName(_andThen) + "(...)";
  }
}
