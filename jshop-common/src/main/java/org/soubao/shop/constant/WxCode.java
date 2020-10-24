package org.soubao.shop.constant;

import java.util.HashMap;
import java.util.Map;

public class WxCode {
    //微信错误码与中文提示集合
    private static final Map<Integer, String> wxCodeMap;

    static {
        wxCodeMap = new HashMap<>();
        wxCodeMap.put(-1, "系统繁忙");
        wxCodeMap.put(10003,"redirect_uri域名与后台配置不一致");
        wxCodeMap.put(10004,"此公众号被封禁");
        wxCodeMap.put(10005,"此公众号并没有这些scope的权限");
        wxCodeMap.put(10006,"必须关注此测试号");
        wxCodeMap.put(10009,"操作太频繁了，请稍后重试");
        wxCodeMap.put(10010,"scope不能为空");
        wxCodeMap.put(10011,"redirect_uri不能为空");
        wxCodeMap.put(10012,"appid不能为空");
        wxCodeMap.put(10013,"state不能为空");
        wxCodeMap.put(10015,"公众号未授权第三方平台，请检查授权状态");
        wxCodeMap.put(10016,"不支持微信开放平台的Appid，请使用公众号Appid");

        wxCodeMap.put(40001, "不合法的调用凭证");
        wxCodeMap.put(40002, "不合法的grant_type");
        wxCodeMap.put(40003, "不合法的OpenID");
        wxCodeMap.put(40004, "不合法的媒体文件类型");
        wxCodeMap.put(40007, "不合法的media_id");
        wxCodeMap.put(40008, "不合法的message_type");
        wxCodeMap.put(40009, "不合法的图片大小");
        wxCodeMap.put(40010, "不合法的语音大小");
        wxCodeMap.put(40011, "不合法的视频大小");
        wxCodeMap.put(40012, "不合法的缩略图大小");
        wxCodeMap.put(40013, "不合法的AppID");
        wxCodeMap.put(40014, "不合法的access_token");
        wxCodeMap.put(40015, "不合法的菜单类型");
        wxCodeMap.put(40016, "不合法的菜单按钮个数");
        wxCodeMap.put(40017, "不合法的按钮类型");
        wxCodeMap.put(40018, "不合法的按钮名称长度");
        wxCodeMap.put(40019, "不合法的按钮KEY长度");
        wxCodeMap.put(40020, "不合法的url长度");
        wxCodeMap.put(40023, "不合法的子菜单按钮个数");
        wxCodeMap.put(40024, "不合法的子菜单类型");
        wxCodeMap.put(40025, "不合法的子菜单按钮名称长度");
        wxCodeMap.put(40026, "不合法的子菜单按钮KEY长度");
        wxCodeMap.put(40027, "不合法的子菜单按钮url长度");
        wxCodeMap.put(40029, "不合法或已过期的code");
        wxCodeMap.put(40030, "不合法的refresh_token");
        wxCodeMap.put(40036, "不合法的template_id长度");
        wxCodeMap.put(40037, "不合法的template_id");
        wxCodeMap.put(40039, "不合法的url长度");
        wxCodeMap.put(40048, "不合法的url域名");
        wxCodeMap.put(40054, "不合法的子菜单按钮url域名");
        wxCodeMap.put(40055, "不合法的菜单按钮url域名");
        wxCodeMap.put(40066, "不合法的url");
        wxCodeMap.put(40164, "服务器IP没有在白名单里");
        wxCodeMap.put(41001, "缺失access_token参数");
        wxCodeMap.put(41002, "缺失appid参数");
        wxCodeMap.put(41003, "缺失refresh_token参数");
        wxCodeMap.put(41004, "缺失secret参数");
        wxCodeMap.put(41005, "缺失二进制媒体文件");
        wxCodeMap.put(41006, "缺失media_id参数");
        wxCodeMap.put(41007, "缺失子菜单数据");
        wxCodeMap.put(41008, "缺失code参数");
        wxCodeMap.put(41009, "缺失openid参数");
        wxCodeMap.put(41010, "缺失url参数");
        wxCodeMap.put(42001, "access_token超时");
        wxCodeMap.put(42002, "refresh_token超时");
        wxCodeMap.put(42003, "code超时");
        wxCodeMap.put(43001, "需要使用GET方法请求");
        wxCodeMap.put(43002, "需要使用POST方法请求");
        wxCodeMap.put(43003, "需要使用HTTPS");
        wxCodeMap.put(43004, "需要订阅关系");
        wxCodeMap.put(44001, "空白的二进制数据");
        wxCodeMap.put(44002, "空白的POST数据");
        wxCodeMap.put(44003, "空白的news数据");
        wxCodeMap.put(44004, "空白的内容");
        wxCodeMap.put(44005, "空白的列表");
        wxCodeMap.put(45001, "二进制文件超过限制");
        wxCodeMap.put(45002, "content参数超过限制");
        wxCodeMap.put(45003, "title参数超过限制");
        wxCodeMap.put(45004, "description参数超过限制");
        wxCodeMap.put(45005, "url参数长度超过限制");
        wxCodeMap.put(45006, "picurl参数超过限制");
        wxCodeMap.put(45007, "播放时间超过限制（语音为60s最大）");
        wxCodeMap.put(45008, "article参数超过限制");
        wxCodeMap.put(45009, "接口调动频率超过限制");
        wxCodeMap.put(45010, "建立菜单被限制");
        wxCodeMap.put(45011, "频率限制");
        wxCodeMap.put(45012, "模板大小超过限制");
        wxCodeMap.put(45015, "响应超时或者已取消关注");
        wxCodeMap.put(45016, "不能修改默认组");
        wxCodeMap.put(45017, "修改组名过长");
        wxCodeMap.put(45018, "组数量过多");
        wxCodeMap.put(45027, "模板与所在行业冲突");
        wxCodeMap.put(45056, "创建的标签数过多，请注意不能超过100个");
        wxCodeMap.put(45058, "不能修改0/1/2这三个系统默认保留的标签");
        wxCodeMap.put(45157, "标签名非法，请注意不能和其他标签重名");
        wxCodeMap.put(45158, "标签名长度超过30个字节");
        wxCodeMap.put(45159, "非法的tag_id");
        wxCodeMap.put(47001, "数据格式错误");

        wxCodeMap.put(50001, "接口未授权");

        wxCodeMap.put(61004, "服务器地址尚未登记");
        wxCodeMap.put(61005, "组件ticket已过期");

        wxCodeMap.put(85001, "微信号不存在或微信号设置为不可搜索");
        wxCodeMap.put(85002, "小程序绑定的体验者数量达到上限");
        wxCodeMap.put(85003, "微信号绑定的小程序体验者达到上限");
        wxCodeMap.put(85004, "微信号已经绑定");
        wxCodeMap.put(85006, "标签格式错误");
        wxCodeMap.put(85007, "页面路径错误");
        wxCodeMap.put(85008, "类目填写错误");
        wxCodeMap.put(85009, "已经有正在审核的版本");
        wxCodeMap.put(85010, "item_list有项目为空");
        wxCodeMap.put(85011, "标题填写错误");
        wxCodeMap.put(85012, "无效的审核id");
        wxCodeMap.put(85013, "无效的自定义配置");
        wxCodeMap.put(85014, "无效的模版编号");
        wxCodeMap.put(85019, "没有审核版本");
        wxCodeMap.put(85020, "审核状态未满足发布");
        wxCodeMap.put(85021, "状态不可变");
        wxCodeMap.put(85022, "action非法");
        wxCodeMap.put(85023, "审核列表填写的项目数不在1-5以内");
        wxCodeMap.put(86000, "不是由第三方代小程序进行调用");
        wxCodeMap.put(86001, "不存在第三方的已经提交的代码");
        wxCodeMap.put(86002, "小程序还未设置昵称、头像、简介。请先设置完后再重新提交");
        wxCodeMap.put(89000, "该公众号/小程序已经绑定了开放平台帐号");
        wxCodeMap.put(89001, "授权者与开放平台帐号主体不相同");
        wxCodeMap.put(89002, "该公众号/小程序未绑定微信开放平台帐号");
        wxCodeMap.put(89003, "该开放平台帐号并非通过api创建，不允许操作");
        wxCodeMap.put(89004, "该开放平台帐号所绑定的公众号/小程序已达上限（100个）");
    }

    public static String getErrMsg(Integer code) {
        return wxCodeMap.get(code);
    }

}
