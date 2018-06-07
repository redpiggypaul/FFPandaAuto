package Component.Page.Element;

import Component.Page.ANDPageContent;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.util.HashMap;

/**
 * @author pzhu023
 */
public class noticeWindow {
 //   public ANDPageContent content = null;
    public String sourcePage; // sourcePage Name , getclass().getsimplename()
    public String windType; // complete ok, failure ok, confirm yes/no
    public String windName; //
    public String compType; // TitleMessageBtn, TitleBtn, MessageBtn
    public int btnNum;      // Number of Btn
    public String[] btnName; // OK, YES, NO
 //   public String[] btnRoute; // get from element XML
 //   public HashMap<String, String> btn2Route; // get from element XML
 //   public HashMap<String, By> byMap = null;

    public noticeWindow(String sPage, String wType, String wName, String cType, int bNum, String[] bName) {
        this.sourcePage = sPage;
        this.windType = wType;
        this.windName = wName;
        this.compType = cType;
        this.btnNum = bNum;
        this.btnName = bName;

    }

    public String sourcePage() {
        return sourcePage;
    }

    public String getWindType() {
        return windType;
    }

    public String getWindName() {
        return windName;
    }

    public String getCompType() {
        return compType;
    }

    public int getBtnNum()
    {
        return btnNum;
    }
    public String[] getBtnName()
    {
        return btnName;
    }
    public String toString()
    {
        return ("[sourcePage = " + this.sourcePage + ", windType = " + this.windType + ", windName" + this.windName +  "]");
    }


}
