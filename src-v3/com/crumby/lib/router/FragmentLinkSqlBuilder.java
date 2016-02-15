package com.crumby.lib.router;

import java.util.List;

public class FragmentLinkSqlBuilder {
    public static String build(List<Integer> ids) {
        if (ids.isEmpty()) {
            return null;
        }
        String idString = "(";
        for (Integer id : ids) {
            idString = idString + id + ",";
        }
        return "SELECT * FROM fragment_links_master WHERE id IN " + (idString.substring(0, idString.length() - 1) + ")");
    }
}
