package org.uqbar.thin.parsers

import scala.util.parsing.combinator._

object JsonParser extends JsonParser
trait JsonParser extends RegexParsers {

	trait V
	case class P(key: String, value: V)
	case class N(value: Double) extends V
	case class S(value: String) extends V
	case class Z(value: Boolean) extends V
	case class B(properties: List[P]) extends V

	def apply(input: String) = parseAll(body, input) match {
		case Success(result, _) => result
		case NoSuccess(msg, _) => throw ParseException(msg)
	}

	protected lazy val body = "{" ~> repsep(property, ",") <~ "}" ^^ B
	protected lazy val property: Parser[P] = (propertyKey <~ ":") ~ propertyValue ^^ { case k ~ v => P(k, v) }
	protected lazy val propertyKey = "[a-zA-Z0-9_]+".r
	protected lazy val propertyValue = number | string | boolean | body
	protected lazy val number = """\d+(\.\d+)?""".r ^^ { n => N(n.toDouble) }
	protected lazy val string = "\"".r ~> """[^"]*""".r <~ "\"".r ^^ S
	protected lazy val boolean = ("false" | "true") ^^ { z => Z(z.toBoolean) }

}