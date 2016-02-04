package fr.vekia.tools.showcase.vkgraph.client.showcase.application.layout.code.display;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 23 mai 2012. GWTQuery Vekia Showcase
 * @version 1.0
 */
public class CodeLine extends Composite {

    private String lineOfCode;
    private FlowPanel code;

    /**
     * Default constructor
     * 
     */
    public CodeLine(String codeLine) {
        this.lineOfCode = codeLine;

        addColors();
        ScrollPanel panel = new ScrollPanel();
        panel.setHeight("800px");
        panel.add(code);
        initWidget(panel);
    }

    /**
     * 
     */
    private void addColors() {
        code = new FlowPanel();

        String html = lineOfCode;
        html = html.replaceAll("<", "&#60;");
        html = html.replaceAll("<", "&#62;");

        Element preElement = DOM.createElement("pre");

        List<String> keyWords = new ArrayList<String>();
        keyWords.add("return");
        keyWords.add("public");
        keyWords.add("private");
        keyWords.add("protected");
        keyWords.add("package");
        keyWords.add("void");
        keyWords.add("boolean");
        keyWords.add("null");
        keyWords.add("this");
        keyWords.add("new");
        keyWords.add("class");
        keyWords.add("extends");
        keyWords.add("implements");
        keyWords.add("super");
        keyWords.add("import");
        keyWords.add("for");
        keyWords.add("while");
        keyWords.add("final");
        keyWords.add("static");

        for (String keyWord : keyWords) {
            html = html.replaceAll("(" + keyWord + ")", "<span style=\"color: #B858F4; font-weight:bold;\">$1</span>");
        }
        html = html.replaceAll("(/\\*[.\\r\\s \\*\\w\\:\\$\\{\\}\\@\\(\\)]*/)", "<span style=\"color: #B98064;\">$1</span>");
        html = html.replaceAll("([ ][A-Z]{1}[a-zA-Z]+)", "<span style=\"color: #81BD81;\">$1</span>");

        html = html.replaceAll("(^)", "$1<li class=\"alt\">");
        html = html.replaceAll("($)", "</li>$1");

        preElement.setInnerHTML(html);
        preElement.getStyle().setFontWeight(FontWeight.NORMAL);
        preElement.getStyle().setFontSize(12, Unit.PT);
        code.getElement().appendChild(preElement);
    }
}