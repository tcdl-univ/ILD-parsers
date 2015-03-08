package org.uqbar.thin.parsers

import scala.collection.mutable.Map

case class FinitAutomaton(initialState: Node) {
  
  def apply(word: String): Boolean = {
    initialState(word.toList).isFinal
  }
  
  override def toString = "Automaton"

}

abstract class Node() {
  val transitions: Map[Char, Node] = Map.empty
  
  def ->(char: Char) = {
    transitions.get(char).getOrElse(FailedNode)
  }
  
  def update(char: Char, node: Node) = {
    transitions += char -> node
    this
  }
  
  def apply(word: List[Char]): Node = {
    word match {
      case Nil => this
      case h::t => (this -> h).apply(t)
    }
  }
  
  def isFinal = false
}

case class FinalNode() extends Node {
  override def isFinal = true
}
case class NormalNode() extends Node
case object FailedNode extends Node {
  override def apply(word: List[Char]): Node = this
}

class State