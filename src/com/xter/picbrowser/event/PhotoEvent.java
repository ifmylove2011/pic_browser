package com.xter.picbrowser.event;

import com.xter.picbrowser.element.Photo;

import java.util.List;

/**
 * Created by XTER on 2016/9/19.
 * 事件
 */
public class PhotoEvent {

	List<Photo> pics;
	int position;
	String folderName;

	public PhotoEvent(List<Photo> pics, int position, String folderName) {
		this.pics = pics;
		this.position = position;
		this.folderName = folderName;
	}

	public List<Photo> getPics() {
		return pics;
	}

	public void setPics(List<Photo> pics) {
		this.pics = pics;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
