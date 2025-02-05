package visualsvm;

import svm.SVMParser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class ExecuteVM {

    private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    public static final int MEMSIZE = 10000;
    public static final int CODESIZE = 10000;
    private int[] code;
    private int[] memory;

    private int ip = 0;
    private int sp = MEMSIZE; // punta al top dello stack

    private int tm;
    private int hp = 0;
    private int ra;
    private int fp = MEMSIZE;
    private final List<CodeLine> codeLines = new ArrayList<>();
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JPanel buttonPanel;
    private final JList<CodeLine> asmList;
    private final JList<String> stackList, heapList;
    private final JButton backStep;
    private final JButton backToBreakPoint;
    private final JButton reset;
    private final JButton nextStep;
    private final JButton play;
    private final JPanel registerPanel;
    private final JSplitPane memPanel;
    private final JLabel tmLabel, raLabel, fpLabel, ipLabel, spLabel, hpLabel;
    private final JScrollPane asmScroll, stackScroll, heapScroll, outputScroll;
    private final JTextArea outputText;

    private final int codeLineCount;
    private String keyboardCommand = "";

    private int[] sourceMap;
    private List<String> source;
    private int debugLineCode = 0;

    public ExecuteVM(int[] code, int[] sourceMap, List<String> source) {
        boolean printArgumentLineNumber = false;
        this.code = code;
        this.sourceMap = sourceMap;
        this.source = source;
        this.memory = new int[MEMSIZE];

        this.frame = new JFrame("FOOL Virtual Machine");
        this.mainPanel = new JPanel();

        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.Y_AXIS));
        this.play = new JButton("PLAY");
        this.play.addActionListener(e -> this.playButtonHandler());
        this.reset = new JButton("RESET");
        this.reset.addActionListener(e -> this.resetButtonHandler());
        this.backToBreakPoint = new JButton("BACK TO BREAK POINT");
        this.backToBreakPoint.addActionListener(e -> this.backToBreakPointButtonHandler());
        this.backStep = new JButton("BACK STEP");
        this.backStep.addActionListener(e -> this.backStepButtonHandler());
        this.nextStep = new JButton("STEP");
        this.nextStep.addActionListener(e -> this.stepButtonHandler());
        this.buttonPanel.add(this.play);
        this.buttonPanel.add(this.nextStep);
        this.buttonPanel.add(this.reset);
        this.buttonPanel.add(this.backToBreakPoint);
        this.buttonPanel.add(this.backStep);

        this.registerPanel = new JPanel();
        this.tmLabel = new JLabel();
        this.tmLabel.setFont(FONT);
        this.raLabel = new JLabel();
        this.raLabel.setFont(FONT);
        this.fpLabel = new JLabel();
        this.fpLabel.setFont(FONT);
        this.ipLabel = new JLabel();
        this.ipLabel.setFont(FONT);
        this.spLabel = new JLabel();
        this.spLabel.setFont(FONT);
        this.hpLabel = new JLabel();
        this.hpLabel.setFont(FONT);
        this.registerPanel.setLayout(new BoxLayout(this.registerPanel, BoxLayout.Y_AXIS));
        this.registerPanel.add(this.tmLabel);
        this.registerPanel.add(this.raLabel);
        this.registerPanel.add(this.fpLabel);
        this.registerPanel.add(this.ipLabel);
        this.registerPanel.add(this.spLabel);
        this.registerPanel.add(this.hpLabel);

        this.mainPanel.setLayout(new BorderLayout());
        this.asmList = new JList<>();

        int realIp = 0;
        for (var line : this.source) {

            // line blank should not be printed
            if (line.isBlank()) {
                codeLines.add(CodeLine.simpleLine("\n"));
                continue;
            }

            // label for function definition is not ad instruction in code[]
            // => setting same address of first function instruction
            if (line.contains(":")) {
                //    commandLines.add(String.format("%5d: %s", realIp, line));
                codeLines.add(CodeLine.simpleLine("       " + line));
                continue;
            }

            var macro = line.split(" ");
            if (macro.length > 1) {
                if (printArgumentLineNumber) {
                    codeLines.add(CodeLine.lineWithBreakpoint(String.format("%5d: %s   | %5d: %s", realIp++, macro[0], realIp++, macro[1])));
                } else {
                    codeLines.add(CodeLine.lineWithBreakpoint(String.format("%5d: %s", realIp++, line)));
                    realIp++;
                }
            } else {
                codeLines.add(CodeLine.lineWithBreakpoint(String.format("%5d: %s", realIp++, line)));
            }
        }

        this.asmList.setListData(new Vector<>(codeLines));
        this.asmList.setCellRenderer(new CodeLineCellRenderer());
        this.codeLineCount = this.source.size();

        this.asmList.setFont(FONT);
        removeListenersFrom(this.asmList);

        this.asmList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                codeLines.get(asmList.locationToIndex(e.getPoint())).switchBreakpoint();
                asmList.repaint();
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });

        this.asmScroll = new JScrollPane(this.asmList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.asmScroll.setBorder(BorderFactory.createTitledBorder("CODE"));
        this.mainPanel.add(this.asmScroll, BorderLayout.EAST);

        this.stackList = new JList<>();
        removeListenersFrom(this.stackList);
        this.heapList = new JList<>();
        removeListenersFrom(this.heapList);

        this.stackList.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        this.heapList.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        this.stackScroll = new JScrollPane(this.stackList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.stackScroll.setBorder(BorderFactory.createTitledBorder("STACK"));
        this.heapScroll = new JScrollPane(this.heapList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.heapScroll.setBorder(BorderFactory.createTitledBorder("HEAP"));
        this.memPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                this.stackScroll, this.heapScroll);
        this.mainPanel.add(this.memPanel, BorderLayout.CENTER);

        this.outputText = new JTextArea();
        this.outputText.setRows(7);
        this.outputText.setEditable(false);
        this.outputScroll = new JScrollPane(this.outputText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setMem();
        this.frame.getContentPane().setLayout(new BorderLayout());
        this.frame.add(mainPanel, BorderLayout.CENTER);
        this.frame.add(buttonPanel, BorderLayout.EAST);
        this.frame.add(registerPanel, BorderLayout.WEST);
        this.frame.add(outputScroll, BorderLayout.SOUTH);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.outputText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                ExecuteVM.this.keyboardCommand += e.getKeyChar();
                ExecuteVM.this.checkKeyboardCommand();
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
        });

        this.update();
        this.frame.setMinimumSize(new Dimension(800, 500));
        this.frame.pack();

        this.stackScroll.getVerticalScrollBar().setValue(this.stackScroll.getVerticalScrollBar().getMaximum());
        this.memPanel.setDividerLocation(0.5);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    private void reset() {
        this.memory = new int[MEMSIZE];
        this.tm = 0;
        this.ra = 0;
        this.fp = MEMSIZE;
        this.ip = 0;
        this.sp = MEMSIZE;
        this.hp = 0;
        this.nextStep.setEnabled(true);
        this.play.setEnabled(true);
        this.outputText.setText("");
    }

    private void resetButtonHandler() {
        this.debugLineCode = 0;
        this.reset();
        this.update();
    }

    private void backToBreakPointButtonHandler() {
        this.reset();
        int nearlestBreakpoint = 0;
        int tempBreakpoint = 0;
        while (this.step()) {
            tempBreakpoint++;
            if (lineHasBreakpoint()) {
                nearlestBreakpoint = tempBreakpoint;
            }

            if (tempBreakpoint + 1  == this.debugLineCode) {
                break;
            }
        }
        this.debugLineCode = nearlestBreakpoint+1;
        this.backStepButtonHandler();

    }

    private void backStepButtonHandler() {
        this.reset();
        if (this.debugLineCode < 2) {
            this.debugLineCode = 0;
            this.update();
        } else {
            this.debugLineCode--;
            int tempBreakpoint = 0;
            while (this.step()) {
                tempBreakpoint++;
                if (tempBreakpoint == this.debugLineCode) {
                    this.update();
                    return;
                }
            }
            this.nextStep.setEnabled(false);
            this.play.setEnabled(false);
            ip--;
            this.update();
        }
    }

    private <E> void removeListenersFrom(JList<E> list) {
        for (MouseListener m : list.getMouseListeners()) {
            list.removeMouseListener(m);
        }
        for (MouseMotionListener m : list.getMouseMotionListeners()) {
            list.removeMouseMotionListener(m);
        }
    }

    private void checkKeyboardCommand() {
        if (this.keyboardCommand.endsWith(" ")) {
            this.stepButtonHandler();
        } else if (this.keyboardCommand.endsWith("\n")) {
            this.playButtonHandler();
        } else if (this.keyboardCommand.endsWith("fra")) {
            this.play.setEnabled(false);
        } else if (this.keyboardCommand.endsWith("tranqui")) {
            this.play.setEnabled(true);
        } else {
            return;
        }
        this.keyboardCommand = "";
    }

    private void setMem() {
        // Codice per non visualizzare 0 in memoria
//        this.stackList.setListData(new Vector<>(
//                IntStream.range(0, MEMSIZE).mapToObj(x -> String.format("%5d: %s", x, x <= hp || x >= sp ? this.memory[x] : ""))
//                        .collect(Collectors.toList())));
//        this.heapList.setListData(new Vector<>(
//                IntStream.range(0, MEMSIZE).mapToObj(x -> String.format("%5d: %s", x, x <= hp || x >= sp ? this.memory[x] : ""))
//                        .collect(Collectors.toList())));
        final var mem = IntStream.range(0, MEMSIZE)
                .mapToObj(x -> String.format("%5d: %s", x, this.memory[x]))
                .collect(Collectors.toCollection(ArrayList::new));
        mem.add(String.valueOf(MEMSIZE));

        var memory = new Vector<>(mem);

        this.stackList.setListData(memory);
        this.stackList.clearSelection();
        this.stackList.setSelectedIndex(this.sp);
        this.stackScroll.getVerticalScrollBar()
                .setValue(computeScrollDestination(this.stackScroll.getVerticalScrollBar(), this.sp));

        this.heapList.setListData(memory);
        this.heapList.clearSelection();
        this.heapList.setSelectedIndex(this.hp);
        this.heapScroll.getVerticalScrollBar()
                .setValue(computeScrollDestination(this.heapScroll.getVerticalScrollBar(), this.hp));
    }

    private int computeScrollDestination(JScrollBar scroll, int pointer) {
        return Math.max(
                pointer * (scroll.getMaximum() / MEMSIZE) - scroll.getHeight() / 2,
                0
        );
    }

    private void update() {
        this.raLabel.setText("RA: " + this.ra);
        this.fpLabel.setText("FP: " + this.fp);
        this.tmLabel.setText("TM: " + this.tm);
        this.ipLabel.setText("IP: " + this.ip);
        this.hpLabel.setText("HP: " + this.hp);
        this.spLabel.setText("SP: " + this.sp);
        this.asmList.clearSelection();
        this.asmList.setSelectedIndex(this.sourceMap[this.ip]);
        final JScrollBar s = this.asmScroll.getVerticalScrollBar();
        int dest = this.sourceMap[this.ip] * s.getMaximum() / this.codeLineCount - s.getHeight() / 2;
        s.setValue(Math.max(dest, 0));
        setMem();
        var condToDisableButton = this.ip != 0;
        this.reset.setEnabled(condToDisableButton);
        this.backStep.setEnabled(condToDisableButton);
        this.backToBreakPoint.setEnabled(condToDisableButton);
    }

    public void cpu() {
        this.frame.setVisible(true);
    }

    private void playButtonHandler() {
        while (this.step()) {
            debugLineCode++;
            if (lineHasBreakpoint()) {
                this.update();
                return;
            }
        }
        this.nextStep.setEnabled(false);
        this.play.setEnabled(false);
        ip--;
        this.update();
    }

    private boolean lineHasBreakpoint() {
        return this.codeLines.get(this.sourceMap[this.ip])
                .hasBreakpoint()
                .orElse(false);
    }

//    private int getBreakPointCount() {
//        return (int) this.codeLines.stream()
//                .map(CodeLine::hasBreakpoint)
//                .filter(opt -> opt.orElse(false))
//                .count();
//    }

    private void stepButtonHandler() {
        boolean play = this.step();
        if (!play) {
            this.nextStep.setEnabled(false);
            this.play.setEnabled(false);
        } else {
            this.debugLineCode++;
            this.update();
        }
    }

    private boolean step() {
        int bytecode = fetch();
        int v1, v2;
        int address;
        switch (bytecode) {
            case SVMParser.PUSH:
                v1 = fetch();
                push(v1);
                break;
            case SVMParser.POP:
                pop();
                break;
            case SVMParser.ADD:
                v1 = pop();
                v2 = pop();
                push(v2 + v1);
                break;
            case SVMParser.SUB:
                v1 = pop();
                v2 = pop();
                push(v2 - v1);
                break;
            case SVMParser.MULT:
                v1 = pop();
                v2 = pop();
                push(v2 * v1);
                break;
            case SVMParser.DIV:
                v1 = pop();
                v2 = pop();
                push(v2 / v1);
                break;
            case SVMParser.STOREW:
                address = pop();
                memory[address] = pop();
                break;
            case SVMParser.LOADW:
                push(memory[pop()]);
                break;
            case SVMParser.BRANCH:
                ip = fetch();
                break;
            case SVMParser.BRANCHEQ:
                address = fetch();
                v1 = pop();
                v2 = pop();
                ip = v2 == v1 ? address : ip;
                break;
            case SVMParser.BRANCHLESSEQ:
                address = fetch();
                v1 = pop();
                v2 = pop();
                ip = v2 <= v1 ? address : ip;
                break;
            case SVMParser.JS:
                address = pop();
                ra = ip;
                ip = address;
                break;
            case SVMParser.LOADRA:
                push(ra);
                break;
            case SVMParser.STORERA:
                ra = pop();
                break;
            case SVMParser.LOADTM:
                push(tm);
                break;
            case SVMParser.STORETM:
                tm = pop();
                break;
            case SVMParser.LOADFP:
                push(fp);
                break;
            case SVMParser.STOREFP:
                fp = pop();
                break;
            case SVMParser.COPYFP:
                fp = sp;
                break;
            case SVMParser.LOADHP:
                push(hp);
                break;
            case SVMParser.STOREHP:
                hp = pop();
                break;
            case SVMParser.PRINT:
                final String output = sp == MEMSIZE ? "EMPTY STACK" : Integer.toString(memory[sp]);
                System.out.println(output);
                this.outputText.append(output + "\n");
                break;
            case SVMParser.HALT:
                return false;
        }
        if (this.sp <= this.hp) {
            System.out.println("Segmentation fault");
            this.outputText.append("Segmentation fault\n");
            return false;
        }
        return true;
    }

    private int pop() {
        return memory[sp++];
    }

    private void push(int v) {
        memory[--sp] = v;
    }

    private int fetch() {
        return code[ip++];
    }

}

