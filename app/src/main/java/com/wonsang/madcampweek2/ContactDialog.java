package com.wonsang.madcampweek2;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;

public class ContactDialog extends AlertDialog.Builder {
    private final ApiCallable apiCallable;
    private final int id;
    private final ApiProvider apiProvider;
    private final Contact contact;
    private final int pos;
    private final int themeId;

    public int getpos() {
        return pos;
    }

    public ContactDialog(@NonNull Context context, Contact contact, int id, int pos, ApiCallable apiCallable, int themeId) {
        super(context);
        this.id = id;
        this.pos = pos;
        this.contact = contact;
        this.apiProvider = new ApiProvider(context);
        this.apiCallable =apiCallable;
        this.themeId = themeId;
    }


}
