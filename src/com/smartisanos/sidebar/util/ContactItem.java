package com.smartisanos.sidebar.util;

import java.util.ArrayList;
import java.util.List;

import com.smartisanos.sidebar.PendingDragEventTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;

public abstract class ContactItem extends SidebarItem {
    protected Context mContext;
    protected Drawable mAvatar;
    protected Bitmap mAvatarWithGray;
    protected CharSequence mDisplayName;

    public ContactItem(Context context, Bitmap avatar, CharSequence displayName) {
        mContext = context;
        mAvatar = new BitmapDrawable(context.getResources(), avatar);
        mDisplayName = displayName;
    }

    public Drawable getAvatar() {
        return mAvatar;
    }

    public CharSequence getDisplayName() {
        return mDisplayName;
    }

    public final void delete(){
        ContactManager.getInstance(mContext).remove(this);
    }

    /**
     * this method can only be called by ContactManger. don't invoke it in other place!
     * */
    public abstract void deleteFromDatabase();
    public abstract void save();
    public abstract int getTypeIcon();
    public abstract String getPackageName();
    public abstract boolean sameContact(ContactItem ci);
    public static List<ContactItem> getContactList(Context context){
        List<ContactItem> all = new ArrayList<ContactItem>();
        all.addAll(DingDingContact.getContacts(context));
        all.addAll(WechatContact.getContacts(context));
        all.addAll(MmsContact.getContacts(context));
        all.addAll(MailContact.getContacts(context));
        return all;
    }

    @Override
    public boolean handleDragEvent(Context context, DragEvent event) {
        if(PendingDragEventTask.tryPending(mContext, event, this)){
            return true;
        }
        return false;
    }
}
