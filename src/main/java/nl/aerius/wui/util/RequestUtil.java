package nl.aerius.wui.util;

import java.util.Map;
import java.util.function.Function;

import com.google.gwt.user.client.rpc.AsyncCallback;

import elemental2.dom.FormData;
import elemental2.dom.XMLHttpRequest;

import nl.aerius.wui.service.exception.RequestClientException;

public class RequestUtil {
  /** GET **/

  public static <T> void doGet(final String url, final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doGet(url, null, parser, callback);
  }

  public static <T> void doGet(final String url, final Map<String, String> queryString,
      final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doGet(url, queryString, parser.apply(callback));
  }

  public static <T> void doGet(final String url, final AsyncCallback<String> callback) {
    doGet(url, (Map<String, String>) null, callback);
  }

  public static <T> void doGet(final String url, final Map<String, String> queryString, final AsyncCallback<String> callback) {
    doRequest("GET", url + format(queryString), callback);
  }

  /** POST **/

  public static <T> void doPost(final String url, final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doPost(url, null, parser, callback);
  }

  public static void doPost(final String url, final AsyncCallback<String> callback) {
    doPost(url, (FormData) null, callback);
  }

  public static <T> void doPost(final String url, final FormData payload, final Function<AsyncCallback<T>, AsyncCallback<String>> parser,
      final AsyncCallback<T> callback) {
    doPost(url, payload, parser.apply(callback));
  }

  public static <T> void doPost(final String url, final FormData payload, final AsyncCallback<String> callback) {
    doRequest("POST", url, payload, callback);
  }

  /** DELETE **/

  public static <T> void doDelete(final String url, final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doDelete(url, null, parser, callback);
  }

  public static <T> void doDelete(final String url, final FormData payload, final Function<AsyncCallback<T>, AsyncCallback<String>> parser,
      final AsyncCallback<T> callback) {
    doDelete(url, payload, parser.apply(callback));
  }

  public static <T> void doDelete(final String url, final FormData payload, final AsyncCallback<String> callback) {
    doRequest("DELETE", url, callback);
  }

  /** REQUEST **/

  private static void doRequest(final String method, final String url, final AsyncCallback<String> callback) {
    doRequest(method, url, null, callback);
  }

  private static void doRequest(final String method, final String url, final FormData payload, final AsyncCallback<String> callback) {
    final XMLHttpRequest req = new XMLHttpRequest();

    req.addEventListener("error", evt -> {
      handleError(callback, req.responseText);
    });
    req.addEventListener("load", evt -> {
      if (req.status != 200) {
        handleError(callback, req.responseText);
      } else {
        callback.onSuccess(req.responseText);
      }
    });

    req.open(method, url);
    req.send(payload);
  }

  private static void handleError(final AsyncCallback<String> callback, final String responseText) {
    callback.onFailure(new RequestClientException(responseText));
  }

  private static String format(final Map<String, String> queryString) {
    final StringBuilder bldr = new StringBuilder("?");
    if (queryString != null) {
      queryString.forEach((k, v) -> {
        bldr.append(k + "=" + v + "&");
      });
    }

    // Prune the last (either a & or ?)
    bldr.setLength(bldr.length() - 1);

    return bldr.toString();
  }

  public static String prepareUrl(final String host, final String template, final String... args) {
    if (args.length % 2 != 0) {
      throw new RuntimeException("Template args are of incorrect size: " + args.length);
    }

    String bldr = host + template;
    for (int i = 0; i < args.length; i += 2) {
      if (args[i] == null || args[i + 1] == null) {
        continue;
      }

      bldr = bldr.replaceAll(args[i], args[i + 1]);
    }

    return bldr;
  }
}
