package nl.aerius.wui.future;

import nl.aerius.wui.service.BaseRequestCallback;

public abstract class SimpleDebuggableRequest extends BaseRequestCallback implements DebuggableRequest {
  protected String origin;

  @Override
  public void setRequestOrigin(final String origin) {
    this.origin = origin;
  }
}
