package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class Project01_E {
  // 지도 이미지 생성 메서드
  public static void map_service(String point_x, String point_y, String address) {
    String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
    String client_id = "akmx9d5ucl";
    String client_secret = "XlqBBYW7dXOlEJ7iBpDyInhoUriUqNCMnfzzXXj7";

    try {
      String pos = URLEncoder.encode(point_x + " " + point_y, "UTF-8");
      String url = URL_STATICMAP;

      url += "center=" + point_x + "," + point_y;
      url += "&level=16&w=700&h=500";
      url += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(address, "UTF-8");

      URL u = new URL(url);
      HttpURLConnection con = (HttpURLConnection) u.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
      con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);

      int responseCode = con.getResponseCode();
      BufferedReader br;

      if (responseCode == 200) { // 정상 호출
        InputStream is = con.getInputStream();
        int read = 0;
        byte[] bytes = new byte[1024];
        // 랜덤한 이름으로 파일 생성
        String tempname = Long.valueOf(new Date().getTime()).toString();
        File f = new File(tempname + ".jpg");
        f.createNewFile();

        OutputStream outputStream = new FileOutputStream(f);
        while ((read = is.read(bytes)) != -1) {
          outputStream.write(bytes, 0, read);
        }
        is.close();
      } else { // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = br.readLine()) != null) {
          response.append(inputLine);
        }
        br.close();
        System.out.println(response.toString());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    // String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
    String client_id = "akmx9d5ucl";
    String client_secret = "XlqBBYW7dXOlEJ7iBpDyInhoUriUqNCMnfzzXXj7";

    BufferedReader io = new BufferedReader(new InputStreamReader(System.in));

    try {
      System.out.print("주소를 입력하세요.");

      String address = io.readLine();
      String addr = URLEncoder.encode(address, "UTF-8");
      String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;

      URL url = new URL(reqUrl);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
      con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);

      BufferedReader br;
      int responseCode = con.getResponseCode(); // 200

      if (responseCode == 200) {
        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
      } else {
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }

      String line;
      StringBuffer response = new StringBuffer(); // JSON

      String x = "";
      String y = "";
      String z = "";

      while ((line = br.readLine()) != null) {
        response.append(line);
      }

      br.close();

      JSONTokener tokener = new JSONTokener(response.toString());
      JSONObject object = new JSONObject(tokener);
      System.out.println(object.toString(2));

      JSONArray arr = object.getJSONArray("addresses");

      for (int i = 0; i < arr.length(); i++) {
        JSONObject temp = (JSONObject) arr.get(i);
        System.out.print("address:" + temp.get("roadAddress"));
        System.out.print("jibunAddress:" + temp.get("jibunAddress"));
        System.out.print("경도:" + temp.get("x"));
        System.out.print("위도:" + temp.get("y"));

        // 추가된 부분
        x = (String) temp.get("x");
        y = (String) temp.get("y");
        z = (String) temp.get("roadAddress");
      }
      // 추가된 부분
      map_service(x, y, z);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
