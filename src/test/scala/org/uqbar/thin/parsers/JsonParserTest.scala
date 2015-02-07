package org.uqbar.thin.parsers

class JSonParserTest extends ParserTest with JsonParser {

	"Json Parser" - {

		"should parse" - {

			"booleans" in {
				"false" should beParsedTo(Z(false))(boolean)
				"true" should beParsedTo(Z(true))(boolean)
			}

			"numbers" in {
				"123" should beParsedTo(N(123))(number)
				"123.567" should beParsedTo(N(123.567))(number)

				"123." should not (beParsed()(number))
				".567" should not (beParsed()(number))
			}

			"strings" in {
				""" "" """ should beParsedTo(S(""))(string)
				""" "foo" """ should beParsedTo(S("foo"))(string)

				""" "foo """ should not(beParsed()(string))
				""" foo" """ should not(beParsed()(string))
			}

			"properties" in {
				"""foo: 1""" should beParsedTo(P("foo", N(1)))(property)
				"""foo: "bar"""" should beParsedTo(P("foo", S("bar")))(property)

				"""foo:""" should not(beParsed()(property))
				""":1""" should not(beParsed()(property))
				"""foo: bar""" should not(beParsed()(property))
			}

			"bodies" in {

				""" {} """ should beParsedTo(B(Nil))(body)

				"""
					{
						name: "Douglas",
						answer: 42,
						status: {
							job: "author",
							alive: false
						}
					}
				""" should beParsedTo(
					B(List(
						P("name", S("Douglas")),
						P("answer", N(42)),
						P("status", B(List(
							P("job", S("author")),
							P("alive", Z(false))
						)))
					)))(body)
			}
		}

		//═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════
		// PRETTIER VERSION
		//═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════

		import scala.language.implicitConversions
		implicit def toZ(v: Boolean) = Z(v)
		implicit def toN[T <% Double](v: T) = N(v)
		implicit def toS(v: String) = S(v)
		implicit def toP[T <% V](v: (String, T)) = P(v._1, v._2)
		def $(vs: P*) = B(vs.toList)

		"should parse the same as before, but prettier" - {

			"booleans" in {
				implicit val parser = boolean

				"false" should beParsedTo[Z](false)
				"true" should beParsedTo[Z](true)
			}

			"numbers" in {
				implicit val parser = number

				"123" should beParsedTo[N](123)
				"123.567" should beParsedTo[N](123.567)

				"123." should notBeParsed
				".567" should notBeParsed
			}

			"strings" in {
				implicit val parser = string

				""" "" """ should beParsedTo[S]("")
				""" "foo" """ should beParsedTo[S]("foo")

				""" "foo """ should notBeParsed
				""" foo" """ should notBeParsed
			}

			"properties" in {
				implicit val parser = property

				"""foo: 1""" should beParsedTo[P]("foo" -> 1)
				"""foo: "bar"""" should beParsedTo[P]("foo" -> "bar")

				"""foo:""" should notBeParsed
				""":1""" should notBeParsed
				"""foo: bar""" should notBeParsed
			}

			"bodies" in {
				implicit val parser = body

				""" {} """ should beParsedTo($())

				"""
					{
						name: "Douglas",
						answer: 42,
						status: {
							job: "author",
							alive: false
						}
					}
				""" should beParsedTo(
					$(
						"name" -> "Douglas",
						"answer" -> 42,
						"status" -> $(
							"job" -> "author",
							"alive" -> false
						)
					)
				)
			}
		}
	}
}