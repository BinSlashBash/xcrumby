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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlParser
  extends BaseParser
{
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
  private static List<String> mChangeLogTags = new ArrayList() {};
  protected ChangeLogAdapter mChangeLogAdapter;
  private int mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
  private String mChangeLogFileResourceUrl = null;
  
  public XmlParser(Context paramContext)
  {
    super(paramContext);
  }
  
  public XmlParser(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mChangeLogFileResourceId = paramInt;
  }
  
  public XmlParser(Context paramContext, String paramString)
  {
    super(paramContext);
    this.mChangeLogFileResourceUrl = paramString;
  }
  
  private void readChangeLogRowNode(XmlPullParser paramXmlPullParser, ChangeLog paramChangeLog, String paramString, int paramInt)
    throws Exception
  {
    int i = 1;
    if (paramXmlPullParser == null) {
      return;
    }
    String str = paramXmlPullParser.getName();
    ChangeLogRow localChangeLogRow = new ChangeLogRow();
    localChangeLogRow.setVersionName(paramString);
    localChangeLogRow.setVersionCode(paramInt);
    paramString = paramXmlPullParser.getAttributeValue(null, "changeTextTitle");
    if (paramString != null) {
      localChangeLogRow.setChangeTextTitle(paramString);
    }
    paramString = paramXmlPullParser.getAttributeValue(null, "bulletedList");
    if (paramString != null) {
      if (paramString.equals("true")) {
        localChangeLogRow.setBulletedList(true);
      }
    }
    while (paramXmlPullParser.next() == 4)
    {
      paramString = paramXmlPullParser.getText();
      if (paramString == null)
      {
        throw new ChangeLogException("ChangeLogText required in changeLogText node");
        localChangeLogRow.setBulletedList(false);
        continue;
        localChangeLogRow.setBulletedList(this.bulletedList);
      }
      else
      {
        localChangeLogRow.parseChangeText(paramString);
        if (!str.equalsIgnoreCase("changelogbug")) {
          break label180;
        }
        paramInt = i;
      }
    }
    for (;;)
    {
      localChangeLogRow.setType(paramInt);
      paramXmlPullParser.nextTag();
      paramChangeLog.addRow(localChangeLogRow);
      return;
      label180:
      if (str.equalsIgnoreCase("changelogimprovement")) {
        paramInt = 2;
      } else {
        paramInt = 0;
      }
    }
  }
  
  public ChangeLog readChangeLogFile()
    throws Exception
  {
    for (InputStream localInputStream = null;; localInputStream = this.mContext.getResources().openRawResource(this.mChangeLogFileResourceId))
    {
      try
      {
        if (this.mChangeLogFileResourceUrl != null)
        {
          if (Util.isConnected(this.mContext)) {
            localInputStream = new URL(this.mChangeLogFileResourceUrl).openStream();
          }
          if (localInputStream == null) {
            break;
          }
          localXmlPullParser = Xml.newPullParser();
          localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
          localXmlPullParser.setInput(localInputStream, null);
          localXmlPullParser.nextTag();
          localChangeLog = new ChangeLog();
        }
      }
      catch (XmlPullParserException localXmlPullParserException1)
      {
        XmlPullParser localXmlPullParser;
        ChangeLog localChangeLog;
        Log.d(TAG, "XmlPullParseException while parsing changelog file", localXmlPullParserException1);
        throw localXmlPullParserException1;
      }
      catch (IOException localIOException1)
      {
        Log.d(TAG, "Error i/o with changelog.xml", localIOException1);
        throw localIOException1;
      }
      try
      {
        readChangeLogNode(localXmlPullParser, localChangeLog);
        localInputStream.close();
        return localChangeLog;
      }
      catch (IOException localIOException2)
      {
        break label137;
      }
      catch (XmlPullParserException localXmlPullParserException2)
      {
        break label124;
      }
    }
    Log.d(TAG, "Changelog.xml not found");
    throw new ChangeLogException("Changelog.xml not found");
  }
  
  protected void readChangeLogNode(XmlPullParser paramXmlPullParser, ChangeLog paramChangeLog)
    throws Exception
  {
    if ((paramXmlPullParser == null) || (paramChangeLog == null)) {}
    for (;;)
    {
      return;
      paramXmlPullParser.require(2, null, "changelog");
      String str = paramXmlPullParser.getAttributeValue(null, "bulletedList");
      if ((str == null) || (str.equals("true")))
      {
        paramChangeLog.setBulletedList(true);
        this.bulletedList = true;
      }
      while (paramXmlPullParser.next() != 3) {
        if ((paramXmlPullParser.getEventType() == 2) && (paramXmlPullParser.getName().equals("changelogversion")))
        {
          readChangeLogVersionNode(paramXmlPullParser, paramChangeLog);
          continue;
          paramChangeLog.setBulletedList(false);
          this.bulletedList = false;
        }
      }
    }
  }
  
  protected void readChangeLogVersionNode(XmlPullParser paramXmlPullParser, ChangeLog paramChangeLog)
    throws Exception
  {
    if (paramXmlPullParser == null) {}
    for (;;)
    {
      return;
      paramXmlPullParser.require(2, null, "changelogversion");
      String str1 = paramXmlPullParser.getAttributeValue(null, "versionName");
      String str2 = paramXmlPullParser.getAttributeValue(null, "versionCode");
      int j = 0;
      int i = j;
      if (str2 != null) {}
      try
      {
        i = Integer.parseInt(str2);
        str2 = paramXmlPullParser.getAttributeValue(null, "changeDate");
        if (str1 == null) {
          throw new ChangeLogException("VersionName required in changeLogVersion node");
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          Log.w(TAG, "Error while parsing versionCode.It must be a numeric value. Check you file.");
          i = j;
        }
        ChangeLogRowHeader localChangeLogRowHeader = new ChangeLogRowHeader();
        localChangeLogRowHeader.setVersionName(str1);
        localChangeLogRowHeader.setChangeDate(localNumberFormatException);
        paramChangeLog.addRow(localChangeLogRowHeader);
      }
      while (paramXmlPullParser.next() != 3) {
        if (paramXmlPullParser.getEventType() == 2)
        {
          String str3 = paramXmlPullParser.getName();
          if (mChangeLogTags.contains(str3)) {
            readChangeLogRowNode(paramXmlPullParser, paramChangeLog, str1, i);
          }
        }
      }
    }
  }
  
  public void setChangeLogAdapter(ChangeLogAdapter paramChangeLogAdapter)
  {
    this.mChangeLogAdapter = paramChangeLogAdapter;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/parser/XmlParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */