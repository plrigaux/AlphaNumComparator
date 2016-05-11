package com.plr.comparator.natural
/*
 * This Spock specification was auto generated by running the Gradle 'init' task
 * by 'plr' at '4/23/16 9:04 PM' with Gradle 2.12
 *
 * @author plr, @date 4/23/16 9:04 PM
 */

import static com.plr.comparator.natural.CompType.*
import spock.lang.Specification
import static com.plr.comparator.natural.NaturalComparator.Flags.*

import com.plr.comparator.natural.NaturalComparator;
import com.plr.comparator.natural.NumberTokenComparable;
import com.plr.comparator.natural.TokenComparable;
import com.plr.comparator.natural.Tokenizer;


class NaturalComparatorTest extends Specification {

	def "Pure numbers" () {
		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(REAL_NUMBER);
		Comparator<String> comp = naturalComparator.reversed();
		comp.reversed();
		def expected = [
			"-123",
			"-12",
			"-1.1532456",
			"-1.15",
			"-1.1" ,
			"-1",
			"-0.99999999999",
			"-0.99",
			"-0.15",
			"-0.1",
			"0",
			"0.1",
			"0.15",
			"1",
			"1.1",
			"1.15",
			"11.1",
			"11.67",
			"11.6756745674",
			"11.68",
			"12",
			"123"
		]

		def list = []

		list.addAll(expected)

		Collections.shuffle(list)

		when: "Do nothing"

		then: "The list aren't equal"
		list != expected

		when: "Sorting list"
		Collections.sort(list, naturalComparator)

		then: "The list are equal"

		println list
		println expected

		for(int i = 0; i < list.size(); i++) {
			list[i] == expected[i]
		}

		list == expected
	}

	def "Pure numbers comparison"() {

		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(REAL_NUMBER);

		expect:

		naturalComparator.compare(smaller, bigger) < 0
		naturalComparator.compare(bigger , smaller) > 0

		where:

		smaller 		| bigger
		"-123"			| "-1.15"
		"-1.1532456"	| "-1.15"
		"123"			| "1234"
		"-1.1532456"	| "-1"
		"-123"			| "-0.1"
		"-123"			| "-1.1"
		"-0.99"			| "-0.15"
		"-1.1532456"	| "-0.99"
	}


	def "test sort list" () {
		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator();


		def list = [
			"z1.doc",
			"z10.doc",
			"z100.doc",
			"z101.doc",
			"z102.doc",
			"z11.doc",
			"z12.doc",
			"z13.doc",
			"z14.doc",
			"z15.doc",
			"z16.doc",
			"z17.doc",
			"z18.doc",
			"z19.doc",
			"z2.doc",
			"z20.doc",
			"z3.doc",
			"z4.doc",
			"z5.doc",
			"z6.doc",
			"z7.doc",
			"z8.doc",
			"z9.doc",
		]

		def expected = [
			"z1.doc",
			"z2.doc",
			"z3.doc",
			"z4.doc",
			"z5.doc",
			"z6.doc",
			"z7.doc",
			"z8.doc",
			"z9.doc",
			"z10.doc",
			"z11.doc",
			"z12.doc",
			"z13.doc",
			"z14.doc",
			"z15.doc",
			"z16.doc",
			"z17.doc",
			"z18.doc",
			"z19.doc",
			"z20.doc",
			"z100.doc",
			"z101.doc",
			"z102.doc",
		]



		when: "Do nothing"

		then: "The list aren't equal"
		list != expected

		when: "Sorting list"

		println list
		Collections.sort(list, naturalComparator)
		println list

		then: "The list are equal"
		list == expected
	}

	def "test sort list ignore case" () {
		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(NaturalComparator.CASE_INSENSITIVE);

		def expected = [
			"zo1.doc",
			"Zo2.doc",
			"Zo3.doc",
			"zo4.doc",
			"Zo5.doc",
			"zo6.doc",
			"zo7.doc",
			"zo8.doc",
			"Zo9.doc",
			"zO10.doc",
			"zo11.doc",
			"zo12.doc",
			"Zo13.doc",
			"zo14.doc",
			"zo15.doc",
			"zO16.doc",
			"zo17.doc",
			"Zo18.doc",
			"zo19.doc",
			"ZO20.doc",
			"Zo100.doc",
			"zo101.doc",
			"Zo102.doc",
		]

		def list = []

		list.addAll(expected)

		Collections.shuffle(list)

		when: "Do nothing"

		then: "The list aren't equal"
		list != expected

		when: "Sorting list"

		//println list
		Collections.sort(list, naturalComparator)
		//println list

		then: "The list are equal"
		list == expected
	}

