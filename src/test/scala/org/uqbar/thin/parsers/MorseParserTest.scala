package org.uqbar.thin.parsers

import org.scalatest.FreeSpec
import org.scalatest.Matchers

class MorseParserTest extends FreeSpec with Matchers with MorseParser {

	"Morse Parser" - {
		"should parse" - {
			"letters" in {
				for ((morse, latin) <- morseEquivalences) this(morse) should be(latin.toString)
			}

			"words" in {
				this("..- --.- -... .- .-.") should be("UQBAR")
			}

			"texts" in {
				this(".... . .-.. .-.. --- ; ..- --.- -... .- .-.") should be("HELLO UQBAR")
			}
		}
	}
}