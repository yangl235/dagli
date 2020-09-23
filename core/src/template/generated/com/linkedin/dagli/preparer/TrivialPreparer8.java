// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the preparer/TrivialPreparerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.preparer;

import com.linkedin.dagli.transformer.PreparedTransformer8;


/**
 * Trivial "preparer" that simply returns a provided, prepared transformer of arity 8.
 * @param <R> the type of result produced by the prepared transformer.
 * @param <N> the type of prepared transformer that will be returned
 */
public class TrivialPreparer8<A, B, C, D, E, F, G, H, R, N extends PreparedTransformer8<A, B, C, D, E, F, G, H, R>>
    extends AbstractStreamPreparer8<A, B, C, D, E, F, G, H, R, N> {
  private final N _preparedForNewData;
  private final PreparedTransformer8<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? extends R> _preparedForPreparationData;

  /**
   * Creates a new instance that will "prepare" the provided transformer.
   *
   * @param prepared the transformer which will result from this preparer.
   */
  public TrivialPreparer8(N prepared) {
    this(prepared, prepared);
  }

  /**
     * Creates a new instance that will "prepare" the provided transformers.
     *
     * @param preparedForPreparationData the transformer to be used for preparation data
     * @param preparedForNewData the transformer to be used for new data
     */
  public TrivialPreparer8(
      PreparedTransformer8<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? extends R> preparedForPreparationData,
      N preparedForNewData) {
    _preparedForNewData = preparedForNewData;
    _preparedForPreparationData = preparedForPreparationData;
  }

  @Override
  public PreparerResultMixed<? extends PreparedTransformer8<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? extends R>, N> finish() {
    return new PreparerResultMixed<>(_preparedForPreparationData, _preparedForNewData);
  }

  @Override
  public void process(A value1, B value2, C value3, D value4, E value5, F value6, G value7, H value8) {
  }
}