package Component.AppAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by appledev131 on 4/26/16.
 */
public class userAtt2PafeElement {
    public static Map<String, String> userStatus = new HashMap<String, String>() { //available, on-board, off-board, etc.
        {
            put("homePage" ,       "onBoardTitle");
        }
    };

    public static Map<String, String> pricingEvent = new HashMap<String, String>() {
        {
            put("homePage" ,       "InPricingEventText");
        }
    };
    public static Map<String, String> pendingDec = new HashMap<String, String>() {
        {
            put("homePage" ,       "pendingDecText");
        }
    };
    public static Map<String, String> availableDetail = new HashMap<String, String>() {
        {
            put("homePage" ,       "availBtn");
        }
    };
}
