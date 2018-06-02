package com.example.canma.eurekaswe.components;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.pchmn.materialchips.model.ChipInterface;


public class ContactChip implements ChipInterface {
    public String id;
    public Uri avatarUri;
    public String name;
    public String phoneNumber;

    public ContactChip(String id, Uri avatarUri, String name, String phoneNumber) {
        this.id = id;
        this.avatarUri = avatarUri;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Uri getAvatarUri() {
        return avatarUri;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public String getInfo() {
        return phoneNumber;
    }
}