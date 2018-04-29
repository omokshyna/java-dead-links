# Java dead links

## Input arguments
The program expects target url to take HTML(and check for dead links) from.

## Output format

```
{
    "url": "<input_url_tocheck>"
    "404":  {
        "size": 3,
	"urls": ["url1", "url2", "url3"]
    },
    "50x": {
        "size": 1,
	"urls" ["url4", ]
    }
    "dead": 4,
    "total": 10
}
```

Please suggest a PR with a java code implementing the above contract. Remember to implement also a test verifying the output format.