	def "product names sort"() {
		given:

		NaturalComparator naturalComparator = NaturalComparator.getComparator();

		def list = [
			"1000X Radonius Maximus",
			"10X Radonius",
			"200X Radonius",
			"20X Radonius",
			"20X Radonius Prime",
			"30X Radonius",
			"40X Radonius",
			"Allegia 50 Clasteron",
			"Allegia 500 Clasteron",
			"Allegia 50B Clasteron",
			"Allegia 51 Clasteron",
			"Allegia 6R Clasteron",
			"Alpha 100",
			"Alpha 2",
			"Alpha 200",
			"Alpha 2A",
			"Alpha 2A-8000",
			"Alpha 2A-900",
			"Callisto Morphamax",
			"Callisto Morphamax 500",
			"Callisto Morphamax 5000",
			"Callisto Morphamax 600",
			"Callisto Morphamax 6000 SE",
			"Callisto Morphamax 6000 SE2",
			"Callisto Morphamax 700",
			"Callisto Morphamax 7000",
			"Xiph Xlater 10000",
			"Xiph Xlater 2000",
			"Xiph Xlater 300",
			"Xiph Xlater 40",
			"Xiph Xlater 5",
			"Xiph Xlater 50",
			"Xiph Xlater 500",
			"Xiph Xlater 5000",
			"Xiph Xlater 58"
		]

		def expected = [
			"10X Radonius",
			"20X Radonius",
			"20X Radonius Prime",
			"30X Radonius",
			"40X Radonius",
			"200X Radonius",
			"1000X Radonius Maximus",
			"Allegia 6R Clasteron",
			"Allegia 50 Clasteron",
			"Allegia 50B Clasteron",
			"Allegia 51 Clasteron",
			"Allegia 500 Clasteron",
			"Alpha 2",
			"Alpha 2A",
			"Alpha 2A-900",
			"Alpha 2A-8000",
			"Alpha 100",
			"Alpha 200",
			"Callisto Morphamax",
			"Callisto Morphamax 500",
			"Callisto Morphamax 600",
			"Callisto Morphamax 700",
			"Callisto Morphamax 5000",
			"Callisto Morphamax 6000 SE",
			"Callisto Morphamax 6000 SE2",
			"Callisto Morphamax 7000",
			"Xiph Xlater 5",
			"Xiph Xlater 40",
			"Xiph Xlater 50",
			"Xiph Xlater 58",
			"Xiph Xlater 300",
			"Xiph Xlater 500",
			"Xiph Xlater 2000",
			"Xiph Xlater 5000",
			"Xiph Xlater 10000"
		]

		when: "Do nothing"

		then: "The list aren't equal"
		list != expected

		when: "Sorting list"
		Collections.sort(list, naturalComparator)

		then: "The list are equal"
		println "Sorted:   " + list
		println "Expected: " + expected



		list.eachWithIndex { item, index ->
			def exp =  expected[index]
			boolean b = item == exp
			println "$b $index '$item' ----- $exp"
		}

		list == expected
	}

	def "Sort list"() {

		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(REAL_NUMBER, SECONDARY);
		def expected = [
			"1-2",
			"1-02",
			"1-20",
			"10-20",
			"fred",
			"jane",
			"pic01",
			"pic2",
			"pic02",
			"pic02a",
			"pic3",
			"pic4",
			"pic 4 else",
			"pic 5",
			"pic 5",
			"pic05",
			"pic 5 something",
			"pic 6",
			"pic   7",
			"pic100",
			"pic100a",
			"pic120",
			"pic121",
			"pic02000",
			"tom",
			"x2-g8",
			"x2-y7",
			"x2-y08",
			"x8-y8"
		]


		def list = []

		list.addAll(expected)


		Collections.shuffle(list)

		when: "Do nothing"

		then: "The list aren't equal"
		list != expected

		when: "Sorting list"
		Collections.sort(list, naturalComparator)

		then: "The list are equal"

		println "Sorted:   " + list
		println "Expected: " + expected

		list.eachWithIndex { item, index ->
			def exp =  expected[index]
			boolean b = item == exp
			println "$b $index '$item' ----- $exp"
		}

		list == expected
	}
	
