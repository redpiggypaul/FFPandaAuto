package Component.Page.Element;

/**
 * @author lyou009
 */
public class elementEntity {
    private StringBuilder locatorStr;
    private StringBuilder elementName;
    private StringBuilder type;
    private StringBuilder mode;
    private StringBuilder defaultValue;
    private StringBuilder textContent;
    private StringBuilder triggerWin;
    private StringBuilder strHandleMode;

    public elementEntity(StringBuilder name, StringBuilder lStr, StringBuilder typeExt, StringBuilder modeExt, StringBuilder dValue, StringBuilder tContent, StringBuilder tWin, StringBuilder strHMode) {
        this.elementName = name;
        this.locatorStr = lStr;
        this.type = typeExt;
        this.mode = modeExt;
        this.defaultValue = dValue;
        this.textContent = tContent;
        this.triggerWin = tWin;
        this.strHandleMode = strHMode;
    }


    public elementEntity() {
        this.elementName = new StringBuilder("default");
        this.locatorStr = new StringBuilder("default");
        this.type = new StringBuilder("default");
        this.mode = new StringBuilder("default");
        this.defaultValue = new StringBuilder("default");
        this.textContent = new StringBuilder("default");
        this.triggerWin = new StringBuilder("default");
        this.strHandleMode = new StringBuilder("default");
    }

    public StringBuilder getElementName() {
        return elementName;
    }

    public boolean setElementName(StringBuilder elementName) {
        boolean result = false;
        try {

            this.elementName = elementName;
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

    public StringBuilder getLocatorStr() {
        return locatorStr;
    }

    public boolean setLocatorStr(StringBuilder locatorStr) {
        boolean result = false;
        try {
            this.locatorStr = locatorStr;
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

    public StringBuilder getType() {
        return type;
    }

    public boolean setType(StringBuilder type) {
        boolean result = false;
        try {
            this.type = type;
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

    public StringBuilder getMode() {
        return mode;
    }

    public boolean setMode(StringBuilder extMode) {
        boolean result = false;
        try {
            this.mode = extMode;
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

    public StringBuilder getDefaultValue() {
        return defaultValue;
    }

    public StringBuilder getTextContent() {
        return textContent;
    }

    public StringBuilder getParent() {
        return new StringBuilder(" ");
    }

    public StringBuilder getTriggerWin()
    {
        return triggerWin;
    }

    public boolean setTriggerWin(StringBuilder extWin)
    {
        boolean result = false;
        try{
            this.triggerWin = this.triggerWin.append(";").append(extWin);
            result = true;
        }
        catch ( Exception e)
        {
            result = false;
        }finally {
            return result;
        }
    }
}
