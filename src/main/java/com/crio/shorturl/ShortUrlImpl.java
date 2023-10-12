package com.crio.shorturl;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.security.SecureRandom;
import java.util.Iterator;


public class ShortUrlImpl implements ShortUrl {
String url="http://short.url/";
    // Initialization of a HashMap
    // using Generics
//    HashMap<String, String> hm1 = new HashMap<String, String>();
    Random random =new Random();
    HashMap<String, String>
            map = new HashMap<>();
    HashMap<String, String>
            revesemap = new HashMap<>();
    HashMap<String,Integer> table=new HashMap<>();



    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString()
    {
        int len=9;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
        {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));


        }
        return sb.toString();
    }


    @Override
    public String registerNewUrl(String longUrl) {

        String shortUrl="";
        try {

            boolean isavailable = map.containsKey(longUrl);

            if (isavailable)
            {
                shortUrl=map.get(longUrl);
                return shortUrl;
            }
            else
            {
                map.put(longUrl, url+randomString());
                shortUrl=map.get(longUrl);
                revesemap.put(shortUrl,longUrl);
            }



        } catch (Exception e) {
            System.out.println(e);
        }

        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {


        if (revesemap.containsKey(shortUrl))
        {
            shortUrl= null;

        }

        else {

            map.put(longUrl,shortUrl);
            shortUrl=map.get(longUrl);
            revesemap.put(shortUrl,longUrl);

        }

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        int hit=0;

        String longUrlvalue;
        if (!revesemap.containsKey(shortUrl))
        {
            return null;
        }
        else {



            longUrlvalue=revesemap.get(shortUrl);
            table.putIfAbsent(longUrlvalue,hit);
            table.put(longUrlvalue, table.get(longUrlvalue) + 1);

        }

        return longUrlvalue;

    }

    @Override
    public Integer getHitCount(String longUrl) {
        System.out.println(table);

        if(table.get(longUrl)==null)
        {
            return 0;
        }

        return table.get(longUrl);
    }

    @Override
    public String delete(String longUrl) {



        String returned_value = map.remove(longUrl);
        // Get the iterator over the HashMap
        Iterator<Map.Entry<String, String> >
                iterator = revesemap.entrySet().iterator();

// Iterate over the HashMap
        while (iterator.hasNext()) {

// Get the entry at this iteration
            Map.Entry<String, String>
                    entry
                    = iterator.next();

// Check if this value is the required value
            if (longUrl.equals(entry.getValue())) {

                // Remove this entry from HashMap
                iterator.remove();
            }
        }
        return returned_value;

    }
    }
