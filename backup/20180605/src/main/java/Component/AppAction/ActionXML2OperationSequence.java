package Component.AppAction;

import Component.Page.Element.operationItem;
import LocalException.XMLException;

import java.util.*;

import static utility.XmlUtils.readActionXMLDocument;

public class ActionXML2OperationSequence {

    public static HashMap<StringBuilder, operationItem> getOperationSeq(ArrayList<StringBuilder> inActionSeq, StringBuilder path4Action) throws Exception {
        HashMap<StringBuilder, operationItem> temp = new HashMap<StringBuilder, operationItem>();
        HashMap<StringBuilder, operationItem> result = new HashMap<StringBuilder, operationItem>();
        //按照给定的文件顺序读取文件,并且把 all 文件的内容全部读出去
        try {
            List<StringBuilder> actionXMLname = getActionXMLIOS(inActionSeq);  // get the name list for all used action
            for (int ind = 0; ind < actionXMLname.size(); ind++) {   //  read all files as action sequence
                temp = readActionXMLDocument(new StringBuilder(path4Action + actionXMLname.get(ind).toString()));
                List actionlist = new ArrayList(temp.keySet());
                Object[] ary = actionlist.toArray();
                Arrays.sort(ary);
                actionlist = Arrays.asList(ary);
                if (actionlist.size() != 0) {
                    int flag4length = 0;
                    flag4length = actionlist.size();
                    System.out.println("There are " + flag4length + " step(s) in the related XML file of sub action : " + actionXMLname.get(ind));
                } else {
                    throw new XMLException("action list from XML contains empty content`");
                }
                result.putAll(temp);
            }
            System.out.println("Account the result : " + result.size());

            for (Iterator it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry obj = (Map.Entry) it.next();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<StringBuilder> getActionXMLIOS(ArrayList<StringBuilder> actionName) throws XMLException {
        List<StringBuilder> result = new ArrayList<StringBuilder>();
        try {
            Map<StringBuilder, StringBuilder> testCaseScenario = action2PageOperationXML.action2PageOperationIOS;
            for (int ind = 0; ind < actionName.size(); ind++) {
                for (Iterator it = testCaseScenario.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry obj = (Map.Entry) it.next();
                    if (obj.getKey().toString().equalsIgnoreCase(actionName.get(ind).toString())) {
                        System.out.println(" Find the name of XML file by abs action name ~ ");
                        result.add((StringBuilder) obj.getValue());
                    }
                }
            }
            if ((result == null) || (result.size() == 0)) {
                throw new XMLException("Failed to match XML for Action " + actionName);
            }
        } catch (Exception e) {
            throw new XMLException("Failed to match XML for Action " + actionName);
        } finally {
            return result;
        }
    }
}
