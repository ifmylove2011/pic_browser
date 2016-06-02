package com.xter.picbrowser.element;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XTER on 2016/1/12.
 * 图片资源文件类
 */
public class Photo implements Parcelable {
	private long id;
	private String name;
	private String path;
	private int width;
	private long dateAdded;
	private long dateModified;
	private int height;
	private int size;

	public Photo() {
	}

	/**
	 * @param id           唯一识别ID
	 * @param name         文件名称
	 * @param path         文件路径
	 * @param width        分辨率大小（长）
	 * @param dateAdded    添加日期
	 * @param dateModified 修改日期
	 * @param height       分辨率大小（宽）
	 * @param size         文件大小
	 */
	public Photo(long id, String name, String path, int width, long dateAdded, long dateModified, int height, int size) {
		this.id = id;
		this.name = name;
		this.path = path;
		this.width = width;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.height = height;
		this.size = size;
	}

	protected Photo(Parcel in) {
		id = in.readLong();
		name = in.readString();
		path = in.readString();
		width = in.readInt();
		dateAdded = in.readLong();
		dateModified = in.readLong();
		height = in.readInt();
		size = in.readInt();
	}

	public static final Creator<Photo> CREATOR = new Creator<Photo>() {
		@Override
		public Photo createFromParcel(Parcel in) {
			return new Photo(in);
		}

		@Override
		public Photo[] newArray(int size) {
			return new Photo[size];
		}
	};

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(long dateAdded) {
		this.dateAdded = dateAdded;
	}

	public long getDateModified() {
		return dateModified;
	}

	public void setDateModified(long dateModified) {
		this.dateModified = dateModified;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeString(path);
		dest.writeInt(width);
		dest.writeLong(dateAdded);
		dest.writeLong(dateModified);
		dest.writeInt(height);
		dest.writeInt(size);
	}
}

