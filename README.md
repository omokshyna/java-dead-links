# Step 1
Please suggest a PR with a java code implementing the below contract. Remember to implement a test verifying the output format also.

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

It is ok to use YML/XML or any other format considering the test verifying this format is available.
