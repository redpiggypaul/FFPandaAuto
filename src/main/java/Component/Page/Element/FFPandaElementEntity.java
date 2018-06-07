package Component.Page.Element;

import LocalException.XMLException;

/**
 * @author paul.zhu
 */
public class FFPandaElementEntity {
    private StringBuilder pageName;
    private StringBuilder elementName;
    private StringBuilder locatorType;
    private StringBuilder locatorStr;
    private StringBuilder defaultValue;
    private StringBuilder textContent;
    private StringBuilder triggerWin;
    private StringBuilder showMode;
    private StringBuilder nextPage;

    private StringBuilder checkInputWithException(StringBuilder input) throws Exception {
        StringBuilder result = new StringBuilder("");
        try {
            if (input == null || input.toString().equals("")) {
                throw new XMLException("XML for Page Element miss some mandatory ITEM as pageName/elementName/locatorType/locatorStr ");
            } else {
                result.append(input);
            }
        } catch (XMLException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return result;
        }
    }

    private StringBuilder checkInputWOException(StringBuilder input) throws Exception {
        StringBuilder result = new StringBuilder("");
        try {
            result.append(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return result;
        }
    }

    public FFPandaElementEntity(StringBuilder pName, StringBuilder eName, StringBuilder locType, StringBuilder locStr, StringBuilder dValue, StringBuilder tContent, StringBuilder tWin, StringBuilder sMODE, StringBuilder nPage) throws Exception {
        this.pageName = checkInputWithException(pName);
        this.elementName = checkInputWithException(eName);
        this.locatorType = checkInputWithException(locType);
        this.locatorStr = checkInputWithException(locStr);
        this.defaultValue = checkInputWOException(dValue);
        this.textContent = checkInputWOException(tContent);
        this.triggerWin = checkInputWOException(tWin);
        this.showMode = checkInputWOException(sMODE);
        this.nextPage = checkInputWOException(nPage);

    }

    public void setPageName(StringBuilder pageName) throws Exception {
        this.pageName = checkInputWithException(pageName);
    }

    public StringBuilder getPageName() {
        return this.pageName;
    }

    public StringBuilder getElementName() {
        return elementName;
    }

    public void setElementName(StringBuilder elementName) throws Exception {
        this.elementName = checkInputWithException(elementName);
    }

    public StringBuilder getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(StringBuilder type) throws Exception {
        this.locatorType = checkInputWithException(type);
    }

    //   public String getLocatorType() {
    //     return locatorType.toString();
    //}

    public void setLocatorType(String type) throws Exception {
        this.locatorType = checkInputWithException(new StringBuilder(type));
    }

    public StringBuilder getLocatorStr() {
        return locatorStr;
    }

    public void setLocatorStr(StringBuilder locatorStr) throws Exception {
        this.locatorStr = checkInputWithException(locatorStr);
    }

    public void setDefaultValue(StringBuilder defaultValue) throws Exception {
        this.defaultValue = checkInputWOException(defaultValue);
    }

    public StringBuilder getDefaultValue() {
        return defaultValue;
    }

    public void setTextContent(StringBuilder textContent) throws Exception {
        this.textContent = checkInputWOException(textContent);
    }

    public StringBuilder getTextContent() {
        return textContent;
    }

    public void setTriggerWin(StringBuilder triggerWin) throws Exception {
        this.triggerWin = checkInputWOException(triggerWin);
    }

    public StringBuilder getTriggerWin() {
        return triggerWin;
    }

    public void setShowMode(StringBuilder showMode) {
        this.showMode = showMode;
    }

    public StringBuilder getShowMode() {
        return showMode;
    }

    public void setNextPage(StringBuilder nextPage) {
        this.nextPage = nextPage;
    }

    public StringBuilder getNextPage() {
        return nextPage;
    }
}

