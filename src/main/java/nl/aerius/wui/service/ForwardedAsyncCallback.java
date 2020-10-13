package nl.aerius.wui.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ForwardedAsyncCallback<F, C> implements AsyncCallback<C> {
  protected AsyncCallback<F> callback;

  public ForwardedAsyncCallback(final AsyncCallback<F> callback) {
    this.callback = callback;
  }

  @Override
  public void onFailure(final Throwable caught) {
    callback.onFailure(caught);
  }

  @Override
  public void onSuccess(final C result) {
    F res = null;

    try {
      res = convert(result);
    } catch (final Exception e) {
      onFailure(e);
    }

    callback.onSuccess(res);
  }

  public abstract F convert(C content);
}
