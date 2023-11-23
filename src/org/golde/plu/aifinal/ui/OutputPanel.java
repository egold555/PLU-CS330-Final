package org.golde.plu.aifinal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SizeRequirements;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;

public class OutputPanel extends JPanel {

    private final JTextPane output;

    public OutputPanel() {
        super();
        this.setLayout(new BorderLayout());
        output = new JTextPane();
        output.setEditable(false);
        output.setContentType( "text/html" );

        //weirdly I need to do this to enable word wrapping. Java is weird sometimes
        output.setPreferredSize(new Dimension(300, 200));



        this.add(new JScrollPane(output), BorderLayout.CENTER);
    }

    public void clear() {
        output.setText("");
    }

    public void println(String text) {
        println(text, Color.BLACK);
    }

    public void println(String text, Color c) {
        print(text + "\n", c);
    }

    @lombok.SneakyThrows
    public void printException(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        final String stacktrace = sw.toString();
        pw.close();
        sw.close();

        println(stacktrace, Color.RED);
    }

    public void print(String msg) {
        print(msg, Color.BLACK);
    }

    public void print(String msg, Color c) {
        output.setEditable(true);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = output.getDocument().getLength();
        output.setCaretPosition(len);
        output.setCharacterAttributes(aset, false);
        output.replaceSelection(msg);
        output.setEditable(false);
    }

    public void printError(String error) {
        println("<span style=\"color: red;\">" + error + "</span>");
    }

    // Custom EditorKit to enable word wrap in JTextPane
    static class WrapEditorKit extends StyledEditorKit {
        @Override
        public ViewFactory getViewFactory() {
            return new WrapColumnFactory();
        }
    }

    static class WrapColumnFactory implements ViewFactory {
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName:
                        return new LabelView(elem);
                    case AbstractDocument.ParagraphElementName:
                        return new WrapParagraphView(elem);
                    case AbstractDocument.SectionElementName:
                        return new BoxView(elem, View.Y_AXIS);
                    case StyleConstants.ComponentElementName:
                        return new ComponentView(elem);
                    case StyleConstants.IconElementName:
                        return new IconView(elem);
                }
            }
            return new LabelView(elem);
        }
    }

    static class WrapParagraphView extends ParagraphView {
        public WrapParagraphView(Element elem) {
            super(elem);
        }

        @Override
        public void layout(int width, int height) {
            super.layout(Integer.MAX_VALUE, height);
        }

        @Override
        public float getMinimumSpan(int axis) {
            return super.getPreferredSpan(axis);
        }
    }

}
