package org.uqbar.thin.parsers

import scala.util.parsing.combinator._

object MyXMLParser extends MyXMLParser
trait MyXMLParser extends RegexParsers {
  
  trait Element
  case class XMLNode(nodes: List[Element]) extends Element
  case class Node(id: String, attrs: List[String], children: List[Element]) extends Element
  case class Leaf(id: String, attrs: List[String], body: Option[List[String]]) extends Element

  protected lazy val attribute = regex("""[A-Za-z0-9'!+.]+""".r)
  protected lazy val id = attribute
  protected lazy val body = attribute.*  

  protected lazy val node = ("<" ~> id ~ (" " ~> attribute).* <~ ">") ~ innernode.* <~ ("</" ~> id <~ ">") ^^ { case id ~ attribs ~ children => Node(id, attribs, children) }
  protected lazy val leaf = ("<" ~> id ~ (" " ~> attribute).* <~ ">") ~ body.? <~ ("</" ~> id <~ ">") ^^ { case id ~ attribs ~ body => Leaf(id, attribs, body) }
  protected lazy val innernode: Parser[Element] = leaf | node
  protected lazy val xml = innernode.* ^^ XMLNode

  
  def apply(input: String) = parseAll(xml, input) match {
    case Success(result, _) => result
    case NoSuccess(msg, _)  => throw ParseException(msg)
  }

}