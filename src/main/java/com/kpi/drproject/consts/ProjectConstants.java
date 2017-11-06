package com.kpi.drproject.consts;

public class ProjectConstants {
    public static final String WIKI_TEMP_FILE_URL = "static/wiki-page.html";
    public static final String WIKI_SOURCE_URL = "https://uk.wikipedia.org";
    public static final String POST_MESSAGE_FRAME_SCRIPT = "<script type=\"text/javascript\">function listener(event) {\n" +
            "    if (event.origin !== \"http://rd-client-side.com\") return;\n" +
            "    if (event.data.event === \"getSelectedTextEvent\") {\n" +
            "        parent.postMessage({text: window.getSelection().toString(), type: event.data.type, url: window.location.href}, \"http://rd-client-side.com\")\n" +
            "    }\n" +
            "}\n" +
            "if (window.addEventListener) {\n" +
            "    window.addEventListener(\"message\", listener, false);\n" +
            "} else {\n" +
            "    window.attachEvent(\"onmessage\", listener);\n" +
            "}</script>";
}
