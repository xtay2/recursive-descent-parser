package app.tokenization.tokens;

import helper.util.ArrayHelper;
import helper.util.CollectionHelper;

import java.util.Iterator;

/**
 * A token that contains multiple other tokens.
 * <p>
 * Default value for
 * - {@link app.rules.nonterminals.SequenceRule}
 * - {@link app.rules.nonterminals.MultipleRule}
 */
public class TokenSection extends Token implements Iterable<Token> {

	public final Token[] children;

	/**
	 * Creates a new token section and removes all null elements from the children array.
	 */
	public TokenSection(Token[] children) {
		super(children);
		this.children = children;
	}

	@Override
	public Iterator<Token> iterator() {
		return ArrayHelper.iterator(children);
	}


	@Override
	public String toString() {
		return CollectionHelper.mapJoin(children, Token::toString, "");
	}
}
