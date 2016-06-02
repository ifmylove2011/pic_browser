package com.xter.picbrowser.element;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XTER on 2016/1/12.
 * 图片文件夹类（父级目录）
 */
public class Folder implements Parcelable {
	private long folderId;
	private String folderName;
	private int imgCount;
	private String[] coverUris;
	private List<Photo> photos;

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}

	public String[] getCoverUris() {
		return coverUris;
	}

	public void setCoverUris(String[] coverUris) {
		this.coverUris = coverUris;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public static Creator<Folder> getCREATOR() {
		return CREATOR;
	}

	public Folder() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.folderId);
		dest.writeString(this.folderName);
		dest.writeInt(this.imgCount);
		dest.writeStringArray(this.coverUris);
		dest.writeTypedList(photos);
	}

	protected Folder(Parcel in) {
		this.folderId = in.readLong();
		this.folderName = in.readString();
		this.imgCount = in.readInt();
		this.coverUris = in.createStringArray();
		this.photos = in.createTypedArrayList(Photo.CREATOR);
	}

	public static final Creator<Folder> CREATOR = new Creator<Folder>() {
		public Folder createFromParcel(Parcel source) {
			return new Folder(source);
		}

		public Folder[] newArray(int size) {
			return new Folder[size];
		}
	};
}
