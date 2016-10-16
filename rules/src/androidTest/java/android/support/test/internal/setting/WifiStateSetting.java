package android.support.test.internal.setting;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;

import static android.support.test.InstrumentationRegistry.getTargetContext;

public class WifiStateSetting implements DeviceSetting {

  private final WifiManager wifiManager;
  private final boolean wifiCurrentState;

  public WifiStateSetting() {
    wifiManager = (WifiManager) getTargetContext().getSystemService(Context.WIFI_SERVICE);
    wifiCurrentState = wifiManager.isWifiEnabled();
  }

  @Override
  public void apply() {
    wifiManager.setWifiEnabled(true);
  }

  @Override
  public void reset() {
    wifiManager.setWifiEnabled(wifiCurrentState);
  }
}
