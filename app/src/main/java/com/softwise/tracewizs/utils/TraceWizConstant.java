package com.softwise.tracewizs.utils;

import android.Manifest;
import android.graphics.Color;

public class TraceWizConstant {
    public static final String CONTENT_TYPE = "application/json";
    public static final String MULTI_PART_CONTENT_TYPE = "multipart/form-data";
    public static final String BEARER = "Bearer";
    public static final String UNAUTHORISED = "HTTP 401";
    public static final String SELECT_UNIT = "Select Unit";
    // ****************** Intent Constant *********************
    public static final String I_GATE_ENTRY = "gate_entry";
    public static final String I_ALL_GATE_ENTRY = "all_gate_entry";
    public static final String I_IMAGE_FILE = "image_file";
    public static final String I_PDF_FILE = "pdf_file";
    public static final String I_IMAGE_FILE_NAME = "image_file_name";
    //********************** Form data********************
    public static final String FORM_INVOICE = "invoice";
    public static final String FORM_COA = "coa";
    public static final String FORM_PHOTO = "photo";
    public static final String FORM_GATE_ENTRY_ID = "gateEntryId";
    //******************** Field data ******************************
    public static final String RAW_MATERIAL = "Raw Material";
    public static final String PACKAGGING_MATERIAL = "Packging Material";
    //*********************** Role *********************************
    public static final String ROLE_GATE_ENTRY = "GATE ENTRY USER";
    public static final String ROLE_UNLOADING = "UNLOADING_USER";
    // ********************* File extension **********************
    public static final String PDF_EXTENSION = ".pdf";
    public static final String JPG_EXTENSION = ".jpg";
    public static final String INVOICE_FILE = "/invoice";
    public static final String COA_FILE = "/coa";
    public static final String EXTRA_FILE = "photo";


    public static final String DEFAULT_FONT_SIZE_TEXT = "DefaultFontSize";
    public static final int DEFAULT_FONT_SIZE = 11;
    public static final String DEFAULT_FONT_FAMILY_TEXT = "DefaultFontFamily";
    public static final String DEFAULT_FONT_FAMILY = "TIMES_ROMAN";
    public static final String DEFAULT_FONT_COLOR_TEXT = "DefaultFontColor";
    public static final int DEFAULT_FONT_COLOR = -16777216;
    // key for text to pdf (TTP) page color
    public static final String DEFAULT_PAGE_COLOR_TTP = "DefaultPageColorTTP";
    // key for images to pdf (ITP) page color
    public static final int DEFAULT_PAGE_COLOR = Color.WHITE;
    public static final String DEFAULT_PAGE_SIZE_TEXT = "DefaultPageSize";
    public static final String DEFAULT_PAGE_SIZE = "A4";
    public static final String IMAGE_SCALE_TYPE_ASPECT_RATIO = "maintain_aspect_ratio";
    public static final String PG_NUM_STYLE_PAGE_X_OF_N = "pg_num_style_page_x_of_n";
    public static final String PG_NUM_STYLE_X_OF_N = "pg_num_style_x_of_n";
    public static final String pdfDirectory = "/PDF Converter/";

    public static String BASE_URL = "http://tracewiz.trumonitor.tech:8080/api/";
    public static String IMAGE_BASE_URL = "http://tracewiz.trumonitor.tech:8080/GateEntry/";
    // ****************** Sinner ************************
    public static String SELECT_MATERIAL = "Select Material";
}

