package Component.Page.Element;

/**
 * Created by appledev131 on 4/14/16.
 */
public class negEleMapping {
    private int waitTime = 3;
    private String locatorStr;
    private String elementName;
    private String type;
    private String nextPage;

    public negEleMapping(String name, String locatorStr, String type){
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
    }

    public negEleMapping(String name, String locatorStr, int waitTime, String type){
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
    }

    public negEleMapping(String name, String locatorStr, int waitTime, String type, String nPage){
        this.elementName = name;
        this.locatorStr = locatorStr;
        this.type = type;
        this.waitTime = waitTime;
        this.nextPage = nPage;
    }


    public negEleMapping(String x){

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
  //  public String getNextPage() {
  //      return nextPage;
  //  }
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
