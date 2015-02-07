package org.uqbar.thin.parsers

import scala.language.implicitConversions
import scala.util.parsing.combinator._

object MorseParser extends MorseParser
trait MorseParser extends RegexParsers {
	def apply(input: String) = parseAll(text, input) match {
		case Success(result, _) => result
		case NoSuccess(msg, _) => throw ParseException(msg)
	}

	protected val morseEquivalences = Map(
		".-" -> 'A',
		"-..." -> 'B',
		"-.-." -> 'C',
		"-.." -> 'D',
		"." -> 'E',
		"..-." -> 'F',
		"--." -> 'G',
		"...." -> 'H',
		".." -> 'I',
		".---" -> 'J',
		"-.-" -> 'K',
		".-.." -> 'L',
		"--" -> 'M',
		"-." -> 'N',
		"---" -> 'O',
		".--." -> 'P',
		"--.-" -> 'Q',
		".-." -> 'R',
		"..." -> 'S',
		"-" -> 'T',
		"..-" -> 'U',
		"...-" -> 'V',
		".--" -> 'W',
		"-..-" -> 'X',
		"-.--" -> 'Y',
		"--.." -> 'Z',
		".----" -> '1',
		"..---" -> '2',
		"...--" -> '3',
		"....-" -> '4',
		"....." -> '5',
		"-...." -> '6',
		"--..." -> '7',
		"---.." -> '8',
		"----." -> '9',
		"-----" -> '0'
	)

	protected lazy val text = repsep(word, ";") ^^ { _.mkString(" ") }
	protected lazy val word = letter.+ ^^ { _.mkString }
	protected lazy val letter = "([-.]+)".r ^^ morseEquivalences
}