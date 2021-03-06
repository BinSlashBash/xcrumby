package org.jsoup.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

class HtmlTreeBuilder
  extends TreeBuilder
{
  private static final String[] TagSearchButton;
  private static final String[] TagSearchEndTags;
  private static final String[] TagSearchList;
  private static final String[] TagSearchSelectScope;
  private static final String[] TagSearchSpecial;
  private static final String[] TagSearchTableScope;
  private static final String[] TagsScriptStyle;
  public static final String[] TagsSearchInScope;
  private boolean baseUriSetFromDoc = false;
  private Element contextElement;
  private FormElement formElement;
  private DescendableLinkedList<Element> formattingElements = new DescendableLinkedList();
  private boolean fosterInserts = false;
  private boolean fragmentParsing = false;
  private boolean framesetOk = true;
  private Element headElement;
  private HtmlTreeBuilderState originalState;
  private List<Token.Character> pendingTableCharacters = new ArrayList();
  private HtmlTreeBuilderState state;
  
  static
  {
    if (!HtmlTreeBuilder.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      TagsScriptStyle = new String[] { "script", "style" };
      TagsSearchInScope = new String[] { "applet", "caption", "html", "table", "td", "th", "marquee", "object" };
      TagSearchList = new String[] { "ol", "ul" };
      TagSearchButton = new String[] { "button" };
      TagSearchTableScope = new String[] { "html", "table" };
      TagSearchSelectScope = new String[] { "optgroup", "option" };
      TagSearchEndTags = new String[] { "dd", "dt", "li", "option", "optgroup", "p", "rp", "rt" };
      TagSearchSpecial = new String[] { "address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption", "center", "col", "colgroup", "command", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing", "marquee", "menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section", "select", "style", "summary", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "title", "tr", "ul", "wbr", "xmp" };
      return;
    }
  }
  
  private void clearStackToContext(String... paramVarArgs)
  {
    Iterator localIterator = this.stack.descendingIterator();
    for (;;)
    {
      if (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        if ((!StringUtil.in(localElement.nodeName(), paramVarArgs)) && (!localElement.nodeName().equals("html"))) {}
      }
      else
      {
        return;
      }
      localIterator.remove();
    }
  }
  
  private boolean inSpecificScope(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    return inSpecificScope(new String[] { paramString }, paramArrayOfString1, paramArrayOfString2);
  }
  
  private boolean inSpecificScope(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
  {
    boolean bool2 = false;
    Iterator localIterator = this.stack.descendingIterator();
    while (localIterator.hasNext())
    {
      String str = ((Element)localIterator.next()).nodeName();
      boolean bool1;
      if (StringUtil.in(str, paramArrayOfString1)) {
        bool1 = true;
      }
      do
      {
        return bool1;
        bool1 = bool2;
      } while (StringUtil.in(str, paramArrayOfString2));
      if ((paramArrayOfString3 != null) && (StringUtil.in(str, paramArrayOfString3))) {
        return false;
      }
    }
    Validate.fail("Should not be reachable");
    return false;
  }
  
  private void insertNode(Node paramNode)
  {
    if (this.stack.size() == 0) {
      this.doc.appendChild(paramNode);
    }
    for (;;)
    {
      if (((paramNode instanceof Element)) && (((Element)paramNode).tag().isFormListed()) && (this.formElement != null)) {
        this.formElement.addElement((Element)paramNode);
      }
      return;
      if (isFosterInserts()) {
        insertInFosterParent(paramNode);
      } else {
        currentElement().appendChild(paramNode);
      }
    }
  }
  
  private boolean isElementInQueue(DescendableLinkedList<Element> paramDescendableLinkedList, Element paramElement)
  {
    paramDescendableLinkedList = paramDescendableLinkedList.descendingIterator();
    while (paramDescendableLinkedList.hasNext()) {
      if ((Element)paramDescendableLinkedList.next() == paramElement) {
        return true;
      }
    }
    return false;
  }
  
  private boolean isSameFormattingElement(Element paramElement1, Element paramElement2)
  {
    return (paramElement1.nodeName().equals(paramElement2.nodeName())) && (paramElement1.attributes().equals(paramElement2.attributes()));
  }
  
  private void replaceInQueue(LinkedList<Element> paramLinkedList, Element paramElement1, Element paramElement2)
  {
    int i = paramLinkedList.lastIndexOf(paramElement1);
    if (i != -1) {}
    for (boolean bool = true;; bool = false)
    {
      Validate.isTrue(bool);
      paramLinkedList.remove(i);
      paramLinkedList.add(i, paramElement2);
      return;
    }
  }
  
  Element aboveOnStack(Element paramElement)
  {
    assert (onStack(paramElement));
    Iterator localIterator = this.stack.descendingIterator();
    while (localIterator.hasNext()) {
      if ((Element)localIterator.next() == paramElement) {
        return (Element)localIterator.next();
      }
    }
    return null;
  }
  
  void clearFormattingElementsToLastMarker()
  {
    Element localElement;
    do
    {
      if (this.formattingElements.isEmpty()) {
        break;
      }
      localElement = (Element)this.formattingElements.peekLast();
      this.formattingElements.removeLast();
    } while (localElement != null);
  }
  
  void clearStackToTableBodyContext()
  {
    clearStackToContext(new String[] { "tbody", "tfoot", "thead" });
  }
  
  void clearStackToTableContext()
  {
    clearStackToContext(new String[] { "table" });
  }
  
  void clearStackToTableRowContext()
  {
    clearStackToContext(new String[] { "tr" });
  }
  
  void error(HtmlTreeBuilderState paramHtmlTreeBuilderState)
  {
    if (this.errors.canAddError()) {
      this.errors.add(new ParseError(this.reader.pos(), "Unexpected token [%s] when in state [%s]", new Object[] { this.currentToken.tokenType(), paramHtmlTreeBuilderState }));
    }
  }
  
  void framesetOk(boolean paramBoolean)
  {
    this.framesetOk = paramBoolean;
  }
  
  boolean framesetOk()
  {
    return this.framesetOk;
  }
  
  void generateImpliedEndTags()
  {
    generateImpliedEndTags(null);
  }
  
  void generateImpliedEndTags(String paramString)
  {
    while ((paramString != null) && (!currentElement().nodeName().equals(paramString)) && (StringUtil.in(currentElement().nodeName(), TagSearchEndTags))) {
      pop();
    }
  }
  
  Element getActiveFormattingElement(String paramString)
  {
    Iterator localIterator = this.formattingElements.descendingIterator();
    Element localElement;
    do
    {
      if (localIterator.hasNext())
      {
        localElement = (Element)localIterator.next();
        if (localElement != null) {}
      }
      else
      {
        return null;
      }
    } while (!localElement.nodeName().equals(paramString));
    return localElement;
  }
  
  String getBaseUri()
  {
    return this.baseUri;
  }
  
  Document getDocument()
  {
    return this.doc;
  }
  
  FormElement getFormElement()
  {
    return this.formElement;
  }
  
  Element getFromStack(String paramString)
  {
    Iterator localIterator = this.stack.descendingIterator();
    while (localIterator.hasNext())
    {
      Element localElement = (Element)localIterator.next();
      if (localElement.nodeName().equals(paramString)) {
        return localElement;
      }
    }
    return null;
  }
  
  Element getHeadElement()
  {
    return this.headElement;
  }
  
  List<Token.Character> getPendingTableCharacters()
  {
    return this.pendingTableCharacters;
  }
  
  DescendableLinkedList<Element> getStack()
  {
    return this.stack;
  }
  
  boolean inButtonScope(String paramString)
  {
    return inScope(paramString, TagSearchButton);
  }
  
  boolean inListItemScope(String paramString)
  {
    return inScope(paramString, TagSearchList);
  }
  
  boolean inScope(String paramString)
  {
    return inScope(paramString, null);
  }
  
  boolean inScope(String paramString, String[] paramArrayOfString)
  {
    return inSpecificScope(paramString, TagsSearchInScope, paramArrayOfString);
  }
  
  boolean inScope(String[] paramArrayOfString)
  {
    return inSpecificScope(paramArrayOfString, TagsSearchInScope, null);
  }
  
  boolean inSelectScope(String paramString)
  {
    Iterator localIterator = this.stack.descendingIterator();
    while (localIterator.hasNext())
    {
      String str = ((Element)localIterator.next()).nodeName();
      if (str.equals(paramString)) {
        return true;
      }
      if (!StringUtil.in(str, TagSearchSelectScope)) {
        return false;
      }
    }
    Validate.fail("Should not be reachable");
    return false;
  }
  
  boolean inTableScope(String paramString)
  {
    return inSpecificScope(paramString, TagSearchTableScope, null);
  }
  
  Element insert(String paramString)
  {
    paramString = new Element(Tag.valueOf(paramString), this.baseUri);
    insert(paramString);
    return paramString;
  }
  
  Element insert(Token.StartTag paramStartTag)
  {
    if (paramStartTag.isSelfClosing())
    {
      paramStartTag = insertEmpty(paramStartTag);
      this.stack.add(paramStartTag);
      this.tokeniser.transition(TokeniserState.Data);
      this.tokeniser.emit(new Token.EndTag(paramStartTag.tagName()));
      return paramStartTag;
    }
    paramStartTag = new Element(Tag.valueOf(paramStartTag.name()), this.baseUri, paramStartTag.attributes);
    insert(paramStartTag);
    return paramStartTag;
  }
  
  void insert(Element paramElement)
  {
    insertNode(paramElement);
    this.stack.add(paramElement);
  }
  
  void insert(Token.Character paramCharacter)
  {
    if (StringUtil.in(currentElement().tagName(), TagsScriptStyle)) {}
    for (paramCharacter = new DataNode(paramCharacter.getData(), this.baseUri);; paramCharacter = new TextNode(paramCharacter.getData(), this.baseUri))
    {
      currentElement().appendChild(paramCharacter);
      return;
    }
  }
  
  void insert(Token.Comment paramComment)
  {
    insertNode(new Comment(paramComment.getData(), this.baseUri));
  }
  
  Element insertEmpty(Token.StartTag paramStartTag)
  {
    Tag localTag = Tag.valueOf(paramStartTag.name());
    Element localElement = new Element(localTag, this.baseUri, paramStartTag.attributes);
    insertNode(localElement);
    if (paramStartTag.isSelfClosing())
    {
      if (!localTag.isKnownTag()) {
        break label60;
      }
      if (localTag.isSelfClosing()) {
        this.tokeniser.acknowledgeSelfClosingFlag();
      }
    }
    return localElement;
    label60:
    localTag.setSelfClosing();
    this.tokeniser.acknowledgeSelfClosingFlag();
    return localElement;
  }
  
  FormElement insertForm(Token.StartTag paramStartTag, boolean paramBoolean)
  {
    paramStartTag = new FormElement(Tag.valueOf(paramStartTag.name()), this.baseUri, paramStartTag.attributes);
    setFormElement(paramStartTag);
    insertNode(paramStartTag);
    if (paramBoolean) {
      this.stack.add(paramStartTag);
    }
    return paramStartTag;
  }
  
  void insertInFosterParent(Node paramNode)
  {
    Element localElement2 = getFromStack("table");
    int i = 0;
    Element localElement1;
    if (localElement2 != null) {
      if (localElement2.parent() != null)
      {
        localElement1 = localElement2.parent();
        i = 1;
      }
    }
    while (i != 0)
    {
      Validate.notNull(localElement2);
      localElement2.before(paramNode);
      return;
      localElement1 = aboveOnStack(localElement2);
      continue;
      localElement1 = (Element)this.stack.get(0);
    }
    localElement1.appendChild(paramNode);
  }
  
  void insertMarkerToFormattingElements()
  {
    this.formattingElements.add(null);
  }
  
  void insertOnStackAfter(Element paramElement1, Element paramElement2)
  {
    int i = this.stack.lastIndexOf(paramElement1);
    if (i != -1) {}
    for (boolean bool = true;; bool = false)
    {
      Validate.isTrue(bool);
      this.stack.add(i + 1, paramElement2);
      return;
    }
  }
  
  boolean isFosterInserts()
  {
    return this.fosterInserts;
  }
  
  boolean isFragmentParsing()
  {
    return this.fragmentParsing;
  }
  
  boolean isInActiveFormattingElements(Element paramElement)
  {
    return isElementInQueue(this.formattingElements, paramElement);
  }
  
  boolean isSpecial(Element paramElement)
  {
    return StringUtil.in(paramElement.nodeName(), TagSearchSpecial);
  }
  
  void markInsertionMode()
  {
    this.originalState = this.state;
  }
  
  void maybeSetBaseUri(Element paramElement)
  {
    if (this.baseUriSetFromDoc) {}
    do
    {
      return;
      paramElement = paramElement.absUrl("href");
    } while (paramElement.length() == 0);
    this.baseUri = paramElement;
    this.baseUriSetFromDoc = true;
    this.doc.setBaseUri(paramElement);
  }
  
  void newPendingTableCharacters()
  {
    this.pendingTableCharacters = new ArrayList();
  }
  
  boolean onStack(Element paramElement)
  {
    return isElementInQueue(this.stack, paramElement);
  }
  
  HtmlTreeBuilderState originalState()
  {
    return this.originalState;
  }
  
  Document parse(String paramString1, String paramString2, ParseErrorList paramParseErrorList)
  {
    this.state = HtmlTreeBuilderState.Initial;
    return super.parse(paramString1, paramString2, paramParseErrorList);
  }
  
  List<Node> parseFragment(String paramString1, Element paramElement, String paramString2, ParseErrorList paramParseErrorList)
  {
    this.state = HtmlTreeBuilderState.Initial;
    initialiseParse(paramString1, paramString2, paramParseErrorList);
    this.contextElement = paramElement;
    this.fragmentParsing = true;
    paramString1 = null;
    if (paramElement != null)
    {
      if (paramElement.ownerDocument() != null) {
        this.doc.quirksMode(paramElement.ownerDocument().quirksMode());
      }
      paramString1 = paramElement.tagName();
      if (!StringUtil.in(paramString1, new String[] { "title", "textarea" })) {
        break label194;
      }
      this.tokeniser.transition(TokeniserState.Rcdata);
    }
    for (;;)
    {
      paramString2 = new Element(Tag.valueOf("html"), paramString2);
      this.doc.appendChild(paramString2);
      this.stack.push(paramString2);
      resetInsertionMode();
      paramString1 = paramElement.parents();
      paramString1.add(0, paramElement);
      paramParseErrorList = paramString1.iterator();
      do
      {
        paramString1 = paramString2;
        if (!paramParseErrorList.hasNext()) {
          break;
        }
        paramString1 = (Element)paramParseErrorList.next();
      } while (!(paramString1 instanceof FormElement));
      this.formElement = ((FormElement)paramString1);
      paramString1 = paramString2;
      runParser();
      if (paramElement == null) {
        break;
      }
      return paramString1.childNodes();
      label194:
      if (StringUtil.in(paramString1, new String[] { "iframe", "noembed", "noframes", "style", "xmp" })) {
        this.tokeniser.transition(TokeniserState.Rawtext);
      } else if (paramString1.equals("script")) {
        this.tokeniser.transition(TokeniserState.ScriptData);
      } else if (paramString1.equals("noscript")) {
        this.tokeniser.transition(TokeniserState.Data);
      } else if (paramString1.equals("plaintext")) {
        this.tokeniser.transition(TokeniserState.Data);
      } else {
        this.tokeniser.transition(TokeniserState.Data);
      }
    }
    return this.doc.childNodes();
  }
  
  Element pop()
  {
    if ((((Element)this.stack.peekLast()).nodeName().equals("td")) && (!this.state.name().equals("InCell"))) {
      Validate.isFalse(true, "pop td not in cell");
    }
    if (((Element)this.stack.peekLast()).nodeName().equals("html")) {
      Validate.isFalse(true, "popping html!");
    }
    return (Element)this.stack.pollLast();
  }
  
  void popStackToBefore(String paramString)
  {
    Iterator localIterator = this.stack.descendingIterator();
    for (;;)
    {
      if ((!localIterator.hasNext()) || (((Element)localIterator.next()).nodeName().equals(paramString))) {
        return;
      }
      localIterator.remove();
    }
  }
  
  void popStackToClose(String paramString)
  {
    Iterator localIterator = this.stack.descendingIterator();
    for (;;)
    {
      if (localIterator.hasNext())
      {
        if (((Element)localIterator.next()).nodeName().equals(paramString)) {
          localIterator.remove();
        }
      }
      else {
        return;
      }
      localIterator.remove();
    }
  }
  
  void popStackToClose(String... paramVarArgs)
  {
    Iterator localIterator = this.stack.descendingIterator();
    for (;;)
    {
      if (localIterator.hasNext())
      {
        if (StringUtil.in(((Element)localIterator.next()).nodeName(), paramVarArgs)) {
          localIterator.remove();
        }
      }
      else {
        return;
      }
      localIterator.remove();
    }
  }
  
  protected boolean process(Token paramToken)
  {
    this.currentToken = paramToken;
    return this.state.process(paramToken, this);
  }
  
  boolean process(Token paramToken, HtmlTreeBuilderState paramHtmlTreeBuilderState)
  {
    this.currentToken = paramToken;
    return paramHtmlTreeBuilderState.process(paramToken, this);
  }
  
  void push(Element paramElement)
  {
    this.stack.add(paramElement);
  }
  
  void pushActiveFormattingElements(Element paramElement)
  {
    int i = 0;
    Iterator localIterator = this.formattingElements.descendingIterator();
    Element localElement;
    if (localIterator.hasNext())
    {
      localElement = (Element)localIterator.next();
      if (localElement != null) {
        break label48;
      }
    }
    for (;;)
    {
      this.formattingElements.add(paramElement);
      return;
      label48:
      int j = i;
      if (isSameFormattingElement(paramElement, localElement)) {
        j = i + 1;
      }
      i = j;
      if (j != 3) {
        break;
      }
      localIterator.remove();
    }
  }
  
  void reconstructFormattingElements()
  {
    int n = this.formattingElements.size();
    if ((n == 0) || (this.formattingElements.getLast() == null) || (onStack((Element)this.formattingElements.getLast()))) {
      return;
    }
    Object localObject = (Element)this.formattingElements.getLast();
    int i = n - 1;
    int m = 0;
    int j;
    if (i == 0) {
      j = 1;
    }
    for (;;)
    {
      int k = i;
      if (j == 0)
      {
        localObject = this.formattingElements;
        k = i + 1;
        localObject = (Element)((DescendableLinkedList)localObject).get(k);
      }
      Validate.notNull(localObject);
      j = 0;
      Element localElement = insert(((Element)localObject).nodeName());
      localElement.attributes().addAll(((Element)localObject).attributes());
      this.formattingElements.add(k, localElement);
      this.formattingElements.remove(k + 1);
      i = k;
      if (k == n - 1)
      {
        return;
        localObject = this.formattingElements;
        k = i - 1;
        localElement = (Element)((DescendableLinkedList)localObject).get(k);
        localObject = localElement;
        i = k;
        j = m;
        if (localElement != null)
        {
          localObject = localElement;
          i = k;
          if (!onStack(localElement)) {
            break;
          }
          localObject = localElement;
          i = k;
          j = m;
        }
      }
    }
  }
  
  void removeFromActiveFormattingElements(Element paramElement)
  {
    Iterator localIterator = this.formattingElements.descendingIterator();
    while (localIterator.hasNext()) {
      if ((Element)localIterator.next() == paramElement) {
        localIterator.remove();
      }
    }
  }
  
  boolean removeFromStack(Element paramElement)
  {
    Iterator localIterator = this.stack.descendingIterator();
    while (localIterator.hasNext()) {
      if ((Element)localIterator.next() == paramElement)
      {
        localIterator.remove();
        return true;
      }
    }
    return false;
  }
  
  void replaceActiveFormattingElement(Element paramElement1, Element paramElement2)
  {
    replaceInQueue(this.formattingElements, paramElement1, paramElement2);
  }
  
  void replaceOnStack(Element paramElement1, Element paramElement2)
  {
    replaceInQueue(this.stack, paramElement1, paramElement2);
  }
  
  void resetInsertionMode()
  {
    int i = 0;
    Iterator localIterator = this.stack.descendingIterator();
    int j;
    do
    {
      Object localObject;
      if (localIterator.hasNext())
      {
        localObject = (Element)localIterator.next();
        j = i;
        if (!localIterator.hasNext())
        {
          j = 1;
          localObject = this.contextElement;
        }
        localObject = ((Element)localObject).nodeName();
        if ("select".equals(localObject)) {
          transition(HtmlTreeBuilderState.InSelect);
        }
      }
      else
      {
        return;
      }
      if (("td".equals(localObject)) || (("td".equals(localObject)) && (j == 0)))
      {
        transition(HtmlTreeBuilderState.InCell);
        return;
      }
      if ("tr".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InRow);
        return;
      }
      if (("tbody".equals(localObject)) || ("thead".equals(localObject)) || ("tfoot".equals(localObject)))
      {
        transition(HtmlTreeBuilderState.InTableBody);
        return;
      }
      if ("caption".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InCaption);
        return;
      }
      if ("colgroup".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InColumnGroup);
        return;
      }
      if ("table".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InTable);
        return;
      }
      if ("head".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InBody);
        return;
      }
      if ("body".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InBody);
        return;
      }
      if ("frameset".equals(localObject))
      {
        transition(HtmlTreeBuilderState.InFrameset);
        return;
      }
      if ("html".equals(localObject))
      {
        transition(HtmlTreeBuilderState.BeforeHead);
        return;
      }
      i = j;
    } while (j == 0);
    transition(HtmlTreeBuilderState.InBody);
  }
  
  void setFormElement(FormElement paramFormElement)
  {
    this.formElement = paramFormElement;
  }
  
  void setFosterInserts(boolean paramBoolean)
  {
    this.fosterInserts = paramBoolean;
  }
  
  void setHeadElement(Element paramElement)
  {
    this.headElement = paramElement;
  }
  
  void setPendingTableCharacters(List<Token.Character> paramList)
  {
    this.pendingTableCharacters = paramList;
  }
  
  HtmlTreeBuilderState state()
  {
    return this.state;
  }
  
  public String toString()
  {
    return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + this.state + ", currentElement=" + currentElement() + '}';
  }
  
  void transition(HtmlTreeBuilderState paramHtmlTreeBuilderState)
  {
    this.state = paramHtmlTreeBuilderState;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/parser/HtmlTreeBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */