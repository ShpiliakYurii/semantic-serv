package com.kpi.drproject.helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlLinksHelper {


    public static List<String> getAllLinks(String url) throws IOException {
        HTMLDocument doc = null;
        ArrayList<String> listUrls = new ArrayList<>(); // массив для хранения ссылок
        HTMLEditorKit kit = new HTMLEditorKit(); // swing компонент для парсинга html страницы

        //получаем содержимое html страницы и заполняем этим содержимым компонент HTMLEditorKit
        URL ura = new URL(url);
        try {
            InputStream in = ura.openStream();
            doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
            kit.read(in, doc, 0);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

        // Получаем атрибут HREF тегов <A> и заполняем ими массив
        HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
        while (it.isValid()) {
            AttributeSet attrs = it.getAttributes();
            Object linkAttr = attrs.getAttribute(HTML.Attribute.HREF);
            if (linkAttr != null) {
                if ((linkAttr.toString().matches("^[a-zA-Z]*://.*\\..*")) & (!linkAttr.toString().matches(url + ".*"))) {

                    System.out.println(linkAttr);
                }
                listUrls.add(linkAttr.toString());
            }
            it.next();
        }
        return listUrls;
    }
}
