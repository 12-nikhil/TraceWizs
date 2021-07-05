package com.softwise.tracewizs.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwise.tracewizs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadMultiFileViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgExtraDoc;

    public TextView txtFileName;

    public ImageView imgFileRemove;

    public UploadMultiFileViewHolder(@NonNull View itemView) {
        super(itemView);
        imgExtraDoc = itemView.findViewById(R.id.img_extra_doc);
        imgFileRemove = itemView.findViewById(R.id.img_extra_doc_remove);
        txtFileName = itemView.findViewById(R.id.txt_file_name);
    }
}