/**
 * A code line with instruction and breakpoint if possible.
 */
interface CodeLine {

    Optional<Boolean> hasBreakpoint();
    void switchBreakpoint();
    String getInstruction();

    static CodeLine simpleLine(String instruction) {
        return new CodeLine() {
            @Override
            public Optional<Boolean> hasBreakpoint() {
                return Optional.empty();
            }
            @Override
            public void switchBreakpoint() { }

            @Override
            public String getInstruction() {
                return instruction;
            }
        };
    }

    static CodeLine lineWithBreakpoint(String instruction) {
        return new CodeLine() {
            private boolean breakpoint;

            @Override
            public Optional<Boolean> hasBreakpoint() {
                return Optional.of(this.breakpoint);
            }

            @Override
            public void switchBreakpoint() {
                this.breakpoint = !this.breakpoint;
            }

            @Override
            public String getInstruction() {
                return instruction;
            }
        };
    }
}

/**
 * Component rendering for CodeLine in JList.
 */
class CodeLineCellRenderer extends JPanel implements ListCellRenderer<CodeLine> {
    JLabel label;
    JCheckBox checkBox;

    public CodeLineCellRenderer() {
        setLayout(new BorderLayout());
        checkBox = new JCheckBox();
        label = new JLabel();
        add(checkBox, BorderLayout.WEST);
        add(label, BorderLayout.CENTER);
        checkBox.setEnabled(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends CodeLine> list,
            CodeLine value,
            int index, boolean isSelected, boolean cellHasFocus) {

        label.setText(value.getInstruction());

        if (value.hasBreakpoint().isEmpty()) {
            checkBox.setVisible(false);
        } else {
            checkBox.setVisible(true);
            checkBox.setSelected(value.hasBreakpoint().get());
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            label.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        return this;
    }
}