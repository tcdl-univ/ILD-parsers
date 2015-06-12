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

      "Simple HTML test" in {
        """<html>
            <head>
            <title>Page Title</title>
            </head>
            <body>
            
            <h1>This is a Heading</h1>
            <p>This is a paragraph.</p>
            
            </body>
            </html>""" should beParsedTo(XMLNode(List(Node("html",List(),List(Node("head",List(),List(Leaf("title",List(),Some(List("Page", "Title"))))), Node("body",List(),List(Leaf("h1",List(),Some(List("This", "is", "a", "Heading"))), Leaf("p",List(),Some(List("This", "is", "a", "paragraph."))))))))))(xml)
      }
    }
  }
}