package REXSH.REXAUTO.Component.Page;

import REXSH.REXAUTO.Component.Page.Element.ElementMapping;

import REXSH.REXAUTO.LocalException.PageException;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

import static utility.XmlUtils.*;

/**
 * @author lyou009, modify : pzhu023
 */
public class BasePageContent {
    protected Map<String, ElementMapping> locatorMap = null;
    WebDriver theDriver = null;

    public BasePageContent(WebDriver dr, String path) throws Exception {
        this.theDriver = dr;
        System.out.println("Constructor BasePageContent(WebDriver dr, String path) is called");
        locatorMap = XMLdecoder(path);
    }

    public BasePageContent() throws Exception {
        System.out.println("Constructor with NO parameters has been called !!!");
    }

    public BasePageContent(final String path, final int osType) throws Exception {
        System.out.println("Constructor BasePageContent(final String path, final String osType) is called");
        locatorMap = XMLdecoder(path, osType);
    }


    private HashMap<String, ElementMapping> XMLdecoder(String path) throws Exception {
        System.out.println("Try to get the SimpleName for" + this.getClass().getSimpleName() + " in BasePageContent");
        return readXMLDocument(path, this.getClass().getSimpleName());
    }

    private HashMap<String, ElementMapping> XMLdecoder(String path, int osType) throws Exception {
        System.out.println("BasePageContent : the getLocators with OS TYPE is called");
        try {
            return readXMLDocument(path, osType);
        } catch (Exception e) {
            throw new PageException("Failed to getLocator !!! +++ " + this.getClass().getSimpleName() + " +++ " + e.getCause());
        }
    }

    public WebElement[] getPageEleList() {
        WebElement[] resultList = null;
        WebElement theEleS = null;
        HashMap eleSet = new HashMap<String, AppiumDriver>();
        eleSet.clear();
        return resultList;
    }
}
