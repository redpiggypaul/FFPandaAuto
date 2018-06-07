package Component.Page;

import REXSH.REXAUTO.LocalException.PageException;

import java.util.ArrayList;

/**
 * Created by appledev131 on 4/12/16.
 */
public class PageIndexGroup {
    protected ArrayList<String> nameMap4Classname = new ArrayList<String>();
    protected ArrayList<Integer> indexMap4Classname = new ArrayList<Integer>();

    protected ArrayList<String> name4XPath = new ArrayList<String>();
    protected ArrayList<Integer> index4XPath = new ArrayList<Integer>();

    private String logSpace4thisPage = " --- PageIndexGroupt ---";


    public boolean setMap4Classname(String extName, Integer extInt) {
        boolean result = false;
        try {
            nameMap4Classname.add(extName);
            indexMap4Classname.add(extInt);
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

    public boolean setMap4XPath(String extName, Integer extInt) {
        boolean result = false;
        try {
            name4XPath.add(extName);
            index4XPath.add(extInt);
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

    public int getIndex4Classname(String eleContent) throws Exception{
        int resultIndex = -1;
        if (nameMap4Classname.size() == indexMap4Classname.size()) {
            for (int ind = 0; ind < nameMap4Classname.size(); ) {
                if (nameMap4Classname.get(ind).equals(eleContent)) {
                    resultIndex = indexMap4Classname.get(ind);
                    break;
                }
                else
                {
                    ind++;
                }
            }
        } else {
            throw new PageException("The PageIndexGroup content 4 Classname is wrong, name mis-match with index");
        }
        return resultIndex;
    }

    public int getIndex4XPath(String eleContent) throws Exception{
        int resultIndex = -1;
        if (name4XPath.size() == index4XPath.size()) {
            for (int ind = 0; ind < name4XPath.size(); ) {
                if (name4XPath.get(ind).equals(eleContent)) {
                    resultIndex = index4XPath.get(ind);
                    break;
                }
                else
                {
                    ind++;
                }
            }
        } else {
            throw new PageException("The PageIndexGroup content 4 XPath is wrong, name mis-match with index");
        }
        return resultIndex;
    }




    public PageIndexGroup() throws Exception {
        this.nameMap4Classname.clear();
        this.indexMap4Classname.clear();
        this.name4XPath.clear();
        this.index4XPath.clear();

    }





    public static void main(String args[]) {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
