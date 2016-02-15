/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.Log
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package it.gmariotti.changelibs.library.parser;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.Util;
import it.gmariotti.changelibs.library.internal.ChangeLog;
import it.gmariotti.changelibs.library.internal.ChangeLogAdapter;
import it.gmariotti.changelibs.library.internal.ChangeLogException;
import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import it.gmariotti.changelibs.library.internal.ChangeLogRowHeader;
import it.gmariotti.changelibs.library.parser.BaseParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlParser
extends BaseParser {
    private static final String ATTRIBUTE_BULLETEDLIST = "bulletedList";
    private static final String ATTRIBUTE_CHANGEDATE = "changeDate";
    private static final String ATTRIBUTE_CHANGETEXTTITLE = "changeTextTitle";
    private static final String ATTRIBUTE_VERSIONCODE = "versionCode";
    private static final String ATTRIBUTE_VERSIONNAME = "versionName";
    private static String TAG = "XmlParser";
    private static final String TAG_CHANGELOG = "changelog";
    private static final String TAG_CHANGELOGBUG = "changelogbug";
    private static final String TAG_CHANGELOGIMPROVEMENT = "changelogimprovement";
    private static final String TAG_CHANGELOGTEXT = "changelogtext";
    private static final String TAG_CHANGELOGVERSION = "changelogversion";
    private static List<String> mChangeLogTags = new ArrayList<String>(){};
    protected ChangeLogAdapter mChangeLogAdapter;
    private int mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
    private String mChangeLogFileResourceUrl = null;

    public XmlParser(Context context) {
        super(context);
    }

    public XmlParser(Context context, int n2) {
        super(context);
        this.mChangeLogFileResourceId = n2;
    }

    public XmlParser(Context context, String string2) {
        super(context);
        this.mChangeLogFileResourceUrl = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readChangeLogRowNode(XmlPullParser xmlPullParser, ChangeLog changeLog, String string2, int n2) throws Exception {
        int n3 = 1;
        if (xmlPullParser == null) {
            return;
        }
        String string3 = xmlPullParser.getName();
        ChangeLogRow changeLogRow = new ChangeLogRow();
        changeLogRow.setVersionName(string2);
        changeLogRow.setVersionCode(n2);
        string2 = xmlPullParser.getAttributeValue(null, "changeTextTitle");
        if (string2 != null) {
            changeLogRow.setChangeTextTitle(string2);
        }
        if ((string2 = xmlPullParser.getAttributeValue(null, "bulletedList")) != null) {
            if (string2.equals("true")) {
                changeLogRow.setBulletedList(true);
            } else {
                changeLogRow.setBulletedList(false);
            }
        } else {
            changeLogRow.setBulletedList(this.bulletedList);
        }
        if (xmlPullParser.next() == 4) {
            string2 = xmlPullParser.getText();
            if (string2 == null) {
                throw new ChangeLogException("ChangeLogText required in changeLogText node");
            }
            changeLogRow.parseChangeText(string2);
            n2 = string3.equalsIgnoreCase("changelogbug") ? n3 : (string3.equalsIgnoreCase("changelogimprovement") ? 2 : 0);
            changeLogRow.setType(n2);
            xmlPullParser.nextTag();
        }
        changeLog.addRow(changeLogRow);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ChangeLog readChangeLogFile() throws Exception {
        var1_1 = null;
        try {
            if (this.mChangeLogFileResourceUrl != null) {
                if (Util.isConnected(this.mContext)) {
                    var1_1 = new URL(this.mChangeLogFileResourceUrl).openStream();
                }
            } else {
                var1_1 = this.mContext.getResources().openRawResource(this.mChangeLogFileResourceId);
            }
            if (var1_1 != null) {
                var2_8 = Xml.newPullParser();
                var2_8.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                var2_8.setInput(var1_1, null);
                var2_8.nextTag();
                var3_9 = new ChangeLog();
                this.readChangeLogNode(var2_8, var3_9);
                var1_1.close();
                return var3_9;
            }
            Log.d((String)XmlParser.TAG, (String)"Changelog.xml not found");
            throw new ChangeLogException("Changelog.xml not found");
        }
        catch (XmlPullParserException var1_2) {}
        ** GOTO lbl-1000
        catch (IOException var1_4) {}
        ** GOTO lbl-1000
        catch (IOException var1_6) {}
lbl-1000: // 2 sources:
        {
            Log.d((String)XmlParser.TAG, (String)"Error i/o with changelog.xml", (Throwable)var1_5);
            throw var1_5;
        }
        catch (XmlPullParserException var1_7) {}
lbl-1000: // 2 sources:
        {
            Log.d((String)XmlParser.TAG, (String)"XmlPullParseException while parsing changelog file", (Throwable)var1_3);
            throw var1_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void readChangeLogNode(XmlPullParser xmlPullParser, ChangeLog changeLog) throws Exception {
        if (xmlPullParser != null && changeLog != null) {
            xmlPullParser.require(2, null, "changelog");
            String string2 = xmlPullParser.getAttributeValue(null, "bulletedList");
            if (string2 == null || string2.equals("true")) {
                changeLog.setBulletedList(true);
                this.bulletedList = true;
            } else {
                changeLog.setBulletedList(false);
                this.bulletedList = false;
            }
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() != 2 || !xmlPullParser.getName().equals("changelogversion")) continue;
                this.readChangeLogVersionNode(xmlPullParser, changeLog);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void readChangeLogVersionNode(XmlPullParser xmlPullParser, ChangeLog changeLog) throws Exception {
        if (xmlPullParser != null) {
            int n2;
            xmlPullParser.require(2, null, "changelogversion");
            String string2 = xmlPullParser.getAttributeValue(null, "versionName");
            String string3 = xmlPullParser.getAttributeValue(null, "versionCode");
            int n3 = n2 = 0;
            if (string3 != null) {
                try {
                    n3 = Integer.parseInt(string3);
                }
                catch (NumberFormatException var6_5) {
                    Log.w((String)TAG, (String)"Error while parsing versionCode.It must be a numeric value. Check you file.");
                    n3 = n2;
                }
            }
            string3 = xmlPullParser.getAttributeValue(null, "changeDate");
            if (string2 == null) {
                throw new ChangeLogException("VersionName required in changeLogVersion node");
            }
            ChangeLogRowHeader changeLogRowHeader = new ChangeLogRowHeader();
            changeLogRowHeader.setVersionName(string2);
            changeLogRowHeader.setChangeDate(string3);
            changeLog.addRow(changeLogRowHeader);
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() != 2 || !mChangeLogTags.contains(string3 = xmlPullParser.getName())) continue;
                this.readChangeLogRowNode(xmlPullParser, changeLog, string2, n3);
            }
        }
    }

    public void setChangeLogAdapter(ChangeLogAdapter changeLogAdapter) {
        this.mChangeLogAdapter = changeLogAdapter;
    }

}

