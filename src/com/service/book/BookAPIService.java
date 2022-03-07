package com.service.book;

import com.config.openapi.ApiKey;
import com.dto.bookshop.BookQueryType;
import com.errors.exception.InvalidValueException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class BookAPIService {
    private static final ApiKey apiKey = ApiKey.getInstance();
    private static final String clientID = apiKey.getNaverApiId(); //api 사용 신청시 제공되는 아이디
    private static final String clientSecret = apiKey.getNaverApiSecret(); //패스워드

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public JSONObject searchBook(BookQueryType key, Object value) {

        try {
            if (value instanceof String) {
                value = URLEncoder.encode((String) value, "UTF-8");
            }
            String apiURL = "https://openapi.naver.com/v1/search/book_adv.xml?" + key.getQuery() + "=" + value;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientID);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return XML.toJSONObject(response.toString()).getJSONObject("rss").getJSONObject("channel").getJSONObject("item");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InvalidValueException("도서 정보를 얻을 수 없음");
        }
    }
}
