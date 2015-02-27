package org.uqbar.thin.parsers

class SExpressionTest extends ParserTest with SExpressionsParser {

  "SExpression Parser" - {

    "should parse" - {

      "empty expression" in {
        "()" should beParsedTo(SExpression(List()))(sexpression)
      }

      "simple sum expression" in {
        "(+ 3 4)" should beParsedTo(SExpression(List(SIdentifier("+"), SInt(3), SInt(4))))(sexpression)
      }
      
      "simple recursive expression" in {
        "(+ 3 (* 3 1))" should beParsedTo(SExpression(List(SIdentifier("+"), SInt(3), SExpression(List(SIdentifier("*"), SInt(3), SInt(1))))))(sexpression)
      }
    }
  }
}