	def "Mutiple cases primary vs secondary"() {
		given:

		NaturalComparator naturalComparatorP = NaturalComparator.getComparator(PRIMARY);
		NaturalComparator naturalComparatorS = NaturalComparator.getComparator(SECONDARY);
				
		expect:

		compToZero (naturalComparatorP, first, second, comparison) == true
		compToZero (naturalComparatorS, first, second, comparison2) == true

				
		where:
		
		first		|	second 		| comparison 	| comparison2	
		"1-2"		| "1-02"		| EQUAL			| LESS			
		"1- 2"		| "1-02" 		| EQUAL			| LESS		
		"1-02"		| "1- 2" 		| EQUAL			| GREATER	
		"1-02"		| "1- 2 Tail" 	| LESS			| LESS
		"1-2 Tail"	| "1- 2" 		| GREATER		| GREATER
		"Doc    5"	| "Doc 5" 		| EQUAL			| GREATER
	

	}

	boolean compToZero(NaturalComparator naturalComparator, CharSequence s1, CharSequence s2, CompType compType) {

		int val = naturalComparator.compare(s1, s2)

		switch (compType) {
			case GREATER:
			return val > 0
			case LESS:
			return val < 0
			case EQUAL:
			return val == 0
		}
	}

	def "Mutiple cases"() {

		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(REAL_NUMBER, SECONDARY);

		expect:

		compToZero (naturalComparator, first, second, comparison) == true

		where:

		first 					| second 					| comparison
		"doc20.doc" 			| "doc10.doc" 				| GREATER
		"doc10.doc"				| "doc20.doc" 				| LESS
		"doc2.doc"				| "doc10.doc" 				| LESS
		"doc2.1.doc"			| "doc2.2.doc"				| LESS
		"doc2.10.doc"			| "doc2.2.doc"				| LESS
		"20"					| "10"						| GREATER
		"2"						| "10"						| LESS
		"-20"					| "10"						| LESS
		"-20"					| "-10"						| LESS
		"pic05"					| "pic 5"					| GREATER
		"pic02000"				| "pic2"					| GREATER
		"1-2"					| "1-02"					| LESS

		"z02.doc" 				| "z1.doc" 					| GREATER
		"pic01" 				| "pic2" 					| LESS
		"pic05"					| "pic 5 something"			| LESS
		"1-2"					| "1-02"					| LESS
		"1-2 Pizza"				| "1-02"					| GREATER
		//TODO introduce quasi number concept
		"Allegia 50 Clasteron" 	| "Allegia 50B Clasteron" 	| LESS
		"1-2 Pizza"				| "1- 2"					| GREATER
	}

	def "Mutiple cases2"() {


		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator();

		expect:

		compToZero (naturalComparator, "z1.doc", "z11.doc", LESS) == true
		compToZero (naturalComparator, "z11.doc", "z1.doc", GREATER) == true

		compToZero (naturalComparator, "z1.doc", "z11.doc", GREATER) == false
		compToZero (naturalComparator, "z11.doc", "z1.doc", LESS) == false


		compToZero (naturalComparator, "z10.doc", "z11.doc", LESS) == true
		compToZero (naturalComparator, "z11.doc", "z10.doc", GREATER) == true
	}

	def "Mutiple cases Numbers"() {
		given:
		
		NaturalComparator nc = NaturalComparator.getComparator(NaturalComparator.ASCII);

		NumberTokenComparable nt1 = new NumberTokenComparable(smaller, nc);
		NumberTokenComparable nt2 = new NumberTokenComparable(bigger, nc);

		expect:

		nt1.compareTo(nt2) < 0
		nt2.compareTo(nt1) > 0

		where:

		smaller		|	bigger
		"10"		| "11"
		"10.12"		| "10.123"
		"-10.12"	| "10.123"
		"-10.123"	| "-10.12"
		"-10.123"	| "10.12"
	}


	def "Mutiple white space"() {
		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(REAL_NUMBER, PRIMARY);


		expect:

		naturalComparator.compare(smaller, bigger) == 0

		where:

		smaller		|	bigger
		"   10"		| "      10"
		"10"		| "      10"
		"   -10"	| "      -10"
		"-10"		| "      -10"
		"-10    "	| "-10"
		"   -10  "	| "      -10\t\t"
		"   -10.3"	| "      -10.3\t"
		"   -10.3"	| "      -10.3 "
		"   -10.3"	| "      -10.3\t   "
		"-10.30"	| "-10.3"
		"-10.30"	| "-10.3 "
		"10.300 "	| "10.3"
	}

