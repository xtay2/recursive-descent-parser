package app.tokenization.tokens;

import app.rules.abstractions.Rule;
import helper.util.ArrayHelper;
import helper.util.CollectionHelper;

import java.util.Iterator;
import java.util.Set;

/**
 * A token that contains multiple other tokens.
 * <p>
 * Default value for
 * - {@link app.rules.nonterminals.SequenceRule}
 * - {@link app.rules.nonterminals.MultipleRule}
 */
public class TokenSection extends Token implements Iterable<Token> {

	public TokenSection(Rule rule, Token[] children) {
		super(rule, children);
	}

	@Override
	public Iterator<Token> iterator() {
		return ArrayHelper.iterator(children);
	}


	/** Returns true if the passed token is contained in the children. */
	public boolean contains(Token token) {
		return ArrayHelper.contains(children, token);
	}

	@Override
	public String debugStruct(int indent) {
		var i = "\t".repeat(indent);
		return i + rule + " {\n" +
				CollectionHelper.mapJoin(children, id -> id.debugStruct(indent + 1), ",\n") + "\n" +
				i + "}";
	}

	@Override
	public String toString() {
		return CollectionHelper.mapJoin(children, Token::toString, " ");
	}

}
