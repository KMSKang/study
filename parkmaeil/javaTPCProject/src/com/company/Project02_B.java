package com.company;


import com.company.kr.inflearn.DownloadBroker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Project02_B {
  public static void main(String[] args) {
    String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try {
      System.out.print("[입력->년(yyyy)-월(mm)-일(dd):");
      String bible = br.readLine();
      url = url + "&Base_de=" + bible + "&bibleType=1";
      System.out.println("==========================");
      Document doc = Jsoup.connect(url).post();
      Element bible_text = doc.select(".bible_text").first();
      System.out.println(bible_text.text());

      Element bibleinfo_box = doc.select(".bibleinfo_box").first();
      System.out.println(bibleinfo_box.text());

      Elements liList = doc.select(".body_list > li");
      for (Element li : liList) {
        System.out.print(li.select(".num").first().text() + ":");
        System.out.println(li.select(".info").first().text());
      }
      // 리소스 다운로드(mp3, image)
      // mp3
//      Element tag = doc.select("source").first();
//      String dPath = tag.attr("src").trim();
//      System.out.println(dPath);
//      String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);
      // image
      Element tag = doc.select(".img > img").first();
      String dPath = "https://sum.su.or.kr:8888" + tag.attr("src").trim();
      System.out.println(dPath);
      String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);

      Runnable r = new DownloadBroker(dPath, fileName);
      Thread dLoad = new Thread(r);
      dLoad.start();
      for(int i=0; i<10; i++) {
        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.println("" + (i+1));
      }
      System.out.println();
      System.out.println("==========================");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
