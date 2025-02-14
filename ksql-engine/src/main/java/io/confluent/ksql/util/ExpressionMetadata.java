/*
 * Copyright 2018 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.util;

import io.confluent.ksql.GenericRow;
import io.confluent.ksql.execution.expression.tree.Expression;
import io.confluent.ksql.function.udf.Kudf;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.kafka.connect.data.Schema;
import org.codehaus.commons.compiler.IExpressionEvaluator;

public class ExpressionMetadata {

  private final IExpressionEvaluator expressionEvaluator;
  private final List<Integer> indexes;
  private final List<Kudf> udfs;
  private final Schema expressionType;
  private final GenericRowValueTypeEnforcer typeEnforcer;
  private final ThreadLocal<Object[]> threadLocalParameters;
  private final Expression expression;

  public ExpressionMetadata(
      final IExpressionEvaluator expressionEvaluator,
      final List<Integer> indexes,
      final List<Kudf> udfs,
      final Schema expressionType,
      final GenericRowValueTypeEnforcer typeEnforcer,
      final Expression expression) {
    this.expressionEvaluator = Objects.requireNonNull(expressionEvaluator, "expressionEvaluator");
    this.indexes = Collections.unmodifiableList(Objects.requireNonNull(indexes, "indexes"));
    this.udfs = Collections.unmodifiableList(Objects.requireNonNull(udfs, "udfs"));
    this.expressionType = Objects.requireNonNull(expressionType, "expressionType");
    this.typeEnforcer = Objects.requireNonNull(typeEnforcer, "typeEnforcer");
    this.expression = Objects.requireNonNull(expression, "expression");
    this.threadLocalParameters = ThreadLocal.withInitial(() -> new Object[indexes.size()]);
  }

  public List<Integer> getIndexes() {
    return indexes;
  }

  public List<Kudf> getUdfs() {
    return udfs;
  }

  public Schema getExpressionType() {
    return expressionType;
  }

  public Expression getExpression() {
    return expression;
  }

  public Object evaluate(final GenericRow row) {
    try {
      return expressionEvaluator.evaluate(getParameters(row));
    } catch (InvocationTargetException e) {
      throw new KsqlException(e.getCause().getMessage(), e.getCause());
    }
  }

  private Object[] getParameters(final GenericRow row) {
    final Object[] parameters = this.threadLocalParameters.get();
    for (int idx = 0; idx < indexes.size(); idx++) {
      final int paramIndex = indexes.get(idx);
      if (paramIndex < 0) {
        parameters[idx] = udfs.get(idx);
      } else {
        parameters[idx] = typeEnforcer
            .enforceFieldType(paramIndex, row.getColumns().get(paramIndex));
      }
    }
    return parameters;
  }
}
