package com.xter.picbrowser.event;

import com.xter.picbrowser.element.Folder;

/**
 * Created by XTER on 2016/9/19.
 * 事件
 */
public class FolderEvent {

	Folder folder;

	public FolderEvent(Folder folder) {
		this.folder = folder;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
}
