package com.donarae.ussd;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;

import java.util.List;

import androidx.annotation.RequiresApi;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(
    name = "Ussd",
    permissions = {
        @Permission(strings = { Manifest.permission.CALL_PHONE }, alias = "call"),
        @Permission(strings = { Manifest.permission.READ_PHONE_STATE }, alias = "phone")
    }
)
public class UssdPlugin extends Plugin {

    @PluginMethod
    public void isAvailable(PluginCall call) {
        JSObject ret = new JSObject();
        boolean available = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
        ret.put("available", available);
        if (!available) {
            ret.put("reason", "USSD requires Android 8.0+");
        }
        call.resolve(ret);
    }

    @PluginMethod
    public void listSims(PluginCall call) {
        if (getPermissionState("phone") != com.getcapacitor.PermissionState.GRANTED) {
            call.reject("READ_PHONE_STATE permission denied");
            return;
        }
        resolveSims(call);
    }

    private void resolveSims(PluginCall call) {
        try {
            SubscriptionManager manager = (SubscriptionManager)
                getContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> active = manager.getActiveSubscriptionInfoList();
            com.getcapacitor.JSArray sims = new com.getcapacitor.JSArray();
            if (active != null) {
                for (SubscriptionInfo info : active) {
                    JSObject sim = new JSObject();
                    sim.put("subId", info.getSubscriptionId());
                    sim.put("slot", info.getSimSlotIndex());
                    sim.put("carrierName", info.getCarrierName() == null ? "" : info.getCarrierName().toString());
                    sims.put(sim);
                }
            }
            JSObject ret = new JSObject();
            ret.put("sims", sims);
            call.resolve(ret);
        } catch (SecurityException e) {
            call.reject("READ_PHONE_STATE permission denied: " + e.getMessage());
        } catch (Exception e) {
            call.reject("Unable to enumerate SIM subscriptions: " + e.getMessage());
        }
    }

    @PluginMethod
    public void send(PluginCall call) {
        String code = call.getString("code");
        if (code == null || code.isEmpty()) {
            call.reject("Missing code");
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            call.reject("USSD requires Android 8.0+");
            return;
        }
        if (getPermissionState("call") != com.getcapacitor.PermissionState.GRANTED) {
            requestPermissionForAlias("call", call, "permsCallback");
            return;
        }
        doSend(call, code);
    }

    @com.getcapacitor.annotation.PermissionCallback
    private void permsCallback(PluginCall call) {
        if (getPermissionState("call") == com.getcapacitor.PermissionState.GRANTED) {
            doSend(call, call.getString("code"));
        } else {
            call.reject("Permission denied");
        }
    }

    private void doSend(PluginCall call, String code) {
        TelephonyManager tm = (TelephonyManager)
            getContext().getSystemService(Context.TELEPHONY_SERVICE);
        Integer subId = call.getInt("subId");
        if (subId != null) {
            tm = tm.createForSubscriptionId(subId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendUssdRequest(call, tm, code);
        } else {
            call.reject("USSD requires Android 8.0+");
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void sendUssdRequest(PluginCall call, TelephonyManager tm, String code) {
        try {
            tm.sendUssdRequest(code, new TelephonyManager.UssdResponseCallback() {
                @Override
                public void onReceiveUssdResponse(TelephonyManager t, String req, CharSequence resp) {
                    JSObject ret = new JSObject();
                    ret.put("response", resp.toString());
                    call.resolve(ret);
                }

                @Override
                public void onReceiveUssdResponseFailed(TelephonyManager t, String req, int failure) {
                    call.reject("USSD failed, code " + failure);
                }
            }, new Handler(Looper.getMainLooper()));
        } catch (SecurityException e) {
            call.reject("Security error: " + e.getMessage());
        }
    }
}