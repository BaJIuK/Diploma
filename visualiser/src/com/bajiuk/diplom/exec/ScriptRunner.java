package com.bajiuk.diplom.exec;

import com.bajiuk.diplom.data.MatchedNode;
import com.bajiuk.diplom.data.PointsNode;
import com.sun.istack.internal.NotNull;

import java.io.InputStream;
import java.util.Scanner;

public class ScriptRunner {

    private static final String SCRIPT = "python3 /Users/bajiuk/diplom/matcher/matcher.py %s %s %s";

    @NotNull
    private final MatchedNode matchedNode;

    public ScriptRunner(MatchedNode matchedNode) {
        this.matchedNode = matchedNode;
    }

    @NotNull
    public PointsNode execute() {
        try {
            Process process = Runtime.getRuntime().exec(
                    String.format(SCRIPT, matchedNode.getDataNode().getSrcImage(),
                            matchedNode.getDataNode().getDstImage(), matchedNode.getPointsFile()));
            process.waitFor();

            System.out.println(convertStreamToString(process.getErrorStream()));
            return new PointsNode(matchedNode);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
