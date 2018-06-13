package Component.Page.Element;

/**
 * @author lyou009
 */
public class ElementMapping {
    private int waitTime = 3;
    private String locatorStr;
    private String elementName;
    private String type;
    private String nextPage;
    private String mode;
    private String defaultValue;
    private String textContent;
    private String triggerWin;

    public ElementMapping(String name, String locatorStr, String type) {
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
    }

    public ElementMapping(String name, String locatorStr, int waitTime, String type) {
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
    }

    public ElementMapping(String name, String locatorStr, int waitTime, String type, String nPage, String mode, String dValue, String tContent) {
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
        this.nextPage = nPage;
        this.mode = mode;
        this.defaultValue = dValue;
        this.textContent = tContent;
    }

    public ElementMapping(String name, String locatorStr, int waitTime, String type, String nPage, String mode, String dValue, String tContent, String tWin) {
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
        this.nextPage = nPage;
        this.mode = mode;
        this.defaultValue = dValue;
        this.textContent = tContent;
        this.triggerWin = tWin;
    }

    public ElementMapping(String name, String locatorStr, String type, String nPage, String mode, String dValue, String tContent) {
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
        this.nextPage = nPage;
        this.mode = mode;
        this.defaultValue = dValue;
        this.textContent = tContent;
    }

    public ElementMapping(String name, String locatorStr, String type, String nPage, String mode, String dValue, String tContent, String tWin) {
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
        this.nextPage = nPage;
        this.mode = mode;
        this.defaultValue = dValue;
        this.textContent = tContent;
        this.triggerWin = tWin;
    }

    public ElementMapping(String x) {

    }


    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getLocatorStr() {
        return locatorStr;
    }

    public void setLocatorStr(String locatorStr) {
        this.locatorStr = locatorStr;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getParent() {
        return "";
    }

    public String getTriggerWin() {
        return triggerWin;
    }
}
