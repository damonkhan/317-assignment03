Regular expressions must be passed using the foramat \"REGEX\". That is, the double quotes must be escaped. Without the escaped opening and closing
quotation marks, the parser will throw an error stating that the Regex is not well formed.

REcompiler should accept the following operations:

-literals
-. wildcard
-* closure
-+ one or more
-? zero or one
-| alternation
-() parenthesis
-\ escaped characters

The following operations were either not implemented or implementation is not complete:

-\ (doesn't work when escaped character is followed by a term)
-[]
-![ ]!

List of tests that were run through REcompile:

works:
\"a\"
\"a*\"
\"ab\"
\"a|b\"
\"a*b\"
\"ab*c\"
\"a*b|c\"
\"(a*b|ac)d\"
\"ba*(a|b)a\"
\"a+\"
\"a+b\"
\"a+bc\"
\"a+b|c\"
\"a?\"
\"a?b\"
\"a?b+c\"
\"a?b*c\"
\"a\*b\"
\"a\+b\"

doesn't work:
\"a\*|b\"



invalid:
\"a*b*\" - not valid in language
\"a+bc+d\" - not valid in language
\"a+b*c\" - not valid in language