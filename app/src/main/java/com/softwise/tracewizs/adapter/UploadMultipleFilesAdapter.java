package com.softwise.tracewizs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.models.UploadFiles;
import com.softwise.tracewizs.view_holder.UploadMultiFileViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UploadMultipleFilesAdapter extends RecyclerView.Adapter<UploadMultiFileViewHolder> {
    List<UploadFiles> mUploadFilesList = new ArrayList<>();
    OnFileSelectListeners mOnFileSelectListeners;
    private Context mContext;

    public UploadMultipleFilesAdapter(Context context, List<UploadFiles> uploadFiles, OnFileSelectListeners fileSelectListeners) {
        this.mContext = context;
        this.mUploadFilesList = uploadFiles;
        this.mOnFileSelectListeners = fileSelectListeners;
    }
    public UploadMultipleFilesAdapter(Context context, List<UploadFiles> uploadFiles) {
        this.mContext = context;
        this.mUploadFilesList = uploadFiles;
    }

    @NotNull
    @Override
    public UploadMultiFileViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new UploadMultiFileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_extra_doc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull UploadMultiFileViewHolder holder, int position) {
        UploadFiles uploadFiles = mUploadFilesList.get(position);
        holder.txtFileName.setText("File " + position + 1);
        if(uploadFiles.getFileName()!=null) {
            Glide.with(mContext).load(uploadFiles.getFilePath() + "/" + uploadFiles.getFileName()).apply(new RequestOptions().centerCrop().circleCrop()).into(holder.imgExtraDoc);
        }else {
            Glide.with(mContext).load(uploadFiles.getFilePath()).apply(new RequestOptions().centerCrop().circleCrop()).into(holder.imgExtraDoc);
        }
        holder.imgFileRemove.setOnClickListener(v -> {
            mOnFileSelectListeners.deleteFile(uploadFiles,position);
        });
    }

    @Override
    public int getItemCount() {
        return mUploadFilesList.size();
    }


    public interface OnFileSelectListeners {
        void fileSelect(UploadFiles uploadFiles);
        void deleteFile(UploadFiles uploadFiles,int pos);
    }

}
