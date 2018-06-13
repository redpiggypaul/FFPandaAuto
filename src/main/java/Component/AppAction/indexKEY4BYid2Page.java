package Component.AppAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by appledev131 on 4/26/16.
 */
public class indexKEY4BYid2Page {
    public static Map<String, String> ANDpage2indexKeBYid = new HashMap<String, String>() {
        {
            put("ProfileEdit" ,                       "tv_content");
            put("Profile" ,                       "tv_content");
            put("test2",                        "Action_test2.xml");
            put("test3",                        "Action_test2.xml");
        }
    };

    public static Map<String, String> IOSpage2indexKeBYid = new HashMap<String, String>() {
        {
            put("login" ,                       "Action_login.xml");
            put("test",                         "test.xml");
            put("searchrole",                   "Action_searchrole.xml");
            put("editProfileTitle",             "Action_IOS_editProfile.xml");
            put("switch2Profile",               "Action_switchInNaviBar.xml");
        }
    };

}
