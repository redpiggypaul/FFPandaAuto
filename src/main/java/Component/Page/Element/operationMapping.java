package Component.Page.Element;

/**
 * @author pzhu023
 */
public class operationMapping  {
    private String opeName;
    private String pageName;
    private String opeType;

    private String elementName;
    private String elementType;
    private String elementPara;

    private int step;

    public operationMapping(String oName, String pName, String oType, String eleName, String eleType, String elePara, int s) {
        this.opeName = oName;
        this.pageName = pName;
        this.opeType = oType;

        this.elementName = eleName;
        this.elementType = eleType;
        this.elementPara = elePara;

        this.step = s;
    }


    public String getOpeName() {
        return opeName;
    }

    public String getPageName() {
        return pageName;
    }

    public String getOpeType() {
        return opeType;
    }

    public String getElementName() {
        return elementName;
    }

    public String getElementType() {
        return elementType;
    }

    public String getElementPara()
    {
        return elementPara;
    }

    public int getStep()
    {
        return step;
    }

    public String toString()
    {
        return "[opeName = " + this.opeName + ", step = " + this.step + "]";
    }


}
