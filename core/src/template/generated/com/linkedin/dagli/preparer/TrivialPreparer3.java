// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the preparer/TrivialPreparerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.preparer;

import com.linkedin.dagli.transformer.PreparedTransformer3;


/**
 * Trivial "preparer" that simply returns a provided, prepared transformer of arity 3.
 * @param <R> the type of result produced by the prepared transformer.
 * @param <N> the type of prepared transformer that will be returned
 */
public class TrivialPreparer3<A, B, C, R, N extends PreparedTransformer3<A, B, C, R>> extends
    AbstractStreamPreparer3<A, B, C, R, N> {
  private final N _preparedForNewData;
  private final PreparedTransformer3<? super A, ? super B, ? super C, ? extends R> _preparedForPreparationData;

  /**
   * Creates a new instance that will "prepare" the provided transformer.
   *
   * @param prepared the transformer which will result from this preparer.
   */
  public TrivialPreparer3(N prepared) {
    this(prepared, prepared);
  }

  /**
     * Creates a new instance that will "prepare" the provided transformers.
     *
     * @param preparedForPreparationData the transformer to be used for preparation data
     * @param preparedForNewData the transformer to be used for new data
     */
  public TrivialPreparer3(
      PreparedTransformer3<? super A, ? super B, ? super C, ? extends R> preparedForPreparationData,
      N preparedForNewData) {
    _preparedForNewData = preparedForNewData;
    _preparedForPreparationData = preparedForPreparationData;
  }

  @Override
  public PreparerResultMixed<? extends PreparedTransformer3<? super A, ? super B, ? super C, ? extends R>, N> finish() {
    return new PreparerResultMixed<>(_preparedForPreparationData, _preparedForNewData);
  }

  @Override
  public void process(A value1, B value2, C value3) {
  }
}
