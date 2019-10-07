import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;
import tictactoe.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


class Clue {
    String input;
    Clue(String input) {
        this.input = input;
    }
}

public class TicTacToeTest extends BaseStageTest<Clue> {
    public TicTacToeTest() throws Exception {
        super(Main.class);
    }

    static String[] inputs = new String[] {
        "1 1", "1 2", "1 3",
        "2 1", "2 2", "2 3",
        "3 1", "3 2", "3 3"
    };

    String iterateCells(String initial) {
        int index = -1;
        for (int i = 0; i < inputs.length; i++) {
            if (initial.equals(inputs[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return "";
        }
        String fullInput = "";
        for (int i = index; i < index + 9; i++) {
            fullInput += inputs[i % inputs.length] + "\n";
        }
        return fullInput;
    }

    @Override
    public List<TestCase<Clue>> generate() {
        return List.of(
            new TestCase<Clue>()
                .setInput("\" XXOO OX \"")
                .setAttach(new Clue(
                    "\" XXOO OX \"")),

            new TestCase<Clue>()
                .setInput("\"         \"")
                .setAttach(new Clue(
                    "\"         \"")),

            new TestCase<Clue>()
                .setInput("\"X X O    \"")
                .setAttach(new Clue(
                    "\"X X O    \""))
        );
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        List<TicTacToeField> fields = TicTacToeField.parseAll(reply);

        if (fields.size() != 2) {
            return new CheckResult(false,
                "Output should contain 2 fields, found: " + fields.size());
        }

        if (!reply.contains("Making move level \"easy\"")) {
            return new CheckResult(false,
                "No \"Making move level \"easy\"\" line in output");
        }

        TicTacToeField curr = fields.get(0);
        TicTacToeField next = fields.get(1);

        TicTacToeField correctCurr = new TicTacToeField(clue.input);

        if (!curr.equalTo(correctCurr)) {
            return new CheckResult(false,
                "The first field is not equal to the input field");
        }

        if (curr.equalTo(next)) {
            return new CheckResult(false,
                "The first field is equals to next, but should be different");
        }

        if (!curr.hasNextAs(next)) {
            return new CheckResult(false,
                "The first field is correct, but the second is not");
        }

        return CheckResult.TRUE;
    }
}
