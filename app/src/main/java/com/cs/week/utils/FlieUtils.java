package com.cs.week.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by chenshuai on 2017/5/4.
 */

public class FlieUtils {
    /**
     * 向File中保存数据
     */
    private    void  saveDataToFile(Context context,String fileName){
        String data="Hello,All";
        FileOutputStream fileOutputStream=null;
        OutputStreamWriter outputStreamWriter=null;
        BufferedWriter bufferedWriter=null;
        try {
            fileOutputStream=context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStreamWriter=new OutputStreamWriter(fileOutputStream);
            bufferedWriter=new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (bufferedWriter!=null) {
                    bufferedWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 从File中读取数据
     */
    private  void getDataFromFile(Context context, String fileName){
        FileInputStream fileInputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        StringBuilder stringBuilder=null;
        String line=null;
        try {
            stringBuilder=new StringBuilder();
            fileInputStream=context.openFileInput(fileName);
            inputStreamReader=new InputStreamReader(fileInputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            System.out.println("--->"+stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (bufferedReader!=null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
