package com.mkean.demo.app;

/**
 * Created by Administrator on 2016/ic_yello_ball/30.
 */
public class Constants {
    //用户优惠券
    public static final String NOT_USER = "notUse";
    public static final String TIME_OUT = "timeOut";

    //网络拦截器
    public static final String REQUEST_VERSION = "version";

    public static final String WX_APP_ID = "wxefa4911d6408abe5";

    public static final String DEFAULT_CITY ="苏州";
    public static final String CITY_AD_CODE = "adCode";

    public static final String LOCATION = "location";
    public static final String LOCATION_ACTION = "locationAction";
    //定位的
    public static final int LOCATION_LOCATING = 110;
    public static final int LOCATION_FAILED = 111;
    public static final int LOCATION_SUCCESS = 112;

    //    SharedPreferences
//    判断是否第一次进入app
    public static final int MY_SP = 10;
    public static final String ISFIRST = "isFirst";
    public static final String BASE_CONFIG = "baseConfig";
    public static final String ISLOGIN = "isLogin";
    //保存用户信息
    public static final String USERNAME = "username";
    public static final String NICKNAME = "nickname";
    public static final String AVATAR = "avatar";
    public static final String MOBILE = "mobile";
    public static final String MONEY_ACCOUNT = "moneyAccount";
    public static final String PENSION_ACCOUNT = "pensionMoneyAccount";
    public static final String  UID = "";
    public static final String  PWD = "";

    //优惠买单和普通订单的订单type
    public static final int ORDER = 1001;
    //优惠
    public static final int BENEFIT_BUY = 1002;
    //限时抢购
    public static final int LIMIT_BUY = 1003;
    //酒店抢购
    public static final int HOTEL_BUY = 2001;
    //服务订单
    //保洁订单
    //活动订单
    //生活服务订单
    //惠生活订单

    //当前选中城市id
    public static final String CHOOSE_CITY_ID = "choose_city_id";
    public static final String CHOOSE_CITY_NAME = "chooseCityName";
    public static final String EVER_VISIT_CITY = "everVisitCity";
    //判断支付成功 返回上一页面是否需要弹出抽奖信息
    public static final String  LUCK_DRAW = "luckDraw";

//
//    public static final String WAIT_ASSESS = "finish";
//    public static final String WAIT_PAY = "waitPay";
//    public static final String WAIT_USE = "payFinish";
//    public static final String WAIT_REFUND = "waitClose";
//    public static final String ALL_ORDER = " ";
//
//    public static final String ORDER_CLOSE = "close";
//    public static final String ORDER_WAITPAY = "waitPay";
//    public static final String ORDER_PAYFINISH = "payFinish";
//    public static final String ORDER_WAIYFINISH = "waitFinish";
//    public static final String ORDER_FINISH = "finish";
//    public static final String ORDER_COMMENTED = "commented";
//    public static final String ORDER_WAITCLOSE = "waitClose";

    //支付类型，现在只有支付宝(划掉)  现在都有了
    public static final String ALIPAY = "alipay";
    public static final String WXPAY = "wxpay";
    public static final String YUE = "pay";

    //权限申请
    public static final int PERMISSION_CALL_REQUEST_CODE = 10;
    public static final int PERMISSION_LOACTION_REQUEST_CODE = 20;
    public static final int PERMISSION_CAMERA_REQUEST_CODE = 30;

    //选择验证方式
    public static final int CHOOSE_TEST_MESSAGE = 3110;
    public static final int CHOOSE_TEST_NUMBER = 3111;

    //选择是现金还是养老金页面
    public static final int MY_ACCOUNT_VIEW = 120;
    public static final int PENSION_ACCOUNT_VIEW = 121;

    //EventBus用
    public static final int GO_TO_NEAR = 2001;
    public static final int CHECK_LOCATION = 2002;
    public static final int CITY_CHANGE = 2003;
    public static final int WECHAT_PAY_SUCCESS = 2004;

    public static final int SHOPLIST_FAVOURITE = 3210;
    public static final int SHOPLIST_SEARCH = 3211;

    /************************酒店*************************/
    //SP
    public static final String HOTEL_SARTTIME = "hotelStartTime"; //入住时间
    public static final String HOTEL_ENDTIME = "hotelEndTime"; //离店时间
    public static final String HOTEL_SEARCHISTORY = "hotel_searchHistory"; //酒店搜索历史

    //EventBus
    public static final int HOTEL_TIMECHANGE =4002;//更新酒店首页酒店默认日期
    public static final int HOTEL_KEYCHANGE =4003;//更新酒店首页酒店默认关键字

    //Router 传递数据
    public static final String HOTEL_KEY = "hotelKey"; //酒店搜索关键字
    //酒店相册类型
    public static final String ALL = "全部";
    public static final String EXTERIOR = "外景";
    public static final String INTERIOR = "内景";
    public static final String ROOMS = "客房";
    public static final String ELSE = "其他";

    //确认中修改订单
    public static final int UPDATE = 9001;
    //已确认 修改订单
    public static final int AFFIRM_UPDATE = 9002;
    //购物车
    public static final  int SHOP_CART_CODE = 1010;

    /**REQUEST_CODE_FOR_RESULT**/
    public static final int REQUEST_CODE_RECEIVE_ADDRESS = 1101;
}
