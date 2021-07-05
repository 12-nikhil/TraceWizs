package com.softwise.tracewizs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Optimizer;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.softwise.tracewizs.listeners.OnPDFCreatedInterface;
import com.softwise.tracewizs.models.ImageToPDFOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.softwise.tracewizs.utils.TraceWizConstant.IMAGE_SCALE_TYPE_ASPECT_RATIO;
import static com.softwise.tracewizs.utils.TraceWizConstant.PG_NUM_STYLE_PAGE_X_OF_N;
import static com.softwise.tracewizs.utils.TraceWizConstant.PG_NUM_STYLE_X_OF_N;


/**
 * An async task that converts selected images to Pdf
 */
public class CreatePdf extends AsyncTask<String, String, String> {

    private String mFileName;
    private ArrayList<String> mImagesUri;
    private int mBorderWidth;
    private OnPDFCreatedInterface mOnPDFCreatedInterface;
    private boolean mSuccess;
    private String mQualityString;
    private String mPath;
    private int mMarginTop;
    private int mMarginBottom;
    private int mMarginRight;
    private int mMarginLeft;
    private String mImageScaleType;
    private String mPageNumStyle;
    private final int mPageColor;

    public CreatePdf(ImageToPDFOptions mImageToPDFOptions, String parentPath,
                     OnPDFCreatedInterface onPDFCreated) {
      this.mImagesUri = new ArrayList<>();
        this.mImagesUri = mImageToPDFOptions.getImagesUri();
        this.mFileName = mImageToPDFOptions.getOutFileName();
        this.mQualityString = mImageToPDFOptions.getQualityString();
        this.mOnPDFCreatedInterface = onPDFCreated;
        this.mBorderWidth = mImageToPDFOptions.getBorderWidth();
        this.mMarginTop = mImageToPDFOptions.getMarginTop();
        this.mMarginBottom = mImageToPDFOptions.getMarginBottom();
        this.mMarginRight = mImageToPDFOptions.getMarginRight();
        this.mMarginLeft = mImageToPDFOptions.getMarginLeft();
        this.mImageScaleType = mImageToPDFOptions.getImageScaleType();
        this.mPageNumStyle = mImageToPDFOptions.getPageNumStyle();
        this.mPageColor = mImageToPDFOptions.getPageColor();
        mPath = parentPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mSuccess = true;
        mOnPDFCreatedInterface.onPDFCreationStarted();
    }

    private void setFilePath() {
        mPath ="";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // File directory = new File(folder);

        if (!directory.exists()) {
            directory.mkdirs();
        }
        mPath = directory + mFileName + TraceWizConstant.PDF_EXTENSION;
    }

    @Override
    protected String doInBackground(String... params) {

        setFilePath();

        Log.v("stage 1", "store the pdf in sd card");

        Rectangle pageSize = new Rectangle(PageSize.getRectangle("A4"));
       // Rectangle pageSize = new Rectangle(100, 700, 100, 100);
        pageSize.setBackgroundColor(getBaseColor(mPageColor));
        Document document = new Document(pageSize,
                mMarginLeft, mMarginRight, mMarginTop, mMarginBottom);
        Log.v("stage 2", "Document Created");
        document.setMargins(mMarginLeft, mMarginRight, mMarginTop, mMarginBottom);
        Rectangle documentRect = document.getPageSize();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mPath));

            Log.v("Stage 3", "Pdf writer");


            document.open();

            Log.v("Stage 4", "Document opened");
            Log.e("Image Uri Size ", String.valueOf(mImagesUri.size()));

            for (int i = 0; i < mImagesUri.size(); i++) {
                int quality;
                quality = 100;
                if (StringUtils.getInstance().isNotEmpty(mQualityString)) {
                    quality = Integer.parseInt(mQualityString);
                }
                Image image = Image.getInstance(mImagesUri.get(i));
                // compressionLevel is a value between 0 (best speed) and 9 (best compression)
                double qualityMod = quality * 0.09;
                Log.e("Image quality mode ",String.valueOf(qualityMod));
                image.setCompressionLevel((int) qualityMod);
                image.setBorder(Rectangle.BOX);
                image.setBorderWidth(mBorderWidth);

                Log.v("Stage 5", "Image compressed " + qualityMod);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mImagesUri.get(i), bmOptions);

                Log.v("Stage 6", "Image path adding");

                float pageWidth = document.getPageSize().getWidth() - (mMarginLeft + mMarginRight);
                float pageHeight = document.getPageSize().getHeight() - (mMarginBottom + mMarginTop);
              /*  if (mImageScaleType.equals(IMAGE_SCALE_TYPE_ASPECT_RATIO))
                    image.scaleToFit(pageWidth, pageHeight);
                else
                    image.scaleAbsolute(pageWidth, pageHeight);*/
                image.scaleToFit(pageWidth, pageHeight);

                image.setAbsolutePosition(
                        (documentRect.getWidth() - image.getScaledWidth()) / 2,
                        (documentRect.getHeight() - image.getScaledHeight()) / 2);

                Log.v("Stage 7", "Image Alignments");
                addPageNumber(documentRect, writer);
                document.add(image);

                document.newPage();
            }

            Log.v("Stage 8", "Image adding");

            document.close();
            //writer.setFullCompression();
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            String oldPath = directory + mFileName + TraceWizConstant.PDF_EXTENSION;
            String newPath = directory + mFileName+"New" + TraceWizConstant.PDF_EXTENSION;
            reducePDFSize(oldPath,newPath);

            Log.v("Stage 7", "Document Closed" + mPath);

            Log.v("Stage 8", "Record inserted in database");

        } catch (Exception e) {
            e.printStackTrace();
            mSuccess = false;
        }

        return null;
    }

    private void addPageNumber(Rectangle documentRect, PdfWriter writer) {
        if (mPageNumStyle != null) {
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_BOTTOM,
                    getPhrase(writer, mPageNumStyle, mImagesUri.size()),
                    ((documentRect.getRight() + documentRect.getLeft()) / 2),
                    documentRect.getBottom() + 25, 0);
        }
    }

    @NonNull
    private Phrase getPhrase(PdfWriter writer, String pageNumStyle, int size) {
        Phrase phrase;
        switch (pageNumStyle) {
            case PG_NUM_STYLE_PAGE_X_OF_N:
                phrase = new Phrase(String.format("Page %d of %d", writer.getPageNumber(), size));
                break;
            case PG_NUM_STYLE_X_OF_N:
                phrase = new Phrase(String.format("%d of %d", writer.getPageNumber(), size));
                break;
            default:
                phrase = new Phrase(String.format("%d", writer.getPageNumber()));
                break;
        }
        return phrase;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mImagesUri = new ArrayList<>();
        mOnPDFCreatedInterface.onPDFCreated(mSuccess, mPath);
    }

    /**
     * Read the BaseColor of passed color
     *
     * @param color value of color in int
     */
    private BaseColor getBaseColor(int color) {
        return new BaseColor(
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    private void reducePDFSize(String pdfOld,String pdfNew)
    {
        PdfReader reader = null;
        PdfStamper stamper =null;
        try {
            reader = new PdfReader(new FileInputStream(pdfOld));
            stamper = new PdfStamper(reader, new FileOutputStream(pdfNew));
            int total = reader.getNumberOfPages() + 1;
            for ( int i=1; i<total; i++) {
                reader.setPageContent(i + 1, reader.getPageContent(i + 1));
            }
            stamper.setFullCompression();
            stamper.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}


