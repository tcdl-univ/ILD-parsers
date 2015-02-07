package org.uqbar.thin.parsers

import scala.util.parsing.combinator._

object XMLParser extends XMLParser
trait XMLParser extends RegexParsers {
	def apply(input: String) = parseAll(???, input) match {
		case Success(result, _) => result
		case NoSuccess(msg, _) => throw ParseException(msg)
	}
}