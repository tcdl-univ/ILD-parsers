package org.uqbar.thin.parsers

import scala.util.parsing.combinator._

object SExpressionsParser extends SExpressionsParser
trait SExpressionsParser extends RegexParsers {

  trait Node
  case class SExpression(list: List[Node]) extends Node
  case class SInt(i: Int) extends Node
  case class SIdentifier(s: String) extends Node

  protected lazy val int = "[0-9]+".r ^^ { i => SInt(i.toInt) }
  protected lazy val identifier = regex("""[A-Za-z+-/\*]+""".r) ^^ { si => SIdentifier(si) }
  protected lazy val sexpression = "(" ~> rep(node) <~ ")" ^^ SExpression
  protected lazy val node: Parser[Node] = int | identifier | sexpression

  def apply(input: String) = parseAll(sexpression, input) match {
    case Success(result, _) => result
    case NoSuccess(msg, _)  => throw ParseException(msg)
  }

}