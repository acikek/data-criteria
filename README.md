# Data Criteria

Data-driven advancement criteria.

## Example

1. Create a criterion file. `parameters` is an **ordered list** of parameter names and types to pass in and check for. Make sure to group optional parameters at the end.

`data/namespace/criteria/int_and_bool.json`
```json
{
  "parameters": [
    {
      "name": "number",
      "type": "datacriteria:int"
    },
    {
      "name": "possible",
      "type": "datacriteria:bool",
      "optional": true
    }
  ]
}
```

2. Use the criterion within an advancement file.

```json5
// ...
"criteria": {
  "test": {
    "trigger": "namespace:int_and_bool",
    "conditions": {
      "number": 5,
      // As 'possible' is optional, it's not required in the conditions
    }
  }
}
```

3. Trigger the criterion through `DataCriteriaAPI`.

```java
DataCriteriaAPI.trigger(
    new Identifier("namespace:int_and_bool"),
    serverPlayer,
    // The parameters begin here
    10, true
);
```

See the [wiki](https://github.com/acikek/data-criteria/wiki) for complete documentation.

## License

MIT © 2022 spadeteam
