<#import "../common.ftl" as c />
<#import "transformer.ftl" as t />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.dag.SimpleDAGExecutor;
import com.linkedin.dagli.preparer.PreparerContext;
import com.linkedin.dagli.preparer.PreparerResultMixed;
import com.linkedin.dagli.transformer.internal.PreparableTransformer${arity}InternalAPI;
import com.linkedin.dagli.util.collection.Iterables;

<#assign preparedTypeBound>N extends <@c.PreparedTransformer arity /></#assign>
<#assign subclass>? extends <@c.PreparableTransformer arity "N" /></#assign>
public interface <@c.PreparableTransformer arity preparedTypeBound /> extends <@c.Transformer arity />, PreparableTransformer<R, N> {

  @Override
  <@c.PreparableTransformerInternalAPI arity "N" subclass /> internalAPI();

  /**
   * Casts a preparable transformer to a "supertype".  This cast is safe due to the semantics of preparable
   * transformers.
   *
   * @param preparable the preparable transformer to cast
<#list 1..arity as index>   * @param <${c.InputGenericArgument(index)}> the new type of input #${index}
</#list>
   * @param <R> the new result type
   * @param <N> the new prepared transformer type
   * @return the provided {@code preparable}, cast to the desired "supertype"
   */
  @SuppressWarnings("unchecked")
  static <<@c.InputGenericArguments arity />, R, ${preparedTypeBound}> PreparableTransformer${arity}<<@c.InputGenericArguments arity />, R, N> cast(PreparableTransformer${arity}<<@c.SuperInputGenericArguments arity />, ? extends R, ? extends N> preparable) {
    // safe due to semantics of preparable transformers:
    return (PreparableTransformer${arity}<<@c.InputGenericArguments arity />, R, N>) preparable;
  }

  /**
   * Casts a preparable transformer to a "supertype" with an unknown type of prepared transformer.  This cast is safe
   * due to the semantics of preparable transformers.
   *
   * @param preparable the preparable transformer to cast
<#list 1..arity as index>   * @param <${c.InputGenericArgument(index)}> the new type of input #${index}
</#list>
   * @param <R> the new result type
   * @return the provided {@code preparable}, cast to the desired "supertype"
   */
  @SuppressWarnings("unchecked")
  static <<@c.InputGenericArguments arity />, R> PreparableTransformer${arity}<<@c.InputGenericArguments arity />, R, <@c.PreparedTransformer arity />> castWithGenericPrepared(PreparableTransformer${arity}<<@c.SuperInputGenericArguments arity />, ? extends R, ?> preparable) {
    // safe due to semantics of preparable transformers:
    return (PreparableTransformer${arity}<<@c.InputGenericArguments arity />, R, <@c.PreparedTransformer arity />>) preparable;
  }

  /**
   * Prepares a preparable transformer and returns the result (which includes the prepared transformer for both the
   * "preparation" data (in this case, the values passed to this method) and "new" data.
   *
   * @param preparable the transformer to prepare
<#list 1..arity as index>   * @param values${index} the values for the ${c.positionNames[index]} input to the transformer for each example
</#list>
<@c.Indent 1><@c.GenericInputTypesJavadoc arity /></@c.Indent>
   * @param <R> the type of the result of the prepared transformers
   * @param <N> the type of the prepared transformer ("for new data")
   * @return a {@link PreparerResultMixed} containing the prepared transformers for "preparation data" (the provided
   *         values)
   */
  @SuppressWarnings("unchecked")
  static <<@c.InputGenericArguments arity />, R, ${preparedTypeBound}> PreparerResultMixed<<@c.PreparedTransformer arity />, N> prepare(
      <@c.PreparableTransformer arity "N" /> preparable, <@c.ValuesArguments "Iterable" arity />) {
    return (PreparerResultMixed<<@c.PreparedTransformer arity />, N>) preparable.internalAPI()
        .prepare(PreparerContext.builder(Iterables.size64(values1)).setExecutor(new SimpleDAGExecutor()).build(),
            <#list 1..arity as index>values${c.InputSuffix(index)}<#sep>, </#list>);
  }
}