	def "Dev test"() {

		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(REAL_NUMBER, SECONDARY);


		when: "Do nothing"

		Tokenizer tk = new Tokenizer(naturalComparator, first);

		def list = []


		TokenComparable tc;

		while((tc = tk.getNext()) != null) {
			list << tc
		}


		def list2 = []

		list.each { token ->
			list2 << token.getStr().toString();
		}

		println "$first -> $list"

		then:

		list2 == expected

		where:

		first 				| expected
		"doc20.doc" 		| ["doc", "20", ".doc" ]
		"doc10.doc"			| ["doc", "10", ".doc" ]
		"doc2.doc"			| ["doc", "2", ".doc" ]
		"doc2.1.doc"		| ["doc", "2.1", ".doc" ]
		"doc2.10.doc"		| ["doc", "2.10", ".doc" ]//pure numbers
		"20"				| ["20"]
		"2"					| ["2"]
		"-20"				| ["-20"]
		" -40"				| [" -40"]
		"-20.234"			| ["-20.234"]

		//comma or hyphen in string
		"asdf-20.234"		| ["asdf-", "20.234"]
		"asdf-20.234asdf"	| ["asdf-", "20.234", "asdf"]
		"TEST20.23.4.8asdf"	| [
			"TEST",
			"20",
			".",
			"23",
			".",
			"4.8",
			"asdf"
		]
		"TEST20-23-4-8asdf"	| [
			"TEST",
			"20",
			"-",
			"23",
			"-",
			"4",
			"-",
			"8",
			"asdf"
		]

		//zeros
		"03.50"				| ["03.50"]
		"00003"				| ["00003"]
		"0.3000"			| ["0.3000"]

		//spaces
		"pics 5"			| ["pics", " 5"]
		"pics    5"			| ["pics", "    5"]
		"pics 5 test"		| ["pics", " 5", " test"]
		"pics    5 6"		| ["pics", "    5", " 6"]
		"pics    5 6 "		| ["pics", "    5", " 6", " "]
		"pics"				| ["pics"]


	}

	def "Ignore white space"() {
		given:
		NaturalComparator naturalComparator = NaturalComparator.getComparator(SPACE_INSENSITVE);

		expect:

		naturalComparator.compare(smaller, bigger) == 0

		where:

		smaller		|	bigger


		"Ab Cd"		| "Ab\n\r\tCd   "
		" ab Cd"	| "  a b" + System.getProperty("line.separator") + "Cd "
		"Ab Cd"		| "Ab\n\r\tCd   "
		"Ab56 Cd"		| "Ab\n\r\t56Cd   "
	}


	def "No decimal"() {

		given:
		
		
		Comparator<CharSequence> naturalComparator1 =  NaturalComparator.getComparator();
		NaturalComparator naturalComparator2 =  NaturalComparator.getComparator(RATIONAL_NUMBER)
		NaturalComparator naturalComparator3 =  NaturalComparator.getComparator(SECONDARY, RATIONAL_NUMBER);
		NaturalComparator naturalComparator4 =  NaturalComparator.getComparator(NEGATIVE_NUMBER);
		NaturalComparator naturalComparator5 =  NaturalComparator.getComparator(RATIONAL_NUMBER, NEGATIVE_NUMBER);
		NaturalComparator naturalComparator6 =  NaturalComparator.getComparator(SECONDARY, RATIONAL_NUMBER, NEGATIVE_NUMBER);
		
		expect:

		compToZero (naturalComparator1, first, second, comparison) == true
		compToZero (naturalComparator2, first, second, comparison2) == true
		compToZero (naturalComparator3, first, second, comparison3) == true
		compToZero (naturalComparator4, first, second, comparison4) == true
		compToZero (naturalComparator5, first, second, comparison5) == true
		compToZero (naturalComparator6, first, second, comparison6) == true
		
		where:

		first		|	second 	| comparison 	| comparison2	| comparison3 	| comparison4	| comparison5 	| comparison6
		"11"		| "11"	 	| EQUAL			| EQUAL			| EQUAL			| EQUAL			| EQUAL			| EQUAL
		"111"		| "11"	 	| GREATER		| GREATER		| GREATER		| GREATER		| GREATER		| GREATER
		"111"		| "4444" 	| LESS			| LESS			| LESS			| LESS			| LESS			| LESS
		"10.1"		| "10.10" 	| LESS	 		| EQUAL			| LESS			| LESS			| EQUAL			| LESS
		"10.123"	| "10.2" 	| GREATER 		| LESS			| LESS			| GREATER		| LESS			| LESS
		"-10.10"	| "-10.1" 	| GREATER 		| EQUAL			| GREATER		| GREATER		| EQUAL			| GREATER
		"-10.2"		| "-10.123" | LESS 			| GREATER		| GREATER		| LESS			| LESS			| LESS
		"doc 2"		| "doc02" 	| EQUAL			| EQUAL			| LESS			| EQUAL			| EQUAL			| LESS
		"doc  2"	| "doc 2" 	| EQUAL			| EQUAL			| GREATER		| EQUAL			| EQUAL			| GREATER

	}
}
