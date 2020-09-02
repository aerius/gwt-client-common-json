# GWT Json Parser Wrapper

This is a simple GWT JSON Parser wrapper library adding syntactic sugar for parsing JSON using the ordinary GWT JSON parsing utilities.

## Installation

Add the maven dependency to your pom's `<depencencies>` section:

```xml
    <dependency>
      <groupId>nl.aerius</groupId>
      <artifactId>gwt-client-common-json</artifactId>
      <version>1.0</version>
    </dependency>
```

Add the following line to your GWT Module:

```xml
  <inherits name='nl.overheid.aerius.Json' />
```

## Usage

Given the following simple JSON String:

```JSON
{
  "foo": "bar"
}
```

The bar value can be retrieved as follows:

```java
// Define the above JSON String
String json = "{\"foo\":\"bar\"}";
JSONObjectHandle handle = JSONObjectHandle.from(json);

// Retrieve the 'foo' field
String foo = handle.getString("foo");
```

Fetching array data is similarly made simple, given:

```json
{
  "list": ["foo", "bar", "baz"]
}
```

The list values can be iterated over:

```java
String json = "{\"list\":[\"foo\",\"bar\",\"baz\"]}";
JSONObjectHandle handle = JSONObjectHandle.fromText(json);

// Iterate over array values
handle.getArray("list")
  .forEachString(GWT::log);
```

Or for a complex array:


```json
{
  "list": [
            { "value", "foo" },
            { "value", "bar" },
            { "value", "baz" }
          ]
}

```

The `value` for each respective item can be retrieved as follows:

```java
String json = "{\"list\": [{ \"value\", \"foo\" },{ \"value\", \"bar\" },{ \"value\", \"baz\" }]}";
JSONObjectHandle handle = JSONObjectHandle.fromText(json);

// Fetch the 'value' field for each object
handle.getArray("list")
  .forEach(obj -> GWT.log(obj.getString("value")));
```
