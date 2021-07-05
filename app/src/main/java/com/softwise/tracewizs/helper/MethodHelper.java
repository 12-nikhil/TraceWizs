package com.softwise.tracewizs.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//import com.google.firebase.messaging.FirebaseMessaging;
import com.softwise.tracewizs.models.LoginResponse;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.softwise.tracewizs.utils.TraceWizConstant.BEARER;

public class MethodHelper {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void jumpActivity(Context context, Class<? extends Activity> secondActivity) {
        Intent intent = new Intent(context, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
    public static void saveLoginDataInSP(Context context, LoginResponse loginResponse)
    {
        SPTRaceWizsConstants.saveLoginStatus(context,true);
        SPTRaceWizsConstants.saveUserName(context,loginResponse.getUser().getFirstName());
        SPTRaceWizsConstants.saveUserId(context,loginResponse.getUser().getUserId());
        SPTRaceWizsConstants.saveToken(context,loginResponse.getAccessToken());
    }
    public static String getTokenFromSp(Context context)
    {
        return BEARER+" "+SPTRaceWizsConstants.getToken(context);
    }
    public static String getCurrentDate(String uploadFor)
    {
        Date date=new Date(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        String dateText = df2.format(date);
        System.out.println(dateText);
        return dateText+uploadFor;
    }
   /* public static void subScribeTopic(String mobile)
    {
        FirebaseMessaging.getInstance().subscribeToTopic(mobile);
    }
    public static void unSubscribeTopic(String mobile)
    {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(mobile);
    }*/

    /*
    * public void convertImageToPDF(String fileName,List<UploadFiles> fileList) {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        if (!directory.exists()) {
            directory.mkdirs();
        }
        PdfDocument pdfDocument = new PdfDocument();
        int count = 1;
        for (UploadFiles uploadFiles : fileList) {
            Bitmap bitmap = BitmapFactory.decodeFile(uploadFiles.getFilePath() + "/" + uploadFiles.getFileName());
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(600, 1280, count).create();
            PdfDocument.Page page = pdfDocument.startPage(myPageInfo);

            page.getCanvas().drawBitmap(bitmap, 0, 0, null);

            //String pdfFile = directory + uploadFiles.getFileName();
            // File myPDFFile = new File(uploadFiles.getFilePath()+"/invoice"+".pdf");
            pdfDocument.finishPage(page);
            count++;
        }
        File myPDFFile = new File(directory + "/"+fileName + PDF_EXTENSION);
        try {
            pdfDocument.writeTo(new FileOutputStream(myPDFFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }*/
}

