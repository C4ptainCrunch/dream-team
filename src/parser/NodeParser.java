package parser;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Pair;
import org.codehaus.jparsec.pattern.Patterns;

public class NodeParser {
	public static Parser<String> reference() {
		return Parsers.between(Scanners.string("("), Terminals.Identifier.TOKENIZER.source(), Scanners.string(")"));
	}

	public static Parser<String> label() {
		return Patterns.regex("[^}]").many().toScanner("name string")
				.between(Scanners.isChar('{'), Scanners.isChar('}')).source().map(s -> s.substring(1, s.length() - 1));
	}

	public static Parser<List<String>> options() {
		return Patterns.regex("[^]]").many().toScanner("options string")
				.between(Scanners.isChar('['), Scanners.isChar(']')).source()
				.map(s -> Arrays.asList(s.substring(1, s.length() - 1).split(", *")));
	}

	public static Parser<Pair<Double, Double>> coordinates() {
		return Parsers.between(Scanners.string("("), Terminals.DecimalLiteral.TOKENIZER.next(Scanners.string(","))
				.next(Terminals.DecimalLiteral.TOKENIZER).source().map(new Map<String, Pair<Double, Double>>() {
					@Override
					public Pair<Double, Double> map(String s) {
						String[] splitted = s.split(",");
						return new Pair<>(Double.valueOf(splitted[0]), Double.valueOf(splitted[1]));
					}
				}), Scanners.string(")"));
	}

}
