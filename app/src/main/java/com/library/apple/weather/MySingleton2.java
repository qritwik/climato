package com.library.apple.weather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ritwik on 10/3/2017.
 */

public class MySingleton2 {

    private static MySingleton2 mySingleton;
    private RequestQueue requestQueue;

    private static Context ctx;
    private MySingleton2(Context context)
    {
        ctx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton2 getMySingleton(Context context)
    {
        if(mySingleton==null)
        {
            mySingleton= new MySingleton2(context);
        }
        return mySingleton;

    }
    public<T> void addToRequestque(Request<T> request)
    {
        requestQueue.add(request);
    }
}
