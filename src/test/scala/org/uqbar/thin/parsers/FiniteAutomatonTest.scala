package org.uqbar.thin.parsers

import org.scalatest.FreeSpec
import org.scalatest.Matchers
import scala.collection.mutable.Map

class FiniteAutomatonTest extends FreeSpec with Matchers {

  "FiniteAutomaton" - {

    def normalNode = NormalNode()
    def finalNode = FinalNode()

    "abAutomaton should" -{
//      val nodeA = NormalNode()
//      val nodeB = NormalNode()
//      nodeA('a') = nodeB
//      nodeB('b') = FinalNode()
//      val abAutomaton = FinitAutomaton(nodeA)
      
      val nodeA = normalNode('a') = normalNode('b') = finalNode
      val abAutomaton = FinitAutomaton(nodeA)
      
      "ab matches" in {
    		abAutomaton("ab") should be(true)
    	}
    	
    	"aab not matches" in {
    		abAutomaton("aab") should be(false)
    	}
    }
    
    "abInfiniteAutomaton should" - {
      val initialNode = finalNode
      initialNode('a') = initialNode('b') = initialNode
      val abInfiniteAutomaton = FinitAutomaton(initialNode)
      
      "bababababab matches" in {
        abInfiniteAutomaton("bababababab") should be(true)
      }
      
      "bac not matches" in {
        abInfiniteAutomaton("bac") should be(false)
      }
      
    }
  }

}