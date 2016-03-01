package parser;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.pattern.Patterns;

public class NodeParser {
	public static Parser<String> reference() {
		return Parsers.between(Scanners.string("("), Terminals.Identifier.TOKENIZER.source(), Scanners.string(")"));
	}

	public static Parser<String> label() {
		return Patterns.regex("[^}]").many().toScanner("name string")
				.between(Scanners.isChar('{'), Scanners.isChar('}')).source().map(s -> s.substring(1, s.length() - 1));
	}
}
