# Recursive descent-parser for Java `jdk18+`

## Introduction
This parser is a part of the compiler for the language ["Context"](https://github.com/xtay2/context-compiler).
You can watch the progress in the [weekly german devlog](https://www.youtube.com/watch?v=jWV276dW2P0&list=PLg3Uqkw1bs-kw8Ku7-SVx6ijcD8Yo0Swb) on Youtube.

## Features
This is a linear LL(k)-parser with error-recovery which is able to transform a text into an abstract syntax tree (AST) based on the following rules:

### Terminals
---
| Name | Short Description |
|------|-------------------|
|[`Literal`](https://github.com/xtay2/recursive-descent-parser/wiki/Literal)| Matches a previously set string. |
|[`Word`](https://github.com/xtay2/recursive-descent-parser/wiki/Word)      | Matches a section thats inside a specified range of characters with a max length. |
|[`Section`](https://github.com/xtay2/recursive-descent-parser/wiki/Section)| Matches anything between two previously specified characters. |
|[`Pattern`](https://github.com/xtay2/recursive-descent-parser/wiki/Pattern)| Matches a specified Regex-Pattern. |


### NonTerminals
---
| Name | Short Description |
|------|-------------------|
|[`Ordered`](https://github.com/xtay2/recursive-descent-parser/wiki/Ordered)      | Matches a bunch of rules in their specified order. |
|[`Unordered`](https://github.com/xtay2/recursive-descent-parser/wiki/Unordered)  | Matches all passed rules in any order. |
|[`Alteration`](https://github.com/xtay2/recursive-descent-parser/wiki/Alteration)| Matches any one of the passed rules. |
|[`Multiple`](https://github.com/xtay2/recursive-descent-parser/wiki/Multiple)    | Matches the passed rule as many times as possible. |
|[`Optional`](https://github.com/xtay2/recursive-descent-parser/wiki/Optional)    | Tries to match the passed rule or an empty string. |
|[`Lazy`](https://github.com/xtay2/recursive-descent-parser/wiki/Lazy)            | Matches the passed rule lazily to enable circular dependencies. |

## Example
This is the EBNF for a simple mathematical expression:
```
nr  := ('0' | ... | '9')+
op  := '+' | '-' | '*' | '/'
exp := nr (op nr)*
```

And this is the implementation with this library (the boolean-literal describes the optionality of the rule):
```Java
Rule nr  = new Word('0', '9');
Rule op  = new Alteration(false, "+", "-", "*", "/");
Rule exp = new Ordered(nr, new Multiple(true, op, nr));
```

Now calling `exp.tokenize("10 + 20 * 147")` will yield the following tree:
```json
"TokenArray": {
  "LiteralToken": "10",
  "TokenList": [
    "TokenArray": {
      "LiteralToken": "+",
      "LiteralToken": "20"
    },
    "TokenArray": {
      "LiteralToken": "*",
      "LiteralToken": "147"
    }
  ]
}
```

Meanwhile calling `exp.tokenize("10 xxx * 147")` will result in:
```json
"TokenArray": {
  "LiteralToken": "10",
  "TokenList": [
    "ErrorToken": "xxx", <-- Error
    "TokenArray": {
      "LiteralToken": "*",
      "LiteralToken": "147"
    }
  ]
}
```

## Dependencies
This project requires:
- Java [jdk18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)
- The [helper-library](https://github.com/xtay2/helper)
