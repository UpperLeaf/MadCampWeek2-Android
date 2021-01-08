package com.wonsang.madcampweek2;

import android.content.Context;
import android.content.Intent;

public interface OAuthLogin {
    Intent login(Context context, String clientId);
    void logout(Context context, String clientId);
}
