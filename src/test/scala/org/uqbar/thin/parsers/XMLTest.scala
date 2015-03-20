package org.uqbar.thin.parsers

class XtetsTest extends ParserTest with MyXMLParser {

  "dbhw Parser" - {

    "should parse" - {

      "empty expression" in {
        """<note >
            <to>Tove</to>
            <from>Jani</from>
            <heading>Reminder</heading>
            <body>Don't forget me this weekend!</body>
            </note>""" should beParsedTo(XMLNode(List(Node("note",List(),List(Leaf("to",List(),Some(List("Tove"))), Leaf("from",List(),Some(List("Jani"))), Leaf("heading",List(),Some(List("Reminder"))), Leaf("body",List(),Some(List("Don't", "forget", "me", "this", "weekend!"))))))))(xml) 
      }

    }
  }
}