/*
 * Copyright 2019 Confluent Inc.
 *
 * Licensed under the Confluent Community License; you may not use this file
 * except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.execution.plan;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import io.confluent.ksql.GenericRow;
import java.util.List;
import java.util.Objects;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

@Immutable
public class StreamToTable<K> implements ExecutionStep<KTable<K, GenericRow>> {
  private final ExecutionStep<KStream<K, GenericRow>> source;
  private final Formats formats;
  private final ExecutionStepProperties properties;

  public StreamToTable(
      final ExecutionStep<KStream<K, GenericRow>> source,
      final Formats formats,
      final ExecutionStepProperties properties) {
    this.source = Objects.requireNonNull(source, "source");
    this.formats = Objects.requireNonNull(formats, "formats");
    this.properties = Objects.requireNonNull(properties, "properties");
  }

  @Override
  public ExecutionStepProperties getProperties() {
    return properties;
  }


  @Override
  public List<ExecutionStep<?>> getSources() {
    return ImmutableList.of(source);
  }

  @Override
  public KTable<K, GenericRow> build(final StreamsBuilder builder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final StreamToTable<?> that = (StreamToTable<?>) o;
    return Objects.equals(source, that.source)
        && Objects.equals(formats, that.formats)
        && Objects.equals(properties, that.properties);
  }

  @Override
  public int hashCode() {

    return Objects.hash(source, formats, properties);
  }
}
