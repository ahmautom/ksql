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

package io.confluent.ksql.parser.tree;

import javax.annotation.Nullable;

public abstract class AstVisitor<R, C> {

  public R process(final AstNode node, @Nullable final C context) {
    return node.accept(this, context);
  }

  protected R visitNode(final AstNode node, final C context) {
    return null;
  }

  protected R visitStatements(final Statements node, final C context) {
    return visitNode(node, context);
  }

  protected R visitStatement(final Statement node, final C context) {
    return visitNode(node, context);
  }

  protected R visitQuery(final Query node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitExplain(final Explain node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitShowColumns(final ShowColumns node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitShowFunctions(final ListFunctions node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitSelect(final Select node, final C context) {
    return visitNode(node, context);
  }

  protected R visitRelation(final Relation node, final C context) {
    return visitNode(node, context);
  }

  protected R visitSelectItem(final SelectItem node, final C context) {
    return visitNode(node, context);
  }

  protected R visitSingleColumn(final SingleColumn node, final C context) {
    return visitSelectItem(node, context);
  }

  protected R visitAllColumns(final AllColumns node, final C context) {
    return visitSelectItem(node, context);
  }

  protected R visitTable(final Table node, final C context) {
    return visitRelation(node, context);
  }

  protected R visitAliasedRelation(final AliasedRelation node, final C context) {
    return visitRelation(node, context);
  }

  protected R visitJoin(final Join node, final C context) {
    return visitRelation(node, context);
  }

  protected R visitWithinExpression(final WithinExpression node, final C context) {
    return visitNode(node, context);
  }

  protected R visitWindowExpression(final WindowExpression node, final C context) {
    return visitNode(node, context);
  }

  protected R visitKsqlWindowExpression(final KsqlWindowExpression node, final C context) {
    return visitNode(node, context);
  }

  protected R visitTumblingWindowExpression(final TumblingWindowExpression node, final C context) {
    return visitKsqlWindowExpression(node, context);
  }

  protected R visitHoppingWindowExpression(final HoppingWindowExpression node, final C context) {
    return visitKsqlWindowExpression(node, context);
  }

  protected R visitSessionWindowExpression(final SessionWindowExpression node, final C context) {
    return visitKsqlWindowExpression(node, context);
  }

  protected R visitTableElement(final TableElement node, final C context) {
    return visitNode(node, context);
  }

  protected R visitCreateStream(final CreateStream node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitCreateStreamAsSelect(final CreateStreamAsSelect node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitCreateTable(final CreateTable node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitCreateTableAsSelect(final CreateTableAsSelect node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitInsertInto(final InsertInto node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitInsertValues(final InsertValues node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitDropStream(final DropStream node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitDropTable(final DropTable node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitGroupBy(final GroupBy node, final C context) {
    return visitNode(node, context);
  }

  protected R visitGroupingElement(final GroupingElement node, final C context) {
    return visitNode(node, context);
  }

  protected R visitSimpleGroupBy(final SimpleGroupBy node, final C context) {
    return visitGroupingElement(node, context);
  }

  protected R visitTerminateQuery(final TerminateQuery node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitListStreams(final ListStreams node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitListTables(final ListTables node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitUnsetProperty(final UnsetProperty node, final C context) {
    return visitStatement(node, context);
  }

  protected R visitSetProperty(final SetProperty node, final C context) {
    return visitStatement(node, context);
  }
}
