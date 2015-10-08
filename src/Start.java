import java.util.HashMap;
import java.util.Map;

/**
 * Created by padeoe on 2015/10/8.
 * 2015年10月7日晚11点33分，新浪新闻报道了“http://news.sina.com.cn/c/nd/2015-10-07/doc-ifximrxn8244692.shtml”
 * 有人（http://bbs.nju.edu.cn/vd85364/main.html?bbstcon%3Fboard%3DPictures%26file%3DM.1444235423.A%26start%3D0）表示半夜刷出一万条“评论”（事实上一万多只是参与人数，评论只有几百），怀疑其中有中纪委控制舆论的水军
 * 我是不大相信这种阴谋论。于是写了如下代码爬下了所有评论，并比对评论者IP是否有重复
 * 截至2015-10-08-20:48，共716条评论，独立IP有623个
 * 评论数并不多，并未发现相同IP大量重复发帖的情况
 * 只能说怀疑政府雇佣水军的有点迫害妄想症了
 */
public class Start {
    public static void main(String args[]){
        HashMap<String, Integer> ipMap = new HashMap<>();

        for(int i=1;i<=4;i++){
            int ccc = 0;
            String postData="version=1&format=js&channel=gn&newsid=comos-fximrxn8244692&group=&compress=0&ie=utf-8&oe=utf-8&page="+i+"&page_size=200";
            String cookie="";
            String URL="http://comment5.news.sina.com.cn/page/info?"+postData;
            String result=NetworkUtils.postWithCookie(postData,cookie,URL,500);
            int index = result.indexOf("cmntlist");
            int end = result.indexOf("\"news\"");
            while (index < end) {
                index = result.indexOf("\"ip\": \"", index);
                if (index == -1 || index >= end)
                    break;
                ++ccc;


                index += 7;
                int last = result.indexOf('"', index);
                String ip = result.substring(index, last);
                index = last;

                Integer count = ipMap.get(ip);
                if (count == null)
                    count = 0;
                ++count;
                ipMap.put(ip, count);
            }
            System.out.println(ccc);
            if (ccc == 1) {
                System.out.println(result);
            }
        }

        for (Map.Entry<String, Integer> entry : ipMap.entrySet()) {
            if (entry.getValue()>1) {
                System.out.println("IP: " + entry.getKey() + ", count = " + entry.getValue());
            }
        }

        System.out.println(ipMap.size());
    }
}
