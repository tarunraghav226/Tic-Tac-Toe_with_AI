import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;
import tictactoe.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


class Attach {
    String inputField;
    String outField;

    Attach(String inputField, String outField) {
        this.inputField = inputField;
        this.outField = outField;
    }
}


enum FieldState {
    X, O, FREE;

    static FieldState get(char symbol) {
        switch (symbol) {
            case 'X': return X;
            case 'O': return O;
            case ' ': return FREE;
            default: return null;
        }
    }
}

class TicTacToeField {

    final FieldState[][] field;

    TicTacToeField(FieldState[][] field) {
        this.field = new FieldState[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.field[row][col] = field[row][col];
            }
        }
    }

    TicTacToeField(String str) {
        field = new FieldState[3][3];
        str = str.replace("\"", "");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                field[row][col] =
                    FieldState.get(str.charAt(((2 - row) * 3 + col)));
            }
        }
    }

    boolean equalTo(TicTacToeField other) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] != other.field[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean hasNextAs(TicTacToeField other) {
        boolean improved = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] != other.field[i][j]) {
                    if (field[i][j] == FieldState.FREE && !improved) {
                        improved = true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return improved;
    }

    boolean differByOne(TicTacToeField other) {
        boolean haveSingleDifference = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] != other.field[i][j]) {
                    if (haveSingleDifference) {
                        return false;
                    }
                    haveSingleDifference = true;
                }
            }
        }

        return haveSingleDifference;
    }

    boolean isCloseTo(TicTacToeField other) {
        return equalTo(other)
            || hasNextAs(other)
            || other.hasNextAs(this);
    }

    static TicTacToeField parse(String fieldStr) {

        try {
            List<String> lines = fieldStr
                .lines()
                .map(String::strip)
                .filter(e ->
                    e.startsWith("|")
                        && e.endsWith("|"))
                .collect(Collectors.toList());

            for (String line : lines) {
                for (char c : line.toCharArray()) {
                    if (c != 'X'
                        && c != 'O'
                        && c != '|'
                        && c != ' ') {
                        return null;
                    }
                }
            }

            FieldState[][] field = new FieldState[3][3];

            int y = 2;
            for (String line : lines) {
                char[] cols = new char[] {
                    line.charAt(2),
                    line.charAt(4),
                    line.charAt(6)
                };

                int x = 0;
                for (char c : cols) {
                    FieldState state = FieldState.get(c);
                    if (state == null) {
                        return null;
                    }
                    field[y][x] = state;
                    x++;
                }
                y--;
            }

            return new TicTacToeField(field);
        } catch (Exception ex) {
            return null;
        }
    }


    static List<TicTacToeField> parseAll(String output) {
        List<TicTacToeField> fields = new ArrayList<>();

        List<String> lines = output
            .lines()
            .map(String::strip)
            .filter(e -> e.length() > 0)
            .collect(Collectors.toList());

        String candidateField = "";
        boolean insideField = false;
        for (String line : lines) {
            if (line.contains("----") && !insideField) {
                insideField = true;
                candidateField = "";
            } else if (line.contains("----") && insideField) {
                TicTacToeField field = TicTacToeField.parse(candidateField);
                if (field != null) {
                    fields.add(field);
                }
                insideField = false;
            }

            if (insideField && line.startsWith("|")) {
                candidateField += line + "\n";
            }
        }

        return fields;
    }

}

public class TicTacToeTest extends BaseStageTest<Attach> {
    public TicTacToeTest() throws Exception {
        super(Main.class);
    }

    @Override
    public List<TestCase<Attach>> generate() {
        return List.of(
            new TestCase<Attach>()
                .setInput("\"X X O    \"\n1 2")
                .setAttach(new Attach(
                    "\"X X O    \"",
                     "\"X XXO    \"")),

            new TestCase<Attach>()
                .setInput("\"X X O    \"\n1 1")
                .setAttach(new Attach(
                    "\"X X O    \"",
                     "\"X X O X  \"")),

            new TestCase<Attach>()
                .setInput("\"X X O    \"\n3 2")
                .setAttach(new Attach(
                    "\"X X O    \"",
                     "\"X X OX   \"")),

            new TestCase<Attach>()
                .setInput("\"X X O    \"\n2 1")
                .setAttach(new Attach(
                    "\"X X O    \"",
                     "\"X X O  X \"")),

            new TestCase<Attach>()
                .setInput("\" XXOO OX \"\n1 3")
                .setAttach(new Attach(
                    "\" XXOO OX \"",
                     "\"XXXOO OX \"")),

            new TestCase<Attach>()
                .setInput("\" XXOO OX \"\n3 1")
                .setAttach(new Attach(
                    "\" XXOO OX \"",
                     "\" XXOO OXX\"")),

            new TestCase<Attach>()
                .setInput("\" XXOO OX \"\n3 2")
                .setAttach(new Attach(
                    "\" XXOO OX \"",
                     "\" XXOOXOX \""))

        );
    }

    @Override
    public CheckResult check(String reply, Attach clue) {

        List<TicTacToeField> fields = TicTacToeField.parseAll(reply);

        if (fields.size() != 2) {
            return new CheckResult(false,
                "You should output exactly 2 fields, found: " + fields.size());
        }

        TicTacToeField curr = fields.get(0);
        TicTacToeField next = fields.get(1);

        TicTacToeField correctCurr = new TicTacToeField(clue.inputField);
        TicTacToeField correctNext = new TicTacToeField(clue.outField);

        if (!curr.equalTo(correctCurr)) {
            return new CheckResult(false,
                "The first field is not equal to the input field");
        }

        if (!next.equalTo(correctNext)) {
            return new CheckResult(false,
                "The first field is correct, but the second is not");
        }

        return CheckResult.TRUE;
    }
}
