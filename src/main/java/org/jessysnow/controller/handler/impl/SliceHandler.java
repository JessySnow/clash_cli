package org.jessysnow.controller.handler.impl;

import org.jessysnow.controller.handler.AbstractHandler;
import org.jessysnow.controller.handler.ProxyInfoHandler;

/**
 * Get proxies name array part from response content
 */
public class SliceHandler extends AbstractHandler {
    public static final String PREFIX = "{\"proxies\":{";
    public static final String SUFFIX = "}}";

    private void slice(){
        int length = content.length();
        int begin = PREFIX.length() + 1;
        int end = length - SUFFIX.length();
        super.content = super.content.substring(begin, end);
    }

    @Override
    public String handle() {
        slice();
        return super.content.replace("},\"","}\n\"");
    }

    public static void main(String[] args) {
        System.out.println(new SliceHandler().setContent("{\"proxies\":{\"Bitz Net\":{\"all\":[\"自动选择\",\"故障转移\",\"HongKong\",\"\uD83C\uDDED\uD83C\uDDF0 香港-核心访问 HKT\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-核心访问 DP\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-边缘访问 DP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"\uD83C\uDDEF\uD83C\uDDF5 日本-核心访问 DP\",\"\uD83C\uDDF0\uD83C\uDDF7 韩国-核心访问 VUKR\",\"\uD83C\uDDF8\uD83C\uDDEC 新加坡-核心访问 DP\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-核心访问 GCore\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-边缘访问 GCore\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-核心访问 DP\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-核心访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-边缘访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-核心访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-边缘访问 DP\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-核心访问 VUAU\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-边缘访问 VUAU\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-核心访问 Comfac\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-边缘访问 Comfac\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-核心访问 DeHost\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-边缘访问 DeHost\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-核心访问 DonWeb\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-边缘访问 DonWeb\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-核心访问 Vik\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-边缘访问 Vik\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-核心访问 GCore\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-边缘访问 GCore\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-核心访问 DOIN\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-边缘访问 DOIN\"],\"history\":[],\"name\":\"Bitz Net\",\"now\":\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"type\":\"Selector\",\"udp\":true},\"DIRECT\":{\"history\":[],\"name\":\"DIRECT\",\"type\":\"Direct\",\"udp\":true},\"GLOBAL\":{\"all\":[\"DIRECT\",\"REJECT\",\"HongKong\",\"\uD83C\uDDED\uD83C\uDDF0 香港-核心访问 HKT\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-核心访问 DP\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-边缘访问 DP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"\uD83C\uDDEF\uD83C\uDDF5 日本-核心访问 DP\",\"\uD83C\uDDF0\uD83C\uDDF7 韩国-核心访问 VUKR\",\"\uD83C\uDDF8\uD83C\uDDEC 新加坡-核心访问 DP\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-核心访问 GCore\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-边缘访问 GCore\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-核心访问 DP\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-核心访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-边缘访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-核心访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-边缘访问 DP\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-核心访问 VUAU\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-边缘访问 VUAU\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-核心访问 Comfac\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-边缘访问 Comfac\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-核心访问 DeHost\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-边缘访问 DeHost\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-核心访问 DonWeb\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-边缘访问 DonWeb\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-核心访问 Vik\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-边缘访问 Vik\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-核心访问 GCore\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-边缘访问 GCore\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-核心访问 DOIN\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-边缘访问 DOIN\",\"Bitz Net\",\"自动选择\",\"故障转移\"],\"history\":[],\"name\":\"GLOBAL\",\"now\":\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\",\"type\":\"Selector\",\"udp\":true},\"HongKong\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.258921+08:00\",\"delay\":250},{\"time\":\"2022-11-06T07:47:05.293219+08:00\",\"delay\":284}],\"name\":\"HongKong\",\"type\":\"Trojan\",\"udp\":true},\"REJECT\":{\"history\":[],\"name\":\"REJECT\",\"type\":\"Reject\",\"udp\":true},\"故障转移\":{\"all\":[\"HongKong\",\"\uD83C\uDDED\uD83C\uDDF0 香港-核心访问 HKT\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-核心访问 DP\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-边缘访问 DP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"\uD83C\uDDEF\uD83C\uDDF5 日本-核心访问 DP\",\"\uD83C\uDDF0\uD83C\uDDF7 韩国-核心访问 VUKR\",\"\uD83C\uDDF8\uD83C\uDDEC 新加坡-核心访问 DP\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-核心访问 GCore\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-边缘访问 GCore\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-核心访问 DP\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-核心访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-边缘访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-核心访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-边缘访问 DP\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-核心访问 VUAU\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-边缘访问 VUAU\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-核心访问 Comfac\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-边缘访问 Comfac\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-核心访问 DeHost\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-边缘访问 DeHost\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-核心访问 DonWeb\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-边缘访问 DonWeb\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-核心访问 Vik\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-边缘访问 Vik\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-核心访问 GCore\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-边缘访问 GCore\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-核心访问 DOIN\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-边缘访问 DOIN\"],\"history\":[],\"name\":\"故障转移\",\"now\":\"HongKong\",\"type\":\"Fallback\",\"udp\":true},\"自动选择\":{\"all\":[\"HongKong\",\"\uD83C\uDDED\uD83C\uDDF0 香港-核心访问 HKT\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-核心访问 DP\",\"\uD83C\uDDFA\uD83C\uDDF8 美国-边缘访问 DP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\",\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"\uD83C\uDDEF\uD83C\uDDF5 日本-核心访问 DP\",\"\uD83C\uDDF0\uD83C\uDDF7 韩国-核心访问 VUKR\",\"\uD83C\uDDF8\uD83C\uDDEC 新加坡-核心访问 DP\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-核心访问 GCore\",\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-边缘访问 GCore\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-核心访问 DP\",\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-核心访问 DP\",\"\uD83C\uDDEC\uD83C\uDDE7 英国-边缘访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-核心访问 DP\",\"\uD83C\uDDE9\uD83C\uDDEA 德国-边缘访问 DP\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-核心访问 VUAU\",\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-边缘访问 VUAU\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-核心访问 Comfac\",\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-边缘访问 Comfac\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-核心访问 DeHost\",\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-边缘访问 DeHost\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-核心访问 DonWeb\",\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-边缘访问 DonWeb\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-核心访问 Vik\",\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-边缘访问 Vik\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-核心访问 GCore\",\"\uD83C\uDDE7\uD83C\uDDF7 巴西-边缘访问 GCore\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-核心访问 DOIN\",\"\uD83C\uDDEE\uD83C\uDDF3 印度-边缘访问 DOIN\"],\"history\":[],\"name\":\"自动选择\",\"now\":\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"type\":\"URLTest\",\"udp\":true},\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-核心访问 DonWeb\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.648481+08:00\",\"delay\":1865},{\"time\":\"2022-11-06T07:47:08.67042+08:00\",\"delay\":1910}],\"name\":\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-核心访问 DonWeb\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-边缘访问 DonWeb\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.725336+08:00\",\"delay\":1938},{\"time\":\"2022-11-06T07:47:08.745772+08:00\",\"delay\":1923}],\"name\":\"\uD83C\uDDE6\uD83C\uDDF7 阿根廷-边缘访问 DonWeb\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-核心访问 VUAU\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.78318+08:00\",\"delay\":817},{\"time\":\"2022-11-06T07:47:06.822297+08:00\",\"delay\":814}],\"name\":\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-核心访问 VUAU\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-边缘访问 VUAU\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.897613+08:00\",\"delay\":814},{\"time\":\"2022-11-06T07:47:06.941269+08:00\",\"delay\":827}],\"name\":\"\uD83C\uDDE6\uD83C\uDDFA 澳大利亚-边缘访问 VUAU\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE7\uD83C\uDDF7 巴西-核心访问 GCore\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.921528+08:00\",\"delay\":1899},{\"time\":\"2022-11-06T07:47:08.97722+08:00\",\"delay\":1926}],\"name\":\"\uD83C\uDDE7\uD83C\uDDF7 巴西-核心访问 GCore\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE7\uD83C\uDDF7 巴西-边缘访问 GCore\":{\"history\":[{\"time\":\"2022-11-06T07:47:09.983971+08:00\",\"delay\":1868},{\"time\":\"2022-11-06T07:47:09.984435+08:00\",\"delay\":1851}],\"name\":\"\uD83C\uDDE7\uD83C\uDDF7 巴西-边缘访问 GCore\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE9\uD83C\uDDEA 德国-核心访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.654214+08:00\",\"delay\":1063},{\"time\":\"2022-11-06T07:47:06.686225+08:00\",\"delay\":1115}],\"name\":\"\uD83C\uDDE9\uD83C\uDDEA 德国-核心访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDE9\uD83C\uDDEA 德国-边缘访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:07.021667+08:00\",\"delay\":1092},{\"time\":\"2022-11-06T07:47:07.050829+08:00\",\"delay\":1075}],\"name\":\"\uD83C\uDDE9\uD83C\uDDEA 德国-边缘访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEB\uD83C\uDDF7 法国-核心访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.382638+08:00\",\"delay\":1123},{\"time\":\"2022-11-06T07:47:06.452101+08:00\",\"delay\":1158}],\"name\":\"\uD83C\uDDEB\uD83C\uDDF7 法国-核心访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.526376+08:00\",\"delay\":1121},{\"time\":\"2022-11-06T07:47:06.579423+08:00\",\"delay\":1170}],\"name\":\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEC\uD83C\uDDE7 英国-核心访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.786881+08:00\",\"delay\":1321},{\"time\":\"2022-11-06T07:47:06.824738+08:00\",\"delay\":1337}],\"name\":\"\uD83C\uDDEC\uD83C\uDDE7 英国-核心访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEC\uD83C\uDDE7 英国-边缘访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.691154+08:00\",\"delay\":1115},{\"time\":\"2022-11-06T07:47:06.865425+08:00\",\"delay\":1322}],\"name\":\"\uD83C\uDDEC\uD83C\uDDE7 英国-边缘访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDED\uD83C\uDDF0 香港-核心访问 HKT\":{\"history\":[{\"time\":\"2022-11-06T07:47:09.695174+08:00\",\"delay\":4686},{\"time\":\"2022-11-06T07:47:09.697813+08:00\",\"delay\":4689}],\"name\":\"\uD83C\uDDED\uD83C\uDDF0 香港-核心访问 HKT\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEE\uD83C\uDDF3 印度-核心访问 DOIN\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.671238+08:00\",\"delay\":544},{\"time\":\"2022-11-06T07:47:08.714434+08:00\",\"delay\":573}],\"name\":\"\uD83C\uDDEE\uD83C\uDDF3 印度-核心访问 DOIN\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEE\uD83C\uDDF3 印度-边缘访问 DOIN\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.570871+08:00\",\"delay\":562},{\"time\":\"2022-11-06T07:47:05.590447+08:00\",\"delay\":581}],\"name\":\"\uD83C\uDDEE\uD83C\uDDF3 印度-边缘访问 DOIN\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDEF\uD83C\uDDF5 日本-核心访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.465074+08:00\",\"delay\":456},{\"time\":\"2022-11-06T07:47:05.48757+08:00\",\"delay\":478}],\"name\":\"\uD83C\uDDEF\uD83C\uDDF5 日本-核心访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF0\uD83C\uDDF7 韩国-核心访问 VUKR\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.54237+08:00\",\"delay\":533},{\"time\":\"2022-11-06T07:47:05.575992+08:00\",\"delay\":567}],\"name\":\"\uD83C\uDDF0\uD83C\uDDF7 韩国-核心访问 VUKR\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-核心访问 Comfac\":{\"history\":[{\"time\":\"2022-11-06T07:47:09.497083+08:00\",\"delay\":0},{\"time\":\"2022-11-06T07:47:09.557995+08:00\",\"delay\":0}],\"name\":\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-核心访问 Comfac\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-边缘访问 Comfac\":{\"history\":[{\"time\":\"2022-11-06T07:47:09.63747+08:00\",\"delay\":0},{\"time\":\"2022-11-06T07:47:09.683475+08:00\",\"delay\":0}],\"name\":\"\uD83C\uDDF5\uD83C\uDDED 菲律宾-边缘访问 Comfac\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-核心访问 GCore\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.113398+08:00\",\"delay\":861},{\"time\":\"2022-11-06T07:47:06.6614+08:00\",\"delay\":1410}],\"name\":\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-核心访问 GCore\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-边缘访问 GCore\":{\"history\":[{\"time\":\"2022-11-06T07:47:06.082561+08:00\",\"delay\":825},{\"time\":\"2022-11-06T07:47:06.760174+08:00\",\"delay\":1489}],\"name\":\"\uD83C\uDDF7\uD83C\uDDFA 俄罗斯-边缘访问 GCore\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF8\uD83C\uDDEC 新加坡-核心访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.404465+08:00\",\"delay\":395},{\"time\":\"2022-11-06T07:47:05.408992+08:00\",\"delay\":400}],\"name\":\"\uD83C\uDDF8\uD83C\uDDEC 新加坡-核心访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-核心访问 DeHost\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.140953+08:00\",\"delay\":1486},{\"time\":\"2022-11-06T07:47:08.156022+08:00\",\"delay\":1494}],\"name\":\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-核心访问 DeHost\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-边缘访问 DeHost\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.197002+08:00\",\"delay\":1510},{\"time\":\"2022-11-06T07:47:08.372881+08:00\",\"delay\":1681}],\"name\":\"\uD83C\uDDF9\uD83C\uDDF7 土耳其-边缘访问 DeHost\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.251186+08:00\",\"delay\":242},{\"time\":\"2022-11-06T07:47:05.270283+08:00\",\"delay\":261}],\"name\":\"\uD83C\uDDF9\uD83C\uDDFC 台湾-核心访问 BGP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.251365+08:00\",\"delay\":242},{\"time\":\"2022-11-06T07:47:05.257455+08:00\",\"delay\":248}],\"name\":\"\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-核心访问 Vik\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.115668+08:00\",\"delay\":1250},{\"time\":\"2022-11-06T07:47:08.132769+08:00\",\"delay\":1307}],\"name\":\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-核心访问 Vik\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-边缘访问 Vik\":{\"history\":[{\"time\":\"2022-11-06T07:47:08.127123+08:00\",\"delay\":1229},{\"time\":\"2022-11-06T07:47:08.169799+08:00\",\"delay\":1228}],\"name\":\"\uD83C\uDDFA\uD83C\uDDE6 乌克兰-边缘访问 Vik\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDFA\uD83C\uDDF8 美国-核心访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.965427+08:00\",\"delay\":956},{\"time\":\"2022-11-06T07:47:06.007647+08:00\",\"delay\":999}],\"name\":\"\uD83C\uDDFA\uD83C\uDDF8 美国-核心访问 DP\",\"type\":\"Trojan\",\"udp\":true},\"\uD83C\uDDFA\uD83C\uDDF8 美国-边缘访问 DP\":{\"history\":[{\"time\":\"2022-11-06T07:47:05.928917+08:00\",\"delay\":920},{\"time\":\"2022-11-06T07:47:05.975045+08:00\",\"delay\":966}],\"name\":\"\uD83C\uDDFA\uD83C\uDDF8 美国-边缘访问 DP\",\"type\":\"Trojan\",\"udp\":true}}}").handle());
    }
}
